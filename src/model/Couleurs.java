package model;

public enum Couleurs {

	COEUR("coeur"),
	TREFLE("trèfle"),
	PIQUE("pique"),
	CARREAU("carreau");
	
	private String name;
	
	Couleurs(String name){
		this.name= name;
	}
	
	public String getName() {
		return name;
	}


	
	public String toString(){
		return this.name();
	}
	
}
