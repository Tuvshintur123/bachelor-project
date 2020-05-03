package com.example.myhealth.model;


public class Serving {

    private Long servingId;
    private String servingDescription;
    private String servingUrl;
    private double metricServingAmount;
    private String metricServingUnit;
    private double numberOfUnits;
    private String measurementDescription;
    private double calories;
    private double carbohydrate;
    private double protein;
    private double fat;
    private double saturatedFat;
    private double polyunsaturatedFat;
    private double monounsaturatedFat;
    private double transFat;
    private double cholesterol;
    private double sodium;
    private double potassium;
    private double fiber;
    private double sugar;
    private double vitaminA;
    private double vitaminC;
    private double calcium;
    private double iron;

    public Serving(Long servingId, String servingDescription, double numberOfUnits, String measurementDescription, double calories, double carbohydrate, double protein, double fat) {
        this.servingId = servingId;
        this.servingDescription = servingDescription;
        this.numberOfUnits = numberOfUnits;
        this.measurementDescription = measurementDescription;
        this.calories = calories;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }


    public Long getServingId() {
        return servingId;
    }

    public void setServingId(Long servingId) {
        this.servingId = servingId;
    }

    public String getServingDescription() {
        return servingDescription;
    }

    public void setServingDescription(String servingDescription) {
        this.servingDescription = servingDescription;
    }

    public String getServingUrl() {
        return servingUrl;
    }

    public void setServingUrl(String servingUrl) {
        this.servingUrl = servingUrl;
    }

    public double getMetricServingAmount() {
        return metricServingAmount;
    }

    public void setMetricServingAmount(double metricServingAmount) {
        this.metricServingAmount = metricServingAmount;
    }

    public String getMetricServingUnit() {
        return metricServingUnit;
    }

    public void setMetricServingUnit(String metricServingUnit) {
        this.metricServingUnit = metricServingUnit;
    }

    public double getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(double numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public String getMeasurementDescription() {
        return measurementDescription;
    }

    public void setMeasurementDescription(String measurementDescription) {
        this.measurementDescription = measurementDescription;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public double getPolyunsaturatedFat() {
        return polyunsaturatedFat;
    }

    public void setPolyunsaturatedFat(double polyunsaturatedFat) {
        this.polyunsaturatedFat = polyunsaturatedFat;
    }

    public double getMonounsaturatedFat() {
        return monounsaturatedFat;
    }

    public void setMonounsaturatedFat(double monounsaturatedFat) {
        this.monounsaturatedFat = monounsaturatedFat;
    }

    public double getTransFat() {
        return transFat;
    }

    public void setTransFat(double transFat) {
        this.transFat = transFat;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(double vitaminA) {
        this.vitaminA = vitaminA;
    }

    public double getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(double vitaminC) {
        this.vitaminC = vitaminC;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public double getIron() {
        return iron;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }
}
