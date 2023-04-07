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

public class AddFoodDialogFragment extends DialogFragment {

    EditText editFoodName;
    EditText editCalories;
    EditText editServings;

    TextView foodNameWarning;
    TextView calorieWarning;
    TextView servingWarning;

    public AddFoodDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_food, null);
        builder.setView(view);

        editFoodName = view.findViewById(R.id.editFoodName);
        editCalories = view.findViewById(R.id.editCalorie);
        editServings = view.findViewById(R.id.editServings);

        foodNameWarning = view.findViewById(R.id.foodNameWarning);
        calorieWarning = view.findViewById(R.id.caloriesWarning);
        servingWarning = view.findViewById(R.id.servingsWarning);

        builder.setPositiveButton(R.string.dialog_add, null)
                .setNegativeButton(R.string.dialog_cancel, null);
        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            if (button != null) {
                button.setOnClickListener(view1 -> {
                    positiveButtonAction(dialog);
                });
            }
        });

        return dialog;
    }

    private void positiveButtonAction(Dialog dialog) {
        if (!editFoodName.getText().toString().equals("") &&
                !editCalories.getText().toString().equals("") &&
                !editServings.getText().toString().equals("")) {
            //TODO: Check legal values for food

            Utils.getInstance(requireContext()).addFood(new Food(editFoodName.getText().toString(),
                    Integer.parseInt(editCalories.getText().toString()),
                    Integer.parseInt(editServings.getText().toString())));
            ((MainActivity)requireContext()).updateFoodRecView();
            ((MainActivity)requireContext()).updateCalorieCount();
            ((MainActivity)requireContext()).setNoFoodMessageVisibility(View.GONE);
//                        Toast.makeText(requireContext(), "Food added", Toast.LENGTH_SHORT).show();
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

    public static String TAG = "AddFoodDialogFragment";
}
