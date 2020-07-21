package com.example.fitlift.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitlift.R;
import com.example.fitlift.WeightReps;
import com.example.fitlift.Workout;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.activities.LoginActivity;
import com.example.fitlift.activities.MainActivity;
import com.example.fitlift.adapters.WeightRepsAdapter;
import com.example.fitlift.adapters.WorkoutAdapter;
import com.example.fitlift.adapters.WorkoutJournalAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class WorkoutFragment extends Fragment {

    public static final String TAG = "WorkoutFragment";
    private RecyclerView rvWorkouts;
    private WeightRepsAdapter adapter;
    private List<WeightReps> workoutJournals;
    private String currUser = ParseUser.getCurrentUser().getObjectId();
    private ParseUser user;
    private ImageView ivProfileImg;
    private TextView tvUserName;
    private MainActivity activity;
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
                return false;
            }
        });

        tvUserName.setText(user.getUsername());
        // check that user has profile img
        if (user.getParseFile("profileImg") != null) {
            Glide.with(this).load(user.getParseFile("profileImg").getUrl()).circleCrop().into(ivProfileImg);
        }

        workoutJournals = new ArrayList<>();
        adapter = new WeightRepsAdapter(getContext(), workoutJournals);

        rvWorkouts.setAdapter(adapter);

        rvWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryWorkouts();
    }

    // Todo: store query responses locally as well for quicker access, wipe local storage when user signs out?
    private void queryWorkouts() {

        ParseQuery<WeightReps> query = ParseQuery.getQuery(WeightReps.class);
        // Only pull workout journals belonging to the current signed in user
        query.include("workout.journal");
        //ParseQuery<ParseUser> userParseQuery = ParseUser.getQuery();
        //userParseQuery.whereMatchesKeyInQuery("objectId", "user", query);
        query.whereContains("user", currUser);
        query.setLimit(20);
        query.addDescendingOrder(WorkoutJournal.KEY_CREATED_AT);
        // include woJournal class through pointer
        query.findInBackground(new FindCallback<WeightReps>() {
            @Override
            public void done(List<WeightReps> weightReps, ParseException e) {
                List<WeightReps> tempWoJournals = new ArrayList<>();

                if (e != null) {
                    Log.e(TAG, "Issue with getting posts ", e);
                    return;
                }
                // iterate through workouts fetched
                for (WeightReps weightRep : weightReps) {
                    Workout workout = (Workout) weightRep.getWorkout();
                    WorkoutJournal workoutJournal = null;
                    try {
                        workoutJournal = (WorkoutJournal) workout.getWorkoutJournal().fetchIfNeeded();
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                    tempWoJournals.add(weightRep);

                    Log.i(TAG, "Title: " + workoutJournal.getTitle() + ", Date: " + workoutJournal.getCreatedAt().toString());
                }
                workoutJournals.addAll(tempWoJournals);
                adapter.notifyDataSetChanged();
            }
        });
    }
}