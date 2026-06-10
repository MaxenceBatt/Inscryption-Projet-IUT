package inscryption.cards.powers;

import inscryption.cards.Animal;
import inscryption.cards.Obstacle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeadlyContactTest {

    private DeadlyContact m_pouvoirDeadlyContact;
    private Animal m_cibleAnimal;
    private Obstacle m_cibleObstacle;

    @Before
    public void setUp() {
        // On initialise le pouvoir
        m_pouvoirDeadlyContact = new DeadlyContact();

        // On crée une cible Animal très résistante (10 PV)
        m_cibleAnimal = new Animal("Ours Grizzly", 10, 4, 2, 0);

        // On crée une cible Obstacle (Grand Sapin avec 5 PV)
        // (J'adapte le constructeur selon ce qui est le plus probable pour ton Obstacle)
        m_cibleObstacle = new Obstacle("Grand Sapin", 5);
    }

    @Test
    public void testOnDamageDealt_TueUnAnimalInstantement() {
        // Le pouvoir s'active sur l'animal
        m_pouvoirDeadlyContact.onDamageDealt(m_cibleAnimal);

        // Vérifications
        assertTrue("L'animal cible devrait être mort après le contact mortel.", m_cibleAnimal.isDead());
        assertEquals("Les PV de l'animal cible devraient être exactement à 0.", 0, m_cibleAnimal.getHp());
    }

    @Test
    public void testOnDamageDealt_NeTuePasUnObstacle() {
        // Sauvegarde des PV initiaux
        int pvInitiaux = m_cibleObstacle.getHp();

        // Le pouvoir s'active sur l'obstacle
        m_pouvoirDeadlyContact.onDamageDealt(m_cibleObstacle);

        // Vérifications : l'obstacle doit avoir ignoré l'effet
        assertFalse("L'obstacle ne doit pas mourir du contact mortel.", m_cibleObstacle.isDead());
        assertEquals("L'obstacle doit conserver l'intégralité de ses PV.", pvInitiaux, m_cibleObstacle.getHp());
    }

    // Remarque : Le test vérifiant que le pouvoir ne s'active que si damage > 0
    // relève plutôt des tests de la méthode performAttack() de la classe Animal.
}