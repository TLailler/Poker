package model;

import java.util.ArrayList;

public class Lanceur {

	private static Joueur j1;
	private static Joueur j2;
	private static Joueur j3;
	static Paquet p;
	static Table t;
	
	public static void main(String[] args) {
				
		
		j1 = new Joueur("Thomas", 1500, "192.168.1.1", 8080, null);
		j2 = new Joueur("Leo", 1500, "195.168.1.2", 8080, null);
		j3 = new Joueur("Maurice", 1500,"195.168.1.1", 8080, j1);
		j1.setNext(j2);
		j2.setNext(j3);
			
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(j1);
		joueurs.add(j2);
		joueurs.add(j3);
		p = new Paquet();
			
		t = new Table(joueurs,1,2,p);
		
		t.lancerPartie();
	}
}
