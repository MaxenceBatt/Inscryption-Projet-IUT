package inscryption.cards.powers;

import inscryption.cards.Animal;
import inscryption.game.Board;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SprinterTest {

    private Board m_board;
    private Animal m_carteSprinter;
    private Sprinter m_pouvoirSprinter;

    @Before
    public void setUp() {
        // Initialisation avant chaque test
        m_board = new Board();
        // On crée un animal basique pour simuler le coureur
        m_carteSprinter = new Animal("Loup Sprinter", 2, 2, 1, 0);
        m_pouvoirSprinter = new Sprinter();
    }

    @Test
    public void testDeplacementDroiteSiLibre() {
        // Placer le coureur en position 1
        m_board.placePlayerCard(m_carteSprinter, 1);

        // Simuler l'effet post-attaque
        m_pouvoirSprinter.onPostAttack(m_carteSprinter, m_board, 1);

        // Vérifications
        assertNull("L'ancienne case (1) devrait être vide.", m_board.getPlayerCard(1));
        assertSame("La carte devrait s'être déplacée sur la case de droite (2).", m_carteSprinter, m_board.getPlayerCard(2));
    }

    @Test
    public void testDeplacementGaucheSiDroiteBloquee() {
        // Placer le coureur en position 1
        m_board.placePlayerCard(m_carteSprinter, 1);

        // Placer un obstacle à droite (position 2)
        Animal obstacle = new Animal("Arbre", 2, 0, 0, 0);
        m_board.placePlayerCard(obstacle, 2);

        // Simuler l'effet post-attaque
        m_pouvoirSprinter.onPostAttack(m_carteSprinter, m_board, 1);

        // Vérifications
        assertNull("L'ancienne case (1) devrait être vide.", m_board.getPlayerCard(1));
        assertSame("La carte devrait s'être déplacée sur la case de gauche (0).", m_carteSprinter, m_board.getPlayerCard(0));
        assertSame("L'obstacle à droite ne doit pas avoir bougé.", obstacle, m_board.getPlayerCard(2));
    }

    @Test
    public void testAucunDeplacementSiToutBloque() {
        // Placer le coureur en position 1
        m_board.placePlayerCard(m_carteSprinter, 1);

        // Placer des obstacles des deux côtés
        m_board.placePlayerCard(new Animal("Arbre Gauche", 2, 0, 0, 0), 0);
        m_board.placePlayerCard(new Animal("Arbre Droit", 2, 0, 0, 0), 2);

        // Simuler l'effet post-attaque
        m_pouvoirSprinter.onPostAttack(m_carteSprinter, m_board, 1);

        // Vérifications
        assertSame("La carte ne devrait pas avoir bougé (position 1).", m_carteSprinter, m_board.getPlayerCard(1));
    }

    @Test
    public void testDeplacementBordDroitPlateau() {
        // Placer le coureur tout à droite (position 3)
        m_board.placePlayerCard(m_carteSprinter, 3);

        // Simuler l'effet post-attaque (la case +1 n'existe pas, il doit aller à gauche)
        m_pouvoirSprinter.onPostAttack(m_carteSprinter, m_board, 3);

        // Vérifications
        assertNull("L'ancienne case (3) devrait être vide.", m_board.getPlayerCard(3));
        assertSame("La carte devrait avoir rebondi vers la gauche (2).", m_carteSprinter, m_board.getPlayerCard(2));
    }

    @Test
    public void testAucunDeplacementSiBloqueDansUnCoin() {
        // Placer le coureur tout à droite (position 3)
        m_board.placePlayerCard(m_carteSprinter, 3);

        // Bloquer la seule issue à gauche (position 2)
        m_board.placePlayerCard(new Animal("Arbre", 2, 0, 0, 0), 2);

        // Simuler l'effet post-attaque
        m_pouvoirSprinter.onPostAttack(m_carteSprinter, m_board, 3);

        // Vérifications
        assertSame("La carte est bloquée dans le coin, elle ne doit pas bouger.", m_carteSprinter, m_board.getPlayerCard(3));
    }
}