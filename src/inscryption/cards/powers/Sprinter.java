package inscryption.cards.powers;

import inscryption.cards.Animal;
import inscryption.game.Board;

public class Sprinter extends Power
{
    public Sprinter(){} 

    public Sprinter copy()
    {
        return new Sprinter();
    }

    @Override
    public void onPostAttack(Animal owner, Board board, int position)
    {
        if(board.isTargetPositionEmpty(owner, position + 1))
        {
            board.moveCard(owner, position + 1);
        }
        else if(board.isTargetPositionEmpty(owner, position -1))
        {
            board.moveCard(owner, position - 1);
        }
    }

    @Override
    public String getName(){return "Sprinter";}
}
