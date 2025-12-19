package com.schottenTotten.controller;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;
import com.schottenTotten.model.CarteTactique;
import com.schottenTotten.model.GroupeDeCartes;
import com.schottenTotten.model.Joueur;
import com.schottenTotten.view.ConsoleView;
import java.util.*;

public class VarianteClassique implements Variante {

    @Override
    public GroupeDeCartes genererPioche() {
        GroupeDeCartes pioche = new GroupeDeCartes();
        List<Carte> deck = new ArrayList<>();
        // 6 couleurs, 9 valeurs
        for (int c = 1; c <= 6; c++) {
            for (int v = 1; v <= 9; v++) {
                deck.add(new Carte(c, v));
            }
        }
        //melange
        Collections.shuffle(deck); //fonction fournit par ia
        for (Carte carte : deck) {
            pioche.ajouter(carte);
        }
        return pioche;
    }

    @Override
    public int donnerGagnant(GroupeDeCartes g1, GroupeDeCartes g2) {
        return Decision.determinerGagnant(g1, g2);
    }

    @Override
    public int getNbCartesPourGagner() {
        return 3;
    }

    @Override
    public int getTailleMain() {
        return 6;
    }

     @Override
    public void appliquerEffetImmediat(Carte carte, Borne borne) {
    }

    @Override
    public boolean estCarteAction(Carte carte) {
        return false;
    }

    @Override
    public void executerAction(Carte carte, Joueur joueur, Joueur adversaire, GroupeDeCartes pioche, List<Borne> bornes, ConsoleView view){
    }
}