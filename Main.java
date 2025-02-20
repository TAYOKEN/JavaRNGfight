package jeu;

public class Main {
    public static void main(String[] args) {
        // Créer un joueur et un ennemi
        Player player = new Player(20);
        Enemy enemy = new Enemy(Types.Damage, 3.0, 15);

        // Créer un inventaire
        Inventory inventory = new Inventory();

        // Ajouter des objets à l'inventaire
        Effects fireEffect = new Effects(1, Types.Poison, "Poison the enemy", 10);
        Spells fireball = new Spells(1, "Fireball", fireEffect);
        inventory.add(fireball);

        // Afficher l'inventaire
        System.out.println("Inventory:");
        System.out.println(inventory.showItems());

        // Démarrer le jeu
        Game game = new Game(player, enemy, inventory);
        game.startCombat();
    }
}