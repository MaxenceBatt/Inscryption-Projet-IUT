package inscryption.cards.powers;

import inscryption.cards.Card;

public class SharpQuills extends Power
{
    public SharpQuills() {}

    @Override
    public SharpQuills copy()
    {
        return new SharpQuills();
    }

    @Override
    public void onAttacked(Card attacker)
    {
        attacker.takeDamage(1);
    }

    @Override
    public String getName(){return "Sharp Quills";}
}
