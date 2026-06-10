package inscryption.game;

import inscryption.cards.Animal;
import inscryption.cards.CardFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    private Board m_board;

    @Before
    public void setUp() {
        m_board = new Board();
    }

    @Test
    public void testPlacementCarteSurPlateau() {
        // Arrange
        Animal chat = CardFactory.createChat();

        // Act
        boolean isPlaced = m_board.placePlayerCard(chat, 1);

        // Assert
        assertTrue("La carte doit pouvoir être posée sur une case vide", isPlaced);
        assertEquals("Le chat doit être en position 1", chat, m_board.getPlayerCard(1));
    }
}