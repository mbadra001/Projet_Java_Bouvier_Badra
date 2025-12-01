package com.schottenTotten.model;

import java.util.*;

public class GroupeDeCartes {

    // Attribut
    private List<Carte> cartes;

    // Constructeur
    public GroupeDeCartes() {
        this.cartes = new ArrayList<>();
    }


    // Méthodes
    public void ajouter(Carte c) {
        if (c != null) {
            cartes.add(c);
        }
    }

    public Carte retirer(int index) {
        if (index >= 0 && index < cartes.size()) {
            return cartes.remove(index);
        }
        return null;
    }


    // Getters

    public Carte getCarte(int index) {
        if (index >= 0 && index < cartes.size()) {
            return cartes.get(index);
        }
        return null;
    }

    public int getNombreDeCartes() {
        return cartes.size();
    }

    public List<Carte> getCartes() {
        return new ArrayList<>(cartes);
    }

    // Methode
    public boolean estVide() {
        return cartes.isEmpty();
    }

    public String description() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cartes.size(); i++) {
            sb.append(cartes.get(i).description()).append(" ");
        }
        return sb.toString();
    }
}