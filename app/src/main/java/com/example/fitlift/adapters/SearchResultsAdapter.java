package com.example.fitlift.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitlift.databinding.ItemSearchResultBinding;
import com.parse.ParseUser;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private Context context;
    private List<ParseUser> users;

    public SearchResultsAdapter(Context context, List<ParseUser> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchResultBinding binding = ItemSearchResultBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParseUser user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemSearchResultBinding binding;

        public ViewHolder(@NonNull ItemSearchResultBinding b) {
            super(b.getRoot());
            this.binding = b;
        }

        public void bind(ParseUser user) {
            binding.tvSearchResultUsername.setText(user.getUsername());

            if(user.getParseFile("profileImg") != null) {
                Glide.with(context).load(user.getParseFile("profileImg").getUrl()).circleCrop().into(binding.ivSearchResultProfileImg);
            }
        }
    }
}
