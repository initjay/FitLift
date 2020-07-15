package com.example.fitlift;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("workout")
public class Workout extends ParseObject {
    // getter functions to get columns of post table
    // in this case weight, reps, and woJournal
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_REPS = "reps";
    public static final String KEY_JOURNAL = "woJournal";
    public static final String KEY_TITLE = "title";
    // get workout journal that workout object points to
    // private ParseObject object = new ParseObject(KEY_JOURNAL).fetchIfNeeded();

    public Workout() throws ParseException {
    }

    public int getWeight () {
        return (int) getNumber(KEY_WEIGHT);
    }

    public void setWeight(int weight) {
        put(KEY_WEIGHT, weight);
    }

    public List<Integer> getReps () {
        return getList(KEY_REPS);
    }

    public void addRep(int reps) {
        Integer rep = reps;
        this.add(KEY_REPS, rep);
    }

    public String getTitle () {
        return "Testing";//object.getString(KEY_TITLE);
    }

//    public void setTitle (String title) {
//        object.put(KEY_TITLE, title);
//    }

//    public ParseObject getWoJournal () {
//        return object;
//    }

}