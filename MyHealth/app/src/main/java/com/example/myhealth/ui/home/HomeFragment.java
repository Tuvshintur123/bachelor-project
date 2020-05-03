package com.example.myhealth.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myhealth.EntryActivity;
import com.example.myhealth.R;
import com.example.myhealth.model.Calorie;
import com.example.myhealth.model.Date;
import com.example.myhealth.model.History;
import com.example.myhealth.model.MealFood;
import com.example.myhealth.model.MealPlan;
import com.example.myhealth.model.Profile;
import com.example.myhealth.model.Water;
import com.example.myhealth.model.Weight;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.timqi.sectorprogressview.ColorfulRingProgressView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import im.dacer.androidcharts.LineView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FirebaseAuth mAuth;
    private LineView mLineView;
    private TextView mTxtCurrentWeight;
    private TextView mTxtCurrentBmi;
    private TextView mTxtStartWeight;
    private TextView mTxtStartBmi;
    private TextView mTxtStartDate;
    private TextView mTxtTargetWeight;
    private TextView mTxtTargetBmi;
    private TextView mTxtTargetDate;
    private TextView mTxtBodyFat;
    private TextView mTxtWeightState;
    private TextView mTxtLost;
    private TextView mTxtRemaining;
    private TextView mTxtBmiState;
    private TextView mTxtTime, mTxtProgress;
    private ProgressBar mProgressBarProgress;
    private ProgressBar mProgressBarTime;
    private Profile profile;
    private SimpleDateFormat sdf;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ColorfulRingProgressView mRingProgressView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mTxtCurrentWeight = root.findViewById(R.id.txt_current_weight);
        mAuth = FirebaseAuth.getInstance();
        mRingProgressView = root.findViewById(R.id.crpv);
        mTxtCurrentBmi = root.findViewById(R.id.txt_current_bmi);
        mTxtStartWeight = root.findViewById(R.id.txt_start_weight);
        mTxtStartBmi = root.findViewById(R.id.txt_start_bmi);
        mTxtStartDate = root.findViewById(R.id.txt_start_date);
        mTxtTargetWeight = root.findViewById(R.id.txt_target_weight);
        mTxtTargetBmi = root.findViewById(R.id.txt_target_bmi);
        mTxtTargetDate = root.findViewById(R.id.txt_target_date);
        mTxtBodyFat = root.findViewById(R.id.txt_body_fat);
        mTxtWeightState = root.findViewById(R.id.txt_weight_state);
        mTxtLost = root.findViewById(R.id.txt_lost);
        mTxtRemaining = root.findViewById(R.id.txt_remaining);
        mTxtBmiState = root.findViewById(R.id.txt_bmi_state);
        mProgressBarProgress = root.findViewById(R.id.progressBar_progress);
        mProgressBarTime = root.findViewById(R.id.progressBar_time);
        mTxtProgress = root.findViewById(R.id.txt_progress);
        mTxtTime = root.findViewById(R.id.txt_time);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        profile = Profile.getInstance();
//        homeViewModel.getProfileMutableLiveData().observe(getViewLifecycleOwner(), s -> {
//            if (s != null)
//                setValues();
//        });
//        homeViewModel.getData();
        getData();


        mLineView = root.findViewById(R.id.line_view);
        mLineView.setDrawDotLine(false); //optional
        mLineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        mLineView.setColorArray(new int[]{Color.parseColor("#0097A7")});


        return root;
    }

    public void setValues() {
        mTxtStartWeight.setText(String.valueOf(profile.getStartWeight()));
        mTxtStartBmi.setText(String.valueOf(profile.calcStartBmi()));
        mTxtStartDate.setText(sdf.format(profile.getStartDate().getTime()));
        mTxtCurrentWeight.setText(String.valueOf(profile.getCurrentWeight()));
        mTxtTargetWeight.setText(String.valueOf(profile.getTargetWeight()));
        mTxtTargetBmi.setText(String.valueOf(profile.calcTargetBmi()));
        mTxtTargetDate.setText(sdf.format(profile.getTargetDate().getTime()));
        DecimalFormat df2 = new DecimalFormat("#.#");
        if (profile.getGoal().equals("Жин хасах")) {
            double totalDiff = profile.getStartWeight() - profile.getTargetWeight();
            double lostWeight = profile.getStartWeight() - profile.getCurrentWeight();
            mTxtProgress.setText(df2.format((lostWeight * 100) / totalDiff));
            if (lostWeight > 0) {
                mRingProgressView.setPercent((float) ((lostWeight * 100) / totalDiff));
                mProgressBarProgress.setProgress((int) ((lostWeight * 100) / totalDiff));
            } else {
                mProgressBarProgress.setProgress(0);
                mRingProgressView.setPercent(0);
            }
        } else if (profile.getGoal().equals("Жин нэмэх")) {
            double totalDiff = profile.getTargetWeight() - profile.getStartWeight();
            double gainWeight = profile.getCurrentWeight() - profile.getStartWeight();
            mTxtProgress.setText(df2.format((gainWeight * 100) / totalDiff));
            if (gainWeight > 0) {
                mProgressBarProgress.setProgress((int) ((gainWeight * 100) / totalDiff));
                mRingProgressView.setPercent((float) ((gainWeight * 100) / totalDiff));
            } else {
                mProgressBarProgress.setProgress(0);
                mRingProgressView.setPercent(0);
            }
        }

        int totalDays = profile.getTargetDate().get(Calendar.DAY_OF_YEAR) - profile.getStartDate().get(Calendar.DAY_OF_YEAR);
        int goneDays = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - profile.getStartDate().get(Calendar.DAY_OF_YEAR);
        double timePercent = ((double) goneDays * 100) / totalDays;

        mTxtTime.setText(df2.format(timePercent));
        mProgressBarTime.setProgress((int) timePercent);

//        profile.calcBodyFat();
        mTxtBodyFat.setText(String.valueOf(profile.getHistory().getDate(Calendar.getInstance()).getBodyFat()));
//
        mTxtCurrentBmi.setText(String.valueOf(profile.getHistory().getDate(Calendar.getInstance()).getBmi()));
        if (profile.getCurrentWeight() - profile.getStartWeight() > 0) {
            mTxtWeightState.setText("Нэмсэн");
            mTxtLost.setText(df2.format(profile.getCurrentWeight() - profile.getStartWeight()));
        } else {
            mTxtWeightState.setText("Хассан");
            mTxtLost.setText(df2.format(profile.getStartWeight() - profile.getCurrentWeight()));
        }
        mTxtRemaining.setText(df2.format(Math.abs(profile.getTargetWeight() - profile.getCurrentWeight())));

        mTxtBmiState.setText(profile.getBmiState(Calendar.getInstance()));


        ArrayList<String> strList = new ArrayList<>();
        ArrayList<ArrayList<Float>> floatDataLists = new ArrayList<>();
        ArrayList<Float> arr = new ArrayList<>();
        Collections.reverse(profile.getHistory().getDates());
        for (Date date : profile.getHistory().getDates()) {
            strList.add((date.getDate().get(Calendar.MONTH) + 1) + "/" + date.getDate().get(Calendar.DAY_OF_MONTH));
            arr.add((float) date.getWeight().getAmount());
        }
        floatDataLists.add(arr);
        mLineView.setBottomTextList(strList);
        mLineView.setFloatDataList(floatDataLists);
    }

    public void getData() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("profiles").child(currentUser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateOfBirth = dataSnapshot.child("dateOfBirth").getValue(String.class);
                String gender = dataSnapshot.child("gender").getValue(String.class);
                int height = dataSnapshot.child("height").getValue(Integer.class);
                double startWeight = dataSnapshot.child("startWeight").getValue(Double.class);
                double targetWeight = dataSnapshot.child("targetWeight").getValue(Double.class);
                String startDate = dataSnapshot.child("startDate").getValue(String.class);
                String targetDate = dataSnapshot.child("targetDate").getValue(String.class);
                String goal = dataSnapshot.child("goal").getValue(String.class);
                String activityLevel = dataSnapshot.child("activityLevel").getValue(String.class);
                double currentWeight = dataSnapshot.child("currentWeight").getValue(Double.class);
                double calorieGoal = dataSnapshot.child("calorieGoal").getValue(Double.class);
                double waterGoal = dataSnapshot.child("waterGoal").getValue(Double.class);
                double waterCupSize = dataSnapshot.child("waterCupSize").getValue(Double.class);
                String today = sdf.format(Calendar.getInstance().getTime());
                Calendar calDateOfBirth = Calendar.getInstance();
                Calendar calStartDate = Calendar.getInstance();
                Calendar calTargetDate = Calendar.getInstance();
                try {
                    calDateOfBirth.setTime(sdf.parse(dateOfBirth));
                    calStartDate.setTime(sdf.parse(startDate));
                    calTargetDate.setTime(sdf.parse(targetDate));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Profile.setProfile(new Profile(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getEmail(), "", calDateOfBirth, gender, height, startWeight, targetWeight, calStartDate, calTargetDate, goal, activityLevel, currentWeight, calorieGoal, waterGoal, waterCupSize, new History()));
                profile = Profile.getInstance();

                if (dataSnapshot.child("history").child(today).exists()) {
                    try {
                        getHistory(dataSnapshot, today);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    profile.getHistory().addDate(new Date(Calendar.getInstance(), new Weight(currentWeight), new Calorie(0, calorieGoal), new Water(0), new MealPlan()));
                    profile.calcBmi(Calendar.getInstance());
                    profile.calcBodyFat();
                    Map<String, Object> newDate = new HashMap<>();
                    newDate.put("bmi", profile.getHistory().getDate(Calendar.getInstance()).getBmi());
                    newDate.put("bodyFat", profile.getHistory().getDate(Calendar.getInstance()).getBodyFat());
                    newDate.put("weight/amount", profile.getHistory().getDate(Calendar.getInstance()).getWeight().getAmount());
                    newDate.put("calorie/consumed", profile.getHistory().getDate(Calendar.getInstance()).getCalorie().getConsumed());
                    newDate.put("calorie/remaining", profile.getHistory().getDate(Calendar.getInstance()).getCalorie().getRemaining());
                    newDate.put("water/drunk", profile.getHistory().getDate(Calendar.getInstance()).getWater().getDrunk());
                    myRef.child("history").child(sdf.format(Calendar.getInstance().getTime())).updateChildren(newDate);

                    Log.d("fatsecret", "Value is: algoo shineer hiichle!");
                }
                long count = dataSnapshot.child("history").getChildrenCount();
                if (count > 10)
                    count = 10;

                Calendar calendar = Calendar.getInstance();
                int i = 1;
                while (i < count) {
                    calendar.add(Calendar.DATE, -1);
                    if (dataSnapshot.child("history").child(sdf.format(calendar.getTime())).exists()) {
                        try {
                            getHistory(dataSnapshot, sdf.format(calendar.getTime()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        i++;
                    }
                }

                setValues();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("fatsecret", "Failed to read value.", error.toException());
            }

        });
    }

    private void getHistory(DataSnapshot dataSnapshot, String today) throws ParseException {
        double bmi = dataSnapshot.child("history").child(today).child("bmi").getValue(Double.class);
        double bodyFat = dataSnapshot.child("history").child(today).child("bodyFat").getValue(Double.class);
        double weight = dataSnapshot.child("history").child(today).child("weight").child("amount").getValue(Double.class);
        double consumed = dataSnapshot.child("history").child(today).child("calorie").child("consumed").getValue(Double.class);
        double remaining = dataSnapshot.child("history").child(today).child("calorie").child("remaining").getValue(Double.class);
        double drunk = dataSnapshot.child("history").child(today).child("water").child("drunk").getValue(Double.class);

        MealPlan mealPlan = new MealPlan();
        if (dataSnapshot.child("history").child(today).child("mealPlan").child("Өглөөний цай").exists())
            addMealFood(mealPlan, dataSnapshot.child("history").child(today).child("mealPlan").child("Өглөөний цай").getChildren(), "Өглөөний цай");
        if (dataSnapshot.child("history").child(today).child("mealPlan").child("Үдийн хоол").exists())
            addMealFood(mealPlan, dataSnapshot.child("history").child(today).child("mealPlan").child("Үдийн хоол").getChildren(), "Үдийн хоол");
        if (dataSnapshot.child("history").child(today).child("mealPlan").child("Оройн хоол").exists())
            addMealFood(mealPlan, dataSnapshot.child("history").child(today).child("mealPlan").child("Оройн хоол").getChildren(), "Оройн хоол");
        if (dataSnapshot.child("history").child(today).child("mealPlan").child("Хөнгөн зууш").exists())
            addMealFood(mealPlan, dataSnapshot.child("history").child(today).child("mealPlan").child("Хөнгөн зууш").getChildren(), "Хөнгөн зууш");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(today));
        profile.getHistory().addDate(new Date(calendar, bmi, bodyFat, new Weight(weight), new Calorie(consumed, remaining), new Water(drunk), mealPlan));
    }

    private void addMealFood(MealPlan mealPlan, Iterable<DataSnapshot> children, String mealName) {
        for (DataSnapshot mealSnapShot : children)
            if (mealSnapShot.child("brandName").exists())
                mealPlan.getMealByName(mealName).addFood(new MealFood(mealSnapShot.child("foodId").getValue(Long.class), mealSnapShot.getKey(), mealSnapShot.child("type").getValue(String.class), mealSnapShot.child("description").getValue(String.class), mealSnapShot.child("brandName").getValue(String.class), mealSnapShot.child("servingAmount").getValue(Double.class), mealSnapShot.child("servingId").getValue(Long.class)));
            else
                mealPlan.getMealByName(mealName).addFood(new MealFood(mealSnapShot.child("foodId").getValue(Long.class), mealSnapShot.getKey(), mealSnapShot.child("type").getValue(String.class), mealSnapShot.child("description").getValue(String.class), mealSnapShot.child("servingAmount").getValue(Double.class), mealSnapShot.child("servingId").getValue(Long.class)));
    }

}