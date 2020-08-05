package com.example.fitlift.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fitlift.OnSwipeTouchListener;
import com.example.fitlift.R;
import com.example.fitlift.databinding.ActivityMainBinding;
import com.example.fitlift.fragments.FriendFragment;
import com.example.fitlift.fragments.MealDetailsFragment;
import com.example.fitlift.fragments.MealFragment;
import com.example.fitlift.fragments.WorkoutDetailsFragment;
import com.example.fitlift.fragments.WorkoutFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    final FragmentManager fragmentManager = getSupportFragmentManager();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Fragment selection
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                String TAG;

                switch (menuItem.getItemId()) {
                    case R.id.action_meals:
                        fragment = new MealFragment();
                        TAG = "MEAL_FRAGMENT";
                        break;
                    case R.id.action_friends:
                        fragment = new FriendFragment();
                        TAG = "FRIEND_FRAGMENT";
                        break;
                    case R.id.action_workout:
                    default:
                        fragment = new WorkoutFragment();
                        TAG = "WORKOUT_FRAGMENT";
                        break;
                }

                // handles inserting the fragment to the container in main activity
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment, TAG).commit();
                return true;
            }
        });

        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            Fragment currFragment;

            @Override
            public void onSwipeDown() {
            }

            @Override
            public void onSwipeLeft() {
                currFragment = fragmentManager.findFragmentByTag("FRIEND_FRAGMENT");
                if (currFragment != null && currFragment.isVisible()) {
                    binding.bottomNavigation.setSelectedItemId(R.id.action_workout);
                } else {
                    binding.bottomNavigation.setSelectedItemId(R.id.action_meals);
                }
            }

            @Override
            public void onSwipeUp() {
            }

            @Override
            public void onSwipeRight() {
                currFragment = fragmentManager.findFragmentByTag("MEAL_FRAGMENT");
                if (currFragment != null && currFragment.isVisible()) {
                    binding.bottomNavigation.setSelectedItemId(R.id.action_workout);
                } else {
                    binding.bottomNavigation.setSelectedItemId(R.id.action_friends);
                }
            }

        });

        // Set default selection for fragment
        binding.bottomNavigation.setSelectedItemId(R.id.action_workout);
    }

    public void goToDetails() {
        Fragment fragment = new WorkoutDetailsFragment();

        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fts.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fts.replace(R.id.flContainer, fragment).commit();
    }

    public void goToMealDetails() {
        Fragment fragment = new MealDetailsFragment();

        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        fts.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fts.replace(R.id.flContainer, fragment).commit();
    }

    public void goToFriends() {
        binding.bottomNavigation.setSelectedItemId(R.id.action_friends);
    }

    public void goToWorkout() {
        binding.bottomNavigation.setSelectedItemId(R.id.action_workout);
    }

    public void goToMeals() {
        binding.bottomNavigation.setSelectedItemId(R.id.action_meals);
    }
}