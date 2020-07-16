package com.example.fitlift.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitlift.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkoutDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutDetailsFragment extends Fragment {

    public WorkoutDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // testing bundle functionality
        Bundle bundle = getArguments();
        int position = bundle.getInt("Adapter position");
        //Toast.makeText(getContext(), "Position " + position + " selected.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_details, container, false);
    }
}