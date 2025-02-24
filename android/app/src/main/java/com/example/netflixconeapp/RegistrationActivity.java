package com.example.netflixconeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword, edtConfirmPassword, edtDisplayName;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtDisplayName = findViewById(R.id.edtDisplayName);
        profilePicture = findViewById(R.id.imgProfile);

        profilePicture.setOnClickListener(v -> openGallery());

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            profilePicture.setImageURI(imageUri);
        }
    }

    private void registerUser() {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        String displayName = edtDisplayName.getText().toString();

        if (password.equals(confirmPassword) && password.length() >= 8) {
            // Perform registration logic here
            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid registration details", Toast.LENGTH_SHORT).show();
        }
    }
}
