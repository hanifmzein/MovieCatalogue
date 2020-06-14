package com.example.MovieCatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PreferenceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        getSupportFragmentManager().beginTransaction().add(R.id.setting_holder, new PreferenceFragment()).commit();
    }
}
