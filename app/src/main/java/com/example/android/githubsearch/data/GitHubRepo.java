package com.example.android.githubsearch.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "repos")
public class GitHubRepo implements Serializable {
    @NonNull
    @PrimaryKey
    public String full_name;

    public String description;
    public String html_url;
    public int stargazers_count;
}
