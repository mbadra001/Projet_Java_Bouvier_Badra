package com.schottenTotten;

import com.schottenTotten.controller.Jeu;
import com.schottenTotten.model.Joueur;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== BIENVENUE DANS SCHOTTEN-TOTTEN (V1 : Duel Humain) ===");
        
        // 1. On crée le jeu avec deux noms de joueurs humains
        Jeu jeu = new Jeu("Joueur 1", "Joueur 2");
        
        boolean partieEnCours = true;

        // 2. Boucle de Jeu
        while (partieEnCours) {
            
            // On récupère le joueur dont c'est le tour
            Joueur joueurActuel = jeu.getJoueurActuel();
            
            // A. Affichage du plateau (Borne)
            System.out.println("\n================================================");
            System.out.println("ÉTAT DU JEU :");
            System.out.println(jeu.getBorne().description());
            System.out.println("================================================");

            // B. Affichage du joueur actif et de sa main
            System.out.println("\n>>> C'est au tour de " + joueurActuel.getNom());
            System.out.println("Voici votre main :");

            // On affiche les cartes avec leur index [0], [1], etc.
            for (int i = 0; i < joueurActuel.getNombreDeCartes(); i++) {
                 // Astuce d'affichage : [0] Carte...
                 System.out.println("  [" + i + "] " + joueurActuel.getCarte(i).description());
            }

            // C. Demande de saisie utilisateur
            int indexChoisi = -1;
            boolean saisieValide = false;

            while (!saisieValide) {
                System.out.print("\nQuel numéro de carte voulez-vous jouer ? > ");
                
                if (scanner.hasNextInt()) {
                    indexChoisi = scanner.nextInt();
                    // Petite vérification locale pour éviter de planter le contrôleur inutilement
                    if (indexChoisi >= 0 && indexChoisi < joueurActuel.getNombreDeCartes()) {
                        saisieValide = true;
                    } else {
                        System.out.println("Numéro invalide ! Choisissez entre 0 et " + (joueurActuel.getNombreDeCartes() - 1));
                    }
                } else {
                    System.out.println("Veuillez entrer un chiffre.");
                    scanner.next(); // Vide le tampon
                }
            }

            // D. On joue le tour avec l'index choisi
            // La méthode renvoie 'false' si la partie est terminée
            partieEnCours = jeu.jouerTour(indexChoisi, jeu.getBorne());
        }

        scanner.close();
        System.out.println("Fin du programme.");
    }
}