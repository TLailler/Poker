package test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.Carte;
import model.Combinaisons;
import model.Couleurs;
import model.Joueur;
import model.Main;
import model.Paquet;
import model.Table;
import model.Valeurs;


public class PokerTest extends TestCase {
	
	private Joueur j1;
	private Joueur j2;
	Paquet p;
	Table t;
	
	@Before
	public void init(){
		j1 = new Joueur("Thomas", 1500, "192.168.1.1", 8080, null);
		j2 = new Joueur("Leo", 1500, "195.168.1.2", 8080, j1);
		j1.setNext(j2);
		
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(j1);
		joueurs.add(j2);
		p = new Paquet();
		
		t = new Table(joueurs,1,2,p);
	}
	
	/**
	 * Test de la distribution des cartes
	 */
	@Test
	public void testCarteDistribution(){
		init();
		t.distribuerJoueurs();
		assertTrue(j1.getMain().getCartes()[0] != null);
		
	}	
	
	/**
	 * Test de la combinaison quinte flush royale
	 */
	@Test
	public void testQuinteFlushRoyale(){
		init();
		Carte c1 = new Carte(Valeurs.AS,Couleurs.CARREAU);
		Carte c2 = new Carte(Valeurs.ROI,Couleurs.CARREAU);
		Carte c3 = new Carte(Valeurs.DAME,Couleurs.CARREAU);
		Carte c4 = new Carte(Valeurs.VALET,Couleurs.CARREAU);
		Carte c5 = new Carte(Valeurs.DIX,Couleurs.CARREAU);
		Carte c6 = new Carte(Valeurs.DEUX,Couleurs.TREFLE);
		Carte c7 = new Carte(Valeurs.SEPT,Couleurs.PIQUE);
		Carte[] t1 = {c1,c6};
		Carte[] t2 = {c3,c2,c7};
		j1.setMain(new Main(t1));
		t.setFlop(t2);
		t.setTurn(c4);
		t.setRiver(c5);
		j1.getMain().setCombinaison(t.determinerCombinaison(j1));
		System.out.println(j1.getMain().getCombinaison().getValeur().toString());
		assertTrue(j1.getMain().getCombinaison().getValeur() == Combinaisons.QUINTE_FLUSH_ROYALE);
		
	}	
	
	/**
	 * Test de la combinaison quinte flush
	 */
	@Test
	public void testQuinteFlush(){
		init();
		Carte c1 = new Carte(Valeurs.SEPT,Couleurs.CARREAU);
		Carte c2 = new Carte(Valeurs.HUIT,Couleurs.CARREAU);
		Carte c3 = new Carte(Valeurs.NEUF,Couleurs.CARREAU);
		Carte c4 = new Carte(Valeurs.VALET,Couleurs.CARREAU);
		Carte c5 = new Carte(Valeurs.DIX,Couleurs.CARREAU);
		Carte c6 = new Carte(Valeurs.DEUX,Couleurs.TREFLE);
		Carte c7 = new Carte(Valeurs.SEPT,Couleurs.PIQUE);
		Carte[] t1 = {c1,c6};
		Carte[] t2 = {c3,c2,c7};
		j1.setMain(new Main(t1));
		t.setFlop(t2);
		t.setTurn(c4);
		t.setRiver(c5);
		j1.getMain().setCombinaison(t.determinerCombinaison(j1));
		System.out.println(j1.getMain().getCombinaison().getValeur().toString());
		assertTrue(j1.getMain().getCombinaison().getValeur() == Combinaisons.QUINTE_FLUSH);
		
	}	
	
	/**
	 * Test de la combinaison carre
	 */
	@Test
	public void testCarre(){
		init();
		Carte c1 = new Carte(Valeurs.SEPT,Couleurs.CARREAU);
		Carte c2 = new Carte(Valeurs.SEPT,Couleurs.COEUR);
		Carte c3 = new Carte(Valeurs.SEPT,Couleurs.TREFLE);
		Carte c4 = new Carte(Valeurs.VALET,Couleurs.CARREAU);
		Carte c5 = new Carte(Valeurs.DIX,Couleurs.CARREAU);
		Carte c6 = new Carte(Valeurs.DEUX,Couleurs.TREFLE);
		Carte c7 = new Carte(Valeurs.SEPT,Couleurs.PIQUE);
		Carte[] t1 = {c1,c6};
		Carte[] t2 = {c3,c2,c7};
		j1.setMain(new Main(t1));
		t.setFlop(t2);
		t.setTurn(c4);
		t.setRiver(c5);
		j1.getMain().setCombinaison(t.determinerCombinaison(j1));
		System.out.println(j1.getMain().getCombinaison().getValeur().toString());
		assertTrue(j1.getMain().getCombinaison().getValeur() == Combinaisons.CARRE);
		
	}
	
	/**
	 * Test de la combinaison full
	 */
	@Test
	public void testFull(){
		init();
		Carte c1 = new Carte(Valeurs.SEPT,Couleurs.CARREAU);
		Carte c2 = new Carte(Valeurs.SEPT,Couleurs.COEUR);
		Carte c3 = new Carte(Valeurs.SEPT,Couleurs.TREFLE);
		Carte c4 = new Carte(Valeurs.VALET,Couleurs.CARREAU);
		Carte c5 = new Carte(Valeurs.DIX,Couleurs.CARREAU);
		Carte c6 = new Carte(Valeurs.DEUX,Couleurs.TREFLE);
		Carte c7 = new Carte(Valeurs.DEUX,Couleurs.PIQUE);
		Carte[] t1 = {c1,c6};
		Carte[] t2 = {c3,c2,c7};
		j1.setMain(new Main(t1));
		t.setFlop(t2);
		t.setTurn(c4);
		t.setRiver(c5);
		j1.getMain().setCombinaison(t.determinerCombinaison(j1));
		System.out.println(j1.getMain().getCombinaison().getValeur().toString());
		assertTrue(j1.getMain().getCombinaison().getValeur() == Combinaisons.FULL);
		
	}
	
	/**
	 * Test de la combinaison couleur
	 */
	@Test
	public void testCouleur(){
		init();
		Carte c1 = new Carte(Valeurs.SEPT,Couleurs.CARREAU);
		Carte c2 = new Carte(Valeurs.HUIT,Couleurs.COEUR);
		Carte c3 = new Carte(Valeurs.TROIS,Couleurs.CARREAU);
		Carte c4 = new Carte(Valeurs.VALET,Couleurs.CARREAU);
		Carte c5 = new Carte(Valeurs.DIX,Couleurs.CARREAU);
		Carte c6 = new Carte(Valeurs.CINQ,Couleurs.CARREAU);
		Carte c7 = new Carte(Valeurs.DEUX,Couleurs.PIQUE);
		Carte[] t1 = {c1,c6};
		Carte[] t2 = {c3,c2,c7};
		j1.setMain(new Main(t1));
		t.setFlop(t2);
		t.setTurn(c4);
		t.setRiver(c5);
		j1.getMain().setCombinaison(t.determinerCombinaison(j1));
		System.out.println(j1.getMain().getCombinaison().getValeur().toString());
		assertTrue(j1.getMain().getCombinaison().getValeur() == Combinaisons.COULEUR);
		
	}
	
	/**
	 * Test de la combinaison suite
	 */
	@Test
	public void testSuite(){
		init();
		Carte c1 = new Carte(Valeurs.QUATRE,Couleurs.CARREAU);
		Carte c2 = new Carte(Valeurs.HUIT,Couleurs.COEUR);
		Carte c3 = new Carte(Valeurs.TROIS,Couleurs.TREFLE);
		Carte c4 = new Carte(Valeurs.VALET,Couleurs.CARREAU);
		Carte c5 = new Carte(Valeurs.SIX,Couleurs.CARREAU);
		Carte c6 = new Carte(Valeurs.CINQ,Couleurs.CARREAU);
		Carte c7 = new Carte(Valeurs.DEUX,Couleurs.PIQUE);
		Carte[] t1 = {c1,c6};
		Carte[] t2 = {c3,c2,c7};
		j1.setMain(new Main(t1));
		t.setFlop(t2);
		t.setTurn(c4);
		t.setRiver(c5);
		j1.getMain().setCombinaison(t.determinerCombinaison(j1));
		System.out.println(j1.getMain().getCombinaison().getValeur().toString());
		assertTrue(j1.getMain().getCombinaison().getValeur() == Combinaisons.SUITE);
		
	}
	
	/**
	 * Test de la combinaison brelan
	 */
	@Test
	public void testBrelan(){
		init();
		Carte c1 = new Carte(Valeurs.QUATRE,Couleurs.CARREAU);
		Carte c2 = new Carte(Valeurs.HUIT,Couleurs.COEUR);
		Carte c3 = new Carte(Valeurs.TROIS,Couleurs.TREFLE);
		Carte c4 = new Carte(Valeurs.TROIS,Couleurs.CARREAU);
		Carte c5 = new Carte(Valeurs.SIX,Couleurs.CARREAU);
		Carte c6 = new Carte(Valeurs.CINQ,Couleurs.CARREAU);
		Carte c7 = new Carte(Valeurs.TROIS,Couleurs.PIQUE);
		Carte[] t1 = {c1,c6};
		Carte[] t2 = {c3,c2,c7};
		j1.setMain(new Main(t1));
		t.setFlop(t2);
		t.setTurn(c4);
		t.setRiver(c5);
		j1.getMain().setCombinaison(t.determinerCombinaison(j1));
		System.out.println(j1.getMain().getCombinaison().getValeur().toString());
		assertTrue(j1.getMain().getCombinaison().getValeur() == Combinaisons.BRELAN);
		
	}
	
	/**
	 * Test de la combinaison double paire
	 */
	@Test
	public void testDoublePaire(){
		init();
		Carte c1 = new Carte(Valeurs.QUATRE,Couleurs.CARREAU);
		Carte c2 = new Carte(Valeurs.HUIT,Couleurs.COEUR);
		Carte c3 = new Carte(Valeurs.TROIS,Couleurs.TREFLE);
		Carte c4 = new Carte(Valeurs.TROIS,Couleurs.CARREAU);
		Carte c5 = new Carte(Valeurs.SIX,Couleurs.CARREAU);
		Carte c6 = new Carte(Valeurs.CINQ,Couleurs.CARREAU);
		Carte c7 = new Carte(Valeurs.HUIT,Couleurs.PIQUE);
		Carte[] t1 = {c1,c6};
		Carte[] t2 = {c3,c2,c7};
		j1.setMain(new Main(t1));
		t.setFlop(t2);
		t.setTurn(c4);
		t.setRiver(c5);
		j1.getMain().setCombinaison(t.determinerCombinaison(j1));
		System.out.println(j1.getMain().getCombinaison().getValeur().toString());
		assertTrue(j1.getMain().getCombinaison().getValeur() == Combinaisons.DEUX_PAIRES);
		
	}
	
	/**
	 * Test de la combinaison paire
	 */
	@Test
	public void testPaire(){
		init();
		Carte c1 = new Carte(Valeurs.QUATRE,Couleurs.CARREAU);
		Carte c2 = new Carte(Valeurs.HUIT,Couleurs.COEUR);
		Carte c3 = new Carte(Valeurs.VALET,Couleurs.TREFLE);
		Carte c4 = new Carte(Valeurs.TROIS,Couleurs.CARREAU);
		Carte c5 = new Carte(Valeurs.SIX,Couleurs.CARREAU);
		Carte c6 = new Carte(Valeurs.CINQ,Couleurs.CARREAU);
		Carte c7 = new Carte(Valeurs.HUIT,Couleurs.PIQUE);
		Carte[] t1 = {c1,c6};
		Carte[] t2 = {c3,c2,c7};
		j1.setMain(new Main(t1));
		t.setFlop(t2);
		t.setTurn(c4);
		t.setRiver(c5);
		j1.getMain().setCombinaison(t.determinerCombinaison(j1));
		System.out.println(j1.getMain().getCombinaison().getValeur().toString());
		assertTrue(j1.getMain().getCombinaison().getValeur() == Combinaisons.PAIRE);
		
	}
	
	/**
	 * Test de la saisie utilisateur choisirAction()
	 * A executer manuellement dans un main
	 */
//	public void testActionEnchères(){
//		System.out.println("Action : S pour suivre, R pour augmenter sa mise, F pour se coucher");
//		String s = j1.choisirAction();
//		if(s.equals("S")){
//			System.out.println("Suivi");
//		}else if(s.equals("F")){
//			System.out.println("Couché");
//		}else if(s.equals("R")){
//			System.out.println("Relance");
//		}else{
//			System.out.println("Mauvais choix");
//		}
//		assertTrue(s == "S" && s == "F" && s == "R" && s == "Mauvais choix");	
//	}

}
