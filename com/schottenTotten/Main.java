package com.schottenTotten;

import com.schottenTotten.controller.Jeu;
import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Joueur;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Jeu jeu = new Jeu("Joueur 1", "Joueur 2");
        
        boolean partieEnCours = true;

        while (partieEnCours) {
            
            Joueur joueurActuel = jeu.getJoueurActuel();
            List<Borne> bornes = jeu.getBornes(); 

            // Affichage du plateau (Les 9 bornes)
        
            // Affichage du joueur actif et de sa main

            // Demande de saisie : CARTE (on obtient indexCarte)

            // Demande de saisie : BORNE (on obtient indexBorne)

            // On joue le tour
            int indexCarte = 0;
            int indexBorne = 0;
            partieEnCours = jeu.jouerTour(indexCarte, indexBorne);
        }
    }
}