package com.example.netflixconeapp.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.netflixconeapp.R;
import com.example.netflixconeapp.model.User;
import com.example.netflixconeapp.model.UserDatabase;
import com.example.netflixconeapp.network.ApiProvider;
import com.example.netflixconeapp.network.UserApiService;
import com.example.netflixconeapp.repository.UserRepository;
import com.example.netflixconeapp.viewmodel.SignupViewModel;
import com.example.netflixconeapp.viewmodel.SignupViewModelFactory;

public class SignupActivity extends AppCompatActivity {
    private SignupViewModel signupViewModel;
    private EditText emailField, idField, fullNameField, usernameField, passwordField, verifyPasswordField;
    private CheckBox isAdminCheckbox;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI elements
        emailField = findViewById(R.id.emailField);
        idField = findViewById(R.id.idField);
        fullNameField = findViewById(R.id.fullNameField);
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        verifyPasswordField = findViewById(R.id.verifyPasswordField);
        isAdminCheckbox = findViewById(R.id.isAdminCheckbox);
        loadingBar = findViewById(R.id.loadingBar);
        Button signupButton = findViewById(R.id.signupButton);

        // Initialize ViewModel
        UserDatabase database = UserDatabase.getDatabase(this);
        UserRepository repository = new UserRepository(database.userDao());
        SignupViewModelFactory factory = new SignupViewModelFactory(repository);
        signupViewModel = new ViewModelProvider(this, factory).get(SignupViewModel.class);

        // Set up observers
        signupViewModel.signupMessage.observe(this, message -> {
            Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
        });

        // Handle signup button click
        signupButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String id = idField.getText().toString();
            String fullName = fullNameField.getText().toString();
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();
            String verifyPassword = verifyPasswordField.getText().toString();
            boolean isAdmin = isAdminCheckbox.isChecked();

            signupViewModel.validateId(id);
            signupViewModel.validatePasswords(password, verifyPassword);

            if (signupViewModel.passwordError.getValue() == null && signupViewModel.idError.getValue() == null) {
                User user = new User();
                user.setEmail(email);
                user.setFullName(fullName);
                user.setUserName(username);
                user.setIdNumber(id);
                user.setPassword(password);
                user.setAdmin(isAdmin);
                signupViewModel.signupUser(user);
            }
        });
    }
}
