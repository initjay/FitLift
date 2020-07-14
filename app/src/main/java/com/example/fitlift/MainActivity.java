package com.example.fitlift;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.fitlift.databinding.ActivityMainBinding;
import com.example.fitlift.fragments.FriendFragment;
import com.example.fitlift.fragments.MealFragment;
import com.example.fitlift.fragments.WorkoutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Fragment selection
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;

                switch (menuItem.getItemId()) {
                    case R.id.action_meals:
                        fragment = new MealFragment();
                        break;
                    case R.id.action_friends:
                        fragment = new FriendFragment();
                        break;
                    case R.id.action_workout:
                    default:
                        fragment = new WorkoutFragment();
                        break;
                }

                // handles inserting the fragment to the container in main activity
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        // func to test login
//        ParseUser user = ParseUser.getCurrentUser();
//        user.logOut();

        //setContentView(R.layout.activity_main);
    }
}