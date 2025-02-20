package jeu;

public class Player {
    private int hp;
    private double attack;
    private boolean turn;
    private int xp;
    private int level;

    public Player(double attack) {
        this.hp = 20;
        this.attack = attack;
        this.turn = false;
        this.xp = 0;
        this.level = 1;
    }

    // Méthode pour attaquer un ennemi
    public void attack(Enemy enemy) {
        if (turn) {
            enemy.takeDamage(attack); // Applique les dégâts
            System.out.println("[DEBUG] Joueur attaque (Dégâts : " + attack + ")");
        }
    }

    // Méthode pour subir des dégâts
    public void takeDamage(double damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
        System.out.println("Player takes " + damage + " damage! HP: " + hp);
    }

    // Méthode pour vérifier si le joueur est en vie
    public boolean isAlive() {
        return hp > 0;
    }

    // Getters
    public int getHp() {
        return hp;
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

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }
    public void applyEffect(Effects effect) {
        switch (effect.getType()) {
            case Heal:
                hp += effect.getEffiency(); // Soigne le joueur
                System.out.println("Vous êtes soigné de " + effect.getEffiency() + " points de vie.");
                break;
            case Poison:
                hp -= effect.getEffiency(); // Inflige des dégâts de poison
                System.out.println("Vous subissez " + effect.getEffiency() + " points de dégâts de poison.");
                break;
            case Strength:
                attack += effect.getEffiency(); // Augmente l'attaque
                System.out.println("Votre attaque augmente de " + effect.getEffiency() + ".");
                break;
            case Weakness:
                attack -= effect.getEffiency(); // Réduit l'attaque
                System.out.println("Votre attaque diminue de " + effect.getEffiency() + ".");
                break;
            case Regeneration:
                hp += effect.getEffiency(); // Régénère les points de vie
                System.out.println("Vous régénérez " + effect.getEffiency() + " points de vie.");
                break;
            default:
                System.out.println("Effet inconnu : " + effect.getType());
        }

        // Assurer que les points de vie ne deviennent pas négatifs
        if (hp < 0) {
            hp = 0;
        }
    }
}