package com.example.myhealth.model;

public class Water {
    private double drunk;

    public Water() {
        this.drunk = 0;
    }

    public Water(double drunk) {
        this.drunk = drunk;
    }

    public double getDrunk() {
        return drunk;
    }

    public void setDrunk(double drunk) {
        this.drunk = drunk;
    }

    public void drink(double cupSize) {
        drunk += cupSize;
    }

    public void restore(double cupSize) {
        drunk -= cupSize;
    }
}
