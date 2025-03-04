package com.example.streamingapp.ui.registration;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.streamingapp.R;
import com.example.streamingapp.model.User;
import com.example.streamingapp.repository.UserRepository;
import com.example.streamingapp.ui.BaseActivity;
import com.example.streamingapp.ui.login.LoginActivity;
import com.example.streamingapp.viewmodel.UserViewModel;
import com.example.streamingapp.viewmodel.UserViewModelFactory;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private static final int CAMERA_REQUEST_CODE = 1888;
    private static final int GALLERY_REQUEST_CODE = 1889;
    private ImageButton btnAvatar;
    private EditText txtUserName;
    private EditText txtIDNumber;
    private EditText txtPhone;
    private EditText txtPassword;
    private EditText txtConfirm;
    private EditText txtFullName;
    private CheckBox chkIsAdmin;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnAvatar = findViewById(R.id.imageAvatar);

        // For gallery
        btnAvatar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });
        // For camera
        btnAvatar.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                return true;
            }
        });

        txtUserName = findViewById(R.id.txtSignUpUserName);
        txtPassword = findViewById(R.id.txtSignUpPassword);
        txtConfirm = findViewById(R.id.txtSignUpPasswordConfirm);
        txtFullName = findViewById(R.id.txtSignUpFullName);
        chkIsAdmin = findViewById(R.id.switchIsAdmin);
        txtIDNumber = findViewById(R.id.txtIDNumber);
        txtPhone = findViewById(R.id.txtSignUpPhoneNumber);
        txtIDNumber.setText(generateRandomString(9));

        UserRepository userRepository = new UserRepository(getApplication());
        UserViewModelFactory userViewModelFactory = new UserViewModelFactory(getApplication(), userRepository);
        userViewModel = new ViewModelProvider(this, userViewModelFactory).get(UserViewModel.class);

        Button btnSignUpOK = findViewById(R.id.btnSignUpOK);

        btnSignUpOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateInput()) return;
                String username = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                String fullname = txtFullName.getText().toString();
                String IDNumber = txtIDNumber.getText().toString();
                String phoneNumber = txtPhone.getText().toString();
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setName(fullname);
                user.setTz(IDNumber);
                user.setEmail(username + "@test.com");
                user.setAvatar("test");
                user.setAdmin(chkIsAdmin.isChecked());
                user.setPhone(phoneNumber);
                userViewModel.register(user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                            gotoSignIn();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        
        TextView gotoSignIn = findViewById(R.id.btnSignInFromSignUp);
        gotoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignIn();
            }
        });
    }

    private void gotoSignIn() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    btnAvatar.setImageBitmap(photo);
                    saveImage(photo);
                    break;

                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        Bitmap resizedBitmap = getResizedBitmap(originalBitmap, 200, 200);
                        btnAvatar.setImageBitmap(resizedBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int targetWidth, int targetHeight) {
        float scale;
        float scaleWidth = ((float) targetWidth) / bitmap.getWidth();
        float scaleHeight = ((float) targetHeight) / bitmap.getHeight();

        // Use the smaller scale to ensure the image fits completely within the target dimensions
        scale = Math.min(scaleWidth, scaleHeight);

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create the new bitmap with correct dimensions
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap,
                0, 0,
                bitmap.getWidth(),
                bitmap.getHeight(),
                matrix,
                true
        );
        bitmap.recycle(); // Free up the memory of original bitmap

        return resizedBitmap;
    }



    private void saveImage(Bitmap bitmap) {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(Environment.getExternalStorageDirectory() + "/avatar.jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateInput() {
        if (txtUserName.getText().toString().isEmpty()) {
            Toast.makeText(this, "UserName field is required", Toast.LENGTH_SHORT).show();
            txtUserName.requestFocus();
            return false;
        }
        if (txtIDNumber.getText().toString().length() != 9) {
            Toast.makeText(this, "ID must be exactly 9 digits", Toast.LENGTH_SHORT).show();
            txtIDNumber.requestFocus();
            return false;
        }
        if (txtFullName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Name field is required", Toast.LENGTH_SHORT).show();
            txtFullName.requestFocus();
            return false;
        }
        String password = txtPassword.getText().toString();
        String confirm = txtConfirm.getText().toString();
        if (txtPassword.getText().toString().isEmpty() || txtConfirm.getText().toString().isEmpty() || !txtPassword.getText().toString().equals(txtConfirm.getText().toString())) {
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            txtConfirm.setText("");
            txtConfirm.requestFocus();
            return false;
        }
        return true;
    }

    private String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomDigit = random.nextInt(10);
            sb.append(randomDigit);
        }
        return sb.toString();
    }
}