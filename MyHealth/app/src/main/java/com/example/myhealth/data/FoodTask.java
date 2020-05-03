package com.example.myhealth.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myhealth.model.Food;
import com.example.myhealth.model.FoodContainer;
import com.example.myhealth.model.Serving;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodTask extends AsyncTask {
    private String url;
    private OnPostExecuteListener mListener;
    private Food food;

    public FoodTask(String url) {
        this.url = url;
    }

    public interface OnPostExecuteListener {
        void onPostExecute(Food food);
    }

    public void setOnPostExecuteListener(OnPostExecuteListener listener) {
        this.mListener = listener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        HttpHandler handler = new HttpHandler();
        String jsonStr = handler.makeServiceCall(url);
        if (jsonStr != null) {
            try {
                List<Serving> servings = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject jsonFoodObject = jsonObject.getJSONObject("food");
                Long foodId = jsonFoodObject.getLong("food_id");
                String foodName = jsonFoodObject.getString("food_name");
                String foodType = jsonFoodObject.getString("food_type");
                JSONObject jsonServingObject = jsonFoodObject.getJSONObject("servings");
                JSONArray jsonServingArray = jsonServingObject.getJSONArray("serving");
                for (int i = 0; i < jsonServingArray.length(); i++) {
                    JSONObject jsonServing = jsonServingArray.getJSONObject(0);
                    servings.add(new Serving(jsonServing.getLong("serving_id"), jsonServing.getString("serving_description"), jsonServing.getDouble("number_of_units"), jsonServing.getString("measurement_description"), jsonServing.getDouble("calories"), jsonServing.getDouble("carbohydrate"), jsonServing.getDouble("protein"), jsonServing.getDouble("fat")));
                }
                if (!jsonFoodObject.has("brand_name"))
                    food = new Food(foodId, foodName, foodType, "", servings);
                else
                    food = new Food(foodId, foodName, foodType, "", jsonFoodObject.getString("brand_name"), servings);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
//        Log.i("fatsecret", "values: " + food.getName());
//        if (foodContainer.getFoods().size() == foodContainer.getFoodIds().size())
            mListener.onPostExecute(food);

    }
}
