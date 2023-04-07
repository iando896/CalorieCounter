package com.iando896.caloriecounter.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.iando896.caloriecounter.MainActivity;
import com.iando896.caloriecounter.R;
import com.iando896.caloriecounter.Utils;
import com.iando896.caloriecounter.food.Food;

public class UpdateFoodDialogFragment extends DialogFragment {
    EditText editFoodName;
    EditText editCalories;
    EditText editServings;

    TextView foodNameWarning;
    TextView calorieWarning;
    TextView servingWarning;
    int position;

    public UpdateFoodDialogFragment(int position) {
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_food, null);
        builder.setView(view)
                .setPositiveButton(R.string.dialog_update, null)
                .setNegativeButton(R.string.dialog_remove, null);

        editFoodName = view.findViewById(R.id.editFoodName);
        editCalories = view.findViewById(R.id.editCalorie);
        editServings = view.findViewById(R.id.editServings);

        foodNameWarning = view.findViewById(R.id.foodNameWarning);
        calorieWarning = view.findViewById(R.id.caloriesWarning);
        servingWarning = view.findViewById(R.id.servingsWarning);

        Food food = Utils.getInstance(requireContext()).getAllFoods().get(position);
        if (food.getName() != null)
            editFoodName.setText(food.getName());
        if (food.getCalories() != null)
            editCalories.setText(food.getCalories().toString());
        if (food.getServings() != null)
            editServings.setText(food.getServings().toString());

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button_pos = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (button_pos != null) {
                button_pos.setOnClickListener(view1 -> {
                    positiveButtonAction(dialog);
                });
            }

            Button button_neg = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            if (button_neg != null) {
                button_neg.setOnClickListener(view1 -> {
                    Utils.getInstance(requireContext()).removeFood(position);
                    ((MainActivity)requireContext()).updateFoodRecView();
                    ((MainActivity)requireContext()).updateCalorieCount();
                    dialog.dismiss();
                });
            }
        });

        return dialog;
    }

    private void positiveButtonAction(Dialog dialog) {
        if (!editFoodName.getText().toString().equals("") &&
                !editCalories.getText().toString().equals("") &&
                !editServings.getText().toString().equals("")) {

            Utils.getInstance(requireContext()).updateFood(position, editFoodName.getText().toString(),
                    Integer.parseInt(editCalories.getText().toString()),
                    Integer.parseInt(editServings.getText().toString()));

            ((MainActivity)requireContext()).updateFoodRecView();
            ((MainActivity)requireContext()).updateCalorieCount();
            Toast.makeText(requireContext(), editCalories.getText().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(requireContext(), "Food updated", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        if (editFoodName.getText().toString().equals(""))
            foodNameWarning.setVisibility(View.VISIBLE);
        else
            foodNameWarning.setVisibility(View.GONE);
        if (editCalories.getText().toString().equals(""))
            calorieWarning.setVisibility(View.VISIBLE);
        else
            calorieWarning.setVisibility(View.GONE);
        if (editServings.getText().toString().equals(""))
            servingWarning.setVisibility(View.VISIBLE);
        else
            servingWarning.setVisibility(View.GONE);
    }

    public static String TAG = "UpdateFoodDialogFragment";
}
