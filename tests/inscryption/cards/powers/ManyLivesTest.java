package inscryption.cards.powers;

import inscryption.cards.Animal;
import inscryption.cards.CardFactory;
import inscryption.game.Board;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ManyLivesTest {
    private Board m_board;
    private Animal m_chat;
    private Animal m_loup;

    @Before
    public void setUp() {
        m_board = new Board();
        m_chat = CardFactory.createChat();
        m_loup = CardFactory.createLoup();
    }

    @Test
    public void testManyLives_RenvoieTrueLorsDuSacrifice() {
        assertTrue("Le Chat doit renvoyer true pour signaler qu'il survit.", m_chat.activateSacrificePowers(m_board));
    }

    @Test
    public void testSansManyLives_RenvoieFalseLorsDuSacrifice() {
        assertFalse("Un animal sans pouvoir de survie doit renvoyer false.", m_loup.activateSacrificePowers(m_board));
    }

    @Test
    public void testManyLives_SimulationSurLePlateau() {
        m_board.placePlayerCard(m_chat, 0);
        if (!m_chat.activateSacrificePowers(m_board)) {
            m_board.clearPlayerPosition(0);
        }
        assertNotNull("La case 0 ne devrait pas être vide.", m_board.getPlayerCard(0));
        assertEquals("Le Chat devrait toujours être sur le plateau.", m_chat, m_board.getPlayerCard(0));
    }

    @Test
    public void testSansManyLives_SimulationSurLePlateau() {
        m_board.placePlayerCard(m_loup, 1);
        if (!m_loup.activateSacrificePowers(m_board)) {
            m_board.clearPlayerPosition(1);
        }
        assertNull("La case 1 devrait être vide après le sacrifice du Loup.", m_board.getPlayerCard(1));
    }
}