package com.example.fitlift.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitlift.R;
import com.example.fitlift.databinding.FragmentMealDetailsBinding;

public class MealDetailsFragment extends Fragment {

    public static final String TAG = "MealDetailsFragment";
    private FragmentMealDetailsBinding binding;

    public MealDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentMealDetailsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }
}