package com.iando896.caloriecounter;

import com.iando896.caloriecounter.food.Food;

import java.util.ArrayList;

public class Utils {
    private static Utils instance;

    private static ArrayList<Food> allFoods;

    private Utils() {
        if (allFoods == null) {
            allFoods = new ArrayList<>();
//            initData();
        }
    }

//    private void initData() {
//        allFoods.add(new Food("Waffle", 5, 2));
//    }

    public static Utils getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new Utils();
            return instance;
        }
    }

    public static ArrayList<Food> getAllFoods() {
        return allFoods;
    }

    public static void addFood(Food f) {
        allFoods.add(f);
    }

    public static void removeFood(int position) {
        allFoods.remove(position);
    }

    public static void clear() {
        allFoods.clear();
    }
//    public static void updateFood(int position, String foodName, Integer calories, Integer servings) {
//        allFoods.get()
//    }
}
