package com.example.myhealth.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhealth.R;
import com.example.myhealth.model.MealFood;

import java.util.List;

public class SelectedFoodAdapter extends RecyclerView.Adapter<SelectedFoodAdapter.SelectedFoodViewHolder> {

    private List<MealFood> selectedFoods;
    private SelectedFoodAdapter.OnFoodClickListener mListener;

    public SelectedFoodAdapter(List<MealFood> selectedFoods) {
        this.selectedFoods = selectedFoods;
    }

    public interface OnFoodClickListener {
        void onFoodClick(int position);

        void onClickRemove(int position);
    }

    public void setOnFoodClickListener(SelectedFoodAdapter.OnFoodClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public SelectedFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_food, parent, false);
        SelectedFoodAdapter.SelectedFoodViewHolder holder = new SelectedFoodAdapter.SelectedFoodViewHolder(v, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedFoodViewHolder holder, int position) {
        MealFood food = selectedFoods.get(position);
        holder.mTxtFoodName.setText(food.getName());
//        holder.mTxtServingAmount.setText(oldFood.getServingAmount());
//        holder.mTxtServingUnit.setText(oldFood.getServingUnit());
        holder.mTxtCalories.setText(food.getDescription());
        holder.mImageViewCheck.setVisibility(View.VISIBLE);

    }


    @Override
    public int getItemCount() {
        return selectedFoods.size();
    }

    public static class SelectedFoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTxtFoodName;
        public TextView mTxtServingAmount;
        public TextView mTxtServingUnit;
        public TextView mTxtCalories;
        public ImageView mImageViewAdd;
        public ImageView mImageViewCheck;
        private SelectedFoodAdapter.OnFoodClickListener listener;

        public SelectedFoodViewHolder(@NonNull View itemView, OnFoodClickListener mListener) {
            super(itemView);
            mTxtFoodName = itemView.findViewById(R.id.txt_food_name);
            mTxtServingAmount = itemView.findViewById(R.id.txt_serving_amount);
            mTxtServingUnit = itemView.findViewById(R.id.txt_serving_unit);
            mTxtCalories = itemView.findViewById(R.id.txt_food_calories);
            mImageViewAdd = itemView.findViewById(R.id.imageView_add);
            mImageViewCheck = itemView.findViewById(R.id.imageView_check);
            this.listener = mListener;
            mImageViewCheck.setOnClickListener(this);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onFoodClick(position);
                    }
                }
            });

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mImageViewCheck.setVisibility(View.GONE);
                    mImageViewAdd.setVisibility(View.VISIBLE);
                    listener.onClickRemove(position);
                }
            }
        }
    }
}
