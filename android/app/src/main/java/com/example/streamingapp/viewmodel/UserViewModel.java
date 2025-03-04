package com.example.streamingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.AndroidViewModel;

import com.example.streamingapp.model.LoginResponse;
import com.example.streamingapp.model.User;
import com.example.streamingapp.repository.UserRepository;

import retrofit2.Call;

public class UserViewModel extends AndroidViewModel{
    private UserRepository userRepository;
    private MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application, UserRepository userRepository) {
        super(application);
        this.userRepository = userRepository;
    }

    public Call<Void> register(User user) {
        return userRepository.registerUser(user);
    }

    public Call<LoginResponse> login(String username, String password) {
        return userRepository.loginUser(username, password);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }
}
