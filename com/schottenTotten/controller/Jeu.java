package com.schottenTotten.controller;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;
import com.schottenTotten.model.GroupeDeCartes;
import com.schottenTotten.model.Joueur;

import java.util.*;

public class Jeu {

    private Joueur joueur1;
    private Joueur joueur2;
    private List<Borne> bornes;
    private GroupeDeCartes pioche;
    
    private Joueur joueurActuel; 

    private static int TAILLE_MAX_MAIN = 6;
    private static int NB_BORNES = 9;

    // Constructeur
    public Jeu(String nomJ1, String nomJ2) {
        this.joueur1 = new Joueur(nomJ1, 1);
        this.joueur2 = new Joueur(nomJ2, 2);
        this.joueurActuel = joueur1;
        this.bornes = new ArrayList<>();
        this.pioche = new GroupeDeCartes();

        initialiserPartie();
    }

    // Initialisation 
    private void initialiserPartie() {
        initialiserBornes();
        genererPioche();
        distribuerCartes();
    }

    private void initialiserBornes() {
        for (int i = 0; i < NB_BORNES; i++) {
            bornes.add(new Borne());
        }
    }

    private void genererPioche() {
        List<Carte> Deck = new ArrayList<>();

        // création des 54 cartes
        for (int c = 1; c <= 6; c++) {
            for (int v = 1; v <= 9; v++) {
                Deck.add(new Carte(c, v));
            }
        }
        
        // mélange
        Collections.shuffle(Deck); //fonction fournit par ia 

        for (Carte carte : Deck) {
            pioche.ajouter(carte);
        }
    }

    private void distribuerCartes() {
        for (int i = 0; i < TAILLE_MAX_MAIN; i++) {
            piocherCarte(joueur1);
            piocherCarte(joueur2);
        }
    }

    // Méthodes
    public boolean jouerTour(int indexCarteJoue, int indexBorne) {
        Borne borne = bornes.get(indexBorne);
        Carte carteJouee = joueurActuel.jouerCarte(indexCarteJoue);
        
        System.out.println(joueurActuel.getNom() + " joue " + carteJouee.description());

        borne.poserCarte(carteJouee, joueurActuel);

        verifierVictoireBorne(borne);

        int resultatPartie = verifierFinPartie();
        if (resultatPartie != 0) {
            afficherVainqueur(resultatPartie);
            return false;
        }

        piocherCarte(joueurActuel);

        changerJoueur();
        
        return true;
    }

    public void piocherCarte(Joueur joueur) {
        if (!pioche.estVide()) {
            joueur.recevoirCarte(pioche.retirer(0));
        }
    }

    private void changerJoueur() {
        if (joueurActuel == joueur1) {
            joueurActuel = joueur2;
        } else {
            joueurActuel = joueur1;
        }
        System.out.println("\n--- C'est au tour de " + joueurActuel.getNom() + " ---");
    }

    private void verifierVictoireBorne(Borne borne) {
        if (borne.estComplete()) {
            GroupeDeCartes g1 = borne.getCartes(joueur1);
            GroupeDeCartes g2 = borne.getCartes(joueur2);

            int resultat = Decision.determinerGagnant(g1, g2);
            if (resultat == 1) {
                borne.revendiquer(joueur1);
            }else {
                borne.revendiquer(joueur2);
            }
        }
    }

    private int verifierFinPartie() {
        int bornesJ1 = 0;
        int bornesJ2 = 0;
        
        int[] etatPartie = new int[NB_BORNES];

        for (int i = 0; i < bornes.size(); i+=1){
            Joueur possesseur = bornes.get(i).getPossesseur();
            if (possesseur == joueur1) {
                bornesJ1 += 1;
                etatPartie[i] = 1;
            }else if (possesseur == joueur2) {
                bornesJ2 += 1;
                etatPartie[i] = 2;
            }else{
                etatPartie[i] = 0;
            }
        }
        if (bornesJ1 >= 5) {
            return 1;
        }
        if (bornesJ2 >= 5) {
            return 2;
        }
        for (int i = 0; i <= NB_BORNES - 3; i++) {
            if (etatPartie[i] == 1 && etatPartie[i+1] == 1 && etatPartie[i+2] == 1) {
                return 1;
            }
            if (etatPartie[i] == 2 && etatPartie[i+1] == 2 && etatPartie[i+2] == 2) {
                return 2;
            }
        }
        return 0;
    }

    private void afficherVainqueur(int resultat) {
        System.out.println("\n=================================");
        System.out.println("FIN DE LA PARTIE !");

        if (resultat == 1) {
            System.out.println("BRAVO ! " + joueur1.getNom() + " a gagné la partie !");
        } else {
            System.out.println("BRAVO ! " + joueur2.getNom() + " a gagné la partie !");
        }
        System.out.println("=================================");
    }

    // Getters
    public Joueur getJoueurActuel() { 
        return joueurActuel; 
    }

    public List<Borne> getBornes() {
         return bornes; 
    }
}