package com.example.myhealth.model;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String id;
    private String name;
    private List<MealFood> mealFoods;

    public Meal(String name) {
        this.name = name;
        this.mealFoods = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MealFood> getMealFoods() {
        return mealFoods;
    }

    public void setMealFoods(List<MealFood> mealFoods) {
        this.mealFoods = mealFoods;
    }

    public void addFood(MealFood mealFood){
        this.mealFoods.add(mealFood);
    }

    public void saveMeal() {

    }

    public double calcTotalCalories() {
        return mealFoods.stream().mapToDouble(x -> x.getServing().getCalcium()).sum();
    }
}
