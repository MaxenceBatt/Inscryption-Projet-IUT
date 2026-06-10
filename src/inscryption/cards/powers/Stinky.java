package inscryption.cards.powers;

public class Stinky extends Power {
    public Stinky() {}

    public Stinky copy() { return new Stinky(); }

    @Override
    public int getAttackReduction() { return 1; }

    @Override
    public String getName(){return "Stinky";}
}