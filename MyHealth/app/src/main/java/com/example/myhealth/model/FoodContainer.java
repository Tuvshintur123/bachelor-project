package com.example.myhealth.model;

import java.util.ArrayList;
import java.util.List;

public class FoodContainer {

    private static FoodContainer foodContainer = null;

    private List<CompactFood> foods;

    private String max_results;

    private String page_number;

    private String total_results;

    private FoodContainer() {
        this.foods = new ArrayList<>();
    }

    public static FoodContainer getInstance() {
        if (foodContainer == null)
            foodContainer = new FoodContainer();
        return foodContainer;
    }

    public String getMax_results() {
        return max_results;
    }

    public String getPage_number() {
        return page_number;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setFoods(List<CompactFood> foods) {
        this.foods = foods;
    }

    public List<CompactFood> getFoods() {
        return foods;
    }
}
