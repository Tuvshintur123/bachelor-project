package com.example.myhealth.model;

import java.util.ArrayList;
import java.util.List;

public class Food extends CompactFood {

    private List<Serving> servings;

    public Food(Long id, String name, String type, String description, List<Serving> servings) {
        super(id, name, type, description);
        this.servings = servings;
    }

    public Food(Long id, String name, String type, String description, String brandName, List<Serving> servings) {
        super(id, name, type, description, brandName);
        this.servings = servings;
    }

    public List<Serving> getServings() {
        return servings;
    }

    public void setServings(List<Serving> servings) {
        this.servings = servings;
    }
}
