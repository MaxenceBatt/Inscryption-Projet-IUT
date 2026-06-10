package inscryption.cards.powers;

import inscryption.cards.Animal;
import inscryption.cards.CardFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StinkyTest {
    private Animal m_punaise;
    private Animal m_loup;
    private Animal m_louveteau;

    @Before
    public void setUp() {
        m_punaise = CardFactory.createPunaise();
        m_loup = CardFactory.createLoup();
        m_louveteau = CardFactory.createLouveteau();
    }

    @Test
    public void testStinky_GetTotalAttackReduction() {
        assertEquals("La Punaise doit réduire l'attaque adverse de 1.", 1, m_punaise.getTotalAttackReduction());
    }

    @Test
    public void testSansStinky_GetTotalAttackReduction() {
        assertEquals("Le Loup ne possède pas Stinky, sa réduction doit être de 0.", 0, m_loup.getTotalAttackReduction());
    }

    @Test
    public void testPerformAttack_DegatsReduitsParStinky() {
        int pvInitiauxPunaise = m_punaise.getHp();
        m_loup.performAttack(m_punaise);
        assertEquals("La Punaise devrait avoir perdu 2 PV (3 - 1).", pvInitiauxPunaise - 2, m_punaise.getHp());
    }

    @Test
    public void testPerformAttack_DegatsNeTombentPasSousZero() {
        int pvInitiauxPunaise = m_punaise.getHp();
        m_louveteau.performAttack(m_punaise);
        assertEquals("La Punaise ne devrait perdre aucun PV.", pvInitiauxPunaise, m_punaise.getHp());
    }
}