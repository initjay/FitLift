package com.example.fitlift.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlift.R;
import com.example.fitlift.Workout;
import com.example.fitlift.databinding.ItemWorkoutBinding;
import com.parse.ParseException;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private Context context;
    private List<Workout> workouts;

    public WorkoutAdapter(Context context, List<Workout> workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWorkoutBinding binding = ItemWorkoutBinding.inflate(LayoutInflater.from(context), parent, false);
        //View view = binding.getRoot();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = workouts.get(position);
        try {
            holder.bind(workout);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemWorkoutBinding binding;

        public ViewHolder(@NonNull ItemWorkoutBinding b) {
            super(b.getRoot());
            binding = b;
        }

        public void bind(Workout workout) throws ParseException {
            // Bind the workout data to the view elements
            binding.tvDate.setText(workout.getCreatedAt().toString());
            binding.etTitle.setText(workout.getExercises().get(0));
        }
    }
}
