package com.iando896.caloriecounter;

public class Food {
    private String name;
    private Integer calories;
    private Integer servings;

    public Food(String name, Integer calories, Integer servings) {
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

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public Integer getTotalCalories() {
        return calories * servings;
    }
}
