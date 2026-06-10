package inscryption.cards.powers;

import inscryption.cards.Animal;
import inscryption.cards.Card;
import inscryption.game.Board;

public abstract class Power
{
    public void onTurnStart(Animal owner, Board board) {}
    public abstract Power copy();
    public boolean onSacrifice(Animal owner, Board board) {
        return false;
    }
    public int getAttackReduction() {
        return 0;
    }
    public void onPostAttack(Animal owner, Board board, int position) {}
    public void onDamageDealt  (Card targetCard) {}
    public void onAttacked (Card attaker) {}
    abstract public String getName();
}
