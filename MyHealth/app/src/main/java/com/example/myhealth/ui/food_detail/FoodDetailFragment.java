package com.example.myhealth.ui.food_detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myhealth.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailFragment extends Fragment {

    public FoodDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_food_detail, container, false);
        TextView textView = root.findViewById(R.id.hello);
        textView.setText(String.valueOf(getArguments().getLong("food_id")));
        return root;
    }
}
