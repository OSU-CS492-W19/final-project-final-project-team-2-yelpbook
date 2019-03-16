package com.example.android.githubsearch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.githubsearch.data.GitHubRepo;
import com.example.android.githubsearch.utils.GitHubUtils;

import java.util.List;

public class SavedReposActivity extends AppCompatActivity implements GitHubSearchAdapter.OnSearchItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_search_results);

        RecyclerView savedReposRV = findViewById(R.id.rv_saved_repos);
        savedReposRV.setLayoutManager(new LinearLayoutManager(this));
        savedReposRV.setHasFixedSize(true);

        final GitHubSearchAdapter adapter = new GitHubSearchAdapter(this);
        savedReposRV.setAdapter(adapter);

        GitHubRepoViewModel viewModel = ViewModelProviders.of(this).get(GitHubRepoViewModel.class);
        viewModel.getAllGitHubRepos().observe(this, new Observer<List<GitHubRepo>>() {
            @Override
            public void onChanged(@Nullable List<GitHubRepo> gitHubRepos) {
                adapter.updateSearchResults(gitHubRepos);
            }
        });
    }

    @Override
    public void onSearchItemClick(GitHubRepo repo) {
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra(GitHubUtils.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }
}
