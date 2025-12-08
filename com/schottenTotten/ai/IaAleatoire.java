package com.schottenTotten.ai;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Joueur;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IaAleatoire implements StrategieJeu {

    private Random random = new Random();

    @Override
    public Coup jouer(Joueur joueur, List<Borne> bornes) {
        // 1. Choisir une carte au hasard (index)
        int indexCarte = random.nextInt(joueur.getMain().size());

        // 2. Trouver les bornes valides (non pleines)
        List<Integer> bornesValides = new ArrayList<>();
        
        for (int i = 0; i < bornes.size(); i++) {
            Borne b = bornes.get(i);
            // On vérifie si le joueur peut encore jouer dessus
            // (Note: il faut vérifier si LE CÔTÉ du joueur est plein, pas toute la borne)
            // Pour simplifier ici, on vérifie si la borne est revendiquée ou non
            // Une vérification plus fine est faite dans Jeu.java de toute façon.
            if (b.getPossesseur() == null) {
                // Vérifions si le coté est plein (on accède via la méthode publique getCartes)
                if (b.getCartes(joueur).getNombreDeCartes() < Borne.CAPACITE_MAX) {
                    bornesValides.add(i);
                }
            }
        }

        // Si aucune borne valide (cas rare mais possible), on renvoie 0 par défaut
        if (bornesValides.isEmpty()) {
            return new Coup(indexCarte, 0); 
        }

        // 3. Choisir une borne au hasard parmi les valides
        int indexBorneChoisie = bornesValides.get(random.nextInt(bornesValides.size()));

        return new Coup(indexCarte, indexBorneChoisie);
    }
}