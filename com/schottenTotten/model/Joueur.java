package com.schottenTotten.model;

import com.schottenTotten.ai.StrategieJeu; // AJOUT POUR IA
import com.schottenTotten.ai.Coup;       // AJOUT POUR IA
import java.util.*;

public class Joueur extends GroupeDeCartes {

    private String nom;
    private int numero;
    private List<Carte> main;

    private StrategieJeu strategie; // AJOUT : permet de stocker l'IA si c'en est une

    // Constructeur
    public Joueur(String nom, int numero) {
        super();
        this.nom = nom;
        this.numero = numero;
        this.main = new ArrayList<>();
        this.strategie = null; // Par défaut, pas de stratégie (c'est un humain)
    }

    // ajout pou IA

    public void setStrategie(StrategieJeu strategie) {
        this.strategie = strategie;
    }

    public boolean estIA() {
        return strategie != null;
    }

    public Coup demanderCoupIA(List<Borne> bornes) {
        if (strategie != null) {
            return strategie.jouer(this, bornes);
        }
        return null;
    }
    // ------------------------------------

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

    public Carte getCarte(int index) {
        return super.getCartes().get(index);
    }
    // --- Affichage ---
// IA
    @Override
    public String description() {
        return "Main du joueur '" + nom + "' : " + super.description();
    }
}