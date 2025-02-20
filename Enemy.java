package jeu;

import java.util.Random;

public class Enemy {
    private int hp;
    private final Types type;
    private double attack;
    private boolean turn;
    private final Inventory inventory;

    public Enemy(Types type, double attack, int hp) {
        this.hp = hp;
        this.type = type;
        this.attack = attack;
        this.turn = false;
        this.inventory = new Inventory();
    }

    // Méthode pour attaquer le joueur
    public void attack(Player player) {
        if (turn) {
            player.takeDamage(attack);
            System.out.println("Enemy attacks player for " + attack + " damage!");
        } else {
            System.out.println("It's not the enemy's turn!");
        }
    }

    // Méthode pour subir des dégâts
    public void takeDamage(double damage) {
        hp -= damage;
        if (hp < 0) hp = 0; // Bloquer les PV négatifs
        System.out.println("[DEBUG] PV Ennemi après dégâts : " + hp); // Debug
    }
    

    // Méthode pour vérifier si l'ennemi est en vie
    public boolean isAlive() {
        return hp > 0;
    }

    // Méthode pour l'IA de l'ennemi
    public void enemyAI(Player player) {
        Random random = new Random();
        int choice = random.nextInt(2); // 0 ou 1

        if (choice == 0 || inventory.getItems().isEmpty()) {
            // Attaquer le joueur
            attack(player);
        } else {
            // Utiliser un sort (s'il en a)
            Spells spell = (Spells) inventory.getItems().get(0); // Utilise le premier sort de l'inventaire
            applyEffect(spell.getEffect());
            System.out.println("Enemy uses spell: " + spell.getName());
        }
    }

    // Méthode pour appliquer un effet à l'ennemi
    public void applyEffect(Effects effect) {
        switch (effect.getType()) {
            case Damage:
                hp -= effect.getEffiency(); // Inflige des dégâts
                System.out.println("Enemy takes " + effect.getEffiency() + " damage from effect!");
                break;
            case Poison:
                hp -= effect.getEffiency(); // Inflige des dégâts de poison
                System.out.println("Enemy takes " + effect.getEffiency() + " poison damage!");
                break;
            default:
                System.out.println("Unknown effect applied to enemy: " + effect.getType());
        }

        // Assurer que les points de vie ne deviennent pas négatifs
        if (hp < 0) {
            hp = 0;
        }
    }

    // Getters
    public int getHp() {
        return hp;
    }

    public Types getType() {
        return type;
    }

    public double getAttack() {
        return attack;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public Inventory getInventory() {
        return inventory;
    }
}