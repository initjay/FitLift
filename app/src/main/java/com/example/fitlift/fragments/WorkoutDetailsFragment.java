package com.example.fitlift.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitlift.R;
import com.example.fitlift.WeightReps;
import com.example.fitlift.Workout;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.databinding.FragmentWorkoutDetailsBinding;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// TODO fix back button to go back to workoutFragment

public class WorkoutDetailsFragment extends Fragment {

    public static final String TAG = "WorkoutDetailsFragment";

    private FragmentWorkoutDetailsBinding binding;

    public WorkoutDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment utilizing viewbinding
        binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            int position = bundle.getInt("Adapter position");
            String title = bundle.getString("Title");
            String date = bundle.getString("Date");

            binding.etTitle.setText(title);
            binding.tvDate.setText(date);

        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            binding.tvDate.setText(dtf.format(now));
        }

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Save button clicked");
                // grab edit text data
                String exercise = binding.etExercise.getText().toString();
                Integer weight = Integer.valueOf(binding.etWeight.getText().toString());
                Integer reps = Integer.valueOf(binding.etReps.getText().toString());
                String title = binding.etTitle.getText().toString();

                try {
                    savePost(exercise, weight, reps, title);
                } catch (ParseException e) {
                    Log.e(TAG, "Error in savePost:");
                    e.printStackTrace();
                }
            }
        });


    }

    private void savePost(String exercise, Integer weight, Integer reps, String title) throws ParseException {
        WorkoutJournal workoutJournal = new WorkoutJournal();
        workoutJournal.setTitle(title);
        workoutJournal.setUser(ParseUser.getCurrentUser());

        Workout workout = new Workout();
        //workout.setWeight(weight);
        //workout.addRep(reps);
        workout.setExercise(exercise);

        WeightReps weightReps = new WeightReps();
        weightReps.put("weight", weight);
        weightReps.add("reps", reps);

        workout.put("journal", workoutJournal);
        weightReps.put("workout", workout);


        weightReps.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "Post save was successful!!");

                binding.etTitle.setText("");
                binding.etExercise.setText("");
                binding.etReps.setText("");
                binding.etWeight.setText("");

                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new WorkoutFragment();
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}