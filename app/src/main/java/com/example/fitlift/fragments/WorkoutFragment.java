package com.example.fitlift.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.example.fitlift.OnSwipeTouchListener;
import com.example.fitlift.R;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.activities.LoginActivity;
import com.example.fitlift.activities.MainActivity;
import com.example.fitlift.adapters.WorkoutJournalAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class WorkoutFragment extends Fragment {

    public static final String TAG = "WorkoutFragment";
    private RecyclerView rvWorkouts;
    private WorkoutJournalAdapter adapter;
    private List<WorkoutJournal> workoutJournals;
    private String currUser = ParseUser.getCurrentUser().getObjectId();
    private ParseUser user;
    private ImageView ivProfileImg;
    private TextView tvUserName;
    private MainActivity activity;
    private RecyclerViewSkeletonScreen skeletonScreen;
    //private FragmentManager fragmentManager = getFragmentManager();

    public WorkoutFragment() { }         // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = ParseUser.getCurrentUser();

        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    // TODO add viewbinding library implementation
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvWorkouts = view.findViewById(R.id.rvWorkouts);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ivProfileImg = view.findViewById(R.id.ivProfileImg);
        tvUserName = view.findViewById(R.id.tvUserName);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add_workout:
                        activity.goToDetails();
                        break;
                    case R.id.action_logout:
                    default:
                        ParseUser.logOut();
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                }
                // handles inserting the fragment to the container in main activity
                //transaction.replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        tvUserName.setText(user.getUsername());
        // check that user has profile img
        if (user.getParseFile("profileImg") != null) {
            Glide.with(this).load(user.getParseFile("profileImg").getUrl()).circleCrop().into(ivProfileImg);
        }

        workoutJournals = new ArrayList<>();
        adapter = new WorkoutJournalAdapter(getContext(), workoutJournals);

        rvWorkouts.setAdapter(adapter);

        rvWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));

        skeletonScreen = Skeleton.bind(rvWorkouts).adapter(adapter).load(R.layout.item_workout_journal).show();

        queryWorkouts();

        // TODO: Fix swipelistener to detect swipe anywhere on screen
        rvWorkouts.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            @Override
            public void onSwipeDown() {
            }

            @Override
            public void onSwipeLeft() {
                activity.goToMeals();
            }

            @Override
            public void onSwipeUp() {
            }

            @Override
            public void onSwipeRight() {
                activity.goToFriends();
            }
        });
    }

    // Todo: store query responses locally as well for quicker access, wipe local storage when user signs out?
    private void queryWorkouts() {

        ParseQuery<WorkoutJournal> query = ParseQuery.getQuery(WorkoutJournal.class);
        query.whereContains("user", currUser);
        query.setLimit(20);
        query.addDescendingOrder(WorkoutJournal.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<WorkoutJournal>() {
            @Override
            public void done(List<WorkoutJournal> journals, ParseException e) {

                if (e != null) {
                    Log.e(TAG, "Issue with getting workouts ", e);
                    return;
                }

                workoutJournals.addAll(journals);
                adapter.notifyDataSetChanged();
                skeletonScreen.hide();
            }
        });
    }
}