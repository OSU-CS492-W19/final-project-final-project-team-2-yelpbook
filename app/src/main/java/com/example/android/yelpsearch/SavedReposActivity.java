package com.example.android.yelpsearch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.yelpsearch.R;
import com.example.android.yelpsearch.data.YelpRest;
import com.example.android.yelpsearch.utils.YelpUtils;

import java.util.List;

public class SavedReposActivity extends AppCompatActivity implements YelpSearchAdapter.OnSearchItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_search_results);

        RecyclerView savedReposRV = findViewById(R.id.rv_saved_repos);
        savedReposRV.setLayoutManager(new LinearLayoutManager(this));
        savedReposRV.setHasFixedSize(true);

        final YelpSearchAdapter adapter = new YelpSearchAdapter(this);
        savedReposRV.setAdapter(adapter);

        YelpRestViewModel viewModel = ViewModelProviders.of(this).get(YelpRestViewModel.class);
        viewModel.getAllYelpRests().observe(this, new Observer<List<YelpRest>>() {
            @Override
            public void onChanged(@Nullable List<YelpRest> YelpRests) {
                adapter.updateSearchResults(YelpRests);
            }
        });
    }

    @Override
    public void onSearchItemClick(YelpRest repo) {
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra(YelpUtils.EXTRA_YELP_REST, repo);
        startActivity(intent);
    }
}
