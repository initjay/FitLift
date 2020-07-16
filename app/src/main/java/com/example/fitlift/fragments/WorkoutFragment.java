package com.example.fitlift.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fitlift.R;
import com.example.fitlift.Workout;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.adapters.WorkoutAdapter;
import com.example.fitlift.adapters.WorkoutJournalAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static com.example.fitlift.Workout.KEY_JOURNAL;
import static com.example.fitlift.Workout.KEY_TITLE;

public class WorkoutFragment extends Fragment {

    public static final String TAG = "WorkoutFragment";
    private RecyclerView rvWorkouts;
    private WorkoutJournalAdapter adapter;
    private List<WorkoutJournal> workouts;
    private String currUser = ParseUser.getCurrentUser().getObjectId();
    private ParseUser user;
    private ImageView ivProfileImg;
    private TextView tvUserName;

    public WorkoutFragment() { }         // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = ParseUser.getCurrentUser();
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

        tvUserName.setText(user.getUsername());
        // check that user has profile img
        if (user.getParseFile("profileImg") != null) {
            Glide.with(this).load(user.getParseFile("profileImg").getUrl()).circleCrop().into(ivProfileImg);
        }

        workouts = new ArrayList<>();
        adapter = new WorkoutJournalAdapter(getContext(), workouts);

        rvWorkouts.setAdapter(adapter);

        rvWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryWorkouts();
    }

    // Todo: store query responses locally as well for quicker access, wipe local storage when user signs out?
    private void queryWorkouts() {

        ParseQuery<WorkoutJournal> query = ParseQuery.getQuery(WorkoutJournal.class);
        // Only pull workout journals belonging to the current signed in user
        query.whereContains("user", currUser);
        // include woJournal class through pointer
        // query.include(KEY_JOURNAL);
        query.findInBackground(new FindCallback<WorkoutJournal>() {
            @Override
            public void done(List<WorkoutJournal> workoutJournals, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts ", e);
                    return;
                }
                // iterate through workouts fetched
                for (WorkoutJournal workoutJournal : workoutJournals) {
                    Log.i(TAG, "Title: " + workoutJournal.getTitle() + ", Date: " + workoutJournal.getCreatedAt().toString());
                }
                workouts.addAll(workoutJournals);
                adapter.notifyDataSetChanged();
            }
        });
    }
}