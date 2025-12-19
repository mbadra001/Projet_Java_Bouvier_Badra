package com.schottenTotten.controller;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;
import com.schottenTotten.model.GroupeDeCartes;
import com.schottenTotten.model.Joueur;
import com.schottenTotten.view.ConsoleView;
import java.util.List;

public interface Variante {

    GroupeDeCartes genererPioche();

    int donnerGagnant(GroupeDeCartes g1, GroupeDeCartes g2);

    int getNbCartesPourGagner();

    int getTailleMain();

    void appliquerEffetImmediat(Carte carte, Borne borne);

    boolean estCarteAction(Carte carte);

    void executerAction(Carte carte, Joueur joueur, Joueur adversaire, GroupeDeCartes pioche, List<Borne> bornes, ConsoleView view);
}