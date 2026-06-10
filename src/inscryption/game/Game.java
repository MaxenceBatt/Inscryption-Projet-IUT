package inscryption.game;

import inscryption.cards.Animal;
import inscryption.cards.Card;
import inscryption.cards.CardFactory;
import inscryption.cards.Obstacle;
import inscryption.cards.powers.Power;
import inscryption.ui.ConsoleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.spi.CalendarDataProvider;

public class Game
{
    private int m_scoreDifference;
    private Player m_player;
    private Opponent m_opponent;
    private Board m_gameBoard;
    private ConsoleView m_view;

    public Game()
    {
        m_scoreDifference = 0;
        m_player = new Player();
        m_opponent = new Opponent();
        m_gameBoard = new Board();
        m_view = new ConsoleView();
    }


    public boolean checkGameOver()
    {
        return (this.m_scoreDifference >= 5 || this.m_scoreDifference <= -5);
    }

    public void playPlayerTurn()
    {
        boolean tourFini = false;
        boolean hasDrawn = false;

        while(!tourFini)
        {
            m_view.displayBoard(m_gameBoard, this.m_scoreDifference, m_player.getDrawPileSize(),m_player.getBones());
            m_view.displayHand(m_player);

            String action = m_view.askForAction();
            String temp[] = action.split(" ");

            try
            {
                // On tente de convertir le premier mot en Enum
                PlayerAction actionChoisie = PlayerAction.valueOf(temp[0].toUpperCase());

                switch(actionChoisie)
                {
                    case FIN:
                        tourFini = true; // Quitte la boucle while
                        break;

                    case PIOCHER:
                        if(!hasDrawn)
                        {
                            if(!this.m_player.drawCard())
                            {
                                this.m_view.displayMessage("Votre pioche est vide.");
                            }
                            hasDrawn = true;
                        }
                        else
                        {
                            this.m_view.displayMessage("Vous ne pouvez piocher qu'une seule fois par tour");
                        }
                        break;

                    case PLACER:
                        // Sécurité : vérifier que le joueur a bien tapé 3 mots (ex : placer 2 B1)
                        if(temp.length != 3) {
                            this.m_view.displayMessage("Format invalide. Utilisez : placer <numero> <position>");
                            break;
                        }

                        int cardNumber = Integer.parseInt(temp[1]) - 1; // Ajustement de l'index
                        int boardPosition = -1; // -1 par défaut pour vérifier s'il se trompe

                        // On gère la position (avec toUpperCase au cas où il tape b1 au lieu de B1).
                        switch(temp[2].toUpperCase())
                        {
                            case "B1" : boardPosition = 0; break;
                            case "B2" : boardPosition = 1; break;
                            case "B3" : boardPosition = 2; break;
                            case "B4" : boardPosition = 3; break;
                            default: this.m_view.displayMessage("Position invalide (utilisez B1, B2, B3 ou B4)."); break;
                        }

                        // Si la position est bonne, on place la carte
                        if (boardPosition != -1) {
                            Card carteChoisie = this.m_player.getCardFromHand(cardNumber); // methode à part
                            Animal animalChoisi = (Animal) carteChoisie;

                            if (this.m_player.canAfford(animalChoisi, this.m_gameBoard)) {
                                Card carteExistante = this.m_gameBoard.getPlayerCard(boardPosition);

                                if (carteExistante == null) {
                                    this.handleSacrifice(animalChoisi.getBloodCost());
                                    this.m_player.playCard(animalChoisi);
                                    this.m_gameBoard.placePlayerCard(animalChoisi, boardPosition);
                                    this.m_view.displayMessage(animalChoisi.getName() + " a été placé en " + temp[2].toUpperCase() + " !");
                                }
                                else if (animalChoisi.getBloodCost() > 0 && carteExistante.canBeSacrificed()) {
                                    boolean survit = carteExistante.activateSacrificePowers(this.m_gameBoard);

                                    if (!survit) {
                                        this.m_gameBoard.clearPlayerPosition(boardPosition);
                                    }
                                    this.m_player.addBone();
                                    this.m_view.displayMessage("La carte " + carteExistante.getName() + " a été sacrifiée d'office !");

                                    int sangRestant = animalChoisi.getBloodCost() - 1;

                                    if (sangRestant > 0) {
                                        this.handleSacrifice(sangRestant);
                                    }

                                    this.m_player.playCard(animalChoisi);
                                    this.m_gameBoard.placePlayerCard(animalChoisi, boardPosition);
                                    this.m_view.displayMessage(animalChoisi.getName() + " a été placé en " + temp[2].toUpperCase() + " !");
                                }
                                else {
                                    this.m_view.displayMessage("Cet emplacement est déjà occupé ! Action annulée.");
                                }
                            }
                            else {
                                this.m_view.displayMessage("Vous n'avez pas les ressources nécessaires pour jouer cette carte.");
                            }
                        }
                        break;
                }
            }
            catch (IllegalArgumentException e)
            {
                // Si le mot n'existe pas dans l'énumération
                this.m_view.displayMessage("Action inconnue. Veuillez taper 'fin', 'piocher' ou 'placer'.");
            }
            catch (IndexOutOfBoundsException e)
            {
                // Si le joueur donne un numéro de carte qui n'est pas dans sa main (ex : 9 alors qu'il a 4 cartes).
                this.m_view.displayMessage("Numéro de carte invalide.");
            }
        }
    }


    public void resolvePlayerAttack() // à déplacer dans gameBoard
    {
        for(int i=0; i<4; i++)
        {
            int damage = 0;
            Card playerCard = this.m_gameBoard.getPlayerCard(i);
            if(playerCard != null)
            {
                int attack = playerCard.getAttack();
                Card targetCard = this.m_gameBoard.getOpponentCard(i);
                damage = playerCard.performAttack(targetCard);
                playerCard.activatePostAttackPowers(this.m_gameBoard);
                if(targetCard != null && targetCard.isDead())
                {
                    this.m_gameBoard.clearOpponentPosition(i);
                    targetCard = null;
                }
            }
            this.m_scoreDifference += damage;
        }
    }

    public void resolveOpponentAttack()
    {
        for(int i=0; i<4; i++)
        {
            int damage = 0;
            Card opponentCard = this.m_gameBoard.getOpponentCard(i);
            if(opponentCard != null)
            {
                Card targetCard = this.m_gameBoard.getPlayerCard(i);
                damage = opponentCard.performAttack(targetCard);
                opponentCard.activatePostAttackPowers(this.m_gameBoard);
                if(targetCard != null && targetCard.isDead())
                {
                    this.m_gameBoard.clearPlayerPosition(i);
                    targetCard = null;
                    this.m_player.addBone();
                }
            }
            this.m_scoreDifference -= damage;
        }
    }

    public void handleSacrifice(int bloodCost)
    {
        int cost = bloodCost;
        while(bloodCost > 0)
        {
            int position = this.m_view.askForSacrificePosition(cost, cost-bloodCost);
            Card card = this.m_gameBoard.getPlayerCard(position);
            if(card != null && card.canBeSacrificed())
            {
                if(!card.activateSacrificePowers(this.m_gameBoard))
                {
                    this.m_gameBoard.clearPlayerPosition(position);
                }
                this.m_player.addBone();
                bloodCost--;
            }
            else
            {
                this.m_view.displayInvalidSacrificeError();
            }
        }
    }

    public void initObstacle(int pourcentageChance)
    {
        Random rand = new Random();
        for(int i=0; i<4; i++)
        {
            int proba = rand.nextInt(100);
            if(proba < pourcentageChance)
            {
                int probaObstacle = rand.nextInt(2);
                Obstacle obstacle;
                if(probaObstacle == 0)
                {
                    obstacle = CardFactory.createRocher();
                }
                else
                {
                    obstacle = CardFactory.createSapin();
                }
                int cote = rand.nextInt(2);
                if(cote == 0)
                {
                    this.m_gameBoard.placePlayerCard(obstacle, i);
                }
                else
                {
                    this.m_gameBoard.placeOpponentCard(obstacle, i);
                }
            }
        }
    }

    public void startGame()
    {
        this.m_player.initDeck();
        this.m_opponent.initDeck();

        for (int manche = 1; manche <= 3; manche++)
        {
            // Préparation de la nouvelle manche
            this.m_player.prepareTurn();
            this.m_opponent.prepareTurn();
            this.m_scoreDifference = 0;
            this.m_gameBoard = new Board();
            this.initObstacle(50); //

            // Boucle du match en cours
            boolean isGameOver = false;
            while(!isGameOver)
            {
                this.m_opponent.prepareNextTurn(this.m_gameBoard); //

                activatePlayerPowers();
                playPlayerTurn(); //
                resolvePlayerAttack(); //

                activateOpponentPowers();
                this.m_opponent.playTurn(this.m_gameBoard); //
                resolveOpponentAttack(); //
                isGameOver = checkGameOver(); //
            }

            // Fin de la manche
            if(this.m_scoreDifference <= -5)
            {
                this.m_view.displayMessage("GAME OVER \n Vous avez perdu à la manche numéro " + manche + ".");
                return;
            }
            if(manche < 3)
            {
                this.chooseNewCard();
                this.useSacrificeStone();
            }
        }
    }

    public void chooseNewCard()
    {
        Animal animal1 = CardFactory.createRandomAnimal();
        Animal animal2 = CardFactory.createRandomAnimal();
        this.m_view.displayMessage("\nChoisissez une nouvelle carte pour votre deck (tapez 1 ou 2) :");
        this.m_view.displayAnimal(animal1, 1);
        this.m_view.displayAnimal(animal2, 2);

        int choix = this.m_view.askForChoice();

        switch(choix)
        {
            case 1: this.m_player.addCardToDeck(animal1);
                    this.m_view.displayMessage(animal1.getName() + " a bien été ajouté au deck !");
                    break;
            case 2: this.m_player.addCardToDeck(animal2);
                    this.m_view.displayMessage(animal2.getName() + " a bien été ajouté au deck !");
                    break;
        }
    }

    public void useSacrificeStone()
    {
        this.m_view.displayMessage("\n--- PIERRE DE SACRIFICE ---");
        this.m_view.displayDeck(this.m_player);

        this.m_view.displayMessage("Entrez le numéro de la carte à sacrifier :");
        int deckSize = this.m_player.getDeck().size();
        int sacrificeChoice = this.m_view.askForDeckIndex(deckSize);

        Animal sacrificedAnimal = (Animal) this.m_player.getDeck().get(sacrificeChoice - 1);
        List<Power> savedPowers = sacrificedAnimal.getPowers();

        this.m_view.displayMessage("Entrez le numéro de la carte qui recevra le pouvoir : ");
        int newDeckSize = this.m_player.getDeck().size();
        int targetChoice = this.m_view.askForDeckIndex(newDeckSize);

        while (targetChoice == sacrificeChoice) {
            this.m_view.displayMessage("Vous ne pouvez pas choisir la même carte ! Entrez un autre numéro : ");
            targetChoice = this.m_view.askForDeckIndex(newDeckSize);
        }

        Animal targetAnimal = (Animal) this.m_player.getDeck().get(targetChoice - 1);

        this.m_player.removeCardFromDeck(sacrificedAnimal);

        for(Power power: savedPowers)
        {
            targetAnimal.addPower(power.copy());
        }
        this.m_view.displayMessage("Le(s) pouvoir(s) de " + sacrificedAnimal.getName() + " a/ont bien été transféré(s) vers " + targetAnimal.getName() + " !");
    }

    private void activatePlayerPowers() {
        for (int i = 0; i < 4; i++) {
            Card card = this.m_gameBoard.getPlayerCard(i);
            if (card != null) {
                card.activateTurnStartPowers(this.m_gameBoard);
            }
        }
    }

    private void activateOpponentPowers() {
        for (int i = 0; i < 4; i++) {
            Card card = this.m_gameBoard.getOpponentCard(i);
            if (card != null) {
                card.activateTurnStartPowers(this.m_gameBoard);
            }
        }
    }

    //Getters utiles pour les tests unitaires
    public int getScoreDifference() { return m_scoreDifference; }
    public Board getGameBoard() { return m_gameBoard; }
    public Player getPlayer() { return m_player; }
}
