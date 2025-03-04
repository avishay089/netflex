package com.example.streamingapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.streamingapp.R;
import com.example.streamingapp.ui.BaseActivity;
import com.example.streamingapp.ui.login.LoginActivity;
import com.example.streamingapp.ui.registration.RegisterActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button loginButton = findViewById(R.id.btnLogin);
        Button registerButton = findViewById(R.id.btnRegister);

        loginButton.setOnClickListener(view -> openLoginActivity());
        registerButton.setOnClickListener(view -> openRegisterActivity());
    }

    private void openLoginActivity() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}