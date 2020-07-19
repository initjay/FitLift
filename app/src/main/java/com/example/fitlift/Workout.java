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
    // get workout journal that workout object points to
    public ParseObject workoutJournal = this.fetchIfNeeded().getParseObject(POINTER_WORKOUTJOURNAL);

    public Workout() throws ParseException {
        // needed for fetchIfNeeded
    }

    public String getExercise () {
        return getString(KEY_EXERCISE);
    }

    public void setExercise (String exercise) {
        put(KEY_EXERCISE, exercise);
    }

    public ParseObject getWorkoutJournal () {
        return workoutJournal;
    }

    public void setWorkoutJournal (ParseObject workoutJournal) {
        put(POINTER_WORKOUTJOURNAL, workoutJournal);
    }

}
