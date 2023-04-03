package com.iando896.caloriecounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView calorieGoal;
    ProgressBar calorieProgress;
    TextView calorieCount;
    FloatingActionButton addFood;
    CardView foodListCard;
    Integer calorieGoalValue;
    Integer currentCalorie;
    RecyclerView foodRecView;
    TextView noFoodMessage;
//    ArrayList<Dialog> addFoodDialogArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentCalorie = 0;
        Utils.getInstance();
        initViews();
        updateCalorieCount();

        addFood.setOnClickListener(view -> {
            //Open dialog to add food
            //Attach dialog to array list
            AddFoodDialogFragment addFoodDialogFragment = new AddFoodDialogFragment(this, noFoodMessage);
            addFoodDialogFragment.show(getSupportFragmentManager(), AddFoodDialogFragment.TAG);
//            addFoodDialogFragmentArrayList.get(addFoodDialogFragmentArrayList.size() - 1).show(getSupportFragmentManager(), AddFoodDialogFragment.TAG);
            //addFoodDialogFragmentArrayList.get(addFoodDialogFragmentArrayList.size() - 1).getDialog().s;
            //updateFoodRecView();
            //update info
            //Toast.makeText(MainActivity.this, "Add food button selected", Toast.LENGTH_SHORT).show();
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

        initRecView();
    }

    public void updateCalorieCount() {
        Integer currentCalorie = 0;
        for (Food f: Utils.getAllFoods()) {
            currentCalorie += f.getTotalCalories();
        }
        calorieCount.setText(currentCalorie.toString());
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
}