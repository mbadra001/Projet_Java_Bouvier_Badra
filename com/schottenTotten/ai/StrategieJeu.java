package com.schottenTotten.ai;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Joueur;
import java.util.List;


// Interface pour la stratégie de jeu des IA EN GROS toutes IA devra utilisé cette interface et dans Ia aleatoire Ia normale que ca va changer 
public interface StrategieJeu {
    Coup jouer(Joueur joueur, List<Borne> bornes);
}