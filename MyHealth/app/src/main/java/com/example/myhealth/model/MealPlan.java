package com.example.myhealth.model;


public class MealPlan {
    private String id;
    private Meal breakfast;
    private Meal lunch;
    private Meal dinner;
    private Meal snack;

    public MealPlan() {
        breakfast = new Meal("Өглөөний цай");
        lunch = new Meal("Үдийн хоол");
        dinner = new Meal("Оройн хоол");
        snack = new Meal("Хөнгөн зууш");

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Meal getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Meal breakfast) {
        this.breakfast = breakfast;
    }

    public Meal getLunch() {
        return lunch;
    }

    public void setLunch(Meal lunch) {
        this.lunch = lunch;
    }

    public Meal getDinner() {
        return dinner;
    }

    public void setDinner(Meal dinner) {
        this.dinner = dinner;
    }

    public Meal getSnack() {
        return snack;
    }

    public void setSnack(Meal snack) {
        this.snack = snack;
    }

    public double calcTotalCalories() {
        return breakfast.calcTotalCalories() + lunch.calcTotalCalories() + dinner.calcTotalCalories() + snack.calcTotalCalories();
    }

    public Meal getMealByName (String mealName) {
        switch (mealName) {
            case "Өглөөний цай":
                return breakfast;
            case "Үдийн хоол":
                return lunch;
            case "Оройн хоол":
                return dinner;
            default:
                return snack;
        }
    }
}
