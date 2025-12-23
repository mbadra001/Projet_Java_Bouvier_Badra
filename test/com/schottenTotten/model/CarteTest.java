package com.schottenTotten.model;

import org.junit.jupiter.api.Test; // Note le "jupiter" ici
import static org.junit.jupiter.api.Assertions.*; // Et ici "Assertions" au lieu de "Assert"

class CarteTest {

    @Test
    void testCreationCarte() {
        Carte c = new Carte(1, 5);
        assertEquals(1, c.getCouleur());
        assertEquals(5, c.getValeur());
        // Vérifie que la description contient bien les infos
        assertTrue(c.description().contains("Couleur : 1"));
        assertTrue(c.description().contains("Valeur : 5"));
    }

    @Test
    void testGroupeDeCartes() {
        GroupeDeCartes groupe = new GroupeDeCartes();
        assertTrue(groupe.estVide());

        groupe.ajouter(new Carte(1, 1));
        groupe.ajouter(new Carte(1, 2));

        assertEquals(2, groupe.getNombreDeCartes());
        assertFalse(groupe.estVide());
    }
}