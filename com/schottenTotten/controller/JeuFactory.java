package com.schottenTotten.controller;

import com.schottenTotten.view.ConsoleView;

public class JeuFactory {

    public static Jeu creerJeu(String typeVariante, String nomJ1, String nomJ2, ConsoleView view) {
        Variante variante;

        if (typeVariante.equalsIgnoreCase("TACTIQUE")) {
            variante = new VarianteTactique();
        } else {
            variante = new VarianteClassique();
        }

        return new Jeu(nomJ1, nomJ2, variante, view);
    }
}