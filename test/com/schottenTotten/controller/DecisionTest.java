package com.schottenTotten.controller;

import com.schottenTotten.model.Carte;
import com.schottenTotten.model.GroupeDeCartes;
import org.junit.jupiter.api.BeforeEach; // "BeforeEach" au lieu de "Before"
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DecisionTest {

    private GroupeDeCartes mainJ1;
    private GroupeDeCartes mainJ2;

    @BeforeEach
    void setUp() {
        mainJ1 = new GroupeDeCartes();
        mainJ2 = new GroupeDeCartes();
    }

    @Test
    void testSuiteCouleurBatBrelan() {
        // J1 : Suite-Couleur
        mainJ1.ajouter(new Carte(1, 1));
        mainJ1.ajouter(new Carte(1, 2));
        mainJ1.ajouter(new Carte(1, 3));

        // J2 : Brelan
        mainJ2.ajouter(new Carte(1, 9));
        mainJ2.ajouter(new Carte(2, 9));
        mainJ2.ajouter(new Carte(3, 9));

        // J1 gagne
        assertEquals(1, Decision.determinerGagnant(mainJ1, mainJ2));
    }

    @Test
    void testCouleurBatSuite() {
        // J1 : Couleur
        mainJ1.ajouter(new Carte(1, 1));
        mainJ1.ajouter(new Carte(1, 5));
        mainJ1.ajouter(new Carte(1, 8));

        // J2 : Suite
        mainJ2.ajouter(new Carte(2, 2));
        mainJ2.ajouter(new Carte(3, 3));
        mainJ2.ajouter(new Carte(4, 4));

        assertEquals(1, Decision.determinerGagnant(mainJ1, mainJ2));
    }
}
