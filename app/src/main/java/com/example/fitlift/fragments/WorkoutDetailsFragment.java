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
import java.util.List;

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
            binding.etReps1.setText(reps.toString());

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
                //Integer.valueOf(binding.etReps1.getText().toString());
                String title = binding.etTitle.getText().toString();
                // Add all reps;
                List<Integer> reps = addAllReps();

                try {
                    savePost(exercise, weight, reps, title);
                } catch (ParseException e) {
                    Log.e(TAG, "Error in savePost:");
                    e.printStackTrace();
                }
            }
        });

        // TODO ADD MORE EXERCISES
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
                binding.etExercise2.setVisibility(view.VISIBLE);
                binding.etWeight2.setVisibility(view.VISIBLE);
                binding.etReps11.setVisibility(view.VISIBLE);

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

        // Add more reps

        binding.etReps11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps12.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps13.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps13.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps14.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps14.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps15.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps15.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps16.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps16.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps17.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps17.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps18.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps18.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps19.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps19.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps20.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Add new exercise entry
        binding.etExercise2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etExercise3.setVisibility(view.VISIBLE);
                binding.etWeight3.setVisibility(view.VISIBLE);
                binding.etReps21.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Add more reps
        binding.etReps21.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps22.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps22.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps23.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps23.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps24.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps24.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps25.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps25.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps26.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps26.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps27.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps27.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps28.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps28.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps29.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps29.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps30.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Add new exercise entry
        binding.etExercise3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etExercise4.setVisibility(view.VISIBLE);
                binding.etWeight4.setVisibility(view.VISIBLE);
                binding.etReps31.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps31.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps32.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps32.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps33.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps33.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps34.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps34.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps35.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps35.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps36.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps36.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps37.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps37.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps38.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps38.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps39.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etReps39.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.etReps40.setVisibility(view.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private List<Integer> addAllReps() {
        
        List<Integer> reps = new ArrayList<>();
        
        if (binding.etReps1.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps1.getText().toString()));
        }

        if (binding.etReps2.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps2.getText().toString()));
        }

        if (binding.etReps3.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps3.getText().toString()));
        }

        if (binding.etReps4.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps4.getText().toString()));
        }

        if (binding.etReps5.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps5.getText().toString()));
        }

        if (binding.etReps6.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps6.getText().toString()));
        }

        if (binding.etReps7.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps7.getText().toString()));
        }

        if (binding.etReps8.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps8.getText().toString()));
        }

        if (binding.etReps9.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps9.getText().toString()));
        }

        if (binding.etReps10.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps10.getText().toString()));
        }

        if (binding.etReps11.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps11.getText().toString()));
        }

        if (binding.etReps12.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps12.getText().toString()));
        }

        if (binding.etReps13.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps13.getText().toString()));
        }

        if (binding.etReps14.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps14.getText().toString()));
        }

        if (binding.etReps15.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps15.getText().toString()));
        }

        if (binding.etReps16.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps16.getText().toString()));
        }

        if (binding.etReps17.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps17.getText().toString()));
        }

        if (binding.etReps18.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps18.getText().toString()));
        }

        if (binding.etReps19.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps19.getText().toString()));
        }

        if (binding.etReps20.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps20.getText().toString()));
        }

        if (binding.etReps21.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps21.getText().toString()));
        }

        if (binding.etReps22.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps22.getText().toString()));
        }

        if (binding.etReps23.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps23.getText().toString()));
        }

        if (binding.etReps24.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps24.getText().toString()));
        }

        if (binding.etReps25.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps25.getText().toString()));
        }

        if (binding.etReps26.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps26.getText().toString()));
        }

        if (binding.etReps27.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps27.getText().toString()));
        }

        if (binding.etReps28.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps28.getText().toString()));
        }

        if (binding.etReps29.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps29.getText().toString()));
        }

        if (binding.etReps30.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps30.getText().toString()));
        }

        if (binding.etReps31.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps31.getText().toString()));
        }

        if (binding.etReps32.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps32.getText().toString()));
        }

        if (binding.etReps33.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps33.getText().toString()));
        }

        if (binding.etReps34.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps34.getText().toString()));
        }

        if (binding.etReps35.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps35.getText().toString()));
        }

        if (binding.etReps36.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps36.getText().toString()));
        }

        if (binding.etReps37.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps37.getText().toString()));
        }

        if (binding.etReps38.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps38.getText().toString()));
        }

        if (binding.etReps39.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps39.getText().toString()));
        }

        if (binding.etReps40.getText().toString() != null ) {
            reps.add(Integer.parseInt(binding.etReps40.getText().toString()));
        }

        return reps;
    }

    private void savePost(String exercise, Integer weight, List<Integer> reps, String title) throws ParseException {
        WorkoutJournal workoutJournal = new WorkoutJournal();
        workoutJournal.setTitle(title);
        workoutJournal.setUser(ParseUser.getCurrentUser());

        Workout workout = new Workout();
        //workout.setWeight(weight);
        //workout.addRep(reps);
        workout.setExercise(exercise);

        WeightReps weightReps = new WeightReps();
        weightReps.put("weight", weight);

        weightReps.setReps(reps);

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