package inscryption.game; //Cette classe se trouve dans le package "inscryption.game".

import inscryption.cards.Card;//importe la classe "Card"

import java.lang.reflect.Array;

public class Board //Déclaration de la classe publique "Board"
{
    private Card[] m_playerRow; //Tableau qui représente la ligne du joueur
    private Card[] m_opponentRow; //Tableau qui représente la ligne de l'adversaire
    private Card[] m_opponentIntentions; //Tableau qui représente les intentions de l'adversaire (sa 2ème ligne)

    public Board() //Constructeur de la classe "Board" qui permet de créer une nouvelle instance
    {
        m_playerRow = new Card[4]; //Tableau de 4 cases pour les 4 emplacements de carte
        m_opponentRow = new Card[4]; //Tableau de 4 cases pour les 4 emplacements de carte
        m_opponentIntentions = new Card[4]; //Tableau de 4 cases pour les 4 emplacements de carte
    }

    public boolean placePlayerCard(Card card, int position) //Fonction qui pose une de nos cartes
    {
        if(m_playerRow[position] == null)//Vérifie que la position est nulle
        {
            m_playerRow[position] = card; //Si c'est le cas, on pose la carte
            return true; //Et on renvoie True
        }
        return false;//Sinon on renvoie false
    }

    public boolean placeOpponentCard(Card card, int position) //Fonction qui pose une carte adverse
    {
        if(m_opponentRow[position] == null) { //Vérifie que la position est nulle
            m_opponentRow[position] = card; //Si c'est le cas, on pose la carte
            return true;//Et on renvoie True
        }
        return false;//Sinon on renvoie false
    }

    public boolean placeOpponentIntention(Card card, int position) //Fonction qui pose une carte adverse dans sa deuxième ligne
    {
        if(m_opponentIntentions[position] == null)//Vérifie que la position est nulle
        {
            m_opponentIntentions[position] = card;//Si c'est le cas, on pose la carte
            return true;//Et on renvoie True
        }
        return false;//Sinon on renvoie false
    }

    public Card getPlayerCard(int position) // Fonction qui renvoie la carte sur notre ligne à une certaine position
    {
        return m_playerRow[position];//Renvoie la carte à la position passée en paramètre
    }

    public Card getOpponentCard(int position)// Fonction qui renvoie la carte sur la ligne adverse à une certaine position
    {
        return m_opponentRow[position];//Renvoie la carte à la position passée en paramètre
    }

    public Card getOpponentIntentionsCard(int position)
    {
        return m_opponentIntentions[position];
    }

    public void clearIntentionPosition(int position)
    {
        m_opponentIntentions[position] = null;
    }

    public void clearOpponentPosition(int position)
    {
        m_opponentRow[position] = null;
    }

    public void clearPlayerPosition(int position)
    {
        m_playerRow[position] = null;
    }


    public void replaceCard(Card oldCard, Card newCard) {
        // Parcourt la ligne du joueur
        for (int i = 0; i < m_playerRow.length; i++) {
            if (m_playerRow[i] == oldCard) {
                m_playerRow[i] = newCard;
                return; // On a trouvé et remplacé, on arrête
            }
        }
        // Parcourt la ligne adverse si pas trouvée chez le joueur
        for (int i = 0; i < m_opponentRow.length; i++) {
            if (m_opponentRow[i] == oldCard) {
                m_opponentRow[i] = newCard;
                return;
            }
        }
    }

    public boolean isTargetPositionEmpty(Card owner, int targetPosition)
    {
        if(targetPosition < 0 || targetPosition > 3)
        {
            return false;
        }

        // 2. On cherche à qui appartient la carte 'owner'
        for(int i = 0; i < 4; i++)
        {
            if(this.getPlayerCard(i) == owner)
            {
                return this.getPlayerCard(targetPosition) == null;
            }
            else if(this.getOpponentCard(i) == owner)
            {
                return this.getOpponentCard(targetPosition) == null;
            }
        }
        return false;
    }

    public void moveCard(Card card, int targetPosition)
    {
        for(int i = 0; i < 4; i++)
        {
            if(this.getPlayerCard(i) == card)
            {
                this.m_playerRow[targetPosition] = card;
                this.clearPlayerPosition(i);
                return;
            }
            else if(this.getOpponentCard(i) == card)
            {
                this.m_opponentRow[targetPosition] = card;
                this.clearOpponentPosition(i);
                return;
            }
        }
    }

    public int getCardPosition(Card card)
    {
        for(int i = 0; i < 4; i++)
        {
            if(this.getPlayerCard(i) == card || this.getOpponentCard(i) == card)
            {
                return i;
            }
        }
        return -1;
    }
}
