package test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.Joueur;
import model.Paquet;
import model.Table;


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

		t.distribuerJoueurs();
		assertTrue(t.getJoueurs().get(0).getMain().getCartes()[0] != null);
		
	}	
	
	/**
	 * Test de la saisie utilisateur choisirAction()
	 * A executer manuellement dans un main
	 */
	public void testActionEnchères(){
		System.out.println("Action : S pour suivre, R pour augmenter sa mise, F pour se coucher");
		String s = j1.choisirAction();
		if(s.equals("S")){
			System.out.println("Suivi");
		}else if(s.equals("F")){
			System.out.println("Couché");
		}else if(s.equals("R")){
			System.out.println("Relance");
		}else{
			System.out.println("Mauvais choix");
		}
		assertTrue(s == "S" && s == "F" && s == "R" && s == "Mauvais choix");	
	}

}
