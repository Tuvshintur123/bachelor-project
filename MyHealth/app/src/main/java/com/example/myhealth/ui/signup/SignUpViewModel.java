package com.example.myhealth.ui.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.Date;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<String> goal = new MutableLiveData<>();
    private MutableLiveData<String> gender = new MutableLiveData<>();
    private MutableLiveData<String> activityLevel = new MutableLiveData<>();
    private MutableLiveData<Double> currentWeight = new MutableLiveData<>();
    private MutableLiveData<Integer> height = new MutableLiveData<>();
    private MutableLiveData<Double> targetWeight = new MutableLiveData<>();
    private MutableLiveData<Calendar> birthday = new MutableLiveData<>();


    public MutableLiveData<String> getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal.setValue(goal);
    }

    public MutableLiveData<String> getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.setValue(gender);
    }

    public MutableLiveData<String> getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel.setValue(activityLevel);
    }

    public MutableLiveData<Double> getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight.setValue(currentWeight);
    }

    public MutableLiveData<Integer> getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height.setValue(height);
    }

    public MutableLiveData<Double> getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(Double targetWeight) {
        this.targetWeight.setValue(targetWeight);
    }

    public MutableLiveData<Calendar> getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday.setValue(birthday);
    }

}
