package com.example.fitlift.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fitlift.R;
import com.example.fitlift.databinding.FragmentWorkoutDetailsBinding;

// TODO fix back button to go back to workoutFragment

public class WorkoutDetailsFragment extends Fragment {

    public WorkoutDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int position = bundle.getInt("Adapter position");
        String title = bundle.getString("Title");
        String date = bundle.getString("Date");

        // Inflate the layout for this fragment utilizing viewbinding
        FragmentWorkoutDetailsBinding binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.tvTitle.setText(title);
        binding.tvDate.setText(date);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}