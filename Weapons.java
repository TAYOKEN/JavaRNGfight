package jeu;

public record Weapons(int id, String name, double damage, Effects effect) implements Items {
	public Weapons(int id, String name, double damage) {
		this(id, name, damage, null);
	}
	@Override
	public String toString() {
		return "Name : " + name + " Damage Stats : " + damage + " Effets bonus et id : " + effect + " " + id + "\n";
	}
    @Override
	public int getId() {
		return id;
	}
    @Override
	public String getName() {
		return name;
	}
	public double getDamage() {
		return damage;
	}
	public Effects getEffect() {
		return effect;
	}
	public String Betterweapon(Weapons weapon) {
		double scoreI = 0;
		double scoreII = 0;
		if (damage > weapon.damage) {
			scoreI += damage - weapon.damage;
		} else {
			scoreII += weapon.damage - damage;
		}
		
		if (effect != null && weapon.effect == null) {
			scoreI += 1;
		} else if (effect == null && weapon.effect != null) {
			scoreII += 1;
		}
		
		if (scoreI > scoreII) {
			return "The best weapon is :" + name;
		} else if (scoreII > scoreI) {
			return "The best weapon is :" + weapon.name;
		} else {
			return "They're both equally good";
		}
	}
}
