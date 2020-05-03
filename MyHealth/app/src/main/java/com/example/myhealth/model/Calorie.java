package com.example.myhealth.model;

public class Calorie {
    private double consumed;
    private double remaining;

    public Calorie(double goal) {
        this.consumed = 0;
        this.remaining = goal;
    }

    public Calorie(double consumed, double remaining) {
        this.consumed = consumed;
        this.remaining = remaining;
    }

    public double getConsumed() {
        return consumed;
    }

    public void setConsumed(double consumed) {
        this.consumed = consumed;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public void consume(double calorie) {
        consumed += calorie;
    }
}
