package com.example.fitlift;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("WeightReps")
public class WeightReps extends ParseObject {

    public static final String TAG = "WeightReps class";

    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_REPS = "reps";
    public static final String POINTER_WORKOUT = "workout";
    // Get the workout that WeightReps points to
    private ParseObject workout = this.fetchIfNeeded().getParseObject(POINTER_WORKOUT);

    public WeightReps() throws ParseException {
        // needed for fetchIfNeeded
    }

    public int getWeight () {
        return (int) getNumber(KEY_WEIGHT);
    }

    public void setWeight (int weight) {
        put(KEY_WEIGHT, weight);
    }

    public List<Integer> getReps () {
        return getList(KEY_REPS);
    }

    public void setReps(List<Integer> reps) {
        for (int i = 0; i < reps.size(); i++) {
            this.add(KEY_REPS, reps.get(i));
        }
    }

    public ParseObject getWorkout () {
        return workout;
    }

    public void setWorkout (ParseObject workout) {
        put(POINTER_WORKOUT, workout);
    }

}
