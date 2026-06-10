package inscryption.cards.powers;

import inscryption.cards.Card;

import javax.swing.*;

public class DeadlyContact extends Power
{
    @Override
    public DeadlyContact copy()
    {
        return new DeadlyContact();
    }

    @Override
    public void onDamageDealt  (Card targetCard)
    {
        targetCard.takeLethalDamage();
    }

    @Override
    public String getName(){return "Deadly Contact";}
}
