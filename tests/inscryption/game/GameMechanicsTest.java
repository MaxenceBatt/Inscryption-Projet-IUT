package inscryption.game;

import inscryption.cards.Animal;
import inscryption.cards.CardFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

public class GameMechanicsTest
{
    private InputStream m_originalSystemIn;
    private Game m_game;

    @Before
    public void setUp()
    {
        m_originalSystemIn = System.in;
    }

    @After
    public void tearDown()
    {
        System.setIn(m_originalSystemIn);
    }

    private void setupGameWithInput(String simulatedUserInput)
    {
        System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
        m_game = new Game();
    }

    // ==========================================
    // TESTS POUR CHOOSE NEW CARD
    // ==========================================

    @Test
    public void testChooseNewCard_Choix1AjouteAuDeck()
    {
        setupGameWithInput("1\n");
        int tailleInitiale = m_game.getPlayer().getDeck().size();
        m_game.chooseNewCard();

        assertEquals("Le deck devrait avoir une carte de plus.", tailleInitiale + 1, m_game.getPlayer().getDeck().size());
    }

    @Test
    public void testChooseNewCard_SaisiesInvalidesPuisValides()
    {
        setupGameWithInput("3\nblabla\n2\n");
        m_game.chooseNewCard();

        assertEquals("Le deck devrait avoir reçu une carte malgré les erreurs initiales.", 1, m_game.getPlayer().getDeck().size());
    }

    // ==========================================
    // TESTS POUR LA PIERRE DE SACRIFICE
    // ==========================================

    @Test
    public void testUseSacrificeStone_TransfertDePouvoirReussi()
    {
        // On tape "1" (choix du donneur) puis "2" (choix du receveur, qui est bien resté en 2ème position !)
        setupGameWithInput("1\n2\n");

        Animal donneur = CardFactory.createChat(); // Sera à l'index 0 (choix 1)
        Animal receveur = CardFactory.createLoup(); // Sera à l'index 1 (choix 2)

        m_game.getPlayer().addCardToDeck(donneur);
        m_game.getPlayer().addCardToDeck(receveur);

        m_game.useSacrificeStone();

        assertEquals("Il ne doit rester qu'une seule carte dans le deck.", 1, m_game.getPlayer().getDeck().size());

        Animal survivant = (Animal) m_game.getPlayer().getDeck().get(0);
        assertEquals("Le Loup doit être le survivant.", "Loup", survivant.getName());
        assertEquals("Le Loup doit avoir hérité d'un pouvoir.", 1, survivant.getPowers().size());
        assertEquals("Le pouvoir hérité doit être ManyLives.", "inscryption.cards.powers.ManyLives", survivant.getPowers().get(0).getClass().getName());
    }

    @Test
    public void testUseSacrificeStone_GererLesErreursDeSaisie()
    {
        // On ajoute "1" et "2" à la fin de nos tentatives ratées
        setupGameWithInput("0\n99\ntexte\n1\n2\n");

        Animal donneur = CardFactory.createPunaise();
        Animal receveur = CardFactory.createGrizzly();

        m_game.getPlayer().addCardToDeck(donneur);
        m_game.getPlayer().addCardToDeck(receveur);

        m_game.useSacrificeStone();

        assertEquals("Il ne doit rester qu'une seule carte.", 1, m_game.getPlayer().getDeck().size());
        Animal survivant = (Animal) m_game.getPlayer().getDeck().get(0);
        assertEquals("Le Grizzly doit avoir survécu et reçu le pouvoir.", "Grizzly", survivant.getName());
        assertEquals("Le Grizzly doit avoir 1 pouvoir.", 1, survivant.getPowers().size());
    }
}