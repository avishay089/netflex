package com.example.streamingapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.streamingapp.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class BaseActivity extends AppCompatActivity {
    private SwitchMaterial themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_switch);
        View actionView = item.getActionView();
        themeSwitch = actionView.findViewById(R.id.themeSwitch);
        themeSwitch.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            SharedPreferences.Editor editor = getSharedPreferences("ThemePref", MODE_PRIVATE).edit();
            editor.putBoolean("isDarkMode", isChecked);
            editor.apply();
        });
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isDarkMode = getSharedPreferences("ThemePref", MODE_PRIVATE)
                .getBoolean("isDarkMode", false);
        if (themeSwitch != null) {
            themeSwitch.setChecked(isDarkMode);
        }
    }
}
