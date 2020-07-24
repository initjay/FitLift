package com.example.fitlift;

import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Workout")
public class Workout extends ParseObject {

    public static final String TAG = "Workout class";

    public static final String KEY_EXERCISE = "exercise";
    public static final String KEY_WEIGHT_REPS = "weight_reps";
    // get workout journal that workout object points to
    //public ParseObject workoutJournal = this.fetchIfNeeded().getParseObject(POINTER_WORKOUTJOURNAL);

    public Workout() {
        // needed for fetchIfNeeded
    }

    public List<String> getExercises () {
        return getList(KEY_EXERCISE);
    }

    public void setExercises (List<String> exercises) {
        put(KEY_EXERCISE, exercises);
    }

    public List<List<Integer>> getWeightReps () {
        return getList(KEY_WEIGHT_REPS);
    }

    public void setWeightReps (List<List<Integer>> weightReps) {
        put(KEY_WEIGHT_REPS, weightReps);
    }

}
