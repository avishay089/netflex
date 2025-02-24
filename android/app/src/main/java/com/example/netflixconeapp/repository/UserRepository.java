package com.example.netflixconeapp.repository;

import android.app.Application;

import com.example.netflixconeapp.model.UserDatabase;
import com.example.netflixconeapp.model.User;
import com.example.netflixconeapp.model.UserDao;
import com.example.netflixconeapp.network.ApiProvider;
import com.example.netflixconeapp.network.SignupResponse;
import com.example.netflixconeapp.network.UserApiService;
import com.example.netflixconeapp.network.UserNetworkModel;

import retrofit2.Call;
import retrofit2.Callback;

public class UserRepository {
    private final UserDao userDao;
    private final UserApiService userApiService;

    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
        this.userApiService = ApiProvider.createService(UserApiService.class); // Resolves the API service
    }


    // Method to handle user signup
    public void signupUser(User user, Callback<SignupResponse> callback) {
        UserNetworkModel networkModel = new UserNetworkModel(
                user.getEmail(),
                user.getFullName(),
                user.getUserName(),
                user.getIdNumber(),
                user.getPassword(),
                user.isAdmin(),
                user.getProfilePicture()
        );

        Call<SignupResponse> call = userApiService.signupUser(networkModel);
        call.enqueue(callback);
    }

    // Local database operation (optional, for caching)
//    public void insertUser(User user) {
//        AppExecutors.getInstance().diskIO().execute(() -> userDao.insertUser(user));
//    }
}
