package com.example.myhealth.model;

import com.example.myhealth.data.FoodTask;
import com.example.myhealth.data.FoodsTask;

public class FoodService {

    private static final String KEY = "65e8b024c3f1432381d1f0a6560bcec1";
    private static final String SECRET = "8ee216f7d9064696af2476153c555e83";
    private FoodsTask foodsTask;
    private FoodTask foodTask;

    public FoodsTask getFoods(String query, int pageNumber) {
        RequestBuilder builder = new RequestBuilder(KEY, SECRET);
        String apiUrl;
        {
            try {
                apiUrl = builder.buildFoodsSearchUrl(query, pageNumber);
                foodsTask = new FoodsTask(apiUrl);
                foodsTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return foodsTask;
    }

    public FoodTask getFood(Long id) {
        RequestBuilder builder = new RequestBuilder(KEY, SECRET);
        String apiUrl;
        {
            try {
                apiUrl = builder.buildFoodGetUrl(id);
                foodTask = new FoodTask(apiUrl);
                foodTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return foodTask;
    }
}
