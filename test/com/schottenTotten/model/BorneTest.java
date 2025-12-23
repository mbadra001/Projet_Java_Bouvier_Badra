package com.schottenTotten.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BorneTest {

    @Test
    void testRemplissageBorne() {
        Borne borne = new Borne();
        Joueur j1 = new Joueur("J1", 1);

        assertFalse(borne.estPleine(1));

        borne.poserCarte(new Carte(1, 1), j1);
        borne.poserCarte(new Carte(1, 2), j1);
        
        assertFalse(borne.estPleine(1), "La borne ne devrait pas être pleine avec 2 cartes");
        
        borne.poserCarte(new Carte(1, 3), j1);
        assertTrue(borne.estPleine(1), "La borne devrait être pleine avec 3 cartes");
        
        boolean succes = borne.poserCarte(new Carte(1, 4), j1);
        assertFalse(succes, "On ne devrait pas pouvoir poser une 4ème carte");
    }
}