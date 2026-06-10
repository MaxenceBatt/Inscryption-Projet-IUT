package inscryption.game; //Cette classe se trouve dans le package inscryption.game

import inscryption.cards.Animal;
import inscryption.cards.Card;
import inscryption.cards.CardFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Player //Déclaration de la classe publique "Player"
{
    private int m_bones;    //Quantité d'os
    private List<Card> m_hand; //Cartes dans la main
    private List<Card> m_deck; //Cartes dans le deck
    private List<Card> m_drawPile;

    public Player() //Constructeur de la classe "Player" pour instancier un nouvel objet
    {
        this.m_bones =  0; // On démarre avec aucun os
        this.m_hand = new ArrayList<>(); //La main démarre vide, car les cartes sont attribués aléatoirement
        this.m_deck = new ArrayList<>(); //Le deck démarre vide, car les cartes sont attribués aléatoirement
        this.m_drawPile = new ArrayList<>();
    }

    public boolean drawCard() //Fonction qui permet d'ajouter une carte à notre main
    {
        if(m_drawPile.isEmpty()) //Si le deck est vide
        {
           return false;
        }
        this.m_hand.add(this.m_drawPile.getFirst()); //Sinon, on ajoute à la main la première carte du deck
        this.m_drawPile.removeFirst(); //On supprime la carte du deck
        return true;
    }

    public void playCard(Card card) //Fonction qui vérifie qu'une carte est jouable
    {
        Animal animalCard = (Animal)card;
        this.m_bones -= animalCard.getBoneCost(); // on réduit la quantité d'os qu'au joueur par le montant nécessaire pour poser la carte
        this.m_hand.remove(card); // on enlève la carte posée de la main
    }

    public Card getLastCard()
    {
        return this.m_hand.getLast();
    } // Fonction qui renvoie la dernière carte de la main (pour opponent)

    public void removeCardFromHand(Card card)
    {
        this.m_hand.remove(card);
    } // Fonction qui supprime la carte de la main

    public void initDeck()
    {
        for(int i=0; i<9; i++)
        {
            this.m_deck.add(CardFactory.createEcureuil());
        }
        for(int i=0; i<6; i++)
        {
            m_deck.add(CardFactory.createRandomAnimal());
        }
        Collections.shuffle(m_deck);
    }

    public int getBones()
    {
        return this.m_bones;
    }

    public boolean canAfford(Animal animalCard, Board board)
    {
        int animalCards = 0;
        for(int i=0; i<4; i++)
        {
            if(board.getPlayerCard(i) != null && board.getPlayerCard(i).canBeSacrificed())
            {
                animalCards++;
            }
        }
        return (animalCard.getBoneCost() <= this.m_bones && animalCard.getBloodCost() <= animalCards);
    }


    public List<Card> getHand() {
        return this.m_hand;
    }

    public void addBone()
    {
        this.m_bones++;
    }

    public void prepareTurn()
    {
        this.m_drawPile.clear();
        for(Card card : this.m_deck)
        {
            this.m_drawPile.add(card.copy());
        }
        Collections.shuffle(this.m_drawPile);
        this.initHand();
    }

    public void addCardToDeck(Card card)
    {
        this.m_deck.add(card);
    }

    public List<Card> getDeck()
    {
        return this.m_deck;
    }

    public void removeCardFromDeck(Card card)
    {
        this.m_deck.remove(card);
    }

    public int getDrawPileSize()
    {
        return this.m_drawPile.size();
    }

    public void initHand()
    {
        this.m_hand.clear();
        for(int i = 0; i < 4; i++)
        {
            this.drawCard();
        }
    }

    public Card getCardFromHand(int index)
    {
        return this.m_hand.get(index);
    }

    public boolean isHandEmpty()
    {
        return this.m_hand.isEmpty();
    }
}
