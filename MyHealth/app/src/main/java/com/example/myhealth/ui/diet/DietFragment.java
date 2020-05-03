package com.example.myhealth.ui.diet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myhealth.R;

public class DietFragment extends Fragment {

    private DietViewModel dietViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dietViewModel =
                new ViewModelProvider(requireActivity()).get(DietViewModel.class);
        View root = inflater.inflate(R.layout.fragment_diet, container, false);
        final TextView textView = root.findViewById(R.id.text_diet);
        dietViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
}