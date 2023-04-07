package com.iando896.caloriecounter.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.iando896.caloriecounter.MainActivity;
import com.iando896.caloriecounter.R;

public class RefreshDialogFragment extends DialogFragment {

    Context mContext;

    public RefreshDialogFragment(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(R.string.refresh_confirmation)
                .setPositiveButton("Yes", ((dialogInterface, i) -> {
                    ((MainActivity)mContext).refresh();
                }))
                .setNegativeButton("No", null)
                .create();
    }

    public static String TAG = "Refresh TAG";
}
