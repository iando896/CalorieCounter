package com.iando896.caloriecounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

//        addFood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Open food dialog
//                Toast.makeText(MainActivity.this, "Add food button selected", Toast.LENGTH_SHORT).show();
//            }
//        });
        addFood.setOnClickListener(view -> {
            //Open dialog to add food
            Toast.makeText(MainActivity.this, "Add food button selected", Toast.LENGTH_SHORT).show();
        });

    }

    private void initViews() {
        calorieGoal = findViewById(R.id.calorieGoal);
        calorieProgress = findViewById(R.id.progressBar);
        calorieCount = findViewById(R.id.calorieCount);
        addFood = findViewById(R.id.addFood);
        foodList = findViewById(R.id.cardView);
    }
}