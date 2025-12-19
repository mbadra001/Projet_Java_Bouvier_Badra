package com.schottenTotten.view;

import java.util.List;
import com.schottenTotten.model.*;


public class ConsoleView {

    public void afficherAccueil() {
        System.out.println("SCHOTTEN-TOTTEN ");
    }


// fonction pour afficher les bornes du plateau
    public void afficherBornes(Borne borne) {
        System.out.println("\n=== BORNE ===");
        System.out.println(borne.description());
   
    } 

// IA--------------------
// focntion pour afficher la main d'un joueur
    public void afficherMain(Joueur joueur) {
         System.out.println("\nMain de " + joueur.getNom() + " :");
        List<Carte> main = joueur.getMain();

        for (int i = 0; i < main.size(); i++) {
            System.out.println("[" + i + "] " + main.get(i).description());

    }
    }

//------------------------------
// fonction afficher action possible
    public void afficherActionsPossibles(boolean Revendiquer, boolean Tactique) {
        System.out.println("Actions possibles :");
        System.out.println("1. Jouer une carte");

        if (Revendiquer){
            System.out.println("2. Revendiquer une borne");
        }

        if (Tactique){
            System.out.println("3. Utiliser une carte tactique");
            System.out.println("4. Passer son tour");
        }

    }   
// fonction pour afficher un message carte posé ect
    public void afficherMessage(String msg) {
            System.out.println("\n> " + msg);
        }

    public void afficherErreur(String msg) {
        System.out.println("\n! ERREUR: " + msg);
    }

// focntion pour afficher revendication 
    public void afficherRevendication(Joueur gagnant, int idBorne) {
        System.out.println("Borne n°" + idBorne + " revendiquée par : " + gagnant.getNom());
    }

   


    public void afficherVictoire(Joueur joueur) {
            System.out.println("\n FIN DE PARTIE ");
            System.out.println("Victoire de : " +joueur.getNom());
            // System.out.println("score final: " + joueur.getScore()); pas encore implémenter
     }

    public boolean afficherRelancerPartie() {
        System.out.println("Voulez-vous relancer une partie ? (1 = Oui / 2 = Non)");
        return InputView.demanderEntier() == 1;
    }

//------------------PAS ENCORE IMPLÉMENTÉ---------------------------------
    public void affichercombrePioche(int nbCartes) {
        System.out.println("Cartes restantes dans la pioche : " + nbCartes);
    }
    
    // pout permette de conanitre l'etat des bornes deja revendiquees plus tard
    public void afficherBornesDejaRevendiquees(List<Borne> bornes) {
        System.out.println("Bornes déjà revendiquées :");
        for (Borne borne : bornes) {
            System.out.println(borne.description());
        }
    }

// connaitre à qui appartient la borne
    public void afficherPossesesseurBorne(int idBorne, Joueur possesseur) {
        System.out.println("La borne " + idBorne + " est possédée par " + possesseur.getNom());
    }

// afficher la carte posé plus tard
    public void afficherCartePosée(Carte carte, Joueur joueur) {
        System.out.println(joueur.getNom() + " a utilisé la carte : " + carte);
    }

// affiche cate tactique utilisée  plus tard

    public void afficherCarteTactiqueUtilisee(Carte carte, Joueur joueur) {
        System.out.println(joueur.getNom() + " a utilisé la carte tactique : " + carte);
    }
    //-------------------------------------------------------------------------
    public void afficherEffetTactique(String msg) {
        System.out.println("\n>>> [EFFET TACTIQUE] : " + msg);
    }

    public void afficherConfigurationCarte(String nom) {
        System.out.println("\n--- Configuration de la carte : " + nom + " ---");
    }

    public void afficherSelectionCarteSurBorne(GroupeDeCartes g) {
        System.out.println("Cartes sur la borne :");
        for (int i = 0; i < g.getNombreDeCartes(); i++) {
            System.out.println("  [" + i + "] " + g.getCarte(i).description());
        }
    }

    public void afficherErreurTactique(String msg) {
        System.out.println("! Action Tactique impossible : " + msg);
    }
}
