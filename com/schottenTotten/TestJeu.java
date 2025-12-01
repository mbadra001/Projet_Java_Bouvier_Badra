package com.schottenTotten;

import com.schottenTotten.controller.Decision; // Import pour la logique de victoire
import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;
import com.schottenTotten.model.GroupeDeCartes; // Nécessaire pour tester Decision
import com.schottenTotten.model.Joueur;

public class TestJeu {

    public static void main(String[] args) {
        // ==========================================
        // 1. TEST JOUEUR ET CARTES
        // ==========================================
        System.out.println("--- Test 1 : Joueur et Cartes ---");

        Joueur joueur1 = new Joueur("Alice", 1);
        System.out.println("Joueur créé : " + joueur1.getNom() + " (J" + joueur1.getNumero() + ")");
        
        // Création de cartes avec (int couleur, int valeur)
        // Convention arbitraire : 1=Bleu, 2=Rouge, 3=Vert
        Carte c1 = new Carte(1, 5); // Bleu 5
        Carte c2 = new Carte(2, 9); // Rouge 9
        Carte c3 = new Carte(3, 1); // Vert 1

        // Distribution
        joueur1.recevoirCarte(c1);
        joueur1.recevoirCarte(c2);
        joueur1.recevoirCarte(c3);

        System.out.println("Main d'Alice : " + joueur1.description());

        // ==========================================
        // 2. TEST DE LA BORNE
        // ==========================================
        System.out.println("\n-------------------------------\n");
        System.out.println("--- Test 2 : Interaction avec la Borne ---");

        Joueur joueur2 = new Joueur("Bob", 2);
        Borne borne = new Borne();

        // Alice joue sa carte index 0
        Carte carteJoueeAlice = joueur1.jouerCarte(0); 
        borne.poserCarte(carteJoueeAlice, joueur1);

        // Bob joue une carte Rouge 9 directement (créée à la volée)
        borne.poserCarte(new Carte(2, 9), joueur2);

        System.out.println("État de la borne :\n" + borne.description());

        // ==========================================
        // 3. TEST DE LA DECISION (Règles du jeu)
        // ==========================================
        System.out.println("\n-------------------------------\n");
        System.out.println("--- Test 3 : Logique de Decision (Comparaison de combinaisons) ---");

        // Scénario : Suite-Couleur (Force 500) vs Brelan (Force 400)
        
        // Création main Joueur 1 : Suite-Couleur (Bleu 1, 2, 3)
        GroupeDeCartes mainJ1 = new GroupeDeCartes();
        mainJ1.ajouter(new Carte(1, 1));
        mainJ1.ajouter(new Carte(1, 2));
        mainJ1.ajouter(new Carte(1, 3));
        System.out.println("Combinaison J1 (Suite-Couleur) : " + mainJ1.description());

        // Création main Joueur 2 : Brelan de 5 (Bleu 5, Rouge 5, Vert 5)
        GroupeDeCartes mainJ2 = new GroupeDeCartes();
        mainJ2.ajouter(new Carte(1, 5));
        mainJ2.ajouter(new Carte(2, 5));
        mainJ2.ajouter(new Carte(3, 5));
        System.out.println("Combinaison J2 (Brelan) : " + mainJ2.description());

        // Calcul des forces pour affichage
        System.out.println("Force J1 : " + Decision.calculerForce(mainJ1));
        System.out.println("Force J2 : " + Decision.calculerForce(mainJ2));

        // Détermination du gagnant
        int resultat = Decision.determinerGagnant(mainJ1, mainJ2);
        
        System.out.print("Résultat du duel : ");
        if (resultat == 1) {
            System.out.println("Victoire du Joueur 1 ! (Correct : Suite-Couleur > Brelan)");
        } else if (resultat == 2) {
            System.out.println("Victoire du Joueur 2 !");
        } else {
            System.out.println("Égalité !");
        }
    }
}