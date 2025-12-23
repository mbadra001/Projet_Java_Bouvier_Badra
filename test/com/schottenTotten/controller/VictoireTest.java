package com.schottenTotten.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VictoireTest {

    private int verifierVictoire(int[] proprietairesBornes) {
        int bornesJ1 = 0;
        int bornesJ2 = 0;

        for (int p : proprietairesBornes) {
            if (p == 1) bornesJ1++;
            if (p == 2) bornesJ2++;
        }
        if (bornesJ1 >= 5) return 1;
        if (bornesJ2 >= 5) return 2;

        for (int i = 0; i <= proprietairesBornes.length - 3; i++) {
            if (proprietairesBornes[i] == 1 && proprietairesBornes[i+1] == 1 && proprietairesBornes[i+2] == 1) return 1;
            if (proprietairesBornes[i] == 2 && proprietairesBornes[i+1] == 2 && proprietairesBornes[i+2] == 2) return 2;
        }
        return 0;
    }

    @Test
    void testVictoireParMajorite() {
        int[] etat = {1, 0, 1, 2, 1, 0, 1, 2, 1}; 
        assertEquals(1, verifierVictoire(etat));
    }

    @Test
    void testVictoireParAdjacence() {
        int[] etat = {1, 0, 1, 2, 2, 2, 0, 0, 1}; 
        assertEquals(2, verifierVictoire(etat));
    }
}