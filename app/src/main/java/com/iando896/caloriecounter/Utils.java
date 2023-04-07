package com.iando896.caloriecounter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iando896.caloriecounter.food.Food;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Utils {
    private static Utils instance;
    private SharedPreferences sharedPreferences;
    private final static String DB_KEY = "DB";
    private final static String ALL_FOODS_KEY = "all_food";

    private Utils(Context context) {

        sharedPreferences = context.getSharedPreferences(DB_KEY, Context.MODE_PRIVATE);

        if (getAllFoods() == null) {
            initData();
        }
    }

    public static Utils getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new Utils(context);
            return instance;
        }
    }

    private void initData() {
        ArrayList<Food> foods = new ArrayList<>();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_FOODS_KEY, gson.toJson(foods));
        editor.commit();
    }

    public ArrayList<Food> getAllFoods() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Food>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(ALL_FOODS_KEY, null), type);
    }

    public boolean addFood(Food f) {
        ArrayList<Food> foods = getAllFoods();
        if (foods != null) {
            if (foods.add(f)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALL_FOODS_KEY);
                editor.putString(ALL_FOODS_KEY, gson.toJson(foods));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public void updateFood(int position, String name, Integer calories, Integer servings) {
        ArrayList<Food> foods = getAllFoods();
        if (foods != null) {
            Food food = foods.get(position);
            food.setName(name);
            food.setCalories(calories);
            food.setServings(servings);

            Gson gson = new Gson();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_FOODS_KEY);
            editor.putString(ALL_FOODS_KEY, gson.toJson(foods));
            editor.commit();
        }
    }

    public void removeFood(int position) {
        ArrayList<Food> foods = getAllFoods();
        if (foods != null) {
            if (foods.remove(position) != null) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALL_FOODS_KEY);
                editor.putString(ALL_FOODS_KEY, gson.toJson(foods));
                editor.commit();
            }
        }
    }

    public void clear() {
        initData();
    }
}
