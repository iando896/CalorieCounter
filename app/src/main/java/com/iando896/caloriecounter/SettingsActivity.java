package com.iando896.caloriecounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        @Override
        public void onResume() {
            super.onResume();
            Preference pref = findPreference("github");
            if (pref != null) {
                pref.setOnPreferenceClickListener(preference -> {
                    String url = "https://github.com/iando896";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return false;
                });
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            EditTextPreference preference = findPreference("calorie_goal_preference");
            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = SP.edit();
            try {
                if (Integer.parseInt(preference.getText()) < 50000) {
                    editor.putString("calorie_goal_preference", preference.getText());
                    editor.commit();
                }
            } catch (NumberFormatException e) {

            }


        }
    }
}