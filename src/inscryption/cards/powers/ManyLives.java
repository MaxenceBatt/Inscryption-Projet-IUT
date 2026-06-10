package inscryption.cards.powers;
import inscryption.cards.Animal;
import inscryption.game.Board;

public class ManyLives extends Power {
    public ManyLives() {}

    public ManyLives copy() { return new ManyLives(); }

    @Override
    public boolean onSacrifice(Animal owner, Board board) {
        return true;
    }

    @Override
    public String getName(){return "Many Lives";}
}