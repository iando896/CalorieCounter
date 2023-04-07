package com.iando896.caloriecounter;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iando896.caloriecounter.dialog.AddFoodDialogFragment;
import com.iando896.caloriecounter.dialog.RefreshDialogFragment;
import com.iando896.caloriecounter.food.Food;
import com.iando896.caloriecounter.food.FoodRecViewAdapter;
import com.iando896.caloriecounter.dialog.UpdateFoodDialogFragment;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    TextView calorieGoal;
    ProgressBar calorieProgress;
    TextView calorieCount;
    FloatingActionButton addFood;
    CardView foodListCard;
    RecyclerView foodRecView;
    TextView noFoodMessage;
    ImageView refreshBtn;
    ColorFilter progressBarOrigColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.getInstance(this);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_button:
                //TODO: Open settings activity

                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                Toast.makeText(this, "Opened setting", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        Toast.makeText(this, "Resuming", Toast.LENGTH_SHORT).show();
        super.onResume();
        applySettings();
        progressBarOrigColor = calorieProgress.getProgressDrawable().getColorFilter();
        calorieProgress.setMax(Integer.parseInt(calorieGoal.getText().toString()));
        updateCalorieCount();

        refreshBtn.setOnClickListener(v -> {
            RefreshDialogFragment refreshDialogFragment = new RefreshDialogFragment(this);
            refreshDialogFragment.show(getSupportFragmentManager(), RefreshDialogFragment.TAG);
        });

        addFood.setOnClickListener(view -> {
            AddFoodDialogFragment addFoodDialogFragment = new AddFoodDialogFragment();
            addFoodDialogFragment.show(getSupportFragmentManager(), AddFoodDialogFragment.TAG);
        });
    }

    private void applySettings() {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this);
        calorieGoal.setText(SP.getString("calorie_goal_preference", null));
//        SP.getString("calorie")
    }

    private void initRecView() {
        FoodRecViewAdapter adapter = new FoodRecViewAdapter(this);
        adapter.setFoods(Utils.getInstance(this).getAllFoods());
        foodRecView.setAdapter(adapter);
        foodRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initViews() {
        calorieGoal = findViewById(R.id.calorieGoal);
        calorieProgress = findViewById(R.id.progressBar);
        calorieCount = findViewById(R.id.calorieCount);
        addFood = findViewById(R.id.addFood);
        foodListCard = findViewById(R.id.foodListLayout);
        foodRecView = findViewById(R.id.foodList);
        noFoodMessage = findViewById(R.id.noFoodMessage);
        refreshBtn = findViewById(R.id.refresh);

        initRecView();
    }

    public void updateCalorieCount() {
        Integer currentCalorie = 0;
        for (Food f: Utils.getInstance(this).getAllFoods()) {
            currentCalorie += f.getTotalCalories();
        }
        calorieCount.setText(currentCalorie.toString());
        //Change progress bar?
        if (currentCalorie <= calorieProgress.getMax()) {
            calorieProgress.setProgress(currentCalorie, true);
            calorieProgress.getProgressDrawable().setColorFilter(progressBarOrigColor);
        } else {
            calorieProgress.setProgress(calorieProgress.getMax());
            calorieProgress.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN));
        }
    }

    public void updateFoodRecView() {
        ((FoodRecViewAdapter)foodRecView.getAdapter()).setFoods(Utils.getInstance(this).getAllFoods());
        if (Utils.getInstance(this).getAllFoods().size() == 0) {
            noFoodMessage.setVisibility(View.VISIBLE);
        }
    }

    public void showUpdateDialog(int position) {
        UpdateFoodDialogFragment updateFoodDialogFragment = new UpdateFoodDialogFragment(position);
        updateFoodDialogFragment.show(getSupportFragmentManager(), UpdateFoodDialogFragment.TAG);
    }

    public void setNoFoodMessageVisibility(int v) {
        noFoodMessage.setVisibility(v);
    }

    public void refresh() {
        Utils.getInstance(this).clear();
        updateCalorieCount();
        updateFoodRecView();
    }
}