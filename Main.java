import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> inventaire = new ArrayList<>();
        List<String> items = List.of("Sabre", "Bouclier", "Soin", "Magie");
        Random choix = new Random();
        Scanner scanner = new Scanner(System.in);
        int PVEnnemi = 20;
        int PvJoueur = 50;

        while (true) {
            System.out.println("\nFaites votre choix !");
            System.out.println("| Inventaire | Pioche | Quitter |");
            String utilisateur = scanner.nextLine().trim();

            if (utilisateur.equalsIgnoreCase("Pioche")) {
                if (!Rempli(inventaire)) {
                    String objetAleatoire = items.get(choix.nextInt(items.size())); 
                    inventaire.add(objetAleatoire);
                    System.out.println("Objet ajouté : " + objetAleatoire);
                } else {
                    System.out.println("Inventaire plein !");
                }

            } else if (utilisateur.equalsIgnoreCase("Inventaire")) {
                if (inventaire.isEmpty()) {
                    System.out.println("Votre inventaire est vide.");
                } else {
                    System.out.println("Voici votre inventaire actuel : " + inventaire);
                    System.out.println("Choisissez un objet à utiliser ou tapez 'Retour' pour revenir au menu principal.");
                    String action = scanner.nextLine().trim();

                    if (inventaire.contains(action)) {
                        if (action.equalsIgnoreCase("Sabre")) {
                            System.out.println("Vous infligez 5 PV à l'ennemi.");
                            PVEnnemi -= 5;
                            inventaire.remove(action);
                        } else if (action.equalsIgnoreCase("Bouclier")) {
                            System.out.println("Vous réduisez les dégâts de 2. (Non Implémenté)");
                            inventaire.remove(action);
                        } else if (action.equalsIgnoreCase("Soin")) {
                            System.out.println("Vous avez soigné 5 PV.");
                            PvJoueur += 5;
                            inventaire.remove(action);
                        } else if (action.equalsIgnoreCase("Magie")) {
                            System.out.println("Vous avez utilisé la magie !(Non Implémenté)");
                            inventaire.remove(action);
                        } else {
                            System.out.println("Action annulée.");
                        }
                    } else if (!action.equalsIgnoreCase("Retour")) {
                        System.out.println("Cet objet n'est pas dans votre inventaire.");
                    }
                }

            } else if (utilisateur.equalsIgnoreCase("Quitter")) {
                System.out.println("Fin du programme.");
                break;

            } else {
                System.out.println("Commande inconnue. Essayez encore.");
            }
        }

        scanner.close();
    }

    public static boolean Rempli(ArrayList<String> liste) {
        return liste.size() >= 5; 
    }
}