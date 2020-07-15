package com.example.fitlift.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitlift.R;
import com.example.fitlift.Workout;
import com.example.fitlift.adapters.WorkoutAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.example.fitlift.Workout.KEY_JOURNAL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {

    public static final String TAG = "WorkoutFragment";
    private RecyclerView rvWorkouts;
    private WorkoutAdapter adapter;
    private List<Workout> workouts;

    public WorkoutFragment() { }         // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvWorkouts = view.findViewById(R.id.rvWorkouts);

        workouts = new ArrayList<>();
        adapter = new WorkoutAdapter(getContext(), workouts);

        rvWorkouts.setAdapter(adapter);

        rvWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryWorkouts();
    }

    private void queryWorkouts() {
        ParseQuery<Workout> query = ParseQuery.getQuery(Workout.class);
        // include woJournal class through pointer
        query.include(KEY_JOURNAL);
        query.findInBackground(new FindCallback<Workout>() {
            @Override
            public void done(List<Workout> wos, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts ", e);
                    return;
                }
                // iterate through workouts fetched
                for (Workout workout : wos) {
                    Log.i(TAG, "Title: " + workout.getTitle() + ", Date: " + workout.getCreatedAt().toString());
                }
                workouts.addAll(wos);
                adapter.notifyDataSetChanged();
            }
        });
    }
}