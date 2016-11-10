package model;

import java.util.ArrayList;

public class Paquet {
	
	private ArrayList<Carte> paquet;
	
	public Paquet(){
		this.paquet = new ArrayList<Carte>();		
		for(Couleurs c: Couleurs.values()){
			for(Valeurs v : Valeurs.values()){
				paquet.add(new Carte(v,c));
			}
		}
	}
	
	public void melanger(){
		
		ArrayList<Carte> paquetMelange = new ArrayList<Carte>();
		while(!paquet.isEmpty()){
			Double d = (Math.random()*(paquet.size()));
			int index = d.intValue();
			paquetMelange.add(paquet.get(index));
			paquet.remove(index);
		}
		paquet = paquetMelange;
	}
	
	public Carte tirerCarte(){
		Carte c = paquet.get(0);
		paquet.remove(0);
		return c;
	}

	@Override
	public String toString() {
		return "Paquet [taille=" + paquet.size() + " paquet=" + paquet + "]";
	}
	
	

}
