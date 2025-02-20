package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {
    private final ArrayList<Items> inventory = new ArrayList<>();

    public void add(Items item) {
        inventory.add(item);
    }

    public void remove(Items item) {
        inventory.remove(item);
    }

    public boolean contains(Items item) {
        return inventory.contains(item);
    }

    public String showItems() {
        StringBuilder list = new StringBuilder();
        for (Items element : inventory) {
            list.append(element).append("\n");
        }
        return list.toString();
    }

    public int size() {
        return inventory.size();
    }
    
    public List<Items> getItems() {
        return Collections.unmodifiableList(inventory);
    }
}