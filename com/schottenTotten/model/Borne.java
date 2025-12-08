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
    // Retourne true si la carte a été posée avec succès 
    public boolean poserCarte(Carte carte, Joueur joueur) {
        if (possesseur != null) {
            // On retourne false : échec
            return false;
        }

        
        // Vérification que la borne n'est pas pleine pour ce joueur
        GroupeDeCartes coteConcerne = (joueur.getNumero() == 1) ? cartesJoueur1 : cartesJoueur2;
        if (coteConcerne.getNombreDeCartes() >= CAPACITE_MAX) {
            return false;
        }

        coteConcerne.ajouter(carte);
        return true; // Succès
    
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