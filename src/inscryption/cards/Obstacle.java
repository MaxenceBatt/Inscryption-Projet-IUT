//Cette classe est un enfant de la classe "Card".
package inscryption.cards; //Cette classe se trouve dans le package "inscryption.cards".


import inscryption.game.Board;

public class Obstacle extends Card  // Déclaration de la classe publique "Obstacle" qui hérite de la classe parente "Card"
{
    public Obstacle(String name, int hp)//Constructeur de la classe "Obstacle"
    {
        super(name, hp); //Hérite des attributs de "Card"
    }

    public Obstacle(Obstacle original)
    {
        super(original.getName(), original.getHp());
    }

    @Override
    public Obstacle copy()
    {
        return new Obstacle(this);
    }

    @Override
    public int performAttack(Card targetCard)
    {
        return 0;
    }

    @Override
    public boolean canBeSacrificed()
    {
        return false;
    }

    @Override
    public void activateTurnStartPowers(Board board) {}

    @Override
    public boolean activateSacrificePowers(Board board) {
        return false;
    }

    @Override
    public int getTotalAttackReduction() {
        return 0;
    }

    @Override
    public void activatePostAttackPowers(Board board) {}

    @Override
    public void takeLethalDamage() {}

    @Override
    public void activateDamageDealtPowers(Card targetCard) {}

    @Override
    public void activateOnAttackedPowers(Card attacker) {}
}
