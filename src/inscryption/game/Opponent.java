package inscryption.game;//Cette classe se trouve dans le package "inscryption.game".

import inscryption.cards.Animal;
import inscryption.cards.Card; //importe la classe "Card"

public class Opponent extends Player //Déclaration de la classe publique "Opponent"
{
    public Opponent()
    {
        super();
    } //Récupère les attributs de "Player"

    public void prepareNextTurn(Board board) //Fonction qui prépare le prochain tour
    {
        if(!this.isHandEmpty())
        {
            this.drawCard(); //Tire une carte
            Card card = this.getLastCard(); //Récupère la dernière carte
            for(int i=0; i<4; i++)//Pour chaque case
            {
                if(board.placeOpponentIntention(card, i))// Si la case est vide on place la carte
                {
                    this.removeCardFromHand(card); //Enlever la carte de la main
                    return;
                }
            }
        }
    }

    public void playTurn(Board board)
    {
        if(!this.isHandEmpty())
        {
            this.drawCard();
        }
        for(int i=0; i<4; i++)
        {
            Card card = board.getOpponentIntentionsCard(i);
            if(card != null)
            {
                boolean result = board.placeOpponentCard(card, i);
                if(result)
                {
                    board.clearIntentionPosition(i);
                }
            }
        }
    }


    @Override
    public boolean canAfford(Animal animalCard, Board board)
    {
        int animalCards = 0;
        for(int i=0; i<4; i++)
        {
            if(board.getOpponentCard(i) instanceof  Animal)
            {
                animalCards++;
            }
        }
        return (animalCard.getBoneCost() <= this.getBones() && animalCard.getBloodCost() <= animalCards);
    }
}
