package com.example.myhealth.model;


import androidx.annotation.Keep;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;

public class Profile extends User {

    private static Profile profile = null;
    private Calendar dateOfBirth;
    private String gender;
    private int height;
    private double startWeight;
    private double targetWeight;
    private Calendar startDate;
    private Calendar targetDate;
    //    private String reminder;
    private String goal;
    private String activityLevel;
    //    private double currentBmi;
//    private double currentBodyFat;
//    private double currentRdi;
    private double currentWeight;
    private double calorieGoal;
    private double waterGoal;
    private double waterCupSize;
    public History history;


    public Profile(String userId, String name, String email, String password, String goal, String gender, String activityLevel, double currentWeight, int height, Calendar dateOfBirth, double targetWeight, History history) {
        super(userId, name, email, password);
        this.goal = goal;
        this.gender = gender;
        this.activityLevel = activityLevel;
        this.currentWeight = currentWeight;
        this.startWeight = currentWeight;
        this.height = height;
        this.targetWeight = targetWeight;
        this.dateOfBirth = dateOfBirth;
        this.startDate = Calendar.getInstance();
        this.waterCupSize = 250;
        this.history = history;
    }

    public Profile(String userId, String name, String email, String password, Calendar dateOfBirth, String gender, int height, double startWeight, double targetWeight, Calendar startDate, Calendar targetDate, String goal, String activityLevel, double currentWeight, double calorieGoal, double waterGoal, double waterCupSize, History history) {
        super(userId, name, email, password);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.height = height;
        this.startWeight = startWeight;
        this.targetWeight = targetWeight;
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.goal = goal;
        this.activityLevel = activityLevel;
        this.currentWeight = currentWeight;
        this.calorieGoal = calorieGoal;
        this.waterGoal = waterGoal;
        this.waterCupSize = waterCupSize;
        this.history = history;
    }

    public Profile() {
        super("", "", "", "");
    }

    public static void setProfile(Profile profile) {
        Profile.profile = profile;
    }

    public static Profile getInstance() {
        if (profile == null)
            profile = new Profile();
        return profile;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(double startWeight) {
        this.startWeight = startWeight;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Calendar targetDate) {
        this.targetDate = targetDate;
    }

//    public String getReminder() {
//        return reminder;
//    }
//
//    public void setReminder(String reminder) {
//        this.reminder = reminder;
//    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }

//    public double getCurrentBmi() {
//        return currentBmi;
//    }
//
//    public void setCurrentBmi(double currentBmi) {
//        this.currentBmi = currentBmi;
//    }
//
//    public double getCurrentBodyFat() {
//        return currentBodyFat;
//    }
//
//    public void setCurrentBodyFat(double currentBodyFat) {
//        this.currentBodyFat = currentBodyFat;
//    }
//
//    public double getCurrentRdi() {
//        return currentRdi;
//    }
//
//    public void setCurrentRdi(double currentRdi) {
//        this.currentRdi = currentRdi;
//    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
//        DecimalFormat df2 = new DecimalFormat("#.#");
//        this.currentWeight = Double.parseDouble(df2.format(currentWeight));
        this.currentWeight = currentWeight;
    }

    public double getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(double calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public double getWaterGoal() {
        return waterGoal;
    }

    public void setWaterGoal(double waterGoal) {
        this.waterGoal = waterGoal;
    }

    public double getWaterCupSize() {
        return waterCupSize;
    }

    public void setWaterCupSize(double waterCupSize) {
        this.waterCupSize = waterCupSize;
    }

    public int calcNumOfCups() {
        return (int) (waterGoal / waterCupSize);
    }

    @Keep
    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public double calcIdealRdi() {
        if (gender.equals("Эрэгтэй")) {
            return (10 * currentWeight + 6.25 * height - 5 * (startDate.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR)) + 5) * calcActivityLevel();
        } else {
            return (10 * currentWeight + 6.25 * height - 5 * (startDate.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR)) - 161) * calcActivityLevel();
        }
    }

    public double calcActivityLevel() {
        switch (activityLevel) {
            case "Маш бага":
                return 1.2;
            case "Бага":
                return 1.375;
            case "Идэвхитэй":
                return 1.55;
            default:
                return 1.725;
        }
    }

    public double calcIdealWeight() {
        return 0;
    }

    public LinkedHashMap<Double, Calendar> calcIdealTargetDate() {
        LinkedHashMap<Double, Calendar> result = new LinkedHashMap<>();
        double rdi = calcIdealRdi();
        if (goal.equals("Жин нэмэх")) {
            double diff = targetWeight - currentWeight;
            int days = (int) ((diff / 0.25) * 7);
            Calendar targetDate1 = Calendar.getInstance();
            targetDate1.add(Calendar.DAY_OF_MONTH, days);
            result.put(rdi + 250, targetDate1);

            days = (int) ((diff / 0.5) * 7);
            Calendar targetDate2 = Calendar.getInstance();
            targetDate2.add(Calendar.DAY_OF_MONTH, days);
            result.put(rdi + 500, targetDate2);

            days = (int) (diff * 7);
            Calendar targetDate3 = Calendar.getInstance();
            targetDate3.add(Calendar.DAY_OF_MONTH, days);
            result.put(rdi + 1000, targetDate3);
        } else if (goal.equals("Жин хасах")) {
            double diff = currentWeight - targetWeight;
            int minRdi;
            if (gender.equals("Эрэгтэй")) {
                minRdi = 1500;
            } else {
                minRdi = 1200;
            }
            if (rdi - 250 > minRdi) {
                int days = (int) ((diff / 0.25) * 7);
                Calendar targetDate1 = Calendar.getInstance();
                targetDate1.add(Calendar.DAY_OF_MONTH, days);
                result.put(rdi - 250, targetDate1);
            }
            if (rdi - 500 > minRdi) {
                int days = (int) ((diff / 0.5) * 7);
                Calendar targetDate1 = Calendar.getInstance();
                targetDate1.add(Calendar.DAY_OF_MONTH, days);
                result.put(rdi - 500, targetDate1);

            }
            if (rdi - 1000 > minRdi) {
                int days = (int) (diff * 7);
                Calendar targetDate1 = Calendar.getInstance();
                targetDate1.add(Calendar.DAY_OF_MONTH, days);
                result.put(rdi - 1000, targetDate1);
            } else {
                double maxRdiDif = rdi - minRdi;
                int days = (int) ((diff / (maxRdiDif / 1000)) * 7);
                Calendar targetDate1 = Calendar.getInstance();
                targetDate1.add(Calendar.DAY_OF_MONTH, days);
                result.put((double) minRdi, targetDate1);
            }
        }
//        Iterator myVeryOwnIterator = result.keySet().iterator();
//
//        while(myVeryOwnIterator.hasNext()) {
//            Double rd = (Double)myVeryOwnIterator.next();
//            Calendar date = (Calendar) result.get(rd);
//            SimpleDateFormat format = new SimpleDateFormat("d/MMMM/yyyy");
//            String targetDate = format.format(date.getTime());
//            Log.i("result", "" + rd + " " + targetDate);
//        }
        return result;
    }

    public void calcBmi(Calendar calendar) {
        history.getDate(calendar).calcBmi(this);
    }

    public double calcStartBmi() {
        DecimalFormat df2 = new DecimalFormat("#.#");
        return Double.parseDouble(df2.format(startWeight / ((double) (height * height) / 10000)));
    }

    public double calcTargetBmi() {
        DecimalFormat df2 = new DecimalFormat("#.#");
        return Double.parseDouble(df2.format(targetWeight / ((double) (height * height) / 10000)));
    }

    public void calcBodyFat() {
        history.getDate(Calendar.getInstance()).calcBodyFat(this);
    }

    public void calcWaterGoal() {
        waterGoal = (int)currentWeight * 1.1 * 29.6;
    }

    public String getBmiState(Calendar date) {
        double bmi = history.getDate(date).getBmi();
        if (Double.compare(18.5, bmi) > 0 | Double.compare(18.5, bmi) == 0)
            return "Туранхай";
        else if (Double.compare(18.5, bmi) < 0 & (Double.compare(24.9, bmi) > 0 | Double.compare(24.9, bmi) == 0))
            return "Хэвийн";
        else if (Double.compare(24.9, bmi) < 0 & (Double.compare(29.9, bmi) > 0 | Double.compare(29.9, bmi) == 0))
            return "Илүүдэл жинтэй";
        else if (Double.compare(29.9, bmi) < 0 & (Double.compare(34.9, bmi) > 0 | Double.compare(34.9, bmi) == 0))
            return "Таргалалттай";
        else
            return "Хэт таргалалттай";
    }
}
