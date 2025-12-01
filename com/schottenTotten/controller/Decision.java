package com.schottenTotten.controller;

import com.schottenTotten.model.Carte;
import com.schottenTotten.model.GroupeDeCartes;
import java.util.*;

public class Decision { //il va falloir aussi géré le cas de l'egalité
                        //peut etre ajoute un marqueur sur la class carte 
                        //qui serait modifié dans borne pour connaitre le dernier qui a pose sur la borne

    public static int determinerGagnant(GroupeDeCartes g1, GroupeDeCartes g2) {
        int score1 = calculerForce(g1);
        int score2 = calculerForce(g2);

        if (score1 > score2) {
            return 1;
        } else if (score2 > score1) {
            return 2;
        }else {
            return 0;
        }
    }

    public static int calculerForce(GroupeDeCartes groupe) {
        List<Carte> cartes = groupe.getCartes();

        boolean memeCouleur = estMemeCouleur(cartes);
        boolean suite = estSuite(cartes);
        boolean brelan = estBrelan(cartes);
        int somme = calculSomme(cartes);

        int score = 0;

        if (memeCouleur && suite) {
            score = 500;
        } else if (brelan) {
            score = 400;
        } else if (memeCouleur) {
            score = 300;
        } else if (suite) {
            score = 200;
        } else {
            score = 100;
        }

        return score + somme;
    }

    // Méthodes
    private static boolean estMemeCouleur(List<Carte> cartes) {
        int codeCouleur = cartes.get(0).getCouleur();
        return cartes.get(1).getCouleur() == codeCouleur && cartes.get(2).getCouleur() == codeCouleur;
    }

    private static boolean estBrelan(List<Carte> cartes) {
        int val = cartes.get(0).getValeur();
        return cartes.get(1).getValeur() == val && cartes.get(2).getValeur() == val;
    }

    private static boolean estSuite(List<Carte> cartes) {
        cartes.sort(Comparator.comparingInt(Carte::getValeur)); //tri du paquet de carte (ligne genere par ia)
        return (cartes.get(0).getValeur() + 1 == cartes.get(1).getValeur()) &&
               (cartes.get(1).getValeur() + 1 == cartes.get(2).getValeur());
    }

    private static int calculSomme(List<Carte> cartes) {
        int somme = 0;
        for (Carte c : cartes) somme += c.getValeur();
        return somme;
    }
}