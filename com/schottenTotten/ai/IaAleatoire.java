package com.schottenTotten.ai;

import com.schottenTotten.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IaAleatoire implements StrategieJeu {

    private Random random = new Random();

    @Override
    public Coup jouer(Joueur joueur, List<Borne> bornes) {
        List<Carte> main = joueur.getMain();
        List<Integer> normales = new ArrayList<>();
        List<Integer> tactiques = new ArrayList<>();

        // Sépare cartes normales et tactiques
        for (int i = 0; i < main.size(); i++) {
            if (main.get(i) instanceof CarteTactique) tactiques.add(i);
            else normales.add(i);
        }

        //  JEU TACTIQUE 25% de chance
        boolean jouerTactique = !tactiques.isEmpty() && random.nextInt(100) < 25;

        if (jouerTactique) {
            int indexCarte = tactiques.get(random.nextInt(tactiques.size()));
            CarteTactique ct = (CarteTactique) main.get(indexCarte);
            String type = ct.getCapacite();

            // Gestion des cartes qui se posent sur une borne
            if (type.equalsIgnoreCase("Colin-Maillard") || type.equalsIgnoreCase("Combat de Boue")) {
                int b = trouverBorneNonPleine(bornes, joueur); 
                if (b != -1) {
                    // Créer le coup tactique
                    Coup c = new Coup(indexCarte, type);
                    c.indexBorne = b;
                    return c;
                }
            }
            // Gestion des cartes Joker/Espion/Porte-Bouclier
            else if (type.equalsIgnoreCase("Joker") || type.equalsIgnoreCase("Espion") || type.equalsIgnoreCase("Porte-Bouclier")) {
                int b = trouverBorneNonPleine(bornes, joueur);
                if (b != -1) {
                    Coup c = new Coup(indexCarte, type, random.nextInt(6) + 1, random.nextInt(9) + 1);
                    c.indexBorne = b;
                    return c;
                }
            }
            // Actions 
            else {
                switch (type) {
                    case "Banshee": 
                        return genererCoupCiblageAdverse(indexCarte, type, bornes, joueur, false);
                    case "Traître": 
                        return genererCoupCiblageAdverse(indexCarte, type, bornes, joueur, true);
                    case "Stratège": 
                        return genererCoupStratege(indexCarte, type, bornes, joueur);
                    case "Chasseur de Tête": 
                        return new Coup(indexCarte, type);
                }
            }
        }

        //  JEU NORMALE
        List<Integer> cartesJouables = new ArrayList<>(normales);
        if (cartesJouables.isEmpty()) {
            cartesJouables.addAll(tactiques);
        }

        if (cartesJouables.isEmpty()) return Coup.impossible();

        int borneCible = trouverBorneNonPleine(bornes, joueur);
        
        if (borneCible == -1) {
            return Coup.impossible(); 
        }

        int indexCarteChoisie = cartesJouables.get(random.nextInt(cartesJouables.size()));
        
        Carte c = joueur.getMain().get(indexCarteChoisie);
        if (c instanceof CarteTactique) {
            CarteTactique ct = (CarteTactique) c;
            String nom = ct.getCapacite();
            if (nom.equalsIgnoreCase("Joker") || nom.equalsIgnoreCase("Espion") || nom.equalsIgnoreCase("Porte-Bouclier")) {
                 Coup coup = new Coup(indexCarteChoisie, nom, random.nextInt(6)+1, random.nextInt(9)+1);
                 coup.indexBorne = borneCible;
                 return coup;
            }
             Coup coup = new Coup(indexCarteChoisie, nom);
             coup.indexBorne = borneCible;
             return coup;
        }

        return new Coup(indexCarteChoisie, borneCible);
    }
    

    private int trouverBorneNonPleine(List<Borne> bornes, Joueur joueur) {
        List<Integer> valides = new ArrayList<>();
        int numJoueur = joueur.getNumero(); 

        for (int i = 0; i < bornes.size(); i++) {
            Borne b = bornes.get(i);
            if (b.getPossesseur() == null && !b.estPleine(numJoueur)) { 
                 valides.add(i);
            }
        }
        
        if (valides.isEmpty()) return -1;
        return valides.get(random.nextInt(valides.size()));
    }
    
    private Coup genererCoupCiblageAdverse(int indexCarte, String type, List<Borne> bornes, Joueur moi, boolean besoinDestination) {
        Joueur adversaire = new Joueur("Dummy", (moi.getNumero() == 1 ? 2 : 1));
        
        List<Integer> bornesAvecCartesAdverses = new ArrayList<>();
        for(int i=0; i<bornes.size(); i++) {
            if (bornes.get(i).getPossesseur() == null && bornes.get(i).getCartes(adversaire).getNombreDeCartes() > 0) {
                bornesAvecCartesAdverses.add(i);
            }
        }
        
        if (bornesAvecCartesAdverses.isEmpty()) {
            int b = trouverBorneNonPleine(bornes, moi);
            if (b != -1) return new Coup(indexCarte, b); 
            return Coup.impossible();
        }

        int idxBorneSrc = bornesAvecCartesAdverses.get(random.nextInt(bornesAvecCartesAdverses.size()));
        int nbCartesAdv = bornes.get(idxBorneSrc).getCartes(adversaire).getNombreDeCartes();
        int idxCarteCible = random.nextInt(nbCartesAdv);

        if (besoinDestination) {
            int idxDest = trouverBorneNonPleine(bornes, moi);
            if (idxDest == -1) return Coup.impossible();
            return new Coup(indexCarte, type, idxBorneSrc, idxCarteCible, idxDest, -1);
        } else {
            return new Coup(indexCarte, type, idxBorneSrc, idxCarteCible);
        }
    }
    
    private Coup genererCoupStratege(int indexCarte, String type, List<Borne> bornes, Joueur moi) {
         List<Integer> mesBornesAvecCartes = new ArrayList<>();
         for(int i=0; i<bornes.size(); i++) {
             if (bornes.get(i).getPossesseur() == null && bornes.get(i).getCartes(moi).getNombreDeCartes() > 0) {
                 mesBornesAvecCartes.add(i);
             }
         }
         
         if (mesBornesAvecCartes.isEmpty()) {
             int b = trouverBorneNonPleine(bornes, moi);
             return (b != -1) ? new Coup(indexCarte, b) : Coup.impossible();
         }

         int src = mesBornesAvecCartes.get(random.nextInt(mesBornesAvecCartes.size()));
         int idxCarteSurBorne = 0; 
         
         return new Coup(indexCarte, type, src, idxCarteSurBorne, -1, 2); 
    }
}