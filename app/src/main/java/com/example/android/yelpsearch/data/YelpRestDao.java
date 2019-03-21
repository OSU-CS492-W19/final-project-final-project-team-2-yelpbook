package com.example.android.yelpsearch.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface YelpRestDao {
    @Insert
    void insert(YelpRest restaurant);

    @Delete
    void delete(YelpRest restaurant);

    @Query("SELECT * FROM restaurant")
    LiveData<List<YelpRest>> getAllRepos();

    @Query("SELECT * FROM restaurant WHERE name = :fullName LIMIT 1")
    LiveData<YelpRest> getRepoByName(String fullName);
}
