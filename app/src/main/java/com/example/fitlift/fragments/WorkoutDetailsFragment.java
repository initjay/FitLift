package com.example.fitlift.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
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
import java.util.ArrayList;

import static com.example.fitlift.R.id.etReps2;
import static com.example.fitlift.R.id.rl_exercise_entry;
import static com.example.fitlift.R.id.visible;

// TODO fix back button to go back to workoutFragment

public class WorkoutDetailsFragment extends Fragment {

    public static final String TAG = "WorkoutDetailsFragment";

    private FragmentWorkoutDetailsBinding binding;
    private ParseUser currUser = ParseUser.getCurrentUser();

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
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null) {
            int position = bundle.getInt("Adapter position");
            String title = bundle.getString("Title");
            String date = bundle.getString("Date");
            String exercise = bundle.getString("Exercise");
            Integer weight = bundle.getInt("Weight");
            ArrayList<Integer> reps = bundle.getIntegerArrayList("Reps");

            binding.etTitle.setText(title);
            binding.tvDate.setText(date);
            binding.etExercise1.setText(exercise);
            // TODO: WILL HAVE TO UPDATE TO ARRAY OF EXERCISES, WEIGHTS, AND REPS
            binding.etWeight1.setText(weight.toString());
            binding.etReps1.setText(reps.get(0).toString());

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
                String exercise = binding.etExercise1.getText().toString();
                Integer weight = Integer.valueOf(binding.etWeight1.getText().toString());
                Integer reps = Integer.valueOf(binding.etReps1.getText().toString());
                String title = binding.etTitle.getText().toString();

                try {
                    savePost(exercise, weight, reps, title);
                } catch (ParseException e) {
                    Log.e(TAG, "Error in savePost:");
                    e.printStackTrace();
                }
            }
        });

        // Add more reps
        binding.etReps1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps2.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps3.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps4.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps5.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps6.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps7.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps8.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps9.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps10.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Add new exercise entry
        binding.etExercise1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // TODO: Add views dynamically
//                LayoutInflater layoutInflater = getLayoutInflater().from(getContext());
//                //RelativeLayout workoutEntry = (RelativeLayout) binding.rlExerciseEntry;
//                RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(R.layout.fragment_workout_details, null, false);
//                RelativeLayout relativeLayout = binding.rlExerciseEntry;
//
//                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//                params.addRule(RelativeLayout.BELOW, binding.rlExerciseEntry.getId());
//
//                relativeLayout.setLayoutParams(params);
//                relativeLayout.addView(layout);

//                RelativeLayout relativeLayout = new RelativeLayout(getContext());
//                EditText editText = new EditText(getContext());
//                editText.setLayoutParams();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
        weightReps.put("user", currUser);


        weightReps.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "Post save was successful!!");

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