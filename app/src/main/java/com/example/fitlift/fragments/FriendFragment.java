package com.example.fitlift.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitlift.R;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.adapters.FriendsAdapter;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {

    public static final String TAG = "FriendFragment";
    private RecyclerView rvFriends;
    private FriendsAdapter adapter;
    private List<WorkoutJournal> friends;

    public FriendFragment() { }         // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    // TODO: ADD SEARCH HINT
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_friends_toolbar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFriends = view.findViewById(R.id.rvFriends);

        friends = new ArrayList<>();
        adapter = new FriendsAdapter(getContext(), friends);

        rvFriends.setAdapter(adapter);

        rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        queryFriends();
    }

    private void queryFriends() {

        ParseRelation<ParseUser> relation = ParseUser.getCurrentUser().getRelation("friends");

        relation.getQuery().findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if (e != null) {
                    Log.e(TAG, "Issue with getting friends relation", e);
                    return;
                }

                ParseQuery<WorkoutJournal> query = ParseQuery.getQuery(WorkoutJournal.class);
                query.whereContainedIn("user", objects);
                query.setLimit(20);
                query.addDescendingOrder(WorkoutJournal.KEY_CREATED_AT);

                query.findInBackground(new FindCallback<WorkoutJournal>() {
                    @Override
                    public void done(List<WorkoutJournal> objects, ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Issue with getting friends workouts", e);
                            return;
                        }

                        friends.addAll(objects);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });


    }
}