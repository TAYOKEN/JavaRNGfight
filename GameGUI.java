package jeu;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class GameGUI extends JFrame {
    private static final long serialVersionUID = -8266525361467979809L;
    private final Player player;
    private final Enemy enemy;
    private final Inventory inventory;
    private boolean isPlayerTurn = true;

    // Composants UI
    private JTextArea gameLog;
    private JProgressBar playerHpBar;
    private JProgressBar enemyHpBar;
    private JButton attackButton;
    private JButton useSpellButton;
    private JButton inventoryButton; // Nouveau bouton d'inventaire

    public GameGUI(Player player, Enemy enemy, Inventory inventory) {
        this.player = player;
        this.enemy = enemy;
        this.inventory = inventory;

        setupUI();
    }

    private void setupUI() {
        // Configuration de la fenêtre
        setTitle("Combat Fantaisiste");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(30, 30, 30));

        // Panel supérieur pour les PV
        JPanel hpPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        hpPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        hpPanel.setBackground(new Color(45, 45, 45));

        // Barre de PV du joueur
        playerHpBar = createHpBar(player.getHp(), new Color(0, 200, 0));
        JPanel playerPanel = createCharacterPanel("Joueur", playerHpBar);
        hpPanel.add(playerPanel);

        // Barre de PV de l'ennemi
        enemyHpBar = createHpBar(enemy.getHp(), new Color(200, 0, 0));
        JPanel enemyPanel = createCharacterPanel("Ennemi", enemyHpBar);
        hpPanel.add(enemyPanel);

        add(hpPanel, BorderLayout.NORTH);

        // Zone de log du jeu
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Consolas", Font.PLAIN, 14));
        gameLog.setForeground(Color.WHITE);
        gameLog.setBackground(new Color(20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(gameLog);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBackground(new Color(45, 45, 45));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        attackButton = createStyledButton("Attaquer", new Color(70, 130, 180));
        useSpellButton = createStyledButton("Utiliser un sort", new Color(147, 112, 219));
        inventoryButton = createStyledButton("Inventaire", new Color(255, 165, 0));

        attackButton.addActionListener(this::playerAttack);
        useSpellButton.addActionListener(this::useSpell);
        inventoryButton.addActionListener(this::showInventory);

        buttonPanel.add(attackButton);
        buttonPanel.add(useSpellButton);
        buttonPanel.add(inventoryButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Afficher la fenêtre
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JProgressBar createHpBar(int value, Color color) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(value);
        bar.setStringPainted(true);
        bar.setForeground(color);
        bar.setBackground(new Color(60, 60, 60));
        bar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        bar.setFont(new Font("Arial", Font.BOLD, 12));
        return bar;
    }

    private JPanel createCharacterPanel(String title, JProgressBar hpBar) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(new Color(45, 45, 45));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(hpBar, BorderLayout.CENTER);

        return panel;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        return button;
    }

    private void playerAttack(ActionEvent e) {
        if (isPlayerTurn) {
            player.attack(enemy);
            enemy.takeDamage(player.getAttack());
            updateHpBars();
            updateGameLog("Vous attaquez l'ennemi !");
            checkCombatStatus();
            isPlayerTurn = false;
            enemyTurn();
        } else {
            updateGameLog("Ce n'est pas votre tour !");
        }
    }

    private void useSpell(ActionEvent e) {
        if (isPlayerTurn) {
            if (!inventory.getItems().isEmpty()) {
                Spells spell = (Spells) inventory.getItems().get(0);
                player.applyEffect(spell.getEffect());
                updateHpBars();
                updateGameLog("Vous utilisez le sort : " + spell.getName());
                checkCombatStatus();
                isPlayerTurn = false;
                enemyTurn();
            } else {
                updateGameLog("Aucun sort disponible !");
            }
        } else {
            updateGameLog("Ce n'est pas votre tour !");
        }
    }

    private void enemyTurn() {
        if (enemy.isAlive()) {
            enemy.setTurn(true);
            enemy.enemyAI(player);
            updateHpBars();
            updateGameLog("L'ennemi contre-attaque !");
            checkCombatStatus();
            isPlayerTurn = true;
        }
    }

    private void updateHpBars() {
        playerHpBar.setValue(player.getHp());
        enemyHpBar.setValue(enemy.getHp());
        playerHpBar.repaint();
        enemyHpBar.repaint();
    }

    private void checkCombatStatus() {
        if (!player.isAlive() || !enemy.isAlive()) {
            endGame();
        }
    }

    private void endGame() {
        attackButton.setEnabled(false);
        useSpellButton.setEnabled(false);
        inventoryButton.setEnabled(false);
        updateGameLog("\n=== COMBAT TERMINÉ ===");
        if (!player.isAlive()) {
            updateGameLog("Vous avez été vaincu...");
        } else {
            updateGameLog("Victoire ! L'ennemi est vaincu !");
        }
    }

    private void updateGameLog(String message) {
        gameLog.append("> " + message + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }

    private void showInventory(ActionEvent e) {
        JDialog inventoryDialog = new JDialog(this, "Inventaire", false);
        inventoryDialog.setSize(300, 400);
        inventoryDialog.setLayout(new BorderLayout());
        inventoryDialog.getContentPane().setBackground(new Color(30, 30, 30));

        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        contentArea.setForeground(Color.WHITE);
        contentArea.setBackground(new Color(20, 20, 20));

        StringBuilder sb = new StringBuilder();
        sb.append("=== Contenu de l'inventaire ===\n\n");
        
        if(inventory.getItems().isEmpty()) {
            sb.append("Votre inventaire est vide !");
        } else {
            for(Items item : inventory.getItems()) {
                if(item instanceof Spells) {
                    Spells spell = (Spells) item;
                    sb.append("- ").append(spell.name())
                      .append(" (")
                      .append(spell.effect().description())
                      .append(")\n");
                } else {
                    sb.append("- ").append(item.toString()).append("\n");
                }
            }
        }
        
        contentArea.setText(sb.toString());
        
        JButton closeButton = createStyledButton("Fermer", new Color(70, 130, 180));
        closeButton.addActionListener(ev -> inventoryDialog.dispose());

        inventoryDialog.add(new JScrollPane(contentArea), BorderLayout.CENTER);
        inventoryDialog.add(closeButton, BorderLayout.SOUTH);
        inventoryDialog.setLocationRelativeTo(this);
        inventoryDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Player player = new Player(20);
            Enemy enemy = new Enemy(Types.Poison, 3.0, 30);
            Inventory inventory = new Inventory();

            Effects fireEffect = new Effects(1, Types.Damage, "Boule de feu", 15);
            Effects healEffect = new Effects(2, Types.Heal, "Soin", 15);
            Effects poisonEffect = new Effects(3, Types.Poison, "Poison", 8);
            Effects strengthEffect = new Effects(4, Types.Strength, "Potion de Force", 5);
            Effects weakEffect = new Effects(5, Types.Weakness, "Affaiblissement", 2);
            Effects regenEffect = new Effects(6, Types.Regeneration, "Regen", 9);

            // Ajout des sorts à l'inventaire
            inventory.add(new Spells(1, "Fireball", fireEffect));
            inventory.add(new Spells(2, "Soin", healEffect));
            inventory.add(new Spells(3, "Poison", poisonEffect));
            inventory.add(new Spells(4, "Potion de Force", strengthEffect));
            inventory.add(new Spells(5, "Affaiblissement", weakEffect));
            inventory.add(new Spells(6, "Regen", regenEffect));
            
            new GameGUI(player, enemy, inventory);
        });
    }
}