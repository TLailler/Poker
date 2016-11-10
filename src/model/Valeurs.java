package model;

public enum Valeurs {

	AS(14),
	DEUX(2),
	TROIS(3),
	QUATRE(4),
	CINQ(5),
	SIX(6),
	SEPT(7),
	HUIT(8),
	NEUF(9),
	DIX(10),
	VALET(11),
	DAME(12),
	ROI(13);
	
	private int value;
	
	Valeurs(int i){
		this.value = i;
	}

	public int getValue() {
		return value;
	}

}
