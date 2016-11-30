package model;

public class Main {

	private Combinaison combinaison;
	
	private Carte[] cartes;

	public Main() {
		super();
		this.cartes = new Carte[2];
	}

	public Main(Carte[] cartes) {
		super();
		this.cartes = cartes;
	}

	public Carte[] getCartes() {
		return cartes;
	}

	public void setCartes(Carte[] cartes) {
		this.cartes = cartes;
	}

	public Combinaison getCombinaison() {
		return combinaison;
	}

	public void setCombinaison(Combinaison combinaison) {
		this.combinaison = combinaison;
	}
	
	public String toString(){
		if(this.getCombinaison() != null){
			return(this.getCombinaison().toString());
		}else{
			return(cartes[0].toString() + " - " + cartes[1].toString());

		}
	}
	
}
