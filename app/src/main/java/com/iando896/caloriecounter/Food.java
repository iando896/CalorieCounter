package com.iando896.caloriecounter;

public class Food {
    String name;
    int calories;
    int servings;

    public Food(String name, int calories, int servings) {
        this.name = name;
        this.calories = calories;
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getTotalCalories() {
        return calories * servings;
    }
}
