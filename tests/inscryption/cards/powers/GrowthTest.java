package inscryption.cards.powers;

import inscryption.cards.Animal;
import inscryption.cards.CardFactory;
import inscryption.game.Board;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GrowthTest {
    private Board m_board;
    private Animal m_louveteau;

    @Before
    public void setUp() {
        m_board = new Board();
        m_louveteau = CardFactory.createLouveteau();
    }

    @Test
    public void testGrowth_NeSeTransformePasAuPremierTour() {
        m_board.placePlayerCard(m_louveteau, 0);
        m_louveteau.activateTurnStartPowers(m_board);
        assertEquals("La carte ne devrait pas évoluer au tour 1.", m_louveteau, m_board.getPlayerCard(0));
    }

    @Test
    public void testGrowth_SeTransformeEnLoupAuDeuxiemeTour() {
        m_board.placePlayerCard(m_louveteau, 1);
        m_louveteau.activateTurnStartPowers(m_board);
        m_louveteau.activateTurnStartPowers(m_board);
        Animal carteApresDeuxTours = (Animal) m_board.getPlayerCard(1);
        assertNotEquals("La carte devrait avoir été remplacée.", m_louveteau, carteApresDeuxTours);
        assertEquals("La nouvelle carte doit être un Loup.", "Loup", carteApresDeuxTours.getName());
    }

    @Test
    public void testGrowth_FonctionneAussiPourLAdversaire() {
        m_board.placeOpponentCard(m_louveteau, 3);
        m_louveteau.activateTurnStartPowers(m_board);
        m_louveteau.activateTurnStartPowers(m_board);
        Animal carteAdverse = (Animal) m_board.getOpponentCard(3);
        assertNotNull("Il doit y avoir une carte sur la case.", carteAdverse);
        assertEquals("Le Louveteau adverse doit s'être transformé en Loup.", "Loup", carteAdverse.getName());
    }
}