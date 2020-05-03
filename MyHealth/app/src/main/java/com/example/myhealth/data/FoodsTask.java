package com.example.myhealth.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myhealth.model.CompactFood;
import com.example.myhealth.model.FoodContainer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FoodsTask extends AsyncTask {
    private String url;
    private FoodContainer foodContainer;
    private List<CompactFood> foods;
    private OnUpdateFoodsListener mListener;

    public FoodsTask(String url) {
        this.url = url;
    }

    public interface OnUpdateFoodsListener {
        void onUpdateFoods();
    }

    public void setOnUpdateFoodsListener(OnUpdateFoodsListener listener) {
        this.mListener = listener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        foodContainer = FoodContainer.getInstance();
        foods = foodContainer.getFoods();
        foods.clear();
        HttpHandler handler = new HttpHandler();
        String jsonStr = handler.makeServiceCall(url);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject jsonFoodObject = jsonObject.getJSONObject("foods");
                JSONArray jsonFoodArray = jsonFoodObject.getJSONArray("food");

                for (int i = 0; i < jsonFoodArray.length(); i++) {
                    JSONObject jsonFoodArrayItem = jsonFoodArray.getJSONObject(i);
                    if (!jsonFoodArrayItem.has("brand_name"))
                        foods.add(new CompactFood(jsonFoodArrayItem.getLong("food_id"), jsonFoodArrayItem.getString("food_name"), jsonFoodArrayItem.getString("food_type"), jsonFoodArrayItem.getString("food_description")));
                    else
                        foods.add(new CompactFood(jsonFoodArrayItem.getLong("food_id"), jsonFoodArrayItem.getString("food_name"), jsonFoodArrayItem.getString("food_type"), jsonFoodArrayItem.getString("food_description"), jsonFoodArrayItem.getString("brand_name")));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

//    @Override
//    protected void onProgressUpdate(Object[] values) {
//        super.onProgressUpdate(values);
//        CompactFood food = (CompactFood) values[0];
//        if (food != null) {
//            foods.add(food);
//            mListener.onUpdateFoods(food);
//            Log.i("fatsecret", "brand name: " + food.getBrandName() + " " + food.getName() + " " + food.getDescription());
//        }
//    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        mListener.onUpdateFoods();
        Log.i("fatsecret", "total: " + foods.size());

    }


}
