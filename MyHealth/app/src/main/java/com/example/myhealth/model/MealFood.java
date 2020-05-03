package com.example.myhealth.model;


public class MealFood extends CompactFood{

    private Serving serving;
    private double servingAmount;
    private Long servingId;

    public MealFood(Long id, String name, String type, String description, double servingAmount, long servingId) {
        super(id, name, type, description);
        this.servingAmount = servingAmount;
        this.servingId = servingId;
    }

    public MealFood(Long id, String name, String type, String description, String brandName, double servingAmount, long servingId) {
        super(id, name, type, description, brandName);
        this.servingAmount = servingAmount;
        this.servingId = servingId;
    }

    public MealFood(Long id, String name, String type, String description, String brandName, double servingAmount, Serving serving) {
        super(id, name, type, description, brandName);
        this.serving = serving;
    }

    public Serving getServing() {
        return serving;
    }

    public void setServing(Serving serving) {
        this.serving = serving;
    }

    public double getServingAmount() {
        return servingAmount;
    }

    public void setServingAmount(double servingAmount) {
        this.servingAmount = servingAmount;
    }

    public Long getServingId() {
        return servingId;
    }

    public void setServingId(Long servingId) {
        this.servingId = servingId;
    }
}
