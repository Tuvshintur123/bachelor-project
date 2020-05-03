package com.example.myhealth.ui.recommendation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.myhealth.MainActivity;
import com.example.myhealth.R;
import com.example.myhealth.model.Profile;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationFragment extends Fragment {

    private TableLayout mTblRcmd;
    private RadioGroup mRadioGroup;
    private TextView mTxtEasyDate, mTxtEasyRdi, mTxtNormalDate, mTxtNormalRdi, mTxtHardDate, mTxtHardRdi, mTxtRdi;
    private RadioButton mRdBtnEasy, mRdBtnNormal, mRdBtnHard;
    private Profile profile;
    private Button mBtnNext;
    private ProgressBar mProgressBar;
    private ConstraintLayout mLayoutMaintain;
    private TextView mTxtTitle;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    SimpleDateFormat sdf;

    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recommendation, container, false);
        mTblRcmd = root.findViewById(R.id.tbl_recommend);
        mRadioGroup = root.findViewById(R.id.radioGroup);
        mRdBtnEasy = root.findViewById(R.id.rdBtn_easy);
        mRdBtnNormal = root.findViewById(R.id.rdBtn_normal);
        mRdBtnHard = root.findViewById(R.id.rdBtn_hard);
        mTxtEasyDate = root.findViewById(R.id.txt_easy_date);
        mTxtEasyRdi = root.findViewById(R.id.txt_easy_rdi);
        mTxtNormalDate = root.findViewById(R.id.txt_normal_date);
        mTxtNormalRdi = root.findViewById(R.id.txt_normal_rdi);
        mTxtHardDate = root.findViewById(R.id.txt_hard_date);
        mTxtHardRdi = root.findViewById(R.id.txt_hard_rdi);
        profile = Profile.getInstance();
        mBtnNext = root.findViewById(R.id.btn_next);
        mProgressBar = root.findViewById(R.id.progress_bar);
        mLayoutMaintain = root.findViewById(R.id.layout_maintain);
        mTxtRdi = root.findViewById(R.id.txt_rdi);
        mTxtTitle = root.findViewById(R.id.txt_title);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("profiles");
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (profile.getGoal().equals("Жингээ барих")) {
            mTxtRdi.setText(String.valueOf(profile.calcIdealRdi()));
            mLayoutMaintain.setVisibility(View.VISIBLE);
            mTxtRdi.setVisibility(View.VISIBLE);
            mTxtTitle.setVisibility(View.VISIBLE);
            mTblRcmd.setVisibility(View.INVISIBLE);
            mBtnNext.setEnabled(true);
        } else {
            mLayoutMaintain.setVisibility(View.GONE);
            mTxtRdi.setVisibility(View.GONE);
            mTxtTitle.setVisibility(View.GONE);
            mTblRcmd.setVisibility(View.VISIBLE);
            LinkedHashMap<Double, Calendar> result = profile.calcIdealTargetDate();

            Iterator myVeryOwnIterator = result.keySet().iterator();
            int i = 0;
            while (myVeryOwnIterator.hasNext()) {
                Double rdi = (Double) myVeryOwnIterator.next();
                Calendar date = result.get(rdi);
                String targetDate = sdf.format(date.getTime());

                if (i == 0) {
                    mTxtEasyRdi.setText(rdi.toString());
                    mTxtEasyDate.setText(targetDate);
                } else if (i == 1) {
                    mTxtNormalRdi.setText(rdi.toString());
                    mTxtNormalDate.setText(targetDate);
                } else {
                    mTxtHardRdi.setText(rdi.toString());
                    mTxtHardDate.setText(targetDate);
                }
                i++;
            }
        }


        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            mBtnNext.setEnabled(true);
            int color = Color.parseColor("#FFCDDC39");
            if (checkedId == R.id.rdBtn_easy) {
//                    mRdBtnEasy.setBackgroundColor(color);
//                    mRdBtnNormal.setBackgroundColor(Color.WHITE);
//                    mRdBtnHard.setBackgroundColor(Color.WHITE);
                mTxtEasyDate.setBackgroundColor(color);
                mTxtEasyRdi.setBackgroundColor(color);
                mTxtNormalDate.setBackgroundColor(Color.WHITE);
                mTxtNormalRdi.setBackgroundColor(Color.WHITE);
                mTxtHardDate.setBackgroundColor(Color.WHITE);
                mTxtHardRdi.setBackgroundColor(Color.WHITE);
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(sdf.parse(mTxtEasyDate.getText().toString()));
                    profile.setTargetDate(calendar);
                    profile.setCalorieGoal(Double.parseDouble(mTxtEasyRdi.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (checkedId == R.id.rdBtn_normal) {
//                    mRdBtnEasy.setBackgroundColor(Color.WHITE);
//                    mRdBtnNormal.setBackgroundColor(color);
//                    mRdBtnHard.setBackgroundColor(Color.WHITE);
                mTxtEasyDate.setBackgroundColor(Color.WHITE);
                mTxtEasyRdi.setBackgroundColor(Color.WHITE);
                mTxtNormalDate.setBackgroundColor(color);
                mTxtNormalRdi.setBackgroundColor(color);
                mTxtHardDate.setBackgroundColor(Color.WHITE);
                mTxtHardRdi.setBackgroundColor(Color.WHITE);
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(sdf.parse(mTxtNormalDate.getText().toString()));
                    profile.setTargetDate(calendar);
                    profile.setCalorieGoal(Double.parseDouble(mTxtNormalRdi.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
//                    mRdBtnEasy.setBackgroundColor(Color.WHITE);
//                    mRdBtnNormal.setBackgroundColor(Color.WHITE);
//                    mRdBtnHard.setBackgroundColor(color);
                mTxtEasyDate.setBackgroundColor(Color.WHITE);
                mTxtEasyRdi.setBackgroundColor(Color.WHITE);
                mTxtNormalDate.setBackgroundColor(Color.WHITE);
                mTxtNormalRdi.setBackgroundColor(Color.WHITE);
                mTxtHardDate.setBackgroundColor(color);
                mTxtHardRdi.setBackgroundColor(color);
                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(sdf.parse(mTxtHardDate.getText().toString()));
                    profile.setTargetDate(calendar);
                    profile.setCalorieGoal(Double.parseDouble(mTxtHardRdi.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        mBtnNext.setOnClickListener(v -> {
            uploadProfile();
        });

        return root;
    }

    private void uploadProfile() {
        mProgressBar.setVisibility(View.VISIBLE);

        Map<String, Object> newProfile = new HashMap<>();
        newProfile.put("activityLevel", profile.getActivityLevel());
        newProfile.put("calorieGoal", profile.getCalorieGoal());
        newProfile.put("currentWeight", profile.getCurrentWeight());
        newProfile.put("dateOfBirth", sdf.format(profile.getDateOfBirth().getTime()));
        newProfile.put("gender", profile.getGender());
        newProfile.put("goal", profile.getGoal());
        newProfile.put("height", profile.getHeight());
        newProfile.put("startDate", sdf.format(profile.getStartDate().getTime()));
        newProfile.put("startWeight", profile.getStartWeight());
        newProfile.put("targetDate", sdf.format(profile.getTargetDate().getTime()));
        newProfile.put("targetWeight", profile.getTargetWeight());
        newProfile.put("waterCupSize", profile.getWaterCupSize());
        newProfile.put("waterGoal", profile.getWaterGoal());
        myRef.child(profile.getUserId()).updateChildren(newProfile, (databaseError, databaseReference) -> {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (databaseError != null) {
                System.out.println("Жаахан алдаа гарчлаа." + databaseError.getMessage());
            } else {
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
