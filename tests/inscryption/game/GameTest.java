package inscryption.game;

import inscryption.cards.Animal;
import inscryption.cards.CardFactory;
import inscryption.cards.Obstacle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game m_game;

    @Before
    public void setUp() {
        m_game = new Game();
    }

    @Test
    public void testMiseEnPlacePartie() {
        // On simule manuellement la préparation du début de manche
        m_game.getPlayer().initDeck();
        m_game.getPlayer().prepareTurn();
        m_game.initObstacle(100); // On met 100% de chance pour s'assurer qu'au moins un obstacle pop pour le test

        assertEquals("Le joueur pioche 4 cartes au début", 4, m_game.getPlayer().getHand().size());

        boolean obstacleTrouve = false;
        Board board = m_game.getGameBoard();
        for(int i=0; i<4; i++) {
            if(board.getPlayerCard(i) instanceof Obstacle || board.getOpponentCard(i) instanceof Obstacle) {
                obstacleTrouve = true;
            }
        }
        assertTrue("Un obstacle a dû être généré lors de l'init", obstacleTrouve);
    }

    @Test
    public void testResolutionAttaquesEtMiseAJourScore() {

        Animal ours = CardFactory.createGrizzly();
        m_game.getGameBoard().placePlayerCard(ours, 0);

        m_game.resolvePlayerAttack();

        assertEquals("Le score devrait être de 4", 4, m_game.getScoreDifference());
    }

    @Test
    public void testVictoirePartie() {
        Animal grizzly = CardFactory.createGrizzly(); // 4 Dégâts
        Animal loup = CardFactory.createLoup(); // 3 dégâts
        m_game.getGameBoard().placePlayerCard(grizzly, 0);
        m_game.getGameBoard().placePlayerCard(loup, 1);

        m_game.resolvePlayerAttack();
        boolean estFini = m_game.checkGameOver();

        assertTrue("La partie doit être finie car le score dépasse 5", estFini);
        assertTrue("La différence de score est bien >= 5", m_game.getScoreDifference() >= 5);
    }
}