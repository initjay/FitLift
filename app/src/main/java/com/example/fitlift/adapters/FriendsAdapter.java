package com.example.fitlift.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.databinding.ItemFriendBinding;
import com.example.fitlift.databinding.ItemWorkoutBinding;
import com.parse.ParseUser;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private Context context;
    // Use workoutJournals to populate recycler view with workouts belong to users
    // apart of the friends relation
    private List<WorkoutJournal> friends;

    public FriendsAdapter(Context context, List<WorkoutJournal> friends) {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFriendBinding binding = ItemFriendBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutJournal workoutJournal = friends.get(position);
        holder.bind(workoutJournal);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemFriendBinding binding;

        public ViewHolder(@NonNull ItemFriendBinding b) {
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(WorkoutJournal workoutJournal) {
            binding.tvFriendDate.setText(workoutJournal.getCreatedAt().toString());
            binding.tvFriendTitle.setText(workoutJournal.getTitle());
            binding.tvFriendUsername.setText(workoutJournal.getUser().getUsername());

            if (workoutJournal.getUser().getParseFile("profileImg") != null) {
                Glide.with(context).load(workoutJournal.getUser().getParseFile("profileImg").getUrl()).circleCrop().into(binding.ivFriendProfileImg);
            }
        }
    }
}
