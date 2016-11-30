package model;

public class Combinaison implements Comparable<Combinaison>{

	private Carte[] cartes;
	
	private Combinaisons valeur;
	
	
	public Combinaison(Carte[] cartes, Combinaisons valeur){
		this.cartes = cartes;
		this.valeur = valeur;
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
	
	public String toString(){
		String s = "{" + cartes[0].toString();
		for(int i = 1; i < cartes.length; i++){
			s += " - " + cartes[i].toString();
		}
		s+= "}";
		return (valeur.name() + " : " + s);
	}

	@Override
	public int compareTo(Combinaison c) {
		if(this.getValeur().getVal() > c.getValeur().getVal()){
			return 1;
		}else if(this.getValeur().getVal() == c.getValeur().getVal()){
			//QFR -> Egalité
			if(this.getValeur() == Combinaisons.QUINTE_FLUSH_ROYALE){
				return 0;
			}
			//QF - Suite -> Comparer plus grande carte
			else if(this.getValeur() == Combinaisons.QUINTE_FLUSH || this.getValeur() == Combinaisons.SUITE){
				if(this.getCartes()[0].getValeur().getValue() > c.getCartes()[0].getValeur().getValue()){
					return 1;
				}else if(this.getCartes()[0].getValeur().getValue() < c.getCartes()[0].getValeur().getValue()){
					return -1;
				}else{
					return 0;
				}
			}
			//Couleur - Hauteur -> Comparer les premieres si encore égal les deuxiemes etc...
			else if(this.getValeur() == Combinaisons.COULEUR || this.getValeur() == Combinaisons.HAUTEUR){
				for(int i = 0; i < this.getCartes().length; i++){
					if(this.getCartes()[i].getValeur().getValue() > c.getCartes()[i].getValeur().getValue()){
						return 1;
					}else if(this.getCartes()[i].getValeur().getValue() < c.getCartes()[i].getValeur().getValue()){
						return -1;
					}
				}
				return 0;
			}
			//Paire -> Comparer hauteur, si egal faire comme hauteur
			else if(this.getValeur() == Combinaisons.PAIRE){
				if(this.getCartes()[0].getValeur().getValue() > c.getCartes()[0].getValeur().getValue()){
					return 1;
				}else if(this.getCartes()[0].getValeur().getValue() < c.getCartes()[0].getValeur().getValue()){
					return -1;
				}else{
					for(int i = 2; i < this.getCartes().length; i++){
						if(this.getCartes()[i].getValeur().getValue() > c.getCartes()[i].getValeur().getValue()){
							return 1;
						}else if(this.getCartes()[i].getValeur().getValue() < c.getCartes()[i].getValeur().getValue()){
							return -1;
						}
					}
					return 0;
				}
			}
			//Double Paire -> Comparer hauteur plus haute paire, si egal hauteur deuxieme, sinon hauteur derniere
			else if(this.getValeur() == Combinaisons.DEUX_PAIRES){
				if(this.getCartes()[0].getValeur().getValue() > c.getCartes()[0].getValeur().getValue()){
					return 1;
				}else if(this.getCartes()[0].getValeur().getValue() < c.getCartes()[0].getValeur().getValue()){
					return -1;
				}else{
					if(this.getCartes()[1].getValeur().getValue() > c.getCartes()[1].getValeur().getValue()){
						return 1;
					}else if(this.getCartes()[1].getValeur().getValue() < c.getCartes()[1].getValeur().getValue()){
						return -1;
					}else{
						if(this.getCartes()[4].getValeur().getValue() > c.getCartes()[4].getValeur().getValue()){
							return 1;
						}else if(this.getCartes()[4].getValeur().getValue() < c.getCartes()[4].getValeur().getValue()){
							return -1;
						}else{
							return 0;	
						}
						
					}	
				}
			}else if(this.getValeur() == Combinaisons.FULL || this.getValeur() == Combinaisons.CARRE){
				if(this.getCartes()[0].getValeur().getValue() > c.getCartes()[0].getValeur().getValue()){
					return 1;
				}else if(this.getCartes()[0].getValeur().getValue() < c.getCartes()[0].getValeur().getValue()){
					return -1;
				}else{
					if(this.getCartes()[4].getValeur().getValue() > c.getCartes()[4].getValeur().getValue()){
						return 1;
					}else if(this.getCartes()[4].getValeur().getValue() < c.getCartes()[4].getValeur().getValue()){
						return -1;
					}else{
						return 0;	
					}	
				}
			}else if(this.getValeur() == Combinaisons.BRELAN){
				if(this.getCartes()[0].getValeur().getValue() > c.getCartes()[0].getValeur().getValue()){
					return 1;
				}else if(this.getCartes()[0].getValeur().getValue() < c.getCartes()[0].getValeur().getValue()){
					return -1;
				}else{
					if(this.getCartes()[3].getValeur().getValue() > c.getCartes()[3].getValeur().getValue()){
						return 1;
					}else if(this.getCartes()[3].getValeur().getValue() < c.getCartes()[3].getValeur().getValue()){
						return -1;
					}else{
						if(this.getCartes()[4].getValeur().getValue() > c.getCartes()[4].getValeur().getValue()){
							return 1;
						}else if(this.getCartes()[4].getValeur().getValue() < c.getCartes()[4].getValeur().getValue()){
							return -1;
						}else{
							return 0;	
						}
						
					}	
				}
			}else{
				return 0;
			}
		}else{
			return -1;
		}
	}


	
	
}
 