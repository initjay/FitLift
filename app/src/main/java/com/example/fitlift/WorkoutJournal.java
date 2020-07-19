package com.example.fitlift;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("WorkoutJournal")
public class WorkoutJournal extends ParseObject {

    public static final String TAG = "Workout Journal Class";

    public static final String KEY_TITLE = "title";
    public static final String POINTER_USER = "user";

    public String getTitle () {
        return getString(KEY_TITLE);
    }

    public void setTitle (String title) {
        put(KEY_TITLE, title);
    }

    public ParseUser getUser() {
        return getParseUser(POINTER_USER);
    }

    public void setUser(ParseUser currentUser) {
        put(POINTER_USER, currentUser);
    }
}
