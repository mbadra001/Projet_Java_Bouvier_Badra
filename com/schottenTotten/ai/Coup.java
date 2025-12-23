package com.schottenTotten.ai;

public class Coup {

    public int indexCarte;      
    public int indexBorne;      
    public boolean coupPossible; 

    // TACTIQUE
    public boolean estTactique;      
    public String typeTactique;      
    
    // On augmente le nombre de paramètres pour gérer Traître/Stratège
    public int param1; //  Borne Source / Couleur
    public int param2; // Index Carte / Valeur
    public int param3; //  Borne Destination
    public int param4; //  Option (défausser ou déplacer)

    // Constructeur tactique
    public Coup(int indexCarte, String typeTactique, int p1, int p2, int p3, int p4) {
        this.indexCarte = indexCarte;
        this.indexBorne = -1;
        this.coupPossible = true;
        this.estTactique = true;
        this.typeTactique = typeTactique;
        this.param1 = p1;
        this.param2 = p2;
        this.param3 = p3;
        this.param4 = p4;
    }

    // Constructeur simple
    public Coup(int indexCarte, int indexBorne) {
        this(indexCarte, null, -1, -1, -1, -1);
        this.estTactique = false;
        this.indexBorne = indexBorne;
    }
    
    // Constructeur Tactique simple 
    public Coup(int indexCarte, String typeTactique) {
        this(indexCarte, typeTactique, -1, -1, -1, -1);
    }
    
    // Constructeur Tactique avec params (ex: Joker)
    public Coup(int indexCarte, String typeTactique, int p1, int p2) {
        this(indexCarte, typeTactique, p1, p2, -1, -1);
    }

    public static Coup impossible() {
        Coup c = new Coup(-1, -1);
        c.coupPossible = false;
        return c;
    }
}