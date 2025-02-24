package com.example.netflixconeapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netflixconeapp.view.SignupActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.sign_up);

        // Login button click functionality
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Validate input fields
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    authenticateUser(username, password);
                }
            }
        });

        // Redirect to Sign Up screen
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    // Authentication logic
    private void authenticateUser(String username, String password) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.99.4.168:5000/api/tokens");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject payload = new JSONObject();
                payload.put("username", username);
                payload.put("password", password);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(payload.toString());
                writer.flush();
                writer.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == 201) {

                    // Parse the success response
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    String userId = jsonResponse.getString("userId");
                    String token = jsonResponse.getString("token");
                    boolean IsAdmin = jsonResponse.getBoolean("isAdmin");
                    String profilePicture = jsonResponse.getString("profilePicture");
                    String userName = jsonResponse.getString("userName");

                    // Use SharedPreferences to store data locally
                    SharedPreferences sharedPreferences = this.getSharedPreferences("UserPreferences", this.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("token", token);
                    editor.putString("user_id",userId );
                    editor.putBoolean("isAdmin", IsAdmin);
                    editor.putString("userImage", profilePicture);
                    editor.putString("userName", userName);
                    editor.apply();

                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show());
                    // Redirect to Home/Dashboard
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error occurred" + e.getMessage() , Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
