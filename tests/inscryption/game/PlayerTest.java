package inscryption.game;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    private Player m_player;

    @Before
    public void setUp() {
        m_player = new Player();
    }

    @Test
    public void testPiocherUneCarte() {
        m_player.initDeck();
        m_player.prepareTurn();

        m_player.drawCard();
        m_player.drawCard();

        assertEquals("Le joueur doit avoir 6 cartes en main après 2 pioches", 6, m_player.getHand().size());
    }
}