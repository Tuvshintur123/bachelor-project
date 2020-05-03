package com.example.myhealth.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<Integer> mText;
    private MutableLiveData<Profile> profileMutableLiveData;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Profile profile;


    public HomeViewModel() {
        mText = new MutableLiveData<>();
        profileMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Profile> getProfileMutableLiveData() {
        return profileMutableLiveData;
    }

    public MutableLiveData<Integer> getText() {
        return mText;
    }

    public void setText(Integer bool) {
        mText.setValue(bool);
        this.profileMutableLiveData.setValue(Profile.getInstance());
    }




}