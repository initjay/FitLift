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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitlift.MealJournal;
import com.example.fitlift.R;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.databinding.ItemMealBinding;
import com.example.fitlift.fragments.MealDetailsFragment;
import com.example.fitlift.fragments.WorkoutDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class MealJournalAdapter extends RecyclerView.Adapter<MealJournalAdapter.ViewHolder> {

    private Context context;
    private List<MealJournal> mealJournals;

    public MealJournalAdapter(Context context, List<MealJournal> mealJournals) {
        this.context = context;
        this.mealJournals = mealJournals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealBinding binding = ItemMealBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealJournal mealJournal = mealJournals.get(position);
        holder.bind(mealJournal);
    }

    @Override
    public int getItemCount() {
        return mealJournals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemMealBinding binding;

        public ViewHolder(@NonNull ItemMealBinding b) {
            super(b.getRoot());
            this.binding = b;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(MealJournal mealJournal) {
            binding.etTitleMealFragment.setText(mealJournal.getTitle());
            binding.tvDateMealFragment.setText(mealJournal.getCreatedAt().toString());
        }

        @Override
        public void onClick(View view) {
            // must cast context to FragmentActivity to call getSupportFragmentManager
            FragmentTransaction fts = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            //Toast.makeText(context, "You clicked", Toast.LENGTH_SHORT).show();
            int position = getAdapterPosition();
            // make sure the position is valid - exists in the view
            if (position != RecyclerView.NO_POSITION) {
                MealJournal mealJournal = mealJournals.get(position);
                // bundle needed to pass data to other fragments
                Bundle bundle = new Bundle();
                bundle.putString("objectId", mealJournal.getObjectId());

                Fragment fragment = new MealDetailsFragment();

                fts.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom);
                // attach bundle to fragment
                fragment.setArguments(bundle);
                // navigate to new fragment
                fts.replace(R.id.flContainer, fragment).commit();
            }
        }
    }
}

