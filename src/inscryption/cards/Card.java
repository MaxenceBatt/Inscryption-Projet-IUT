//Cette classe est la classe mère de "Animal" et "Obstacle".
package inscryption.cards; //Cette classe se trouve dans le package "inscryption.cards".

import inscryption.game.Board;

public abstract class Card // Déclaration de la classe publique et abstraite "Card"
{
    private String m_name; //Nom de la carte
    private int m_hp; // Hp de la carte

    public Card (String name, int hp) //Constructeur de la classe "Card" pour initialiser un nouvel objet
    {
        // Initialisation des attributs spécifiques de la carte avec les paramètres reçus
        m_name = name;
        m_hp = hp;
    }

    public String getName ()
    {
        return m_name;
    } //Renvoie le nom de la carte

    public int getAttack() { return 0; }

    public int getHp ()
    {
        return m_hp;
    } //Renvoie le nombre de points de vie

    public void takeDamage(int damage)
    {
        m_hp -= damage;
    } // Inflige les dégâts à la carte

    public boolean isDead()
    {
        return m_hp <= 0;
    } // Renvoie si la carte est morte ou non

    public abstract int performAttack(Card targetCard);

    public abstract boolean canBeSacrificed();

    public abstract Card copy ();

    public abstract void activateTurnStartPowers(Board board);

    public abstract boolean activateSacrificePowers(Board board);

    public abstract int getTotalAttackReduction();

    public abstract void activatePostAttackPowers(Board board);

    public abstract void takeLethalDamage();

    public abstract void activateDamageDealtPowers(Card targetCard);

    public abstract void activateOnAttackedPowers(Card attacker);
}
