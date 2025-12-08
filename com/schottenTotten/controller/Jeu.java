package com.schottenTotten.controller;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;
import com.schottenTotten.model.GroupeDeCartes;
import com.schottenTotten.model.Joueur;
import com.schottenTotten.view.ConsoleView;

import java.util.*;

public class Jeu {

    private Joueur joueur1;
    private Joueur joueur2;
    private List<Borne> bornes;
    private GroupeDeCartes pioche;
    
    private Joueur joueurActuel; 
    
    private ConsoleView view;

    private static int TAILLE_MAX_MAIN = 6;
    private static int NB_BORNES = 9;

    // Constructeur : Ajout du paramètre ConsoleView view
    public Jeu(String nomJ1, String nomJ2, ConsoleView view) {
        this.joueur1 = new Joueur(nomJ1, 1);
        this.joueur2 = new Joueur(nomJ2, 2);
        this.joueurActuel = joueur1;
        this.bornes = new ArrayList<>();
        this.pioche = new GroupeDeCartes();
        this.view = view; // Initialisation de la vue

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
        Collections.shuffle(Deck); 

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
        // regarder si l'index de la carte et de la borne sont valides
        if (indexBorne < 0 || indexBorne >= bornes.size()) {
           view.afficherErreur("Numéro de borne invalide (" + indexBorne + ") !");
            return true; // On continue la partie sans changer de joueur
        }

        Borne borne = bornes.get(indexBorne);
        
        // 1. On retire la carte TEMPORAIREMENT
        Carte carteJouee = joueurActuel.jouerCarte(indexCarteJoue);
        
        if (carteJouee == null) {
            view.afficherErreur("Carte invalide (index incorrect) !");
             return true;
        }

        view.afficherMessage(joueurActuel.getNom() + " joue " + carteJouee.description()); 

        // 2. On essaie de la poser
        boolean coupValide = borne.poserCarte(carteJouee, joueurActuel);

        if (!coupValide) {
            // La borne est pleine ou prise
            view.afficherErreur("IMPOSSIBLE : La borne " + indexBorne + " est pleine ou déjà prise !");
            
            // CRUCIAL : On rend la carte au joueur !
            joueurActuel.recevoirCarte(carteJouee); 
            
            // On retourne true (partie continue) MAIS on ne change pas de joueur
            return true; 
        }

        // Si on arrive ici, le coup est valide
        verifierVictoireBorne(borne);

        int resultatPartie = verifierFinPartie();
        if (resultatPartie != 0) {
            afficherVainqueur(resultatPartie);
            return false; // Fin de partie
        }

        piocherCarte(joueurActuel);
        changerJoueur(); // On change de joueur uniquement si le coup était valide
        
        return true;
    }

    private void verifierVictoireBorne(Borne borne) {
        if (borne.estComplete()) {
            GroupeDeCartes g1 = borne.getCartes(joueur1);
            GroupeDeCartes g2 = borne.getCartes(joueur2);

            int resultat = Decision.determinerGagnant(g1, g2);
            
            // On récup l'index de la borne pour l'affichage
            int indexBorne = bornes.indexOf(borne);
            
            // On récup la raison de la victoire
            String raison = Decision.expliquerVictoire(g1, g2);

            if (resultat == 1) {
                borne.revendiquer(joueur1);
                //  on passe indexBorne en 2ème argument
                view.afficherRevendication(joueur1, indexBorne);
                view.afficherMessage("Victoire grâce à : " + raison);
                
            } else if (resultat == 2) { 
                borne.revendiquer(joueur2);
                // on passe indexBorne en 2ème argument
                view.afficherRevendication(joueur2, indexBorne);
                view.afficherMessage("Victoire grâce à : " + raison);
            }
        }
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
        // UTILISATION DE LA VUE
        view.afficherMessage("--- C'est au tour de " + joueurActuel.getNom() + " ---");
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
        // UTILISATION DE LA VUE
        if (resultat == 1) {
            view.afficherVictoire(joueur1);
        } else {
            view.afficherVictoire(joueur2);
        }
    }

    // Getters
    public Joueur getJoueurActuel() { 
        return joueurActuel; 
    }

    public List<Borne> getBornes() {
         return bornes; 
    }
}