package com.schottenTotten.ai;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Joueur;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IaAleatoire implements StrategieJeu {

    private Random random = new Random();

    @Override // à voir car IA 
    public Coup jouer(Joueur joueur, List<Borne> bornes) {
        // Choisir une carte au hasard (index)
        int indexCarte = random.nextInt(joueur.getMain().size());

        // Trouver les bornes valides (non pleines)
        List<Integer> bornesValides = new ArrayList<>();
        
        for (int i = 0; i < bornes.size(); i++) {
            Borne b = bornes.get(i);
            // On vérifie si le joueur peut encore jouer dessus
            if (b.getPossesseur() == null) {
                // Vérifie si le coté est plein
                if (b.getCartes(joueur).getNombreDeCartes() < b.getCapaciteMax()) {
                    bornesValides.add(i);
                }
            }
        }

        // Si aucune borne valide on renvoie 0 par défaut
        if (bornesValides.isEmpty()) {
            return new Coup(indexCarte, 0); 
        }

        // Choisir une borne au hasard parmi les valides
        int indexBorneChoisie = bornesValides.get(random.nextInt(bornesValides.size()));

        return new Coup(indexCarte, indexBorneChoisie);
    }
}