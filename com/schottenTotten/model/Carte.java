package com.schottenTotten.model;

public class Carte {

    // Attributs
    private int couleur;
    private int valeur;
    

    // Constructeur
    public Carte(int couleur, int valeur) {
        this.couleur = couleur;
        this.valeur = valeur; // penser a proteger la valeur entre 1 et 9
    }

    // Getters
    public int getCouleur() {
        return couleur;
    }

    public int getValeur() {
        return valeur;
    }

    // Affichage
    public String description() {
        return "[Couleur : " + couleur + ", Valeur : " + valeur + "]"; 
    }
}