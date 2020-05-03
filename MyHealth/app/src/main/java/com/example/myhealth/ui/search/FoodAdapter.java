package com.example.myhealth.ui.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhealth.R;
import com.example.myhealth.model.CompactFood;
import com.example.myhealth.model.MealFood;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<CompactFood> foods;
    private List<MealFood> selectedFoods;
    private OnFoodClickListener mListener;

    public FoodAdapter(List<CompactFood> foods, List<MealFood> selectedFoods) {
        this.foods = foods;
        this.selectedFoods = selectedFoods;
    }

    public interface OnFoodClickListener {
        void onFoodClick(int position);

        void onAddClick(CompactFood selectedFood);

        void onCheckClick(CompactFood selectedFood);
    }

    public void setOnFoodClickListener(OnFoodClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_food, parent, false);
        FoodViewHolder holder = new FoodViewHolder(v, mListener, selectedFoods, foods);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        CompactFood food = foods.get(position);
        holder.mTxtFoodName.setText(food.getName());
//        holder.mTxtServingAmount.setText(oldFood.getServingAmount());
//        holder.mTxtServingUnit.setText(oldFood.getServingUnit());
        holder.mTxtCalories.setText(food.getDescription());
//        Log.w("fatsecret", "===================================== INSTANCE ==================================== ");
//        selectedFoods.forEach(x -> Log.i("fatsecret", "==============List uusgehed umnu songogdson huns============== " + x.getName() + " " + x.getId()));
        if (!holder.isMatches(food)) {
            holder.mImageViewAdd.setVisibility(View.VISIBLE);
            holder.mImageViewCheck.setVisibility(View.GONE);
        } else {
            holder.mImageViewAdd.setVisibility(View.GONE);
            holder.mImageViewCheck.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTxtFoodName;
        public TextView mTxtServingAmount;
        public TextView mTxtServingUnit;
        public TextView mTxtCalories;
        public ImageView mImageViewAdd;
        public ImageView mImageViewCheck;
        private OnFoodClickListener listener;
        private List<CompactFood> foods;
        private List<MealFood> selectedFoods;

        public FoodViewHolder(@NonNull View itemView, final OnFoodClickListener listener, List<MealFood> selectedFoods, List<CompactFood> foods) {
            super(itemView);
            mTxtFoodName = itemView.findViewById(R.id.txt_food_name);
            mTxtServingAmount = itemView.findViewById(R.id.txt_serving_amount);
            mTxtServingUnit = itemView.findViewById(R.id.txt_serving_unit);
            mTxtCalories = itemView.findViewById(R.id.txt_food_calories);
            mImageViewAdd = itemView.findViewById(R.id.imageView_add);
            mImageViewCheck = itemView.findViewById(R.id.imageView_check);
            this.listener = listener;
            this.foods = foods;
            this.selectedFoods = selectedFoods;


            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onFoodClick(position);
                    }
                }
            });

            mImageViewAdd.setOnClickListener(this);
            mImageViewCheck.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
//                    Log.w("fatsecret", "===================================== CLICK EVENT ==================================== ");
//                    selectedFoods.forEach(x -> Log.i("fatsecret", "==============Click event uusgehed umnu n baisan huns============== " + x.getName() + " " + x.getId()));
                    if (isMatches(foods.get(position))) {
                        mImageViewAdd.setVisibility(View.VISIBLE);
                        mImageViewCheck.setVisibility(View.GONE);
                        listener.onCheckClick(foods.get(position));

                    } else {
                        mImageViewAdd.setVisibility(View.GONE);
                        mImageViewCheck.setVisibility(View.VISIBLE);
                        listener.onAddClick(foods.get(position));
                    }

                }
            }
        }

        public boolean isMatches(CompactFood food) {
//            Log.w("fatsecret", "===================================== ISMATCHES ==================================== ");
            for (MealFood selectedFood : selectedFoods) {
//                Log.i("fatsecret", "===================================== " + food.getId() + " vs " + selectedFood.getId() +" ==================================== ");
                if ((long)food.getId() == (long)selectedFood.getId()) {
//                    Log.e("fatsecret", "\n===================================== " + food.getId() + " == " + selectedFood.getId() +" ==================================== \n");
                    return true;
                }
            }
            return false;
        }
    }


}
