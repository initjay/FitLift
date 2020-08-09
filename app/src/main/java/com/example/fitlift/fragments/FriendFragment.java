package com.example.fitlift.fragments;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.example.fitlift.OnSwipeTouchListener;
import com.example.fitlift.R;
import com.example.fitlift.WorkoutJournal;
import com.example.fitlift.activities.MainActivity;
import com.example.fitlift.adapters.FriendsAdapter;
import com.example.fitlift.adapters.SearchResultsAdapter;
import com.example.fitlift.databinding.FragmentFriendBinding;
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
    private MainActivity activity;
    private FragmentFriendBinding binding;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private RecyclerViewSkeletonScreen skeletonScreen;

    public FriendFragment() { }         // Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFriendBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = binding.friendsToolbar;
        activity.setSupportActionBar(toolbar);
        // needed to get rid of default title
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        rvFriends = binding.rvFriends;

        friends = new ArrayList<>();
        adapter = new FriendsAdapter(getContext(), friends);

        rvFriends.setAdapter(adapter);
        rvFriends.setLayoutManager(new LinearLayoutManager(getContext()));

        skeletonScreen = Skeleton.bind(rvFriends).adapter(adapter).load(R.layout.item_friend).show();

        queryFriends();

        rvFriends.setOnTouchListener(new OnSwipeTouchListener(getContext()) {

            @Override
            public void onSwipeDown() {
            }

            @Override
            public void onSwipeLeft() {
                activity.goToWorkout();
            }

            @Override
            public void onSwipeUp() {
            }

        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.menu_friends_toolbar, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: FIX WHERE FRAGMENT IS NOT RECOGNIZING WHICH USER FOLLOW/UNFOLLOW BUTTON IS CLICKED
//                if (!newText.isEmpty()) {
//                    fetchUsers(newText);
//                }
                return false;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Toast.makeText(activity, "Search Closed", Toast.LENGTH_SHORT).show();
                //getFragmentManager().popBackStackImmediate();
                return true;
            }
        });

    }

    private void fetchUsers(final String query) {
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
        userQuery.whereContains("username", query);
        userQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with searching users", e);
                    return;
                }

                insertSearchResultsNestedFragment(objects);
            }
        });
    }

    // TODO WORK ON FRAGMENT STACK TO GET BACK TO FRIEND TIMELINE WITH BACK BUTTON
    private void insertSearchResultsNestedFragment(List<ParseUser> objects) {
        Fragment childFragment = new SearchResultsFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("users", (ArrayList<? extends Parcelable>) objects);
        childFragment.setArguments(bundle);
        //transaction.addToBackStack(null);
        transaction.replace(R.id.child_fragment_friends_search, childFragment).commit();
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
                        skeletonScreen.hide();
                    }
                });
            }
        });


    }
}