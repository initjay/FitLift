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
import android.widget.Toast;

import com.example.fitlift.MealJournal;
import com.example.fitlift.R;
import com.example.fitlift.WeightReps;
import com.example.fitlift.Workout;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.databinding.FragmentWorkoutDetailsBinding;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO fix back button to go back to workoutFragment

public class WorkoutDetailsFragment extends Fragment {

    public static final String TAG = "WorkoutDetailsFragment";

    private FragmentWorkoutDetailsBinding binding;
    private ParseUser currUser = ParseUser.getCurrentUser();
    private boolean workoutUpdate = false;
    private Workout updateWorkout;
    private WorkoutJournal updateJournal;
    //private List<EditText> views;

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

        // Add all edit texts here
        //views = Arrays.asList(binding.etExercise1);

        // Populate views with data if workout object already exists (Selected from timeline)
        if (bundle != null) {

            workoutUpdate = true;
            final String workoutId = bundle.getString("workoutId");
            String workoutJournalId = bundle.getString("workoutJournalId");

            ParseQuery<WorkoutJournal> workoutJournalQuery = ParseQuery.getQuery(WorkoutJournal.class);
            workoutJournalQuery.getInBackground(workoutJournalId, new GetCallback<WorkoutJournal>() {
                @Override
                public void done(WorkoutJournal object, ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while fetching existing workout journal", e);
                    }

                    updateJournal = object;

                    binding.etTitle.setText(object.getTitle());
                    binding.tvDate.setText(object.getCreatedAt().toString());
                }
            });

            ParseQuery<Workout> query = ParseQuery.getQuery(Workout.class);
            query.getInBackground(workoutId, new GetCallback<Workout>() {
                @Override
                public void done(Workout object, ParseException e) {
                    if (e != null) {
                        Log.e(TAG, "Error while fetching existing workout", e);
                    }

                    updateWorkout = object;

                    List<List<Integer>> weightReps = object.getWeightReps();

                    if (object.getExercises().size() > 0) {
                        binding.etExercise1.setText(object.getExercises().get(0));

                        // Must use this comparison since weightReps will be initialized with empty lists
                        if (!weightReps.get(0).isEmpty()) {
                            binding.etWeight1.setText(weightReps.get(0).get(0).toString());

                            if (weightReps.get(0).size() > 1) {
                                binding.etReps1.setText(weightReps.get(0).get(1).toString());
                                if (weightReps.get(0).size() > 2) {
                                    binding.etReps2.setText(weightReps.get(0).get(2).toString());
                                    if (weightReps.get(0).size() > 3) {
                                        binding.etReps3.setText(weightReps.get(0).get(3).toString());
                                    }
                                }
                            }
                        }

                        if (!weightReps.get(1).isEmpty()) {
                            binding.etWeight2.setText(weightReps.get(1).get(0).toString());

                            if (weightReps.get(1).size() > 1) {
                                binding.etReps4.setText(weightReps.get(1).get(1).toString());
                                if (weightReps.get(1).size() > 2) {
                                    binding.etReps5.setText(weightReps.get(1).get(2).toString());
                                    if (weightReps.get(1).size() > 3) {
                                        binding.etReps6.setText(weightReps.get(1).get(3).toString());
                                    }
                                }
                            }
                        }

                        if (!weightReps.get(2).isEmpty()) {
                            binding.etWeight3.setText(weightReps.get(2).get(0).toString());

                            if (weightReps.get(2).size() > 1) {
                                binding.etReps7.setText(weightReps.get(2).get(1).toString());
                                if (weightReps.get(2).size() > 2) {
                                    binding.etReps8.setText(weightReps.get(2).get(2).toString());
                                    if (weightReps.get(2).size() > 3) {
                                        binding.etReps9.setText(weightReps.get(2).get(3).toString());
                                    }
                                }
                            }
                        }
                    }

                    if (object.getExercises().size() > 1) {
                        binding.etExercise2.setText(object.getExercises().get(1));

                        if (!weightReps.get(3).isEmpty()) {
                            binding.etWeight4.setText(weightReps.get(3).get(0).toString());

                            if (weightReps.get(3).size() > 1) {
                                binding.etReps10.setText(weightReps.get(3).get(1).toString());
                                if (weightReps.get(3).size() > 2) {
                                    binding.etReps11.setText(weightReps.get(3).get(2).toString());
                                    if (weightReps.get(3).size() > 3) {
                                        binding.etReps12.setText(weightReps.get(3).get(3).toString());
                                    }
                                }
                            }
                        }

                        if (!weightReps.get(4).isEmpty()) {
                            binding.etWeight5.setText(weightReps.get(4).get(0).toString());

                            if (weightReps.get(4).size() > 1) {
                                binding.etReps13.setText(weightReps.get(4).get(1).toString());
                                if (weightReps.get(4).size() > 2) {
                                    binding.etReps14.setText(weightReps.get(4).get(2).toString());
                                    if (weightReps.get(4).size() > 3) {
                                        binding.etReps15.setText(weightReps.get(4).get(3).toString());
                                    }
                                }
                            }
                        }

                        if (!weightReps.get(5).isEmpty()) {
                            binding.etWeight6.setText(weightReps.get(5).get(0).toString());

                            if (weightReps.get(5).size() > 1) {
                                binding.etReps16.setText(weightReps.get(5).get(1).toString());
                                if (weightReps.get(5).size() > 2) {
                                    binding.etReps17.setText(weightReps.get(5).get(2).toString());
                                    if (weightReps.get(5).size() > 3) {
                                        binding.etReps18.setText(weightReps.get(5).get(3).toString());
                                    }
                                }
                            }
                        }
                    }

                    if (object.getExercises().size() > 2) {
                        binding.etExercise3.setText(object.getExercises().get(2));

                        if (!weightReps.get(6).isEmpty()) {
                            binding.etWeight7.setText(weightReps.get(6).get(0).toString());

                            if (weightReps.get(6).size() > 1) {
                                binding.etReps19.setText(weightReps.get(6).get(1).toString());
                                if (weightReps.get(6).size() > 2) {
                                    binding.etReps20.setText(weightReps.get(6).get(2).toString());
                                    if (weightReps.get(6).size() > 3) {
                                        binding.etReps21.setText(weightReps.get(6).get(3).toString());
                                    }
                                }
                            }
                        }

                        if (!weightReps.get(7).isEmpty()) {
                            binding.etWeight8.setText(weightReps.get(7).get(0).toString());

                            if (weightReps.get(7).size() > 1) {
                                binding.etReps22.setText(weightReps.get(7).get(1).toString());
                                if (weightReps.get(7).size() > 2) {
                                    binding.etReps23.setText(weightReps.get(7).get(2).toString());
                                    if (weightReps.get(7).size() > 3) {
                                        binding.etReps24.setText(weightReps.get(7).get(3).toString());
                                    }
                                }
                            }
                        }

                        if (!weightReps.get(8).isEmpty()) {
                            binding.etWeight9.setText(weightReps.get(8).get(0).toString());

                            if (weightReps.get(8).size() > 1) {
                                binding.etReps25.setText(weightReps.get(8).get(1).toString());
                                if (weightReps.get(8).size() > 2) {
                                    binding.etReps26.setText(weightReps.get(8).get(2).toString());
                                    if (weightReps.get(8).size() > 3) {
                                        binding.etReps27.setText(weightReps.get(8).get(3).toString());
                                    }
                                }
                            }
                        }
                    }

                    if (object.getExercises().size() > 3) {
                        binding.etExercise4.setText(object.getExercises().get(3));

                        if (!weightReps.get(9).isEmpty()) {
                            binding.etWeight10.setText(weightReps.get(9).get(0).toString());

                            if (weightReps.get(9).size() > 1) {
                                binding.etReps28.setText(weightReps.get(9).get(1).toString());
                                if (weightReps.get(9).size() > 2) {
                                    binding.etReps29.setText(weightReps.get(9).get(2).toString());
                                    if (weightReps.get(9).size() > 3) {
                                        binding.etReps30.setText(weightReps.get(9).get(3).toString());
                                    }
                                }
                            }
                        }

                        if (!weightReps.get(10).isEmpty()) {
                            binding.etWeight11.setText(weightReps.get(10).get(0).toString());

                            if (weightReps.get(10).size() > 1) {
                                binding.etReps31.setText(weightReps.get(10).get(1).toString());
                                if (weightReps.get(10).size() > 2) {
                                    binding.etReps32.setText(weightReps.get(10).get(2).toString());
                                    if (weightReps.get(10).size() > 3) {
                                        binding.etReps33.setText(weightReps.get(10).get(3).toString());
                                    }
                                }
                            }
                        }

                        if (!weightReps.get(11).isEmpty()) {
                            binding.etWeight12.setText(weightReps.get(11).get(0).toString());

                            if (weightReps.get(11).size() > 1) {
                                binding.etReps34.setText(weightReps.get(11).get(1).toString());
                                if (weightReps.get(11).size() > 2) {
                                    binding.etReps35.setText(weightReps.get(11).get(2).toString());
                                    if (weightReps.get(11).size() > 3) {
                                        binding.etReps36.setText(weightReps.get(11).get(3).toString());
                                    }
                                }
                            }
                        }
                    }
                }
            });

        } else { // New workout entry pre-populate with date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            binding.tvDate.setText(dtf.format(now));
        }

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Save button clicked");

                WorkoutJournal workoutJournal = null;

                String title = binding.etTitle.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(getContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO CHECK THAT DATE IS BEING PROPERLY SAVED
                List<String> newExercises = new ArrayList<>();
                List<List<Integer>> newWeightReps = new ArrayList<>();

                List<Integer> newReps1 = new ArrayList<>();

                if (!binding.etExercise1.getText().toString().isEmpty()) {
                    newExercises.add(binding.etExercise1.getText().toString());
                }

                if (!binding.etWeight1.getText().toString().isEmpty()) {
                    newReps1.add(Integer.valueOf(binding.etWeight1.getText().toString()));
                }

                if (!binding.etReps1.getText().toString().isEmpty()) {
                    newReps1.add(Integer.valueOf(binding.etReps1.getText().toString()));
                }

                if (!binding.etReps2.getText().toString().isEmpty()) {
                    newReps1.add(Integer.valueOf(binding.etReps2.getText().toString()));
                }

                if (!binding.etReps3.getText().toString().isEmpty()) {
                    newReps1.add(Integer.valueOf(binding.etReps3.getText().toString()));
                }

                newWeightReps.add(newReps1);
                List<Integer> newReps2 = new ArrayList<>();


                if (!binding.etWeight2.getText().toString().isEmpty()) {
                    newReps2.add(Integer.valueOf(binding.etWeight2.getText().toString()));
                }

                if (!binding.etReps4.getText().toString().isEmpty()) {
                    newReps2.add(Integer.valueOf(binding.etReps4.getText().toString()));
                }

                if (!binding.etReps5.getText().toString().isEmpty()) {
                    newReps2.add(Integer.valueOf(binding.etReps5.getText().toString()));
                }

                if (!binding.etReps6.getText().toString().isEmpty()) {
                    newReps2.add(Integer.valueOf(binding.etReps6.getText().toString()));
                }

                newWeightReps.add(newReps2);
                List<Integer> newReps3 = new ArrayList<>();

                if (!binding.etWeight3.getText().toString().isEmpty()) {
                    newReps3.add(Integer.valueOf(binding.etWeight3.getText().toString()));
                }

                if (!binding.etReps7.getText().toString().isEmpty()) {
                    newReps3.add(Integer.valueOf(binding.etReps7.getText().toString()));
                }

                if (!binding.etReps8.getText().toString().isEmpty()) {
                    newReps3.add(Integer.valueOf(binding.etReps8.getText().toString()));
                }

                if (!binding.etReps9.getText().toString().isEmpty()) {
                    newReps3.add(Integer.valueOf(binding.etReps9.getText().toString()));
                }

                newWeightReps.add(newReps3);
                List<Integer> newReps4 = new ArrayList<>();

                if (!binding.etExercise2.getText().toString().isEmpty()) {
                    newExercises.add(binding.etExercise2.getText().toString());
                }

                if (!binding.etWeight4.getText().toString().isEmpty()) {
                    newReps4.add(Integer.valueOf(binding.etWeight4.getText().toString()));
                }

                if (!binding.etReps10.getText().toString().isEmpty()) {
                    newReps4.add(Integer.valueOf(binding.etReps10.getText().toString()));
                }

                if (!binding.etReps11.getText().toString().isEmpty()) {
                    newReps4.add(Integer.valueOf(binding.etReps11.getText().toString()));
                }

                if (!binding.etReps12.getText().toString().isEmpty()) {
                    newReps4.add(Integer.valueOf(binding.etReps12.getText().toString()));
                }

                newWeightReps.add(newReps4);
                List<Integer> newReps5 = new ArrayList<>();

                if (!binding.etWeight5.getText().toString().isEmpty()) {
                    newReps5.add(Integer.valueOf(binding.etWeight5.getText().toString()));
                }

                if (!binding.etReps13.getText().toString().isEmpty()) {
                    newReps5.add(Integer.valueOf(binding.etReps13.getText().toString()));
                }

                if (!binding.etReps14.getText().toString().isEmpty()) {
                    newReps5.add(Integer.valueOf(binding.etReps14.getText().toString()));
                }

                if (!binding.etReps15.getText().toString().isEmpty()) {
                    newReps5.add(Integer.valueOf(binding.etReps15.getText().toString()));
                }

                newWeightReps.add(newReps5);
                List<Integer> newReps6 = new ArrayList<>();

                if (!binding.etWeight6.getText().toString().isEmpty()) {
                    newReps6.add(Integer.valueOf(binding.etWeight6.getText().toString()));
                }

                if (!binding.etReps16.getText().toString().isEmpty()) {
                    newReps6.add(Integer.valueOf(binding.etReps16.getText().toString()));
                }

                if (!binding.etReps17.getText().toString().isEmpty()) {
                    newReps6.add(Integer.valueOf(binding.etReps17.getText().toString()));
                }

                if (!binding.etReps18.getText().toString().isEmpty()) {
                    newReps6.add(Integer.valueOf(binding.etReps18.getText().toString()));
                }

                newWeightReps.add(newReps6);
                List<Integer> newReps7 = new ArrayList<>();

                if (!binding.etExercise3.getText().toString().isEmpty()) {
                    newExercises.add(binding.etExercise3.getText().toString());
                }

                if (!binding.etWeight7.getText().toString().isEmpty()) {
                    newReps7.add(Integer.valueOf(binding.etWeight7.getText().toString()));
                }

                if (!binding.etReps19.getText().toString().isEmpty()) {
                    newReps7.add(Integer.valueOf(binding.etReps19.getText().toString()));
                }

                if (!binding.etReps20.getText().toString().isEmpty()) {
                    newReps7.add(Integer.valueOf(binding.etReps20.getText().toString()));
                }

                if (!binding.etReps21.getText().toString().isEmpty()) {
                    newReps7.add(Integer.valueOf(binding.etReps21.getText().toString()));
                }

                newWeightReps.add(newReps7);
                List<Integer> newReps8 = new ArrayList<>();

                if (!binding.etWeight8.getText().toString().isEmpty()) {
                    newReps8.add(Integer.valueOf(binding.etWeight8.getText().toString()));
                }

                if (!binding.etReps22.getText().toString().isEmpty()) {
                    newReps8.add(Integer.valueOf(binding.etReps22.getText().toString()));
                }

                if (!binding.etReps23.getText().toString().isEmpty()) {
                    newReps8.add(Integer.valueOf(binding.etReps23.getText().toString()));
                }

                if (!binding.etReps24.getText().toString().isEmpty()) {
                    newReps8.add(Integer.valueOf(binding.etReps24.getText().toString()));
                }

                newWeightReps.add(newReps8);
                List<Integer> newReps9 = new ArrayList<>();

                if (!binding.etWeight9.getText().toString().isEmpty()) {
                    newReps9.add(Integer.valueOf(binding.etWeight9.getText().toString()));
                }

                if (!binding.etReps25.getText().toString().isEmpty()) {
                    newReps9.add(Integer.valueOf(binding.etReps25.getText().toString()));
                }

                if (!binding.etReps26.getText().toString().isEmpty()) {
                    newReps9.add(Integer.valueOf(binding.etReps26.getText().toString()));
                }

                if (!binding.etReps27.getText().toString().isEmpty()) {
                    newReps9.add(Integer.valueOf(binding.etReps27.getText().toString()));
                }

                newWeightReps.add(newReps9);
                List<Integer> newReps10 = new ArrayList<>();

                if (!binding.etExercise4.getText().toString().isEmpty()) {
                    newExercises.add(binding.etExercise4.getText().toString());
                }

                if (!binding.etWeight10.getText().toString().isEmpty()) {
                    newReps10.add(Integer.valueOf(binding.etWeight10.getText().toString()));
                }

                if (!binding.etReps28.getText().toString().isEmpty()) {
                    newReps10.add(Integer.valueOf(binding.etReps28.getText().toString()));
                }

                if (!binding.etReps29.getText().toString().isEmpty()) {
                    newReps10.add(Integer.valueOf(binding.etReps29.getText().toString()));
                }

                if (!binding.etReps30.getText().toString().isEmpty()) {
                    newReps10.add(Integer.valueOf(binding.etReps30.getText().toString()));
                }

                newWeightReps.add(newReps10);
                List<Integer> newReps11 = new ArrayList<>();

                if (!binding.etWeight11.getText().toString().isEmpty()) {
                    newReps11.add(Integer.valueOf(binding.etWeight11.getText().toString()));
                }

                if (!binding.etReps31.getText().toString().isEmpty()) {
                    newReps11.add(Integer.valueOf(binding.etReps31.getText().toString()));
                }

                if (!binding.etReps32.getText().toString().isEmpty()) {
                    newReps11.add(Integer.valueOf(binding.etReps32.getText().toString()));
                }

                if (!binding.etReps33.getText().toString().isEmpty()) {
                    newReps11.add(Integer.valueOf(binding.etReps33.getText().toString()));
                }

                newWeightReps.add(newReps11);
                List<Integer> newReps12 = new ArrayList<>();

                if (!binding.etWeight12.getText().toString().isEmpty()) {
                    newReps12.add(Integer.valueOf(binding.etWeight12.getText().toString()));
                }

                if (!binding.etReps34.getText().toString().isEmpty()) {
                    newReps12.add(Integer.valueOf(binding.etReps34.getText().toString()));
                }

                if (!binding.etReps35.getText().toString().isEmpty()) {
                    newReps12.add(Integer.valueOf(binding.etReps35.getText().toString()));
                }

                if (!binding.etReps36.getText().toString().isEmpty()) {
                    newReps12.add(Integer.valueOf(binding.etReps36.getText().toString()));
                }

                newWeightReps.add(newReps12);

                if (workoutUpdate) {
                    saveWorkoutUpdate(title, newExercises, newWeightReps, updateWorkout, updateJournal);
                } else {
                    saveWorkout(title, newExercises,newWeightReps);
                }

            }
        });

//        // TODO ADD MORE EXERCISES
//        // Add more reps
//        binding.etReps1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps2.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps3.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps4.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps5.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps6.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps6.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps7.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps7.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps8.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps8.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps9.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps9.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps10.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        // Add new exercise entry
//        binding.etExercise1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etExercise2.setVisibility(view.VISIBLE);
//                binding.etWeight2.setVisibility(view.VISIBLE);
//                binding.etReps11.setVisibility(view.VISIBLE);
//
//                // TODO: Add views dynamically
////                LayoutInflater layoutInflater = getLayoutInflater().from(getContext());
////                //RelativeLayout workoutEntry = (RelativeLayout) binding.rlExerciseEntry;
////                RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(R.layout.fragment_workout_details, null, false);
////                RelativeLayout relativeLayout = binding.rlExerciseEntry;
////
////                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
////                params.addRule(RelativeLayout.BELOW, binding.rlExerciseEntry.getId());
////
////                relativeLayout.setLayoutParams(params);
////                relativeLayout.addView(layout);
//
////                RelativeLayout relativeLayout = new RelativeLayout(getContext());
////                EditText editText = new EditText(getContext());
////                editText.setLayoutParams();
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        // Add more reps
//
//        binding.etReps11.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps12.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps12.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps13.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps13.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps14.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps14.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps15.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps15.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps16.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps16.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps17.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps17.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps18.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps18.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps19.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps19.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps20.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        // Add new exercise entry
//        binding.etExercise2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etExercise3.setVisibility(view.VISIBLE);
//                binding.etWeight3.setVisibility(view.VISIBLE);
//                binding.etReps21.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        // Add more reps
//        binding.etReps21.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps22.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps22.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps23.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps23.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps24.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps24.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps25.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps25.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps26.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps26.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps27.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps27.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps28.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps28.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps29.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps29.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps30.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        // Add new exercise entry
//        binding.etExercise3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etExercise4.setVisibility(view.VISIBLE);
//                binding.etWeight4.setVisibility(view.VISIBLE);
//                binding.etReps31.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps31.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps32.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps32.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps33.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps33.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps34.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps34.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps35.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps35.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps36.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps36.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps37.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps37.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps38.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps38.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps39.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        binding.etReps39.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                binding.etReps40.setVisibility(view.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//    }

//        private List<Integer> addAllReps1 () {
//            List<Integer> reps = new ArrayList<>();
//
//            if (!binding.etReps1.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps1.getText().toString()));
//            }
//
//            if (!binding.etReps2.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps2.getText().toString()));
//            }
//
//            if (!binding.etReps3.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps3.getText().toString()));
//            }
//
//            if (!binding.etReps4.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps4.getText().toString()));
//            }
//
//            if (!binding.etReps5.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps5.getText().toString()));
//            }
//
//            if (!binding.etReps6.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps6.getText().toString()));
//            }
//
//            if (!binding.etReps7.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps7.getText().toString()));
//            }
//
//            if (!binding.etReps8.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps8.getText().toString()));
//            }
//
//            if (!binding.etReps9.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps9.getText().toString()));
//            }
//
//            if (!binding.etReps10.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps10.getText().toString()));
//            }
//            return reps;
//        }
//
//        private List<Integer> addAllReps2 () {
//            List<Integer> reps = new ArrayList<>();
//
//            if (!binding.etReps11.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps11.getText().toString()));
//            }
//
//            if (!binding.etReps12.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps12.getText().toString()));
//            }
//
//            if (!binding.etReps13.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps13.getText().toString()));
//            }
//
//            if (!binding.etReps14.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps14.getText().toString()));
//            }
//
//            if (!binding.etReps15.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps15.getText().toString()));
//            }
//
//            if (!binding.etReps16.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps16.getText().toString()));
//            }
//
//            if (!binding.etReps17.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps17.getText().toString()));
//            }
//
//            if (!binding.etReps18.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps18.getText().toString()));
//            }
//
//            if (!binding.etReps19.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps19.getText().toString()));
//            }
//
//            if (!binding.etReps20.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps20.getText().toString()));
//            }
//
//            return reps;
//        }
//
//        private List<Integer> addAllReps3 () {
//            List<Integer> reps = new ArrayList<>();
//
//            if (!binding.etReps21.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps21.getText().toString()));
//            }
//
//            if (!binding.etReps22.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps22.getText().toString()));
//            }
//
//            if (!binding.etReps23.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps23.getText().toString()));
//            }
//
//            if (!binding.etReps24.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps24.getText().toString()));
//            }
//
//            if (!binding.etReps25.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps25.getText().toString()));
//            }
//
//            if (!binding.etReps26.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps26.getText().toString()));
//            }
//
//            if (!binding.etReps27.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps27.getText().toString()));
//            }
//
//            if (!binding.etReps28.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps28.getText().toString()));
//            }
//
//            if (!binding.etReps29.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps29.getText().toString()));
//            }
//
//            if (!binding.etReps30.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps30.getText().toString()));
//            }
//
//            return reps;
//        }
//
//        private List<Integer> addAllReps4 () {
//
//            List<Integer> reps = new ArrayList<>();
//
//            if (!binding.etReps31.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps31.getText().toString()));
//            }
//
//            if (!binding.etReps32.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps32.getText().toString()));
//            }
//
//            if (!binding.etReps33.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps33.getText().toString()));
//            }
//
//            if (!binding.etReps34.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps34.getText().toString()));
//            }
//
//            if (!binding.etReps35.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps35.getText().toString()));
//            }
//
//            if (!binding.etReps36.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps36.getText().toString()));
//            }
//
//            if (!binding.etReps37.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps37.getText().toString()));
//            }
//
//            if (!binding.etReps38.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps38.getText().toString()));
//            }
//
//            if (!binding.etReps39.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps39.getText().toString()));
//            }
//
//            if (!binding.etReps40.getText().toString().isEmpty()) {
//                reps.add(Integer.parseInt(binding.etReps40.getText().toString()));
//            }
//
//            return reps;
//        }


    }

    private void saveWorkout(String title, List<String> newExercises, List<List<Integer>> newWeightReps) {
        WorkoutJournal workoutJournal = new WorkoutJournal();
        Workout workout = new Workout();

        String newWorkoutId;
        String newWorkoutJournalId;

        workout.setExercises(newExercises);
        workout.setWeightReps(newWeightReps);

        workout.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving new workout", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "New workout save was successful");
            }
        });

        workoutJournal.setTitle(title);
        workoutJournal.setWorkout(workout);
        workoutJournal.setUser(currUser);

        workoutJournal.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving new workout journal", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "New workout journal save was successful");

                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new WorkoutFragment();
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            }
        });

    }

    private void saveWorkoutUpdate(String title, List<String> newExercises, List<List<Integer>> newWeightReps, Workout updateWorkout, WorkoutJournal updateJournal) {
        updateJournal.setTitle(title);

        updateJournal.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving workout journal update", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "Journal Update save was successful");
            }
        });

        updateWorkout.setExercises(newExercises);
        updateWorkout.setWeightReps(newWeightReps);

        updateWorkout.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving workout update", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "Workout Update save was successful");

                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new WorkoutFragment();
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            }
        });
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        binding = null;
    }
}