//Cette classe est un enfant de la classe "Card."
package inscryption.cards; //Cette classe se trouve dans le package "inscryption.cards."

import inscryption.cards.powers.Power;
import inscryption.game.Board;

import java.util.ArrayList;
import java.util.List;

public class Animal extends Card // Déclaration de la classe publique "Animal" qui hérite de la classe parente "Card"
{   // Attributs privés (variables d'instance) propres à un Animal :
    private int m_attack; //Nombre de dégâts qu'inflige la carte
    private int m_bloodCost; //Quantité de sang nécessaire pour poser la carte
    private int m_boneCost; //Quantité d'os nécessaire pour poser la carte
    private List<Power> m_powers;

    public Animal(String name, int hp, int attack, int bloodCost, int boneCost) // Constructeur de la classe Animal pour initialiser un nouvel objet
    {
        super(name, hp);//importe name et hp de la classe parent "Card"
        // Initialisation des attributs spécifiques de l'Animal avec les paramètres reçus
        m_attack = attack;
        m_bloodCost = bloodCost;
        m_boneCost = boneCost;
        m_powers = new ArrayList<>();
    }

    public Animal(Animal original)
    {
        super(original.getName(), original.getHp());

        this.m_attack = original.getAttack();
        this.m_bloodCost = original.getBloodCost();
        this.m_boneCost = original.getBoneCost();
        this.m_powers = new ArrayList<>();
        for(Power power : original.m_powers)
        {
            m_powers.add(power.copy());
        }
    }

    public int getBloodCost()
    {
        return m_bloodCost;
    } //Renvoie la quantité de sang nécessaire à la pose

    public  int getBoneCost()
    {
        return m_boneCost;
    }//Renvoie la quantité d'os nécessaire à la pose

    @Override
    public int performAttack(Card targetCard) {
        if(targetCard == null)
        {
            return this.getAttack();
        }
        else
        {
            int damage = this.getAttack();
            damage -= targetCard.getTotalAttackReduction();
            if(damage < 0) { damage = 0; }
            targetCard.takeDamage(damage);
            if(damage > 0)
            {
                this.activateDamageDealtPowers(targetCard);
                targetCard.activateOnAttackedPowers(this);
            }
            return 0;
        }
    }

    @Override
    public boolean canBeSacrificed()
    {
        return true;
    }

    @Override
    public int getAttack()
    {
        return this.m_attack;
    } //Renvoie le nombre de dégâts qu'inflige la carte

    public void addPower(Power power)
    {
        m_powers.add(power);
    }

    @Override
    public Animal copy()
    {
        return new Animal(this);
    }

    public List<Power> getPowers()
    {
        return  m_powers;
    }

    @Override
    public void activateTurnStartPowers(Board board) {
        List<Power> powersCopy = new ArrayList<>(this.getPowers());
        for (Power power : powersCopy) {
            power.onTurnStart(this, board);
        }
    }

    @Override
    public boolean activateSacrificePowers(Board board) {
        boolean survives = false;
        List<Power> powersCopy = new ArrayList<>(this.getPowers());
        for (Power power : powersCopy) {
            if(power.onSacrifice(this, board)) {
                survives = true;
            }
        }
        return survives;
    }

    @Override
    public int getTotalAttackReduction() {
        int total = 0;
        List<Power> powersCopy = new ArrayList<>(this.getPowers());
        for(Power power : powersCopy) {
            total += power.getAttackReduction();
        }
        return total;
    }

    @Override
    public void activatePostAttackPowers(Board board)
    {
        List<Power> powersCopy = new ArrayList<>(this.getPowers());
        for (Power power : powersCopy) {
            power.onPostAttack(this, board, board.getCardPosition(this));
        }
    }

    @Override
    public void takeLethalDamage()
    {
        this.takeDamage(this.getHp());
    }

    @Override
    public void activateDamageDealtPowers(Card targetCard)
    {
        for(Power power : this.getPowers())
        {
            power.onDamageDealt(targetCard);
        }
    }

    @Override
    public void activateOnAttackedPowers(Card attacker)
    {
        for(Power power : this.getPowers())
        {
            power.onAttacked(attacker);
        }
    }

    public StringBuilder getPower() {
        StringBuilder pouvoirs = new StringBuilder("     └ Pouvoirs : ");
        for (int i = 0; i < this.m_powers.size(); i++) {
            // Si ce n'est pas le premier pouvoir, on ajoute une virgule pour séparer
            if (i > 0) {
                pouvoirs.append(", ");
            }
            // On récupère le nom de la classe du pouvoir (ex: Volant, Incassable...)
            String nomPouvoir = this.getPowerName(i);
            pouvoirs.append(nomPouvoir);
        }
        return pouvoirs;
    }

    public String getPowerName(int i)
    {
        return this.m_powers.get(i).getName();
    }
}
