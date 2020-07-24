package com.example.fitlift;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("MealJournal")
public class MealJournal extends ParseObject {
    public static final String TAG = "MealJournal Class";

    public static final String KEY_TITLE = "title";
    public static final String KEY_MEALDESCRIPTION = "mealDescription";
    public static final String KEY_NUTRIENTS = "nutrients";
    public static final String KEY_AMOUNTS = "amounts";
    public static final String POINTER_USER = "user";

    public String getTitle () {
        return getString(KEY_TITLE);
    }

    public void setTitle (String title) {
        put(KEY_TITLE, title);
    }

    public String getMealDescription () {
        return getString(KEY_MEALDESCRIPTION);
    }

    public void setMealDescription (String mealDescription) {
        put(KEY_MEALDESCRIPTION, mealDescription);
    }

    public List<String> getNutrients () {
        return getList(KEY_NUTRIENTS);
    }

    public void setNutrients (List<String> nutrients) {
        put(KEY_NUTRIENTS, nutrients);
    }

    public List<Integer> getAmounts () {
        return getList(KEY_AMOUNTS);
    }

    public void setAmounts (List<Integer> amounts) {
        put(KEY_AMOUNTS, amounts);
    }

}
