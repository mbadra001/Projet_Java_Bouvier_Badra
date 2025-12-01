package com.schottenTotten.model;

import java.util.*;

public class Borne {

    // Attributs
    private GroupeDeCartes cartesJoueur1;
    private GroupeDeCartes cartesJoueur2;
    
    private Joueur possesseur; 

    public static int CAPACITE_MAX = 3;

    // Constructeur
    public Borne() {
        // On instancie les deux groupes vides
        this.cartesJoueur1 = new GroupeDeCartes();
        this.cartesJoueur2 = new GroupeDeCartes();
        this.possesseur = null;
    }

    // Méthodes
    public void poserCarte(Carte carte, Joueur joueur) {
        if (possesseur != null) {
            System.out.println("Erreur : La borne est déjà revendiquée !");
            return;
        }

        GroupeDeCartes coteConcerne;
        if (joueur.getNumero() == 1) {
            coteConcerne = cartesJoueur1;
        } else {
            coteConcerne = cartesJoueur2;
        }

        coteConcerne.ajouter(carte);
    }

    public boolean estComplete() {
        return cartesJoueur1.getNombreDeCartes() == CAPACITE_MAX && 
               cartesJoueur2.getNombreDeCartes() == CAPACITE_MAX;
    }
    
    // Setters 

    public void revendiquer(Joueur joueur) {
        this.possesseur = joueur;
    }
    
    // Getters
    public Joueur getPossesseur() {
        return possesseur;
    }
    
    public GroupeDeCartes getCartes(Joueur joueur) {
        if (joueur.getNumero() == 1) {
            return cartesJoueur1;
        } else {
            return cartesJoueur2;
        }
    }

    // Affichage
    public String description() {
        StringBuilder sb = new StringBuilder();
        sb.append("Borne ");
        
        if (possesseur != null) {
            sb.append("[Gagnée par ").append(possesseur.getNom()).append("]");
        } else {
            sb.append("[En cours]");
        }
        sb.append("\n  Côté J1 : ").append(cartesJoueur1.description());
        sb.append("\n  Côté J2 : ").append(cartesJoueur2.description());
        
        return sb.toString();
    }
}