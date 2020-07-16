package com.example.fitlift.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.databinding.ItemWorkoutJournalBinding;

import java.util.List;

public class WorkoutJournalAdapter extends RecyclerView.Adapter<WorkoutJournalAdapter.ViewHolder> {

    private Context context;
    private List<WorkoutJournal> workoutJournals;

    public WorkoutJournalAdapter(Context context, List<WorkoutJournal> workoutJournals) {
        this.context = context;
        this.workoutJournals = workoutJournals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWorkoutJournalBinding binding = ItemWorkoutJournalBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutJournal workoutJournal = workoutJournals.get(position);
        holder.bind(workoutJournal);
    }

    @Override
    public int getItemCount() {
        return workoutJournals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemWorkoutJournalBinding binding;

        public ViewHolder(@NonNull ItemWorkoutJournalBinding b) {
            super(b.getRoot());
            binding = b;
        }

        public void bind(WorkoutJournal workoutJournal) {
            // bind the workout data to the view elements
            binding.tvTitle.setText(workoutJournal.getTitle());
            binding.tvDate.setText(workoutJournal.getCreatedAt().toString());
        }
    }
}
