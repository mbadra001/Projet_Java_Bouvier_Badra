package com.schottenTotten.ai;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Joueur;
import java.util.List;

public interface StrategieJeu {
    // L'IA reçoit le joueur (pour voir sa main) et la liste des bornes (pour voir où jouer)
    Coup jouer(Joueur joueur, List<Borne> bornes);
}