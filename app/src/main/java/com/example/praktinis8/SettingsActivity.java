package com.example.praktinis8;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        applySavedTheme();

        setContentView(R.layout.activity_settings);


        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        RadioGroup themeRadioGroup = findViewById(R.id.themeRadioGroup);
        RadioButton lightThemeRadio = findViewById(R.id.lightThemeRadio);
        RadioButton darkThemeRadio = findViewById(R.id.darkThemeRadio);
        Button saveButton = findViewById(R.id.saveThemeButton);


        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String savedTheme = preferences.getString("theme", "light");
        if ("light".equals(savedTheme)) {
            lightThemeRadio.setChecked(true);
        } else {
            darkThemeRadio.setChecked(true);
        }


        saveButton.setOnClickListener(v -> {
            String selectedTheme = lightThemeRadio.isChecked() ? "light" : "dark";
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("theme", selectedTheme);
            editor.apply();
            recreate();
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void applySavedTheme() {
        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String theme = preferences.getString("theme", "light");

        if ("light".equals(theme)) {
            setTheme(R.style.Theme_Praktinis8_Light);
        } else {
            setTheme(R.style.Theme_Praktinis8_Dark);
        }
    }
}
