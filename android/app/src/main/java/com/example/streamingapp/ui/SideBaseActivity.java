package com.example.streamingapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.streamingapp.R;
import com.example.streamingapp.data.local.TokenManager;
import com.example.streamingapp.ui.categories.ManageCategoriesActivity;
import com.example.streamingapp.ui.login.LoginActivity;
import com.example.streamingapp.ui.main.MainActivity;
import com.example.streamingapp.ui.movies.ManageMoviesActivity;
import com.example.streamingapp.ui.search.SearchActivity;
import com.google.android.material.navigation.NavigationView;

public class SideBaseActivity extends BaseActivity{
    protected DrawerLayout drawerLayout;
    protected TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerLayout = findViewById(R.id.drawer_layout);
        tokenManager = new TokenManager(this);
        if (!tokenManager.hasToken()) {
            redirectActivity(SideBaseActivity.this, LoginActivity.class);
            return;
        }
        setNavigationMenu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_menu) {
            openDrawer(drawerLayout);
        }
        return super.onOptionsItemSelected(item);
    }

    protected static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    protected static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    protected static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    protected void setNavigationMenu() {

        NavigationView navigationView = findViewById(R.id.side_view);


        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.nav_home:
                    redirectActivity(SideBaseActivity.this, MainActivity.class);
                    break;
                case R.id.nav_search:
                    redirectActivity(SideBaseActivity.this, SearchActivity.class);
                    break;
                case R.id.nav_manage_movie:
                    redirectActivity(SideBaseActivity.this, ManageMoviesActivity.class);
                    break;
                case R.id.nav_manage_category:
                    redirectActivity(SideBaseActivity.this, ManageCategoriesActivity.class);
                    break;
                case R.id.nav_logout:
                    redirectActivity(SideBaseActivity.this, LoginActivity.class);
                    break;
                default:
                    break;
            }
            closeDrawer(drawerLayout);
            return true;
        });
        Menu menu = navigationView.getMenu();
        MenuItem manageMovieItem = menu.findItem(R.id.nav_manage_movie);
        if (manageMovieItem != null) {
            manageMovieItem.setVisible(tokenManager.getUserInfo() != null && tokenManager.getUserInfo().isAdmin());
        }

        MenuItem manageCategoryItem = menu.findItem(R.id.nav_manage_category);
        if (manageCategoryItem != null) {
            manageCategoryItem.setVisible(tokenManager.getUserInfo() != null && tokenManager.getUserInfo().isAdmin());
        }
    }
}
