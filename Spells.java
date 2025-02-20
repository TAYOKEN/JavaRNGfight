package jeu;

public record Spells(int id, String name, Effects effect) implements Items {

    @Override
    // Getters explicites
    public int getId() {
        return id;
    }
    @Override
    public String getName() {
        return name;
    }

    public Effects getEffect() {
        return effect;
    }

}