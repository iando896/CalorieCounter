package com.iando896.caloriecounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.iando896.caloriecounter.food.AddFoodDialogFragment;
import com.iando896.caloriecounter.food.Food;
import com.iando896.caloriecounter.food.FoodRecViewAdapter;
import com.iando896.caloriecounter.food.UpdateFoodDialogFragment;

public class MainActivity extends AppCompatActivity {

    TextView calorieGoal;
    ProgressBar calorieProgress;
    TextView calorieCount;
    FloatingActionButton addFood;
    CardView foodListCard;
    Integer calorieGoalValue;
    RecyclerView foodRecView;
    TextView noFoodMessage;
    ProgressBar progressBar;
    ImageView refreshBtn;
    ColorFilter progressBarOrigColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utils.getInstance();
        initViews();

        updateCalorieCount();
        progressBarOrigColor = progressBar.getProgressDrawable().getColorFilter();
        progressBar.setMax(Integer.parseInt(calorieGoal.getText().toString()));

        refreshBtn.setOnClickListener(v -> {
            refresh();
        });

        addFood.setOnClickListener(view -> {
            //Open dialog to add food
            //Attach dialog to array list
            AddFoodDialogFragment addFoodDialogFragment = new AddFoodDialogFragment(this);
            addFoodDialogFragment.show(getSupportFragmentManager(), AddFoodDialogFragment.TAG);
        });

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
//                Intent intent = new Intent();
//                startActivity(intent);
                Toast.makeText(this, "Opened setting", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initRecView() {
        FoodRecViewAdapter adapter = new FoodRecViewAdapter(this);
        adapter.setFoods(Utils.getAllFoods());

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
        progressBar = findViewById(R.id.progressBar);
        refreshBtn = findViewById(R.id.refresh);


        initRecView();
    }

    public void updateCalorieCount() {
        Integer currentCalorie = 0;
        for (Food f: Utils.getAllFoods()) {
            currentCalorie += f.getTotalCalories();
        }
        calorieCount.setText(currentCalorie.toString());
        //Change progress bar?
        if (currentCalorie <= progressBar.getMax()) {
            progressBar.setProgress(currentCalorie, true);
            progressBar.getProgressDrawable().setColorFilter(progressBarOrigColor);
        } else {
            progressBar.setProgress(progressBar.getMax());
            progressBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN));
        }
    }

    public void updateFoodRecView() {
        ((FoodRecViewAdapter)foodRecView.getAdapter()).setFoods(Utils.getAllFoods());
        if (Utils.getAllFoods().size() == 0) {
            noFoodMessage.setVisibility(View.VISIBLE);
        }
    }

    public void showUpdateDialog(int position) {
        UpdateFoodDialogFragment updateFoodDialogFragment = new UpdateFoodDialogFragment(this, position);
        updateFoodDialogFragment.show(getSupportFragmentManager(), UpdateFoodDialogFragment.TAG);
    }

    public void setNoFoodMessageVisibility(int v) {
        noFoodMessage.setVisibility(v);
    }

    private void refresh() {
        //TODO: Ensure users want to clear
        Utils.clear();
        updateFoodRecView();
        updateCalorieCount();
    }
}