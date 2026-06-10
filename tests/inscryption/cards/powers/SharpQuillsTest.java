package inscryption.cards.powers;

import inscryption.cards.Animal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SharpQuillsTest {

    private SharpQuills m_pouvoirPiques;
    private Animal m_attaquantRobuste;
    private Animal m_attaquantFaible;

    @Before
    public void setUp() {
        // On initialise le pouvoir des piques
        m_pouvoirPiques = new SharpQuills();

        // On crée un attaquant qui a beaucoup de PV
        m_attaquantRobuste = new Animal("Loup", 5, 3, 1, 0);

        // On crée un attaquant qui n'a qu'un seul PV
        m_attaquantFaible = new Animal("Fourmi", 1, 1, 1, 0);
    }

    @Test
    public void testOnAttacked_InfligeExactementUnPointDeDegat() {
        // Sauvegarde des PV initiaux
        int pvInitiaux = m_attaquantRobuste.getHp();

        // La carte possédant les piques subit une attaque, le pouvoir riposte sur l'attaquant
        m_pouvoirPiques.onAttacked(m_attaquantRobuste);

        // Vérifications
        assertEquals("L'attaquant robuste devrait avoir perdu exactement 1 PV.", pvInitiaux - 1, m_attaquantRobuste.getHp());
        assertFalse("L'attaquant robuste ne devrait pas être mort car il avait assez de PV.", m_attaquantRobuste.isDead());
    }

    @Test
    public void testOnAttacked_PeutTuerUnAttaquantFaible() {
        // La riposte touche l'attaquant faible qui n'a qu'1 PV
        m_pouvoirPiques.onAttacked(m_attaquantFaible);

        // Vérifications
        assertEquals("Les PV de l'attaquant faible devraient être tombés à 0.", 0, m_attaquantFaible.getHp());
        assertTrue("L'attaquant faible devrait être considéré comme mort après s'être piqué.", m_attaquantFaible.isDead());
    }

    @Test
    public void testCopy_RetourneNouvelleInstance() {
        // On appelle la méthode copy()
        SharpQuills copie = m_pouvoirPiques.copy();

        // Vérifications
        assertNotNull("La copie ne doit pas être nulle.", copie);

        // assertNotSame vérifie que ce sont bien deux objets différents en mémoire,
        // pas juste le même objet pointé par deux variables. C'est crucial pour ton jeu !
        assertNotSame("La méthode copy() doit créer un nouvel objet, pas retourner la même référence.", m_pouvoirPiques, copie);

        // On vérifie que c'est bien la bonne classe
        assertEquals("L'objet copié doit bien être de la classe SharpQuills.", SharpQuills.class, copie.getClass());
    }
}