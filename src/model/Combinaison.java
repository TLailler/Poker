package model;

public class Combinaison implements Comparable{

	private Carte[] cartes;
	
	private Combinaisons valeur;
	
	private Carte hauteur;	
	
	public Combinaison(Carte[] cartes, Combinaisons valeur){
		this.cartes = cartes;
		this.valeur = valeur;
		this.hauteur = this.determinerHauteur();
	}
	
	public Carte determinerHauteur(){
		Carte haut = null;
		for(Carte c : this.cartes){
			if(haut == null){
				haut = c;
			}else{
				if(haut.getValeur().getValue() < c.getValeur().getValue()){
					haut = c;
				}
			}
		}
		return haut;	
	}
	
	public Carte[] getCartes() {
		return cartes;
	}

	public void setCartes(Carte[] cartes) {
		this.cartes = cartes;
	}

	public Combinaisons getValeur() {
		return valeur;
	}

	public void setValeur(Combinaisons valeur) {
		this.valeur = valeur;
	}

	public Carte getHauteur() {
		return hauteur;
	}

	public void setHauteur(Carte hauteur) {
		this.hauteur = hauteur;
	}
	
	public String toString(){
		return (valeur.name() + " à hauteur de " + hauteur.getValeur().name());
	}

	@Override
	public int compareTo(Object o) {
		Combinaison c = (Combinaison)o;
		if(this.getValeur().getVal() > c.getValeur().getVal()){
			return 1;
		}else if(this.getValeur().getVal() == c.getValeur().getVal()){
			if(this.getHauteur().getValeur().getValue() > c.getHauteur().getValeur().getValue()){
				return 1;
			}else if(this.getHauteur().getValeur().getValue() == c.getHauteur().getValeur().getValue()){
				return 0;
			}else{
				return -1;
			}
		}else{
			return -1;
		}
	}
	
	
}
 