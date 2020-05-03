package com.example.myhealth.model;

public class MealTemp {
    public Long foodId;
    public String description;
    public double servingAmount;
    public Long servingId;
    public String type;

    public MealTemp(Long foodId, String description, double servingAmount, Long servingId, String type) {
        this.foodId = foodId;
        this.description = description;
        this.servingAmount = servingAmount;
        this.servingId = servingId;
        this.type = type;
    }
}
