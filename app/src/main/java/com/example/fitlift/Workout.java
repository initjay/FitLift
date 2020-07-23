package com.example.fitlift;

import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Workout")
public class Workout extends ParseObject {

    public static final String TAG = "Workout class";

    public static final String POINTER_WORKOUTJOURNAL = "journal";
    public static final String KEY_EXERCISE = "exercise";
    public static final String KEY_WEIGHT_REPS = "weight_reps";
    // get workout journal that workout object points to
    //public ParseObject workoutJournal = this.fetchIfNeeded().getParseObject(POINTER_WORKOUTJOURNAL);

    public Workout() throws ParseException {
        // needed for fetchIfNeeded
    }

    public String getExercise () {
        return getString(KEY_EXERCISE);
    }

    public void setExercise (String exercise) {
        put(KEY_EXERCISE, exercise);
    }

    public List<Integer> getWeightReps () {
        return getList(KEY_WEIGHT_REPS);
    }

    public void setWeightReps (List<Integer> weightReps) {
        put(KEY_WEIGHT_REPS, weightReps);
    }

    public WorkoutJournal getWorkoutJournal () {
        return (WorkoutJournal) get(POINTER_WORKOUTJOURNAL);
    }

    public void setWorkoutJournal (WorkoutJournal workoutJournal) {
        put(POINTER_WORKOUTJOURNAL, workoutJournal);
    }

}
