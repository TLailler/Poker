package model;

public enum Combinaisons {

	HAUTEUR(0),
	PAIRE(1),
	DEUX_PAIRES(2),
	BRELAN(3),
	SUITE(4),
	COULEUR(5),
	FULL(6),
	CARRE(7),
	QUINTE_FLUSH(8),
	QUINTE_FLUSH_ROYALE(9);
	
	private int val;
	
	Combinaisons(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}
	
}
