package com.example.myhealth.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myhealth.data.FoodTask;
import com.example.myhealth.data.FoodsTask;
import com.example.myhealth.model.CompactFood;
import com.example.myhealth.model.FoodContainer;
import com.example.myhealth.model.FoodService;
import com.example.myhealth.model.MealFood;
import com.example.myhealth.model.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private static final String KEY = "65e8b024c3f1432381d1f0a6560bcec1";
    private static final String SECRET = "8ee216f7d9064696af2476153c555e83";
    private FoodsTask foodsTask;
    private FoodTask foodTask;
    private final MutableLiveData<List<CompactFood>> filteredFoods;
    private MutableLiveData<List<MealFood>> selectedFoods;
    private FoodService foodService;

    public SearchViewModel() {
        filteredFoods = new MutableLiveData<>();
        selectedFoods = new MutableLiveData<>();
        selectedFoods.setValue(new ArrayList<>());
        foodService = new FoodService();
    }

    public void getFoods(String query, int pageNumber) {
        foodsTask = foodService.getFoods(query, pageNumber);
        foodsTask.setOnUpdateFoodsListener(() -> {
            this.filteredFoods.setValue(FoodContainer.getInstance().getFoods());
        });
    }

//    public void getFood(Long id) {
//        foodTask = foodService.getFood(id);
//        foodTask.setOnPostExecuteListener((food) -> {
//            this.selectedFoods.getValue().add(new MealFood(food.getId(), food.getName(), food.getType(), food.getDescription(), food.getBrandName(), food.getServings().get(0).getNumberOfUnits(), food.getServings().get(0)));
//        });
//
//    }

    public MutableLiveData<List<CompactFood>> getFilteredFoods() {
        return filteredFoods;
    }

    public MutableLiveData<List<MealFood>> getSelectedFoods() {
        return selectedFoods;
    }

    public void setSelectedFoods(List<MealFood> selectedFoods) {
        this.selectedFoods.setValue(selectedFoods);
    }
}
