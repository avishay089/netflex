package com.example.streamingapp.ui.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.streamingapp.R;
import com.example.streamingapp.data.local.TokenManager;
import com.example.streamingapp.model.LoginResponse;
import com.example.streamingapp.model.User;
import com.example.streamingapp.repository.UserRepository;
import com.example.streamingapp.ui.BaseActivity;
import com.example.streamingapp.ui.main.MainActivity;
import com.example.streamingapp.ui.registration.RegisterActivity;
import com.example.streamingapp.viewmodel.UserViewModel;
import com.example.streamingapp.viewmodel.UserViewModelFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private UserViewModel userViewModel;
    private EditText txtUserName;
    private EditText txtPassword;
    private Button loginButton;
    private TextView gotoSignUp;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tokenManager = new TokenManager(this);

        gotoSignUp = findViewById(R.id.btnSignUpFromSignIn);
        loginButton = findViewById(R.id.btnSignInOK);
        txtUserName = findViewById(R.id.txtSignInUserName);
        txtPassword = findViewById(R.id.txtSignInPassword);

        UserRepository userRepository = new UserRepository(getApplication());
        UserViewModelFactory userViewModelFactory = new UserViewModelFactory(getApplication(), userRepository);
        userViewModel = new ViewModelProvider(this, userViewModelFactory).get(UserViewModel.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                userViewModel.login(username, password).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            tokenManager.saveToken(response.body().getToken());
                            tokenManager.saveUserInfo(response.body());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}