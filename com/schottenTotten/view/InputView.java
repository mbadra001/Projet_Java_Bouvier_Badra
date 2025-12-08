
package com.schottenTotten.view;

import java.util.Scanner;

public class InputView {

   // scanner permet de lire les entrees utilisateur clavier
    private static Scanner scan = new Scanner(System.in);

   //static pour evite de faire des objet pour utiliser les methodes
    public static int demanderEntier() {
// Integer parsingint permet de convertir une chaine de caractere en entier
// si la conversion echoue, une exception est crée 
        while(true){
             try {
                return Integer.parseInt(scan.nextLine());
            } 
            catch (Exception erreur) {
                System.out.println("Entrée invalide: entre un nouveau nombre.");
            }
        }
    }

    public static int demanderCarteAJouer() {
        System.out.print("Index de la carte à jouer : ");
        return demanderEntier();
    }

    public static int demanderBorne() {
        System.out.print("numéro borne: ");
        return demanderEntier();
    }


    public static boolean demanderSiRevendiquer() {
        System.out.println("Revendiquez ? 1 = Oui / 2 = Non");
        return demanderEntier() == 1;
    }

    // si la personne veut utiliser une carte tactique

    public static String demanderTexte() {
        return scan.nextLine();
    }

// focnction pour les cartes tactqiues demander les valeurs pour chaque carte --------------------------


    public static int demanderCouleurJoker() {
        System.out.print("Couleur pour le joker (1-6) : ");
        return demanderEntier();
       
    }

    public static int demanderNumeroJoker() {
        System.out.print("Numéro de la carte joker dans la main : ");
        return demanderEntier();
    }


    public static int demanderCouleurPourEspion() {
    System.out.print("Couleur de l’Espion (1–6) : ");
    return demanderEntier();
    }

    public static int demanderCouleurPourPorteBouclier() {
    System.out.print("Couleur du Porte-Bouclier (1–6) : ");
    return demanderEntier();
    }

    public static int demanderValeurPourPorteBouclier() {
        System.out.print("Valeur du Porte-Bouclier (1, 2 ou 3) : ");
        return demanderEntier();
    }





// ----------------------------------------------------------------------------------------------
    public static void fermerScanner() {
        scan.close();
    }

}
