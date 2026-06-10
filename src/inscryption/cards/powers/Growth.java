package inscryption.cards.powers;

import inscryption.cards.Animal;
import inscryption.cards.CardFactory;
import inscryption.game.Board;

public class Growth extends Power {
    private int m_turnsAlive;

    public Growth() {
        this.m_turnsAlive = 0;
    }

    @Override
    public Power copy() {
        Growth copy = new Growth();
        copy.m_turnsAlive = this.m_turnsAlive;
        return copy;
    }

    @Override
    public void onTurnStart(Animal owner, Board board) {
        this.m_turnsAlive++;

        if (this.m_turnsAlive == 2) {
            Animal loup = CardFactory.createLoup();

            board.replaceCard(owner, loup);
        }
    }

    @Override
    public String getName(){return "Growth";}
}