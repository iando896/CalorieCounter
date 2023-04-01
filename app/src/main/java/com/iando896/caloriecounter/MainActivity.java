package com.iando896.caloriecounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView calorieGoal;
    ProgressBar calorieProgress;
    TextView calorieCount;
    FloatingActionButton addFood;
    CardView foodList;
    Integer calorieGoalValue;
    Integer currentCalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentCalorie = 0;
        initViews();

        //Set up current foods eaten and determine current calorie value

        addFood.setOnClickListener(view -> {
            //Open dialog to add food
            new AddFoodDialogFragment().show(getSupportFragmentManager(), AddFoodDialogFragment.TAG);
            Toast.makeText(MainActivity.this, "Add food button selected", Toast.LENGTH_SHORT).show();
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
                //Open settings activity
//                Intent intent = new Intent();
//                startActivity(intent);
                Toast.makeText(this, "Opened setting", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {
        calorieGoal = findViewById(R.id.calorieGoal);
        calorieProgress = findViewById(R.id.progressBar);
        calorieCount = findViewById(R.id.calorieCount);
        addFood = findViewById(R.id.addFood);
        foodList = findViewById(R.id.cardView);

        calorieCount.setText(currentCalorie.toString());
    }
}