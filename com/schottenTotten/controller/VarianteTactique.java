package com.schottenTotten.controller;

import com.schottenTotten.ai.Coup; 
import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;
import com.schottenTotten.model.CarteTactique;
import com.schottenTotten.model.GroupeDeCartes;
import com.schottenTotten.model.Joueur;
import com.schottenTotten.view.ConsoleView;
import com.schottenTotten.view.InputView;
import java.util.*;

public class VarianteTactique implements Variante {

    @Override
    public GroupeDeCartes genererPioche() {
        GroupeDeCartes pioche = new GroupeDeCartes();
        List<Carte> deck = new ArrayList<>();

        // Cartes Normales
        for (int c = 1; c <= 6; c++) {
            for (int v = 1; v <= 9; v++) {
                deck.add(new Carte(c, v));
            }
        }
        // Cartes Tactiques
        deck.add(new CarteTactique("Joker"));
        deck.add(new CarteTactique("Joker"));
        deck.add(new CarteTactique("Espion"));
        deck.add(new CarteTactique("Porte-Bouclier"));
        deck.add(new CarteTactique("Colin-Maillard")); 
        deck.add(new CarteTactique("Combat de Boue"));
        deck.add(new CarteTactique("Chasseur de Tête"));
        deck.add(new CarteTactique("Stratège"));
        deck.add(new CarteTactique("Banshee"));
        deck.add(new CarteTactique("Traître"));

        Collections.shuffle(deck);
        for (Carte c : deck) pioche.ajouter(c);
        
        return pioche;
    }

    @Override public int getNbCartesPourGagner() { return 3; }
    @Override public int getTailleMain() { return 7; }

    @Override
    public int donnerGagnant(GroupeDeCartes g1, GroupeDeCartes g2) {
        preparerCartesSpeciales(g1);
        preparerCartesSpeciales(g2);

        boolean modeSomme = contientCarteNom(g1, "Colin-Maillard") || contientCarteNom(g2, "Colin-Maillard");

        if (modeSomme) {
            int s1 = calculerSomme(g1);
            int s2 = calculerSomme(g2);
            if (s1 > s2) return 1;
            else if (s2 > s1) return 2;
            else return 0;
        }
        return Decision.determinerGagnant(g1, g2);
    }

    @Override
    public void appliquerEffetImmediat(Carte carte, Borne borne) {
        if (carte instanceof CarteTactique && ((CarteTactique)carte).getCapacite().equalsIgnoreCase("Combat de Boue")) {
            borne.setCapaciteMax(4);
        }
    }

    @Override
    public boolean estCarteAction(Carte carte) {
        if (carte instanceof CarteTactique) {
            String nom = ((CarteTactique) carte).getCapacite();
            return nom.equalsIgnoreCase("Chasseur de Tête") || nom.equalsIgnoreCase("Stratège") || nom.equalsIgnoreCase("Banshee") || nom.equalsIgnoreCase("Traître");
        }
        return false;
    }

    //  paramètre "Coup coup" pour récupérer les choix de l'IA
    @Override
    public void executerAction(CarteTactique carte, Joueur j, Joueur adversaire, GroupeDeCartes pioche, List<Borne> bornes, ConsoleView view, Coup coup) {
        String nom = carte.getCapacite();
        view.afficherEffetTactique(nom);

        // Si c'est une IA, on prend les valeurs dans le Coup, sinon, InputView 
        if (j.estIA() && coup != null) {
            if (nom.equalsIgnoreCase("Joker") || nom.equalsIgnoreCase("Espion") || nom.equalsIgnoreCase("Porte-Bouclier")) {
                carte.setCouleur(coup.param1);
                if (coup.param2 != -1) carte.setValeur(coup.param2);
            }
        } 
        
        // tactique 
        if (nom.equalsIgnoreCase("Chasseur de Tête")) {
            effetChasseur(j, pioche, view); 
        } else if (nom.equalsIgnoreCase("Stratège")) {
            effetStratege(j, bornes, view, coup); 
        } else if (nom.equalsIgnoreCase("Banshee")) {
            effetBanshee(adversaire, bornes, view, coup); 
        } else if (nom.equalsIgnoreCase("Traître")) {
            effetTraitre(j, adversaire, bornes, view, coup); 
        } else if (nom.equalsIgnoreCase("Colin-Maillard") || nom.equalsIgnoreCase("Combat de Boue")) {
            
            // Ces cartes se posent sur une borne, si IA a défini une borne cibleon utilise.
            if (coup != null && coup.indexBorne != -1) {
                bornes.get(coup.indexBorne).poserCarte(carte, j);
            } else if (!j.estIA()) {
                // Pour humain on demande où poser
                int b = InputView.demanderBorne();
                bornes.get(b).poserCarte(carte, j);
            }
        }
    }

    // Effets des Cartes Tactiques 

    private void effetStratege(Joueur j, List<Borne> b, ConsoleView view, Coup coup) {
        int idxSrc, idxCarte, choix, idxDest;
        
        //  IA vs HUMAIN
        if (j.estIA() && coup != null) {
            idxSrc = coup.param1; 
            idxCarte = coup.param2; 
            choix = coup.param4; 
            idxDest = coup.param3;
        } else {
            idxSrc = InputView.demanderBorneCible("Source");
            GroupeDeCartes g = b.get(idxSrc).getCartes(j);
            view.afficherSelectionCarteSurBorne(g); 
            idxCarte = InputView.demanderIndexCarteSurBorne();
            choix = InputView.demanderChoixStratege(); // 1=Déplacer, 2=Défausser
            idxDest = (choix == 1) ? InputView.demanderBorneCible("Destination") : -1;
        }
        
        GroupeDeCartes g = b.get(idxSrc).getCartes(j);
        if (g.getNombreDeCartes() <= idxCarte) return; 

        // On récupère la carte choisie
        Carte c = g.retirer(idxCarte);
        if (choix == 2) { 
             view.afficherMessage(j.getNom() + " défausse une carte.");
             
        } else { 
            if (idxDest != -1 && !b.get(idxDest).poserCarte(c, j)) {
                g.ajouter(c);
                view.afficherErreur("Déplacement impossible (borne pleine/gagnée).");
            }
        }
    }

    private void effetBanshee(Joueur adv, List<Borne> b, ConsoleView view, Coup coup) {
        int idxBorne, idxCarte;
        
        if (coup != null && coup.estTactique && coup.param1 != -1) { // IA
            idxBorne = coup.param1; 
            idxCarte = coup.param2;
        } else { // Humain
            idxBorne = InputView.demanderBorneCible("Adverse");
            GroupeDeCartes g = b.get(idxBorne).getCartes(adv);
            view.afficherSelectionCarteSurBorne(g);
            idxCarte = InputView.demanderIndexCarteSurBorne();
        }

        GroupeDeCartes g = b.get(idxBorne).getCartes(adv);
        if (g.getNombreDeCartes() > idxCarte) {
            Carte c = g.retirer(idxCarte);
            view.afficherMessage("Carte " + c.description() + " défaussée par la Banshee !");
        }
    }

    private void effetTraitre(Joueur j, Joueur adv, List<Borne> b, ConsoleView view, Coup coup) {
        int idxSrc, idxCarte, idxDest;
        
        if (j.estIA() && coup != null) { // IA
            idxSrc = coup.param1; 
            idxCarte = coup.param2; 
            idxDest = coup.param3;
        } else { // Humain
            idxSrc = InputView.demanderBorneCible("Adverse");
            GroupeDeCartes gAdv = b.get(idxSrc).getCartes(adv);
            view.afficherSelectionCarteSurBorne(gAdv);
            idxCarte = InputView.demanderIndexCarteSurBorne();
            idxDest = InputView.demanderBorneCible("Destination chez vous");
        }

        GroupeDeCartes gAdv = b.get(idxSrc).getCartes(adv);
        if (gAdv.getNombreDeCartes() > idxCarte) {
            Carte c = gAdv.retirer(idxCarte);
            if (!b.get(idxDest).poserCarte(c, j)) {
                gAdv.ajouter(c); 
                view.afficherErreurTactique("Votre borne cible est pleine ou invalide.");
            } else {
                view.afficherMessage("Le Traître vole une carte !");
            }
        }
    }

    private void effetChasseur(Joueur j, GroupeDeCartes p, ConsoleView view) {
        // Si IA = choix aléatoire pour la défausse
        if (j.estIA()) {
            view.afficherMessage("L'IA utilise Chasseur de Tête...");
            for(int i=0; i<3; i++) { if(!p.estVide()) j.recevoirCarte(p.retirer(0)); }
            Collections.shuffle(j.getMain()); // Mélange pour rendre aléatoire
            p.ajouter(j.retirer(0));
            p.ajouter(j.retirer(0));
        } else {
            for(int i=0; i<3; i++) { if(!p.estVide()) j.recevoirCarte(p.retirer(0)); }
            for(int i=0; i<2; i++) {
                view.afficherMain(j);
                int idx = InputView.demanderIndexCarteSurBorne(); 
                p.ajouter(j.retirer(idx));
            }
        }
    }


    private void preparerCartesSpeciales(GroupeDeCartes g) {
        for (Carte c : g.getCartes()) {
             if (c instanceof CarteTactique && ((CarteTactique)c).getCapacite().equalsIgnoreCase("Joker")) {
                 if (c.getValeur() == 0) c.setValeur(9); // Devient  9 si pas défini
                 if (c.getCouleur() == 0) c.setCouleur(1); 
             }
        }
    }

    private boolean contientCarteNom(GroupeDeCartes g, String nomCherche) {
        for (Carte c : g.getCartes()) {
            if (c instanceof CarteTactique && ((CarteTactique)c).getCapacite().equalsIgnoreCase(nomCherche)) {
                return true;
            }
        }
        return false;
    }

    private int calculerSomme(GroupeDeCartes g) {
        int somme = 0;
        for (Carte c : g.getCartes()) somme += c.getValeur();
        return somme;
    }
}