package com.schottenTotten.model;

public class CarteTactique extends Carte {

    private String capacite;

    public CarteTactique(String capacite) {
        super(0, 0);
        this.capacite = capacite;
    }

    public String getCapacite() {
        return capacite;
    }

    @Override
    public String description() {
        return "[TACTIQUE : " + capacite + super.description() + "]";
    }
}