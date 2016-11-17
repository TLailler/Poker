package model;

public class Carte implements Comparable<Carte> {	
	
	private Valeurs valeur;
	
	private Couleurs couleur;
	
	private boolean isOuverte;
	

	public Valeurs getValeur() {
		return valeur;
	}

	public void setValeur(Valeurs valeur) {
		this.valeur = valeur;
	}

	public Couleurs getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleurs couleur) {
		this.couleur = couleur;
	}

	public boolean isOuverte() {
		return isOuverte;
	}

	public void setOuverte(boolean isOuverte) {
		this.isOuverte = isOuverte;
	}
	
	public Carte(Valeurs val, Couleurs coul){
		this.valeur = val;
		this.couleur = coul;
	}

	@Override
	public String toString() {
		return "Carte [valeur=" + valeur.name() + ", couleur=" + couleur.name() + "]";
	}

	@Override
	public int compareTo(Carte o) {
		if(this.getValeur().getValue() > o.getValeur().getValue()){
			return 1;
		}else if(this.getValeur().getValue() == o.getValeur().getValue()){
			return 0;
		}else{
			return -1;
		}
	}

}
