package com.schottenTotten.model;

import java.util.*;

public class Joueur extends GroupeDeCartes {

    private String nom;
    private int numero;

    // Constructeur
    public Joueur(String nom, int numero) {
        super();
        this.nom = nom;
        this.numero = numero;
    }

    // Méthodes
    public void recevoirCarte(Carte carte) {
        super.ajouter(carte);
    }

    public Carte jouerCarte(int index) {
        return super.retirer(index);
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public int getNumero() {
        return numero;
    }

    public int getNombreDeCartes() {
        return super.getNombreDeCartes();
    }
    
    public List<Carte> getMain() {
        return super.getCartes();
    }

    // --- Affichage ---

    @Override
    public String description() {
        return "Main du joueur '" + nom + "' : " + super.description();
    }
}