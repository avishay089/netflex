package com.example.netflixconeapp.viewmodel;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;

import com.example.netflixconeapp.model.User;
import com.example.netflixconeapp.network.SignupResponse;
import com.example.netflixconeapp.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupViewModel extends ViewModel {
    private final UserRepository repository;
    public MutableLiveData<String> signupMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<String> passwordError = new MutableLiveData<>();
    public MutableLiveData<String> idError = new MutableLiveData<>();

    public SignupViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public void validateId(String id) {
        if (id.length() != 9 || !id.matches("\\d+")) {
            idError.setValue("ID must be exactly 9 digits");
        } else {
            idError.setValue(null);
        }
    }

    public void validatePasswords(String password, String verifyPassword) {
        if (password.length() < 8) {
            passwordError.setValue("Password must be at least 8 characters long");
        } else if (!password.equals(verifyPassword)) {
            passwordError.setValue("Passwords do not match");
        } else {
            passwordError.setValue(null);
        }
    }

    public void signupUser(User user) {
        isLoading.setValue(true);
        repository.signupUser(user, new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    signupMessage.setValue(response.body().getMessage());
                } else {
                    signupMessage.setValue("Signup failed. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                isLoading.setValue(false);
                signupMessage.setValue("An error occurred: " + t.getMessage());
            }
        });
    }
}
