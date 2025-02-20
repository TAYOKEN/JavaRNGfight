package jeu;

public class Game {
    private final Player player;
    private final Enemy enemy;
    private final Inventory inventory;

    public Game(Player player, Enemy enemy, Inventory inventory) {
        this.player = player;
        this.enemy = enemy;
        this.inventory = inventory;
    }

    public void startCombat() {
        System.out.println("Combat starts!");

        while (player.isAlive() && enemy.isAlive()) {
            player.setTurn(true);
            player.attack(enemy);

            if (!enemy.isAlive()) {
                System.out.println("Enemy defeated!");
                break;
            }

            enemy.setTurn(true);
            enemy.attack(player);

            if (!player.isAlive()) {
                System.out.println("Player defeated!");
                break;
            }
        }
    }
}