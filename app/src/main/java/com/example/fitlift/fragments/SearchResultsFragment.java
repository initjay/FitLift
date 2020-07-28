package com.example.fitlift.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitlift.R;
import com.example.fitlift.adapters.SearchResultsAdapter;
import com.example.fitlift.databinding.FragmentSearchResultsBinding;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsFragment extends Fragment {

    public static final String TAG = "SearchResultsFragment";
    private RecyclerView rvSearchResults;
    private SearchResultsAdapter adapter;
    private List<ParseUser> users;
    private FragmentSearchResultsBinding binding;

    public SearchResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSearchResults = binding.rvSearchResults;

        users = new ArrayList<>();
        adapter = new SearchResultsAdapter(getContext(), users);

        rvSearchResults.setAdapter(adapter);
        rvSearchResults.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = getArguments();

        if (bundle != null) {
            List<ParseUser> usersResults = bundle.getParcelableArrayList("users");
            users.addAll(usersResults);
            adapter.notifyDataSetChanged();
        }
    }

}