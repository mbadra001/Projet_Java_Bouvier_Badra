package com.schottenTotten;

import com.schottenTotten.controller.Jeu;
import com.schottenTotten.view.ConsoleView;
import com.schottenTotten.view.InputView;
import com.schottenTotten.view.MenuView;

public class Main {

    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        ConsoleView gameView = new ConsoleView();

        menuView.afficherMenuPrincipal();

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
        Jeu jeu = new Jeu(nomJ1, nomJ2, gameView);
        
        boolean partieEnCours = true;

        // 4. Boucle de jeu
        while (partieEnCours) {
            // On affiche qui doit jouer
            gameView.afficherMessage("C'est au tour de " + jeu.getJoueurActuel().getNom());
            
            // On affiche la main du joueur
            gameView.afficherMain(jeu.getJoueurActuel());
            
        
            int indexCarte = InputView.demanderCarteAJouer();
            int indexBorne = InputView.demanderBorne();

            
            try {
                // jouerTour renvoie false si la partie est finie ou s'il y a problème
                boolean resultatTour = jeu.jouerTour(indexCarte, indexBorne);
                
                // fin de partie 
                if (!resultatTour) {
                    // Vérifie si c'est vraiment fini ou juste une erreur
                   partieEnCours = false;
                }
            } catch (Exception e) {
                gameView.afficherErreur("Coup invalide (mauvais index ?). Réessayez.");
            }
        }
        
    
        InputView.fermerScanner();
    }
}