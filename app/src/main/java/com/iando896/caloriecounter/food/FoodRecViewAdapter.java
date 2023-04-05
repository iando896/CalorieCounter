package com.iando896.caloriecounter.food;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iando896.caloriecounter.MainActivity;
import com.iando896.caloriecounter.R;

import java.util.ArrayList;

public class FoodRecViewAdapter extends RecyclerView.Adapter<FoodRecViewAdapter.ViewHolder> {

    ArrayList<Food> foods = new ArrayList<>();
    Context mContext;
    private static String TAG = "FoodRecView";

    public FoodRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.foodName.setText(foods.get(position).getName());
        holder.calorieCount.setText(foods.get(position).getTotalCalories().toString());
        holder.itemView.setOnClickListener(view -> {
            ((MainActivity)mContext).showUpdateDialog(position);
            Toast.makeText(mContext, "Position: " + Integer.toString(position), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView foodName;
        private TextView calorieCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            calorieCount = itemView.findViewById(R.id.calorieListCount);
        }
    }
}
