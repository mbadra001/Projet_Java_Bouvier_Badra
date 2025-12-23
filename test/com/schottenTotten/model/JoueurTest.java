package com.schottenTotten.model;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class JoueurTest {

    @Test
    public void testRecevoirCarte() {
        Joueur j = new Joueur("Iliess", 1);
        Carte c = new Carte(2, 8);

        j.recevoirCarte(c);

        assertEquals(1, j.getNombreDeCartes());
        assertEquals(c, j.getMain().get(0));
    }

    @Test
    public void testJouerCarte() {
        Joueur j = new Joueur("Iliess", 1);
        Carte c = new Carte(4, 9);
        j.recevoirCarte(c);

        Carte jouee = j.jouerCarte(0);

        assertEquals(c, jouee);      // La même carte
        assertEquals(0, j.getNombreDeCartes()); // main vide
    }
}
