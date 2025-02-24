package com.example.netflixconeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button loginButton = findViewById(R.id.btnLogin);
        Button registerButton = findViewById(R.id.btnRegister);

        loginButton.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, LoginActivity.class)));
        registerButton.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, RegistrationActivity.class)));
    }
}
