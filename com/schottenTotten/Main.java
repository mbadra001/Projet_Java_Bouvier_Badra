package com.schottenTotten;

import com.schottenTotten.ai.Coup;
import com.schottenTotten.ai.IaAleatoire;
import com.schottenTotten.controller.Jeu;
import com.schottenTotten.controller.JeuFactory;
import com.schottenTotten.model.Borne;
import com.schottenTotten.view.ConsoleView;
import com.schottenTotten.view.InputView;
import com.schottenTotten.view.MenuView;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        ConsoleView gameView = new ConsoleView();

        menuView.afficherMenuPrincipal();

        int choixVariante = menuView.demanderVarianteDeJeu();
        String typeVariante = (choixVariante == 2) ? "TACTIQUE" : "CLASSIQUE";

        boolean contreIA = menuView.demanderAvecIA();
        String nomJ1 = menuView.demanderNomJoueur(1);
        String nomJ2 = contreIA ? "Ordinateur" : menuView.demanderNomJoueur(2);

        Jeu jeu = JeuFactory.creerJeu(typeVariante, nomJ1, nomJ2, gameView);
        boolean partieEnCours = true;

        // BOUCLE DE JEU
        while (partieEnCours) {
            // Config IA
            if (contreIA && jeu.getJoueurActuel().getNom().equals("Ordinateur") && !jeu.getJoueurActuel().estIA()) {
                 jeu.getJoueurActuel().setStrategie(new IaAleatoire());
            }

            // Vérification si blocage
            if (!jeu.joueurPeutJouer(jeu.getJoueurActuel()) || jeu.getJoueurActuel().getMain().isEmpty()) {
                gameView.afficherMessage("ATTENTION : " + jeu.getJoueurActuel().getNom() + " ne peut plus jouer (Main vide ou Plateau plein).");
                gameView.afficherMessage("Fin de la partie.");
                partieEnCours = false;
                break;
            }

            gameView.afficherMessage("C'est au tour de " + jeu.getJoueurActuel().getNom());
            gameView.afficherMain(jeu.getJoueurActuel());

            boolean resultatTour = false;

            //  Tour de jeu 
            if (jeu.getJoueurActuel().estIA()) {
                gameView.afficherMessage("L'IA réfléchit...");
                try { Thread.sleep(1000); } catch (InterruptedException e) {}

                Coup coupIA = jeu.getJoueurActuel().demanderCoupIA(jeu.getBornes());
                
                if (!coupIA.coupPossible) {
                    gameView.afficherMessage("L'IA ne peut plus jouer.");
                    partieEnCours = false;
                    break;
                }

                if (coupIA.indexCarte != -1) {
                    gameView.afficherMessage("L'IA choisit la carte " + coupIA.indexCarte + 
                                             (coupIA.indexBorne != -1 ? " sur la borne " + coupIA.indexBorne : ""));
                }
                resultatTour = jeu.jouerCoup(coupIA);
            
            } else {
                // Humain
                int indexCarte = InputView.demanderCarteAJouer();
                int indexBorne = InputView.demanderBorne();

                try {
                    resultatTour = jeu.jouerTour(indexCarte, indexBorne);
                } catch (Exception e) {
                    gameView.afficherErreur("Erreur de saisie ou coup invalide.");
                    resultatTour = true; 
                }
            }

            if (!resultatTour) {
                partieEnCours = false;
            }
        }
        
        // AFFICHAGE DES RÉSULTATS FINAUX (c'est IA)
        afficherResultatsFinaux(jeu, gameView);
        
        InputView.fermerScanner();
    }

    private static void afficherResultatsFinaux(Jeu jeu, ConsoleView view) {
        System.out.println("\n=================================");
        System.out.println("       RÉSULTATS FINAUX          ");
        System.out.println("=================================");

        int scoreJ1 = 0;
        int scoreJ2 = 0;
        List<Borne> bornes = jeu.getBornes();

        for (int i = 0; i < bornes.size(); i++) {
            Borne b = bornes.get(i);
            String possesseur = "Personne";
            if (b.getPossesseur() != null) {
                possesseur = b.getPossesseur().getNom();
                if (b.getPossesseur().getNumero() == 1) scoreJ1++;
                else scoreJ2++;
            }
            System.out.println("Borne " + i + " : " + possesseur);
        }

        System.out.println("\n---------------------------------");
        System.out.println("SCORE FINAL :");
        System.out.println("Joueur 1 : " + scoreJ1 + " bornes");
        System.out.println("Joueur 2 : " + scoreJ2 + " bornes");
        System.out.println("---------------------------------");

        if (scoreJ1 > scoreJ2) {
            System.out.println(" VICTOIRE DU JOUEUR 1 !");
        } else if (scoreJ2 > scoreJ1) {
             System.out.println(" VICTOIRE DU JOUEUR 2 / ORDINATEUR !");
        } else {
            System.out.println(" ÉGALITÉ PARFAITE !");
        }
        System.out.println("=================================\n");
    }
}