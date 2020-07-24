package com.example.fitlift.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlift.R;
import com.example.fitlift.WeightReps;
import com.example.fitlift.Workout;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.databinding.ItemWorkoutBinding;
import com.example.fitlift.fragments.WorkoutDetailsFragment;

import java.util.ArrayList;
import java.util.List;

// Base adapter for all workouts, this enables access to all workout data

public class WeightRepsAdapter extends RecyclerView.Adapter<WeightRepsAdapter.ViewHolder> {

    private Context context;
    private List<WeightReps> workouts;

    public WeightRepsAdapter(Context context, List<WeightReps> workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWorkoutBinding binding = ItemWorkoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeightReps workout = workouts.get(position);

        holder.bind(workout);

    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemWorkoutBinding binding;

        public ViewHolder(@NonNull ItemWorkoutBinding b) {
            super(b.getRoot());
            this.binding = b;
            // needed to enable onClickListener on views
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(WeightReps workout) {

            Workout workoutEntry = (Workout) workout.getWorkout();
            //WorkoutJournal workoutJournalEntry = (WorkoutJournal) workoutEntry.getWorkoutJournal();

            //binding.tvDate.setText(workoutJournalEntry.getCreatedAt().toString());
            //binding.etTitle.setText(workoutJournalEntry.getTitle());
        }

        @Override
        public void onClick(View view) {
            // must cast context to FragmentActivity to call getSupportFragmentManager
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            //Toast.makeText(context, "You clicked", Toast.LENGTH_SHORT).show();
            int position = getAdapterPosition();
            // make sure the position is valid - exists in the view
            if (position != RecyclerView.NO_POSITION) {
                WeightReps workout = workouts.get(position);
                // workout and workoutjournal objects
                Workout workoutEntry = (Workout) workout.getWorkout();
               // WorkoutJournal workoutJournalEntry = (WorkoutJournal) workoutEntry.getWorkoutJournal();

                // bundle needed to pass data to other fragments
                Bundle bundle = new Bundle();
                bundle.putInt("Adapter position", position);
               // bundle.putString("Title", workoutJournalEntry.getTitle());
                //bundle.putString("Date", workoutJournalEntry.getCreatedAt().toString());
                // TODO: WILL HAVE TO UPDATE TO ARRAY OF EXERCISES, WEIGHTS, AND REPS
                //bundle.putString("Exercise", workoutEntry.getExercise());
                bundle.putInt("Weight", workout.getWeight());
                bundle.putIntegerArrayList("Reps", (ArrayList<Integer>) workout.getReps());

                Fragment fragment = new WorkoutDetailsFragment();
                // attach bundle to fragment
                fragment.setArguments(bundle);
                // navigate to new fragment
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
            }
        }
    }
}
