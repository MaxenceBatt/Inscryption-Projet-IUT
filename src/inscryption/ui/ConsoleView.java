package inscryption.ui; // Cette classe se trouve dans le package "inscryption.ui"

import inscryption.cards.Animal;
import inscryption.cards.Card;
import inscryption.cards.FlyingAnimals;
import inscryption.game.Board;
import inscryption.game.Player;

import java.util.Scanner; // Importation de l'outil pour lire le clavier

public class ConsoleView // Déclaration de la classe publique "ConsoleView"
{
    private Scanner m_scanner; // Attribut pour lire les saisies de l'utilisateur

    // Codes ANSI pour les couleurs selon le type de carte
    private static final String RESET = "\u001B[0m";
    private static final String VERT  = "\u001B[32m";  // Animal terrestre -> vert
    private static final String CYAN  = "\u001B[36m";  // Animal volant    -> cyan
    private static final String JAUNE = "\u001B[33m";  // Obstacle         -> jaune
    private static final String GRIS  = "\u001B[90m";  // Emplacement vide -> gris

    public ConsoleView() // Constructeur de la vue
    {
        this.m_scanner = new Scanner(System.in); // On initialise le scanner sur le clavier
    }

    // Fonction qui affiche un simple message textuel à l'écran
    public void displayMessage(String message)
    {
        System.out.println(message); // Affiche le message et passe à la ligne
    }

    // Fonction qui affiche les actions disponibles et renvoie la saisie du joueur
    public String askForAction()
    {
        System.out.println("\nActions possibles:");
        System.out.println("  [fin] Terminer votre tour");
        System.out.println("  [piocher] Piocher une carte");
        System.out.println("  [placer <numero carte> <position>] Placer une carte sur le plateau");
        System.out.print("\n$ ");
        return m_scanner.nextLine(); // On lit et renvoie ce que le joueur a tapé
    }

    // Fonction qui affiche le plateau complet : intentions adversaire, ligne adversaire, score et ligne joueur
    public void displayBoard(Board board, int scoreDifference, int drawPileSize, int bones)
    {
        // Labels des positions pour l'adversaire et le joueur
        String[] positionsOpponent = {"A1", "A2", "A3", "A4"};
        String[] positionsPlayer = {"B1", "B2", "B3", "B4"};

        // Récupère les 4 cartes de chaque ligne du plateau
        Card[] intentions = new Card[4];
        Card[] Opponent = new Card[4];
        Card[] Player = new Card[4];
        for (int i = 0; i < 4; i++)
        {
            intentions[i] = board.getOpponentIntentionsCard(i); // Ce que l'adversaire prépare
            Opponent[i] = board.getOpponentCard(i);           // Cartes posées par l'adversaire
            Player[i] = board.getPlayerCard(i);             // Cartes posées par le joueur
        }

        // On construit les 3 rangées en assemblant les boîtes ASCII côte à côte
        String[] rowIntentions = renderRow(intentions, positionsOpponent);
        String[] rowOpponent = renderRow(Opponent, positionsOpponent);
        String[] rowPlayer = renderRow(Player, positionsPlayer);

        // Flèches d'attaque affichées entre les lignes pour indiquer qui attaque qui
        String eyes  = "         👁️             👁️             👁️             👁️";
        String sword  = "         ⚔️             ⚔️             ⚔️             ⚔️";

        // Boîte "Pioche" affichée à droite de la ligne adversaire (11 lignes pour s'aligner)
        String[] pioche = new String[11];
        pioche[0]  = "           Pioche";
        pioche[1]  = "      ╔═══════════╗";
        pioche[2]  = "      ║           ║";
        pioche[3]  = "      ║           ║";
        pioche[4]  = "      ║    " + String.format("%-2s", drawPileSize) + "     ║"; // Nombre de cartes restantes
        pioche[5]  = "      ║  cartes   ║";
        pioche[6]  = "      ║           ║";
        pioche[7]  = "      ║           ║";
        pioche[8]  = "      ║           ║";
        pioche[9]  = "      ║           ║";
        pioche[10] = "      ╚═══════════╝";

        // Affichage de la ligne des intentions de l'adversaire
        System.out.println("\n");
        printRow(rowIntentions);

        // Flèches entre intentions et ligne adversaire
        System.out.println(eyes);

        // Affichage de la ligne adversaire avec la boîte pioche collée à droite
        for (int i = 0; i < 11; i++)
        {
            System.out.println("    " + rowOpponent[i] + pioche[i]);
        }

        System.out.println(sword);
        // Affichage de la ligne du joueur
        printRow(rowPlayer);

        // Affichage du score et des os entre les deux lignes
        System.out.println("  Score : " + scoreDifference + "    🦴 Os : " + bones);
    }

    // Fonction qui affiche toutes les cartes dans la main du joueur avec leurs statistiques
    public void displayHand(Player player)
    {
        System.out.println("\nVotre main :");

        if (player.getHand().isEmpty())
        {
            System.out.println("  (Votre main est vide)");
            return;
        }

        // On parcourt toutes les cartes de la main et on affiche leurs stats
        for (int i = 0; i < player.getHand().size(); i++)
        {
            Card card = player.getHand().get(i);
            int num = i + 1; // On commence l'affichage à 1 (pas à 0)

            if (card instanceof Animal)
            {
                Animal animal = (Animal) card;
                // Affichage formaté : numéro, nom, PV, Attaque, coût en sang, coût en os
                System.out.printf("  %d. %-10s PV: %-3d Att: %-3d Gouttes de sang: %-3d Os: %d%n",
                        num,
                        animal.getName(),
                        animal.getHp(),
                        animal.getAttack(),
                        animal.getBloodCost(),
                        animal.getBoneCost()
                );

                // Affichage des pouvoirs s'il y en a
                if (!animal.getPowers().isEmpty())
                {
                    StringBuilder pouvoirs = animal.getPower();
                    System.out.println(pouvoirs);
                }
            }
        }
    }

    // Fonction qui demande au joueur quelle carte il veut sacrifier (B1 à B4)
    // Elle boucle jusqu'à ce que le joueur entre une position valide
    public int askForSacrificePosition(int bloodCost, int currentSacrifices)
    {
        while (true)
        {
            System.out.println("\nQuelle carte voulez-vous sacrifier ? (" + currentSacrifices + " / " + bloodCost + ")");
            System.out.print("$ ");

            String index = m_scanner.nextLine();

            // On convertit la position saisie en index (0 à 3)
            switch(index.toUpperCase())
            {
                case "B1" : return 0;
                case "B2" : return 1;
                case "B3" : return 2;
                case "B4" : return 3;
                default: System.out.println("Position invalide (utilisez B1, B2, B3 ou B4)."); break;
            }
        }
    }

    // Fonction qui affiche un message d'erreur quand le sacrifice est invalide
    public void displayInvalidSacrificeError()
    {
        System.out.println("Saisie invalide : L'emplacement est vide ou la carte ne peut pas être sacrifiée. Veuillez réessayer.");
    }

    // Fonction qui affiche une carte animal avec son numéro et toutes ses statistiques
    public void displayAnimal(Animal animal, int numeroAffichage)
    {
        System.out.println("  " + numeroAffichage + ". " + animal.getName()
                + " [PV: " + animal.getHp()
                + " | Att: " + animal.getAttack()
                + " | Sang: " + animal.getBloodCost()
                + " | Os: " + animal.getBoneCost() + "]");
    }

    // Fonction qui attend que le joueur tape 1 ou 2, et boucle jusqu'à une saisie valide
    public int askForChoice()
    {
        while (true)
        {
            System.out.print("$ ");
            String input = m_scanner.nextLine();

            if (input.equals("1"))
            {
                return 1;
            }
            else if (input.equals("2"))
            {
                return 2;
            }
            else
            {
                System.out.println("Saisie invalide. Veuillez taper 1 ou 2.");
            }
        }
    }

    // Fonction qui affiche toutes les cartes du deck permanent du joueur, 4 par ligne
    public void displayDeck(Player player)
    {
        System.out.println("\n--- VOTRE DECK PERMANENT ---");
        if (player.getDeck().isEmpty())
        {
            System.out.println("  (Votre deck est vide)");
            return;
        }

        for (int i = 0; i < player.getDeck().size(); i++)
        {
            Animal animal = (Animal) player.getDeck().get(i);
            int num = i + 1;

            // Affichage de la carte avec son numéro, son nom et ses PV
            System.out.print("[" + num + ". " + animal.getName() + " PV:" + animal.getHp() + "]   ");

            // Retour à la ligne tous les 4 éléments pour afficher 4 cartes par ligne
            if ((i + 1) % 4 == 0)
            {
                System.out.println();
            }
        }
        System.out.println(); // Saut de ligne final après le deck
    }

    // Fonction qui demande au joueur de choisir un numéro entre 1 et maxChoice
    // Elle boucle et gère les erreurs (texte, nombre hors plage) jusqu'à une saisie valide
    public int askForDeckIndex(int maxChoice)
    {
        while (true)
        {
            System.out.print("$ ");
            try
            {
                int choix = Integer.parseInt(this.m_scanner.nextLine()); // On tente de convertir en entier
                if (choix >= 1 && choix <= maxChoice) // Si le choix est dans la plage valide
                {
                    return choix;
                }
                else
                {
                    System.out.println("Saisie invalide. Tapez un nombre entre 1 et " + maxChoice + ".");
                }
            }
            catch (NumberFormatException e) // Si le joueur a tapé autre chose qu'un nombre
            {
                System.out.println("Saisie invalide. Veuillez taper un nombre.");
            }
        }
    }

    // Fonction privée qui construit la boîte ASCII d'une carte sur 11 lignes
    // Si la carte est null, on affiche une boîte vide avec le label de position (ex: A1, B2)
    // Sinon, on affiche le nom et les statistiques de la carte, colorisé selon son type
    private String[] renderCard(Card card, String position)
    {
        String[] lines = new String[11]; // Une boîte fait 11 lignes de haut

        if (card == null)
        {
            // Boîte vide grisée : on affiche juste le label de position au centre
            String positionCenter = String.format("%-2s", position); // Cadré sur 2 caractères
            lines[0]  = GRIS + "╔═══════════╗" + RESET;
            lines[1]  = GRIS + "║           ║" + RESET;
            lines[2]  = GRIS + "║           ║" + RESET;
            lines[3]  = GRIS + "║     " + positionCenter + "    ║" + RESET;
            lines[4]  = GRIS + "║           ║" + RESET;
            lines[5]  = GRIS + "║           ║" + RESET;
            lines[6]  = GRIS + "║           ║" + RESET;
            lines[7]  = GRIS + "║           ║" + RESET;
            lines[8]  = GRIS + "║           ║" + RESET;
            lines[9]  = GRIS + "║           ║" + RESET;
            lines[10] = GRIS + "╚═══════════╝" + RESET;
        }
        else
        {
            // On détermine la couleur selon le type de carte
            String C = (card instanceof FlyingAnimals) ? CYAN
                    : (card instanceof Animal)        ? VERT
                    :                                   JAUNE; // Obstacle

            // Boîte avec carte : nom, PV, attaque, Volant si applicable, puis les pouvoirs
            String nom   = String.format("%-9s", card.getName());       // Nom cadré sur 9 caractères
            String pv    = String.format("PV:%-7s", card.getHp());      // PV cadré pour aligner
            String att   = String.format("Att:%-6s", card.getAttack()); // Attaque cadrée
            String volant = (card instanceof FlyingAnimals) ? "Volant   " : "         "; // Volant si applicable

            // Récupère les noms de pouvoirs tronqués à 9 caractères (3 slots max)
            String[] pouvoirs = {"─────────", "─────────", "─────────"};
            if (card instanceof Animal)
            {
                Animal animal = (Animal) card;
                for (int i = 0; i < animal.getPowers().size() && i < 3; i++)
                {
                    String nomPouvoir = animal.getPowers().get(i).getClass().getSimpleName();
                    if (nomPouvoir.length() > 9) { nomPouvoir = nomPouvoir.substring(0, 9); }
                    pouvoirs[i] = String.format("%-9s", nomPouvoir);
                }
            }

            lines[0]  = C + "╔═══════════╗" + RESET;
            lines[1]  = C + "║ " + nom    + " ║" + RESET;
            lines[2]  = C + "╠═══════════╣" + RESET;
            lines[3]  = C + "║ " + pv     + "║" + RESET;
            lines[4]  = C + "║ " + att    + "║" + RESET;
            lines[5]  = C + "║ " + volant + " ║" + RESET;
            lines[6]  = C + "╠═══════════╣" + RESET;
            lines[7]  = C + "║ " + pouvoirs[0] + " ║" + RESET;
            lines[8]  = C + "║ " + pouvoirs[1] + " ║" + RESET;
            lines[9]  = C + "║ " + pouvoirs[2] + " ║" + RESET;
            lines[10] = C + "╚═══════════╝" + RESET;
        }

        return lines;
    }

    // Fonction privée qui assemble 4 boîtes de cartes côte à côte sur 11 lignes
    // Cela permet d'afficher une rangée entière du plateau en une seule fois
    private String[] renderRow(Card[] cards, String[] positions)
    {
        // On génère le rendu (11 lignes) de chacune des 4 cartes
        String[][] render = new String[4][];
        for (int i = 0; i < 4; i++)
        {
            render[i] = renderCard(cards[i], positions[i]);
        }

        // On assemble les 4 cartes ligne par ligne en les séparant de 2 espaces
        String[] result = new String[11];
        for (int ligne = 0; ligne < 11; ligne++)
        {
            result[ligne] = render[0][ligne] + "  "
                    + render[1][ligne] + "  "
                    + render[2][ligne] + "  "
                    + render[3][ligne];
        }

        return result;
    }

    // Fonction privée qui affiche une rangée assemblée avec une indentation de 4 espaces
    private void printRow(String[] row)
    {
        for (String line : row)
        {
            System.out.println("    " + line); // Indentation pour centrer visuellement le plateau
        }
    }

    public void displayEndOfRound(int manche)
    {
        System.out.println("\n===========================================");
        System.out.println("   FIN DE LA MANCHE " + manche + " - PRÉPARATION...");
        System.out.println("===========================================\n");
    }

    // Affiche le résultat final de la partie
    public void displayEndOfGame(boolean victory)
    {
        System.out.println("\n===========================================");
        if (victory)
        {
            System.out.println("   VICTOIRE TOTALE ! Vous avez survécu.");
        }
        else
        {
            System.out.println("   GAME OVER... Votre âme m'appartient.");
        }
        System.out.println("===========================================\n");
    }
}