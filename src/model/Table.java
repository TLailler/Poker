package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Table {

	private ArrayList<Joueur> joueurs;
	
	private Joueur current;
		
	private int pot;
	
	private int smallBlind;
	
	private int bigBlind;
	
	private int miseIndiv;
	
	private Carte[] flop;
	
	private Carte turn;	
	
	private Carte river;
	
	private Paquet paquet;
	

	public Table(ArrayList<Joueur> joueurs, int smallBlind, int bigBlind, Paquet paquet) {
		super();
		this.joueurs = joueurs;
		this.pot = 0;
		this.smallBlind = smallBlind;
		this.bigBlind = bigBlind;
		this.paquet = paquet;
		this.flop = new Carte[3];
	}
	
	public void lancerPartie(){
		System.out.println("////////////////// Début de la partie //////////////////");
		int index = (int)(Math.random()*(joueurs.size()));
		joueurs.get(index).setDealer(true);
		joueurs.get(index).getNext().setSmallBlind(true);
		joueurs.get(index).getNext().getNext().setBigBlind(true);
		
		this.setCurrent(joueurs.get(index));
		
		if(this.nbJoueursValides() > 0){
			this.lancerManche();
		}
	}
	
	public void lancerManche(){
		System.out.println("////////////////// Début de la manche //////////////////");
		paquet.melanger();
		this.setPot(0);
		this.decalerBlinds();
		System.out.println("Dealer : " + this.getDealerPlayer().getNom());
		System.out.println("Petite Blind : " + this.getSmallBlindPlayer().getNom());
		System.out.println("Grosse Blind : " + this.getBigBlindPlayer().getNom());
		this.current = this.getBigBlindPlayer().getNext();
		this.distribuerJoueurs();
		System.out.println("Mises obligatoires ...");
		this.misesObligatoires();
		this.encheres();
		this.distribuerFlop();
		this.encheres();
		this.distribuerTurn();
		this.encheres();
		this.distribuerRiver();
		this.encheres();
		this.terminerManche();
	}

	public void ajouterAuPot(int montant){
		this.setPot(this.pot+montant);
	}
	
	public Joueur getDealerPlayer(){
		Joueur ret = null;
		for(Joueur j : joueurs){
			if(j.isDealer()){
				ret = j;
			}
		}
		return ret;
	}
	
	public Joueur getSmallBlindPlayer(){
		Joueur ret = null;
		for(Joueur j : joueurs){
			if(j.isSmallBlind()){
				ret = j;
			}
		}
		return ret;
	}
	
	public Joueur getBigBlindPlayer(){
		Joueur ret = null;
		for(Joueur j : joueurs){
			if(j.isBigBlind()){
				ret = j;
			}
		}
		return ret;
	}
	
	private void misesObligatoires() {
		Joueur jS = this.getSmallBlindPlayer();
		jS.miser(this.getSmallBlind());
		System.out.println(jS.getNom() +" mise " + this.getSmallBlind() + " jetons pour la small Blind");
		Joueur jB = this.getBigBlindPlayer();
		jB.miser(this.bigBlind);
		System.out.println(jB.getNom() +" mise " + this.getBigBlind() + " jetons pour la big Blind");
		this.ajouterAuPot(this.getSmallBlind() + this.getBigBlind());
		this.setMiseIndiv(this.getBigBlind());
	}
	

	public void encheres(){
		System.out.println("Début du tour d'enchères...");
		boolean misesEgales = false;
		boolean premiereMise = true;
		int miseMax = this.getMiseIndiv();
		while(!misesEgales && nbJoueursNonCouches() > 1 && nbJoueursNonTapis() > 0){
				for(int i = 0; i < joueurs.size(); i++){
					if(!this.current.isAllIn() && !this.current.isFolded()){
						System.out.println("Au tour de "+ this.getCurrent().getNom() + " de miser");
						System.out.println("Mise actuelle : "+ this.getCurrent().getMise());
						
						String action = this.current.choisirAction(miseMax, premiereMise);
						
						if(action.equals("S")){ // Suivre
							if(this.current.getMise() + this.current.getJetons() < miseMax){
								this.setPot(this.getPot()+ this.getCurrent().getJetons());
								this.getCurrent().miser(this.getCurrent().getJetons());
								premiereMise = false;
							}else{
								this.setPot(this.getPot()+miseMax - this.getCurrent().getMise());
								this.getCurrent().miser(miseMax - this.getCurrent().getMise());
								premiereMise = false;
							}												
						}else if(action.equals("F")){//Se Coucher
							this.getCurrent().setFolded(true);
							
						}else if(action.matches("^R\\d+")){// Relance
							Integer chiffre = Integer.parseInt(action.substring(1,action.length()));
							this.setPot(this.getPot() + miseMax - this.getCurrent().getMise() + chiffre);
							this.setMiseIndiv(miseMax - this.getCurrent().getMise() + chiffre);
							this.getCurrent().miser(miseMax - this.getCurrent().getMise() + chiffre);
							miseMax = this.getCurrent().getMise();
							premiereMise = false;
							
						}else if(action.equals("A")){ //All in
							this.setPot(this.getPot() + this.getCurrent().getJetons());
							this.setMiseIndiv(this.getCurrent().getJetons());
							this.getCurrent().miser(this.getCurrent().getJetons());
							miseMax = this.getCurrent().getMise();
							premiereMise = false;
							this.getCurrent().setAllIn(true);
							
						}else if(action.equals("C")){ //Call
							this.setPot(this.getPot()+this.getMiseIndiv());
							this.getCurrent().miser(this.getMiseIndiv());
							miseMax = this.getCurrent().getMise();
							premiereMise = false;
							
						}else if(action.equals("K")){ //Check
							
						}
						if(this.getCurrent().getJetons() == 0){
							this.getCurrent().setAllIn(true);
						}
						misesEgales = this.verifierMisesEgales(miseMax);
					}	
					this.current = this.current.getNext();					
				}
		}
	}
	
	public Combinaison determinerCombinaison(Joueur j){
		//Creer une arrayList triée avec toutes les cartes
		ArrayList<Carte> cartesTriees = new ArrayList<Carte>();		
		for(Carte c2 : j.getMain().getCartes()){
			cartesTriees.add(c2);
		}
		if(this.getFlop() != null){
			for(Carte c : this.getFlop()){
				cartesTriees.add(c);
			}
		}
		if(this.getTurn() != null){
			cartesTriees.add(this.getTurn());
		}
		if(this.getRiver() != null){
			cartesTriees.add(this.getRiver());
		}
		Collections.sort(cartesTriees, Collections.reverseOrder());
		
		//Verifier si quinte flush, sinon descendre jusqu'a la paire

		//Quinte Flush Royale
		for(int i = 0; i < 3; i++){
			Carte carte = cartesTriees.get(i);
			if(carte.getValeur() == Valeurs.AS){
				ArrayList<Carte> quinteFlushRoyale = new ArrayList<Carte>();
				quinteFlushRoyale.add(carte);
				for(int k = i+1; k < cartesTriees.size(); k++){
					if(cartesTriees.get(k).getValeur().getValue() == carte.getValeur().getValue()-1 && cartesTriees.get(k).getCouleur().getName().equals(carte.getCouleur().getName())){
						quinteFlushRoyale.add(cartesTriees.get(k));
						carte = cartesTriees.get(k);
					}
				}				
				if(quinteFlushRoyale.size() == 5){
					return new Combinaison(quinteFlushRoyale.toArray(new Carte[5]), Combinaisons.QUINTE_FLUSH_ROYALE);
				}
			}
		}
		
		//Quinte Flush
		for(int i = 0; i < 3; i++){
			Carte carte = cartesTriees.get(i);
			ArrayList<Carte> quinteFlush = new ArrayList<Carte>();
			quinteFlush.add(carte);
			for(int k = i+1; k < cartesTriees.size(); k++){
				if(cartesTriees.get(k).getValeur().getValue() == carte.getValeur().getValue()-1 && cartesTriees.get(k).getCouleur().getName().equals(carte.getCouleur().getName())){
					quinteFlush.add(cartesTriees.get(k));
					carte = cartesTriees.get(k);
				}
			}				
			if(quinteFlush.size() == 5){
				return new Combinaison(quinteFlush.toArray(new Carte[5]), Combinaisons.QUINTE_FLUSH);
			}
		}
		
		//Carré
		for(int k = 0; k < 4; k++){
			Carte carte = cartesTriees.get(k);
			ArrayList<Carte> carre = new ArrayList<Carte>();
			carre.add(carte);
			for(Carte c : cartesTriees){
				if(c.getValeur().getValue() == carte.getValeur().getValue() && !c.getCouleur().getName().equals(carte.getCouleur().getName())){
					carre.add(c);
				}
			}
			if(carre.size() == 4){
				//Ajouter meilleure carte
				for(int i = cartesTriees.size()-1; i > -1; i--){
					if(cartesTriees.get(i).getValeur().getValue() != carre.get(0).getValeur().getValue()){
						carre.add(cartesTriees.get(i));
						return new Combinaison(carre.toArray(new Carte[5]), Combinaisons.CARRE);
					}
				}	
			}		
		}
		
		//Full
		for(int i = 0; i < 5; i++){
			Carte carte = cartesTriees.get(i);
			ArrayList<Carte> cartes = new ArrayList<Carte>(cartesTriees);
			ArrayList<Carte> full = new ArrayList<Carte>();
			full.add(carte);
			cartes.remove(carte);
			Iterator<Carte> it = cartes.iterator();
			while(it.hasNext()){
				Carte c = it.next();
				if(c.getValeur().getValue() == carte.getValeur().getValue() && !c.getCouleur().getName().equals(carte.getCouleur().getName())){
					full.add(c);
					it.remove();	
				}
			}
			if(full.size() == 3){
				for(Carte c2 : cartes){
					for(Carte c3 : cartes){
						if(c2.getValeur().getValue() == c3.getValeur().getValue() && !c2.getCouleur().getName().equals(c3.getCouleur().getName())){
							full.add(c2);
							full.add(c3);
							break;
						}
					}
					if(full.size() == 5){
						return new Combinaison(full.toArray(new Carte[5]), Combinaisons.FULL);
					}					
				}
			}		
		}
		
		//Couleur
		for(int i = 0; i < 3; i++){
			ArrayList<Carte> cartes = new ArrayList<>(cartesTriees);
			ArrayList<Carte> flush = new ArrayList<Carte>();
			flush.add(cartes.get(i));
			for(Carte carte : cartes){
				if(carte.getValeur().getValue() != cartes.get(i).getValeur().getValue() && carte.getCouleur().getName().equals(cartes.get(i).getCouleur().getName())){
					flush.add(carte);
					if(flush.size() == 5){
						return new Combinaison(flush.toArray(new Carte[5]), Combinaisons.COULEUR);
					}				
				}
			}
		}
		
		//Suite
		for(int i = 0; i < 3; i++){
			Carte carte = cartesTriees.get(i);
			ArrayList<Carte> quinteFlush = new ArrayList<Carte>();
			quinteFlush.add(carte);
			for(int k = i+1; k < cartesTriees.size(); k++){
				if(cartesTriees.get(k).getValeur().getValue() == carte.getValeur().getValue()-1){
					quinteFlush.add(cartesTriees.get(k));
					carte = cartesTriees.get(k);
				}
			}				
			if(quinteFlush.size() == 5){
				return new Combinaison(quinteFlush.toArray(new Carte[5]), Combinaisons.SUITE);
			}
		}
		
		//Brelan
		for(int i = 0; i < 5; i++){
			Carte carte = cartesTriees.get(i);
			ArrayList<Carte> cartes = new ArrayList<>(cartesTriees);
			ArrayList<Carte> brelan = new ArrayList<Carte>();
			brelan.add(carte);
			Iterator<Carte> it = cartes.iterator();
			while(it.hasNext()){
				Carte c = it.next();
				if(c.getValeur().getValue() == carte.getValeur().getValue() && !c.getCouleur().getName().equals(carte.getCouleur().getName())){
					brelan.add(c);
					it.remove();
				}
			}
			if(brelan.size() == 3){
				for(Carte c2 : cartes){
					brelan.add(c2);
					if(brelan.size() == 5){
						return new Combinaison(brelan.toArray(new Carte[5]), Combinaisons.BRELAN);
					}					
				}
			}		
		}
		
		//Double Paire
		for(int i = 0; i < 4; i++){
			Carte carte = cartesTriees.get(i);
			ArrayList<Carte> cartes = new ArrayList<>(cartesTriees);
			ArrayList<Carte> doublePaire = new ArrayList<Carte>();
			doublePaire.add(carte);
			cartes.remove(carte);
			Iterator<Carte> it = cartes.iterator();
			while(it.hasNext()){
				Carte c = it.next();
				if(c.getValeur().getValue() == carte.getValeur().getValue() && !c.getCouleur().getName().equals(carte.getCouleur().getName())){
					doublePaire.add(c);
					it.remove();
					break;
				}
			}
			if(doublePaire.size() == 2){
				Iterator<Carte> it2 = cartes.iterator();
				while(it2.hasNext()){
					Carte c2 = it2.next();
					doublePaire.add(c2);
					Iterator<Carte> it3 = cartes.iterator();
					Carte cSave = null;
					while(it3.hasNext()){
						Carte c3 = it3.next();
						if(c2.getValeur().getValue() == c3.getValeur().getValue() && !c2.getCouleur().getName().equals(c3.getCouleur().getName())){
							cSave = c3;
							doublePaire.add(c3);
							break;
						}
					}
					if(doublePaire.size() == 4){
						int in = 0;
						while(doublePaire.size() < 5){
							if(!(cartes.get(in) == c2 || cartes.get(in) == cSave)){
								doublePaire.add(cartes.get(in));
								break;
							}
						}					
						return new Combinaison(doublePaire.toArray(new Carte[5]), Combinaisons.DEUX_PAIRES);
					}else{
						doublePaire.remove(c2);
					}
				}
			}		
		}
		
		//Paire
		for(int i = 0; i < 7; i++){
			Carte carte = cartesTriees.get(i);
			ArrayList<Carte> cartes = new ArrayList<>(cartesTriees);
			ArrayList<Carte> doublePaire = new ArrayList<Carte>();
			doublePaire.add(carte);
			cartes.remove(carte);
			Iterator<Carte> it = cartes.iterator();
			while(it.hasNext()){
				Carte c = it.next();
				if(c.getValeur().getValue() == carte.getValeur().getValue() && !c.getCouleur().getName().equals(carte.getCouleur().getName())){
					doublePaire.add(c);
					it.remove();
					break;
				}
			}
			if(doublePaire.size() == 2){
				for(Carte c2 : cartes){
					doublePaire.add(c2);					
					if(doublePaire.size() == 5){
						return new Combinaison(doublePaire.toArray(new Carte[5]), Combinaisons.PAIRE);
					}					
				}
			}		
		}
		
		//Garder les meilleures cartes restantes pour faire 5 cartes
		cartesTriees.remove(cartesTriees.size()-1);
		cartesTriees.remove(cartesTriees.size()-1);
		return new Combinaison(cartesTriees.toArray(new Carte[cartesTriees.size()]), Combinaisons.HAUTEUR);
	}
	
	private void terminerManche() {
		// Montrer les cartes restantes
		System.out.println("Dévoilement des cartes des joueurs restants : ");
		for (Joueur j : joueurs){
			if(!j.isFolded()){
				j.getMain().setCombinaison(this.determinerCombinaison(j));
			}
		}
		ArrayList<Joueur> gagnant = new ArrayList<Joueur>();
		
		for(Joueur j : joueurs){
			if(!j.isFolded()){
				j.montrerMain();
				if(gagnant.size() > 0){
					if(gagnant.get(0).getMain().getCombinaison().compareTo(j.getMain().getCombinaison()) == -1){
						gagnant = new ArrayList<Joueur>();
						gagnant.add(j);
					}else if (gagnant.get(0).getMain().getCombinaison().compareTo(j.getMain().getCombinaison()) == 0){
						gagnant.add(j);
					}
				}else{
					gagnant.add(j);
				}
				
			}
		}
		
		//Les gagnants sont dans l'ArrayList gagnants
		System.out.println("Gagnant(s) de la manche : ");
		//Remise des gains
		int prix = this.getPot()/gagnant.size();
		for(Joueur j : gagnant){
			System.out.println(j.getNom() + " avec " + j.getMain().getCombinaison().toString());
			j.verserGains(prix);
		}
		if(gagnant.size() > 1)
			System.out.println("Chaque joueur remporte " + prix + " jetons");
		else
			System.out.println(gagnant.get(0).getNom() + " remporte " + prix + " jetons");
		
		//Vider la table
		this.viderTable();
		
		//Nouvelle manche
		if(nbJoueursValides() > 1)
			this.lancerManche();
		else
			this.terminerPartie();
	}
	
	private void terminerPartie(){
		System.out.println(joueurs.get(0).getNom() + " a remporté la partie !");
	}
	
	private boolean verifierMisesEgales(int mise) {
		boolean ret = true;
		for(Joueur j : joueurs){
			if(j.getMise() != mise && !j.isFolded()){
				ret = false;
			}
		}
		return ret;
	}

	private int nbJoueursValides() {
		return this.joueurs.size();
	}
	
	private int nbJoueursNonCouches(){
		int i = 0;
		for(Joueur j : joueurs){
			if(!j.isFolded()){
				i++;
			}
		}
		return i;
	}
	
	private int nbJoueursNonTapis(){
		int i = 0;
		for(Joueur j : joueurs){
			if(!j.isAllIn()){
				i++;
			}
		}
		return i;
	}
	
	private void decalerBlinds() {
		for(Joueur j : joueurs){
			if(j.isDealer()){
				j.setDealer(false);
				j.getNext().setDealer(true);
				j.getNext().setSmallBlind(false);
				j.getNext().getNext().setSmallBlind(true);
				j.getNext().getNext().setBigBlind(false);
				j.getNext().getNext().getNext().setBigBlind(true);
				break;
			}
		}				
	}

	public void distribuerJoueurs(){
		for(Joueur j : joueurs){
			j.recevoirCartes(this.paquet);
			System.out.println("Cartes de " + j.getNom() + " : " + j.getMain().toString());
		};
	}
	
	public void distribuerFlop(){
		System.out.println("Distribution du Flop");
		Carte[] cartes = new Carte[3]; 
		for(int i=0; i<3; i++){
			cartes[i] = this.paquet.tirerCarte();
			System.out.println(cartes[i]);
		}
		this.setFlop(cartes);
	}
	
	public void distribuerTurn(){
		this.setTurn(this.paquet.tirerCarte());
		System.out.println("Turn : " + this.getTurn());
	}
	
	public void distribuerRiver(){
		this.setRiver(this.paquet.tirerCarte());
		System.out.println("River : " + this.getRiver());
	}
	
	public void viderTable(){
		//Vider joueurs sans argent, remplir paquet, retirer mains des joueurs, retirer mise des joueurs, remettre en jeu les joueurs couchés ...
		
		for(int i = 0; i < joueurs.size(); i++){
			Joueur j = joueurs.get(i);
			if(j.getJetons() <= 0){
				System.out.println(j.getNom() + " a perdu !");
				if(i > 0){
					joueurs.get(i-1).setNext(j.getNext());
				}else{
					joueurs.get(joueurs.size()-1).setNext(j.getNext());
				}
				joueurs.remove(i);
				i--;
			}
			j.setMain(null);
			j.setMise(0);
			j.setFolded(false);
			j.setAllIn(false);
		}
		
		this.setPaquet(new Paquet());
		this.getPaquet().melanger();
	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}

	public int getSmallBlind() {
		return smallBlind;
	}

	public void setSmallBlind(int smallBlind) {
		this.smallBlind = smallBlind;
	}

	public int getBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(int bigBlind) {
		this.bigBlind = bigBlind;
	}

	public int getMiseIndiv() {
		return miseIndiv;
	}

	public void setMiseIndiv(int miseIndiv) {
		this.miseIndiv = miseIndiv;
	}

	public Carte[] getFlop() {
		return flop;
	}

	public void setFlop(Carte[] flop) {
		this.flop = flop;
	}

	public Carte getTurn() {
		return turn;
	}

	public void setTurn(Carte turn) {
		this.turn = turn;
	}

	public Carte getRiver() {
		return river;
	}

	public void setRiver(Carte river) {
		this.river = river;
	}

	public Joueur getCurrent() {
		return current;
	}

	public void setCurrent(Joueur current) {
		this.current = current;
	}

	public Paquet getPaquet() {
		return paquet;
	}

	public void setPaquet(Paquet paquet) {
		this.paquet = paquet;
	}
	
	
}
