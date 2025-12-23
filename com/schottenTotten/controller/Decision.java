package com.schottenTotten.controller;

import com.schottenTotten.model.Carte;
import com.schottenTotten.model.GroupeDeCartes;
import java.util.*;

public class Decision {

    public static int determinerGagnant(GroupeDeCartes g1, GroupeDeCartes g2) {
        int score1 = calculerForce(g1);
        int score2 = calculerForce(g2);

        if (score1 > score2) return 1;
        if (score2 > score1) return 2;
        return 0;
    }

    public static int calculerForce(GroupeDeCartes groupe) {
        List<Carte> cartes = groupe.getCartes();
        
        // Si le groupe n'est pas complet , on ne peut pas calculer de combinaison On retourne juste la somme pour éviter le crash.
        if (cartes.size() < 3) {
            return calculSomme(cartes);
        }

        boolean memeCouleur = estMemeCouleur(cartes);
        boolean suite = estSuite(cartes);
        boolean brelan = estBrelan(cartes);
        int somme = calculSomme(cartes);

        int score = 0;

        if (memeCouleur && suite) score = 500;
        else if (brelan) score = 400;
        else if (memeCouleur) score = 300;
        else if (suite) score = 200;
        else score = 100;

        return score + somme;
    }

    private static boolean estMemeCouleur(List<Carte> cartes) {
        if (cartes.size() < 3) return false;
        int code = cartes.get(0).getCouleur();
        return cartes.get(1).getCouleur() == code && cartes.get(2).getCouleur() == code;
    }

    private static boolean estBrelan(List<Carte> cartes) {
        if (cartes.size() < 3) return false;
        int val = cartes.get(0).getValeur();
        return cartes.get(1).getValeur() == val && cartes.get(2).getValeur() == val;
    }

    private static boolean estSuite(List<Carte> cartes) {
        if (cartes.size() < 3) return false;
        // On trie copie pour ne pas modifier l'ordre du plateau
        List<Carte> triees = new ArrayList<>(cartes);
        triees.sort(Comparator.comparingInt(Carte::getValeur));
        return (triees.get(0).getValeur() + 1 == triees.get(1).getValeur()) &&
               (triees.get(1).getValeur() + 1 == triees.get(2).getValeur());
    }

    private static int calculSomme(List<Carte> cartes) {
        int somme = 0;
        for (Carte c : cartes) somme += c.getValeur();
        return somme;
    }

    public static String expliquerVictoire(GroupeDeCartes g1, GroupeDeCartes g2) {
        int score1 = calculerForce(g1);
        int score2 = calculerForce(g2);
        
        String type1 = nomCombinaison(score1);
        String type2 = nomCombinaison(score2);
        
        // utilisation de score % 100 pour n'afficher que la partie somme
        return "J1 [" + type1 + " (" + (score1 % 100) + ")] vs J2 [" + type2 + " (" + (score2 % 100) + ")]";
    }

    private static String nomCombinaison(int score) {
        if (score >= 500) return "Suite Couleur";
        if (score >= 400) return "Brelan";
        if (score >= 300) return "Couleur";
        if (score >= 200) return "Suite";
        return "Somme";
    }
}