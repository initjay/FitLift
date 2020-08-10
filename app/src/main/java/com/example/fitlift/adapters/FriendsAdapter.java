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
import com.parse.ParseException;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
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
        try {
            holder.bind(workoutJournal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void clear() {
        friends.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<WorkoutJournal> newFriends) {
        friends.addAll(newFriends);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemFriendBinding binding;

        public ViewHolder(@NonNull ItemFriendBinding b) {
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(WorkoutJournal workoutJournal) throws ParseException {

            String date = new SimpleDateFormat("EEE, MMM d").format(workoutJournal.getCreatedAt());

            binding.tvFriendDate.setText(date);
            binding.tvFriendTitle.setText(workoutJournal.getTitle());
            binding.tvFriendUsername.setText(workoutJournal.getUser().fetchIfNeeded().getUsername());

            if (workoutJournal.getUser().getParseFile("profileImg") != null) {
                Glide.with(context).load(workoutJournal.getUser().getParseFile("profileImg").getUrl()).circleCrop().into(binding.ivFriendProfileImg);
            }
        }
    }
}
