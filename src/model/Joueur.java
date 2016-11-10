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
	
	public Joueur(String nom, int jetons, String iPAddress, int port, Joueur next) {
		super();
		this.nom = nom;
		this.jetons = jetons;
		IPAddress = iPAddress;
		this.port = port;
		this.next = next;
		this.main = new Main();
	}
	
	public void miser(int montant){
		if(this.getJetons() >= montant){
			this.setJetons(this.jetons - montant);
			this.setMise(montant);
		}		
	}
	
	public void recevoirCartes(Paquet p){
		Carte[] cartes = new Carte[2];
		cartes[0] = p.tirerCarte();
		cartes[1] = p.tirerCarte();
		this.main.setCartes(cartes);
	}

	public String choisirAction() {
	Scanner sc = new Scanner(System.in);
	String s = sc.nextLine();
	boolean valid = false;
	while(!valid){
		if(s.equals("S")){
			valid = true;
		}else if(s.equals("F")){
			valid = true;
		}else if(s.matches("^R\\d+$")){
			String chiffre = s.substring(1,s.length()-1);
			int montant = Integer.parseInt(chiffre);
			if(montant <= this.getJetons()){
				valid = true;
			}else{
				//Mauvais montant
				s = sc.nextLine();
			}
		}else if(s.equals("A")){
			valid = true;
		}else{
			s= sc.nextLine();
		}
	}
	sc.close();
	return s;
	}
	
	public void montrerMain(){
		for(Carte c : this.getMain().getCartes()){
			c.setOuverte(true);
		}
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
