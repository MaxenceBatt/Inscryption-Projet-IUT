package inscryption.cards;

import inscryption.cards.powers.*;

import java.util.Random;

public class CardFactory //Va créer les différents animaux
{
    // --- CARTES ANIMAUX TERRESTRES ---

    public static Animal createChat() {
        Animal chat = new Animal("Chat", 1, 0, 1, 0);
        chat.addPower(new ManyLives());
        return chat;
    }

    public static Animal createGrizzly() {
        return new Animal("Grizzly", 6, 4, 3, 0);
    }

    public static Animal createCoyote() {
        return new Animal("Coyote", 1, 2, 0, 4);
    }

    public static Animal createEcureuil() {
        return new Animal("Ecureuil", 1, 0, 0, 0);
    }

    public static Animal createHermine() {
        return new Animal("Hermine", 3, 1, 1, 0);
    }

    public static Animal createLouveteau() {
        Animal louveteau = new Animal("Louveteau", 1, 1, 1, 0); // hp, attack, bloodCost, boneCost
        louveteau.addPower(new Growth());
        return louveteau;
    }

    public static Animal createLoup() {
        return new Animal("Loup", 2, 3, 2, 0);
    }

    public static Animal createPunaise() {
        Animal punaise = new Animal("Punaise", 2, 1, 0, 2);
        punaise.addPower(new Stinky());
        return punaise;
    }

    public static Animal createElan()
    {
        Animal elan = new Animal("Elan", 4, 2, 2, 0);
        elan.addPower(new Sprinter());
        return elan;
    }

    public static Animal createVipere()
    {
        Animal vipere = new Animal("Vipere", 1, 1, 2, 0);
        vipere.addPower(new DeadlyContact());
        return vipere;
    }

    public static Animal createPorcEpic()
    {
        Animal porcEpic = new Animal("PorcEpic", 2, 1, 1, 0);
        porcEpic.addPower(new SharpQuills());
        return porcEpic;
    }

    // --- CARTES ANIMAUX VOLANTS ---

    public static FlyingAnimals createMoineau() {
        return new FlyingAnimals("Moineau", 2, 1, 1, 0);
    }

    public static FlyingAnimals createCorbeau() {
        return new FlyingAnimals("Corbeau", 3, 2, 2, 0);
    }

    // --- CARTES OBSTACLES ---

    public static Obstacle createRocher() {
        return new Obstacle("Rocher", 5);
    }

    public static Obstacle createSapin() {
        return new Obstacle("Sapin", 3);
    }

    public static Animal createRandomAnimal()
    {
        Random rand = new Random();
        int choix = rand.nextInt(12); // Génère un nombre entre 0 et 9.
        switch (choix) {
            case 0: return CardFactory.createChat();
            case 1: return CardFactory.createGrizzly();
            case 2: return CardFactory.createCoyote();
            case 3: return CardFactory.createMoineau();
            case 4: return CardFactory.createCorbeau();
            case 5: return CardFactory.createHermine();
            case 6: return CardFactory.createLouveteau();
            case 7: return CardFactory.createLoup();
            case 8: return CardFactory.createPunaise();
            case 9: return CardFactory.createElan();
            case 10: return CardFactory.createPorcEpic();
            case 11: return CardFactory.createVipere();
            default: return CardFactory.createEcureuil(); 
        }
    }
}
