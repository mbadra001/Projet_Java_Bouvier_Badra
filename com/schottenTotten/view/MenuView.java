package com.schottenTotten.view;

public class MenuView {

    public void afficherMenuPrincipal(){
        System.out.println("=== MENU PRINCIPAL ===");
        System.out.println("Jouer une partie");

    }

    public int demanderVarianteDeJeu(){
        System.out.println("Choisissez une variante de jeu :");
        System.out.println("1. Classique");
        System.out.println("2. Tactique");
        return InputView.demanderEntier();
        
    }

    public String demanderNomJoueur(int numero){
        System.out.println("Entrez le nom du joueur " + numero + " :");
        return InputView.demanderTexte();
        
    }

    public boolean demanderAvecIA(){
        System.out.println("Voulez-vous jouer contre une IA ?");
        System.out.println("1. Oui");
        System.out.println("2. Non");
        return InputView.demanderEntier() ==1;
    }

    public int demanderTypeIA(){
        System.out.println("type d'IA :");
        System.out.println("1. basique");
        System.out.println("2. complexe");

        return InputView.demanderEntier();
    }











}