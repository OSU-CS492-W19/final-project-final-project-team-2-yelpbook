package com.example.android.githubsearch;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.githubsearch.data.GitHubRepo;
import com.example.android.githubsearch.data.GitHubRepoRepository;

import java.util.List;

public class GitHubRepoViewModel extends AndroidViewModel {
    private GitHubRepoRepository mGitHubRepoRepository;

    public GitHubRepoViewModel(Application application) {
        super(application);
        mGitHubRepoRepository = new GitHubRepoRepository(application);
    }

    public void insertGitHubRepo(GitHubRepo repo) {
        mGitHubRepoRepository.insertGitHubRepo(repo);
    }

    public void deleteGitHubRepo(GitHubRepo repo) {
        mGitHubRepoRepository.deleteGitHubRepo(repo);
    }

    public LiveData<List<GitHubRepo>> getAllGitHubRepos() {
        return mGitHubRepoRepository.getAllGitHubRepos();
    }

    public LiveData<GitHubRepo> getGitHubRepoByName(String fullName) {
        return mGitHubRepoRepository.getGitHubRepoByName(fullName);
    }
}
