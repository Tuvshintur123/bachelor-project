package com.example.myhealth.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class History {
    private List<Date> dates;

    public History() {
        this.dates = new ArrayList<>();
    }

    public Date getDate(Calendar date) {
        for (Date date1 : dates) {
            Log.d("fatsecret", date1.getDate().getTime() + " " + date.getTime());
            if (date1.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) & date1.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR))
                return date1;
        }
        Log.d("fatsecret", "not equal");
        return null;
    }

    public void addDate(Date date) {
        dates.add(date);
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
}
