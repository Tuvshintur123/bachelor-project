package com.example.myhealth.ui.water;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.example.myhealth.R;
import com.example.myhealth.model.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class WaterFragment extends Fragment {

    private LinearLayout mLinearLayout;
    private Profile profile;
    private TextView mTxtDrunkCups, mTxtTotalCups;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private SimpleDateFormat sdf;

    public WaterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_water, container, false);
        mLinearLayout = root.findViewById(R.id.linearLayout);
        mTxtDrunkCups = root.findViewById(R.id.txt_drunk_cups);
        mTxtTotalCups = root.findViewById(R.id.txt_total_cups);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        myRef = database.getReference("profiles").child(currentUser.getUid()).child("history").child(sdf.format(calendar.getTime())).child("water").child("drunk");
        profile = Profile.getInstance();
        int f = profile.calcNumOfCups();
        int cups = (int) (profile.getHistory().getDate(Calendar.getInstance()).getWater().getDrunk() / profile.getWaterCupSize());
        mTxtTotalCups.setText(String.valueOf(f));
        mTxtDrunkCups.setText(String.valueOf(cups));

        for (int i = 0, j = 0; i < f; ) {
            LinearLayout layout = new LinearLayout(getContext());
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);
            Space space = new Space(getContext());
            space.setLayoutParams(new LinearLayout.LayoutParams(1, 80));
            mLinearLayout.addView(space);
            mLinearLayout.addView(layout);

            for (; layout.getChildCount() < 7 & i < f; i++) {
                final ImageView imageView = new ImageView(getContext());
                if (j < cups) {
                    imageView.setBackgroundResource(R.drawable.empty_glass_of_water);
                    imageView.setTag(R.drawable.empty_glass_of_water);
                    j++;
                } else {
                    imageView.setBackgroundResource(R.drawable.glass_of_water);
                    imageView.setTag(R.drawable.glass_of_water);
                }
                imageView.setLayoutParams(new ViewGroup.LayoutParams(290, 290));
                imageView.setOnClickListener(v -> {
                    if ((Integer) imageView.getTag() == R.drawable.glass_of_water) {
                        imageView.setBackgroundResource(R.drawable.empty_glass_of_water);
                        imageView.setTag(R.drawable.empty_glass_of_water);
                        profile.getHistory().getDate(Calendar.getInstance()).getWater().drink(profile.getWaterCupSize());
                        int drunkCups = (int) (profile.getHistory().getDate(Calendar.getInstance()).getWater().getDrunk() / profile.getWaterCupSize());
                        mTxtDrunkCups.setText(String.valueOf(drunkCups));
                    } else {
                        imageView.setBackgroundResource(R.drawable.glass_of_water);
                        imageView.setTag(R.drawable.glass_of_water);
                        profile.getHistory().getDate(Calendar.getInstance()).getWater().restore(profile.getWaterCupSize());
                        int drunkCups = (int) (profile.getHistory().getDate(Calendar.getInstance()).getWater().getDrunk() / profile.getWaterCupSize());
                        mTxtDrunkCups.setText(String.valueOf(drunkCups));
                    }
                    myRef.setValue(profile.getHistory().getDate(Calendar.getInstance()).getWater().getDrunk());
                });
                layout.addView(imageView);
                if (layout.getChildCount() < 6) {
                    Space space1 = new Space(getContext());
                    space1.setLayoutParams(new LinearLayout.LayoutParams(50, 1));
                    layout.addView(space1);
                }
            }

        }


        return root;
    }

}
