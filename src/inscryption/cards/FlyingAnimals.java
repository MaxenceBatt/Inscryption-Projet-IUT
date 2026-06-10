package inscryption.cards;

import inscryption.game.Board;

public class FlyingAnimals extends Animal
{
    public FlyingAnimals(String name, int hp, int attack, int bloodCost, int boneCost)
    {
        super(name, hp, attack, bloodCost, boneCost);
    }

    public FlyingAnimals(FlyingAnimals original)
    {
        super(original);
    }

    @Override
    public int performAttack(Card targetCard)
    {
        return this.getAttack();
    }

    @Override
    public FlyingAnimals copy()
    {
        return new FlyingAnimals(this);
    }

}