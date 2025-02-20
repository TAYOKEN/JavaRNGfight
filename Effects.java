package jeu;

public record Effects(int id, Types type, String description, int effiency) {
	public Types getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public int getEffiency() {
		return effiency;
	}
}
