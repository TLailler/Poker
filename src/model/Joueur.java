package model;

import java.util.Scanner;

public class Joueur {

	private String nom;
	
	private int jetons;
	
	private int mise;
	
	private Main main;
	
	private String IPAddress;
	
	private int port;
	
	private boolean isDealer;
	
	private boolean isSmallBlind;
	
	private boolean isBigBlind;
	
	private Joueur next;
	
	private boolean isFolded;
	
	private boolean isAllIn;
	
	public Joueur(String nom, int jetons, String iPAddress, int port, Joueur next) {
		super();
		this.nom = nom;
		this.jetons = jetons;
		IPAddress = iPAddress;
		this.port = port;
		this.next = next;
		this.main = new Main();
		this.isAllIn = false;
		this.isFolded = false;
	}
	
	public void miser(int montant){
		if(this.getJetons() >= montant){
			this.setJetons(this.jetons - montant);
			this.setMise(this.getMise() + montant);
			System.out.println(this.getNom() + " ajoute " + montant +" // Mise totale : " + this.getMise());
		}		
	}
	
	public void recevoirCartes(Paquet p){		
		Carte[] cartes = new Carte[2];
		cartes[0] = p.tirerCarte();
		cartes[1] = p.tirerCarte();
		this.setMain(new Main(cartes));
	}

	public String choisirAction(int miseMax, boolean premiereMise) {
	if(miseMax == this.getMise()){
		System.out.println("Action possibles : C (Call), F (Fold), A (All In), K (Check)");
	}else{
		System.out.println("Actions possibles : S (Suivre), F (Fold), A (All In), RXX (Relance, XX est le montant de la relance)");
	}
	Scanner sc = new Scanner(System.in);
	String s = sc.nextLine();
	boolean valid = false;
	while(!valid){
		if(s.equals("S") && miseMax != this.getMise()){
			valid = true;
			System.out.println(this.getNom() + " suit");
		}else if(s.equals("F")){
			valid = true;
			System.out.println(this.getNom() + " se couche");
		}else if(s.matches("^R\\d+$") && miseMax != this.getMise()){
			String chiffre = s.substring(1,s.length());
			int montant = Integer.parseInt(chiffre);
			if(montant <= this.getJetons()){
				valid = true;
				System.out.println(this.getNom() + " suit, et relance de " + chiffre);
			}else{
				//Mauvais montant
				System.out.println("Montant invalide, entrez un autre montant si vous voulez relancer ");
				s = this.choisirAction(miseMax,premiereMise);
				valid = true;
			}
		}else if(s.equals("A")){
			valid = true;
			System.out.println(this.getNom() + " met tapis !");
		}else if(s.equals("C") && (premiereMise ||  miseMax == this.getMise())){
			valid = true;
			System.out.println(this.getNom() + " mise");
		}else if(s.equals("K") && (miseMax == this.getMise())){
			valid = true;
			System.out.println(this.getNom() + " check");
		}else{
			System.out.println("Entrez un symbole correct");
			s = this.choisirAction(miseMax,premiereMise);
			valid = true;
		}
	}
	return s;
	}
	
	public void montrerMain(){
		System.out.println(this.getNom() + " : " + this.getMain().toString());
	}
	
	public void verserGains(int gains){
		this.setJetons(this.getJetons() + gains);
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Joueur getNext() {
		return next;
	}

	public void setNext(Joueur next) {
		this.next = next;
	}

	public boolean isFolded() {
		return isFolded;
	}

	public void setFolded(boolean isFolded) {
		this.isFolded = isFolded;
	}

	public boolean isAllIn() {
		return isAllIn;
	}

	public void setAllIn(boolean isAllIn) {
		this.isAllIn = isAllIn;
	}

	public int getJetons() {
		return jetons;
	}

	public void setJetons(int jetons) {
		this.jetons = jetons;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public String getIPAddress() {
		return IPAddress;
	}

	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}

	public boolean isSmallBlind() {
		return isSmallBlind;
	}

	public void setSmallBlind(boolean isSmallBlind) {
		this.isSmallBlind = isSmallBlind;
	}

	public boolean isBigBlind() {
		return isBigBlind;
	}

	public void setBigBlind(boolean isBigBlind) {
		this.isBigBlind = isBigBlind;
	}

	public String getStatus(){
		String s = "";
		if(this.isDealer){
			s+= "Dealer ";
		}
		if(this.isBigBlind){
			s+= "BigBlind ";
		}
		if(this.isSmallBlind){
			s+= "SmallBlind ";
		}
		if(s.equals("")){
			s+= "NoStatus";
		}
		return s;
	}

	public int getMise() {
		return mise;
	}

	public void setMise(int mise) {
		this.mise = mise;
	}

	@Override
	public String toString() {
		return "Joueur [nom=" + nom + ", jetons=" + jetons + ", main=" + main + ", IPAddress=" + IPAddress + ", port="
				+ port + ", status=" + getStatus() + ", next=" + next.getNom() + ", isFolded=" + isFolded + "]";
	}

}
