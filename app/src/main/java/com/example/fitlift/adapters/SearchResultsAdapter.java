package com.example.fitlift.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitlift.databinding.ItemSearchResultBinding;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    public static final String TAG = "SearchResultsAdapter";
    private Context context;
    private List<ParseUser> users;
    private Button btnAdd;
    private ParseUser userClicked;

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemSearchResultBinding binding;

        public ViewHolder(@NonNull ItemSearchResultBinding b) {
            super(b.getRoot());
            this.binding = b;

            btnAdd = binding.btnAdd;
            btnAdd.setOnClickListener(this);
        }

        public void bind(ParseUser user) {
            binding.tvSearchResultUsername.setText(user.getUsername());

            userClicked = user;

            if(user.getParseFile("profileImg") != null) {
                Glide.with(context).load(user.getParseFile("profileImg").getUrl()).circleCrop().into(binding.ivSearchResultProfileImg);
            }

            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.whereEqualTo("friends", user);
            query.getFirstInBackground(new GetCallback<ParseUser>() {
                @Override
                public void done(ParseUser object, ParseException e) {
                    if (e == null) { // user found in friends relation
                        binding.btnAdd.setText("UnFollow");
                    } else {
                        if(e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                            binding.btnAdd.setText("Follow");
                        } else {
                            Log.e(TAG, "Error in checking if user is already following");
                        }
                    }
                }
            });

        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(context, "Add button clicked!", Toast.LENGTH_SHORT).show();

            ParseRelation<ParseUser> relation = ParseUser.getCurrentUser().getRelation("friends");

            if (binding.btnAdd.getText() == "Follow") {
                relation.add(userClicked);
                binding.btnAdd.setText("Unfollow");
            } else {
                relation.remove(userClicked);
                binding.btnAdd.setText("Follow");
            }

            ParseUser.getCurrentUser().saveInBackground();
        }
    }
}
