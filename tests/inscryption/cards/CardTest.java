package inscryption.cards;

import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void testAttaqueCarteTerrestreFaceAObstacle() {
        Animal loup = CardFactory.createLoup(); // 3 Attaque
        Obstacle rocher = CardFactory.createRocher(); // 5 PV

        int degatsAuScore = loup.performAttack(rocher);

        // Assert
        assertEquals("Le rocher devrait tomber à 2 PV", 2, rocher.getHp());
        assertEquals("Une attaque terrestre bloquée ne touche pas le score", 0, degatsAuScore);
    }

    @Test
    public void testAttaqueCarteVolanteIgnoreCible() {
        // Arrange
        FlyingAnimals moineau = CardFactory.createMoineau(); // Volant, 1 Attaque
        Animal hermine = CardFactory.createHermine(); // 3 PV

        // Act
        int degatsAuScore = moineau.performAttack(hermine);

        // Assert
        assertEquals("L'Hermine ne devrait perdre aucun PV", 3, hermine.getHp());
        assertEquals("Le moineau inflige son attaque au score", 1, degatsAuScore);
    }
}