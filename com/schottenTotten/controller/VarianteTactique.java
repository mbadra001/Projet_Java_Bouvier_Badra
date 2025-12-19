package com.schottenTotten.controller;

import com.schottenTotten.model.Borne;
import com.schottenTotten.model.Carte;
import com.schottenTotten.model.CarteTactique;
import com.schottenTotten.model.GroupeDeCartes;
import com.schottenTotten.model.Joueur;
import com.schottenTotten.view.ConsoleView;
import com.schottenTotten.view.InputView;
import java.util.*;

public class VarianteTactique implements Variante {

    private int capaciteMax = 3;

    @Override
    public GroupeDeCartes genererPioche() {
        GroupeDeCartes pioche = new GroupeDeCartes();
        List<Carte> deck = new ArrayList<>();

        // 1. Cartes Normales
        for (int c = 1; c <= 6; c++) {
            for (int v = 1; v <= 9; v++) {
                deck.add(new Carte(c, v));
            }
        }

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
        for (Carte c : deck) {
            pioche.ajouter(c);
        }
        
        return pioche;
    }

    @Override
    public int getNbCartesPourGagner() {
        return 3;
    }

    @Override
    public int getTailleMain() {
         return 7;
    }

    @Override
    public int donnerGagnant(GroupeDeCartes g1, GroupeDeCartes g2) {

        preparerCartesSpeciales(g1);
        preparerCartesSpeciales(g2);

        boolean modeSomme = contientCarteNom(g1, "Colin-Maillard") || contientCarteNom(g2, "Colin-Maillard");

        if (modeSomme) {
            int s1 = calculerSomme(g1);
            int s2 = calculerSomme(g2);
            if (s1 > s2) {
                return 1;
            }else if (s2 > s1) {
                return 2;
            }else {
                return 0;
            }
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

    @Override
    public void executerAction(Carte carte, Joueur j, Joueur adv, GroupeDeCartes p, List<Borne> bornes, ConsoleView view) {
        String nom = ((CarteTactique) carte).getCapacite();
        view.afficherEffetTactique(nom);

        if (nom.equalsIgnoreCase("Chasseur de Tête")) {
            effetChasseur(j, p, view); // Passage de view
        } else if (nom.equalsIgnoreCase("Stratège")) {
            effetStratege(j, bornes, view); // Passage de view
        } else if (nom.equalsIgnoreCase("Banshee")) {
            effetBanshee(adv, bornes, view); // Passage de view
        } else if (nom.equalsIgnoreCase("Traître")) {
            effetTraitre(j, adv, bornes, view); // Passage de view
        }
    }

    // ACTIONS

    private void effetChasseur(Joueur j, GroupeDeCartes p, ConsoleView view) {
        for(int i=0; i<3; i++) {
            if(!p.estVide()) {
                j.recevoirCarte(p.retirer(0));
            }
        }
        for(int i=0; i<2; i++) {
            view.afficherMain(j);
            int idx = InputView.demanderIndexCarteSurBorne();
            p.ajouter(j.retirer(idx));
        }
    }

    private void effetStratege(Joueur j, List<Borne> b, ConsoleView view) {
        int idxSrc = InputView.demanderBorneCible("Source");
        GroupeDeCartes g = b.get(idxSrc).getCartes(j);
        view.afficherSelectionCarteSurBorne(g); 
        Carte c = g.retirer(InputView.demanderIndexCarteSurBorne());
        
        if (InputView.demanderChoixStratege() == 2) return;
        
        int idxDest = InputView.demanderBorneCible("Destination");
        if (!b.get(idxDest).poserCarte(c, j)) g.ajouter(c);
    }

    private void effetBanshee(Joueur adv, List<Borne> b, ConsoleView view) {
        int idx = InputView.demanderBorneCible("Adverse");
        GroupeDeCartes g = b.get(idx).getCartes(adv);
        view.afficherSelectionCarteSurBorne(g);
        g.retirer(InputView.demanderIndexCarteSurBorne());
    }

    private void effetTraitre(Joueur j, Joueur adv, List<Borne> b, ConsoleView view) {
        int idxSrc = InputView.demanderBorneCible("Adverse");
        GroupeDeCartes gAdv = b.get(idxSrc).getCartes(adv);
        view.afficherSelectionCarteSurBorne(gAdv);
        
        Carte c = gAdv.retirer(InputView.demanderIndexCarteSurBorne());
        int idxDest = InputView.demanderBorneCible("Destination chez vous");
        if (!b.get(idxDest).poserCarte(c, j)) view.afficherErreurTactique("Borne pleine");
    }

    // OUTILS

    private void preparerCartesSpeciales(GroupeDeCartes g) {
        for(Carte c : g.getCartes()) {
            if(c instanceof CarteTactique) {
                CarteTactique t = (CarteTactique)c;
                if(t.getValeur()==0) {
                    if(t.getCapacite().equalsIgnoreCase("Joker")) {
                        config(t,1,9); // Retrait du nom
                    } else if(t.getCapacite().equalsIgnoreCase("Espion")) {
                        config(t,7,7);
                    } else if(t.getCapacite().equalsIgnoreCase("Porte-Bouclier")) {
                        config(t,1,3);
                    }
                }
            }
        }
    }

    private void config(CarteTactique t, int min, int max) {
        t.setCouleur(InputView.demanderCouleur());
        t.setValeur(InputView.demanderValeur(min, max));
    }


    private boolean contientCarteNom(GroupeDeCartes g, String n) {
        for(Carte c : g.getCartes()) if(c instanceof CarteTactique && ((CarteTactique)c).getCapacite().equalsIgnoreCase(n)) return true;
        return false;
    }

    private int calculerSomme(GroupeDeCartes g) { int s=0; for(Carte c:g.getCartes()) s+=c.getValeur(); return s; }
}
