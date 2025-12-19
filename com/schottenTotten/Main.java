package com.schottenTotten;

import com.schottenTotten.ai.Coup;        // --- AJOUT  pour IA ---
import com.schottenTotten.ai.IaAleatoire; // --- AJOUT pour IA ---
import com.schottenTotten.controller.Jeu;
import com.schottenTotten.controller.JeuFactory;
import com.schottenTotten.view.ConsoleView;
import com.schottenTotten.view.InputView;
import com.schottenTotten.view.MenuView;

public class Main {

    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        ConsoleView gameView = new ConsoleView();

        menuView.afficherMenuPrincipal();

        int choixVariante = menuView.demanderVarianteDeJeu();
        String typeVariante;
        if (choixVariante == 2) {
            typeVariante = "TACTIQUE";
        }else{
            typeVariante = "CLASSIQUE";
        }

        // On demande d'abord si on veut jouer contre l'IA
        boolean contreIA = menuView.demanderAvecIA();
        
        String nomJ1 = "";
        String nomJ2 = "";

        if (contreIA) {
            // Mode IA
            nomJ1 = menuView.demanderNomJoueur(1);
            nomJ2 = "Ordinateur"; // Nom automatique
            // int niveauIA = menuView.demanderTypeIA(); // pas encore fait 
        } else {
            // 2 Joueurs
            nomJ1 = menuView.demanderNomJoueur(1);
            nomJ2 = menuView.demanderNomJoueur(2);
        }

        // 3. Création du jeu (Contrôleur)
        Jeu jeu = JeuFactory.creerJeu(typeVariante, nomJ1, nomJ2, gameView);
        
        boolean partieEnCours = true;

        // 4. Boucle de jeu
        while (partieEnCours) {

            // --- AJOUT pour IA : On configure l'IA si c'est son tour et qu'elle n'est pas encore configurée ---
            if (contreIA && jeu.getJoueurActuel().getNom().equals("Ordinateur") && !jeu.getJoueurActuel().estIA()) {
                 jeu.getJoueurActuel().setStrategie(new IaAleatoire());
            }
            // ------------------------------------------------------------------------------------------

            // On affiche qui doit jouer
            gameView.afficherMessage("C'est au tour de " + jeu.getJoueurActuel().getNom());
            
            // On affiche la main du joueur
            gameView.afficherMain(jeu.getJoueurActuel());
            
            // Initialisation des variables
            int indexCarte = 0;
            int indexBorne = 0;

            // --- AJOUT pour IA : On distingue le tour de l'IA de celui de l'humain ---
            if (jeu.getJoueurActuel().estIA()) {
                // C'est l'IA qui joue
                gameView.afficherMessage("L'IA réfléchit...");
                try { 
                    Thread.sleep(1000); // pause pour simuler la réflexion
                } catch (InterruptedException e) {}

                // L'IA calcule son coup
                Coup coupIA = jeu.getJoueurActuel().demanderCoupIA(jeu.getBornes());
                indexCarte = coupIA.indexCarte;
                indexBorne = coupIA.indexBorne;
                
                gameView.afficherMessage("L'IA choisit la carte " + indexCarte + " sur la borne " + indexBorne);

            } else {
                // C'est un humain (CODE ORIGINAL DÉPLACÉ DANS LE ELSE)
                indexCarte = InputView.demanderCarteAJouer();
                indexBorne = InputView.demanderBorne();
            }
            // -------------------------------------------------------------------
            
            try {
                // jouerTour renvoie false si la partie est finie ou s'il y a problème
                boolean resultatTour = jeu.jouerTour(indexCarte, indexBorne);
                
                // fin de partie 
                if (!resultatTour) {
                    // Vérifie si c'est vraiment fini ou juste une erreur
                   partieEnCours = false;
                }
            } catch (Exception e) {
                // Si l'IA plante (rare), on évite que le jeu crash, mais pour l'humain ça affiche l'erreur
                gameView.afficherErreur("Coup invalide (mauvais index ?). Réessayez.");
            }
        }
        
        InputView.fermerScanner();
    }
}