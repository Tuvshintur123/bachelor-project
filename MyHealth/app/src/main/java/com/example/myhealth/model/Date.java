package com.example.myhealth.model;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Date {
    private Calendar date;
    private double bmi;
    private double bodyFat;
    private Weight weight;
    private Calorie calorie;
    private Water water;
    private MealPlan mealPlan;

    public Date(Calendar date, double bmi, double bodyFat, Weight weight, Calorie calorie, Water water, MealPlan mealPlan) {
        this.date = date;
        this.bmi = bmi;
        this.bodyFat = bodyFat;
        this.weight = weight;
        this.calorie = calorie;
        this.water = water;
        this.mealPlan = mealPlan;
    }

    public Date(Calendar date, Weight weight, Calorie calorie, Water water, MealPlan mealPlan) {
        this.date = date;
        this.weight = weight;
        this.calorie = calorie;
        this.water = water;
        this.mealPlan = mealPlan;
    }

    public void calcBmi(Profile profile) {
        DecimalFormat df2 = new DecimalFormat("#.#");
        double Doubleheight = ((double)profile.getHeight() / 100) * ((double)profile.getHeight() / 100);
        bmi = weight.getAmount() / Doubleheight;
        bmi = Double.parseDouble(df2.format(bmi));
    }

    public void calcBodyFat(Profile profile) {
        int age = profile.getStartDate().get(Calendar.YEAR) - profile.getDateOfBirth().get(Calendar.YEAR);
        if (profile.getGender().equals("Эрэгтэй")){
            if (18 > age)
                bodyFat = 1.51 * bmi - 0.7 * age - 2.2;
            else
                bodyFat = 1.2 * bmi + 0.23 * age - 16.2;
        }
        else {
            if (18 > age)
                bodyFat = 1.51 * bmi - 0.7 * age + 1.4;
            else
                bodyFat = 1.2 * bmi + 0.23 * age - 5.4;
        }
        DecimalFormat df2 = new DecimalFormat("#.#");
        bodyFat = Double.parseDouble(df2.format(bodyFat));
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(double bodyFat) {
        this.bodyFat = bodyFat;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public Calorie getCalorie() {
        return calorie;
    }

    public void setCalorie(Calorie calorie) {
        this.calorie = calorie;
    }

    public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }
}
