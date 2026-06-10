import inscryption.game.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans la cabane... Le jeu commence.");

        // On crée la partie
        Game myGame = new Game();

        // On lance la boucle de jeu principale
        myGame.startGame();

        System.out.println("La partie est terminée !");
    }
}