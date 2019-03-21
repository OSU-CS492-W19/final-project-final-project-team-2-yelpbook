package com.example.android.yelpsearch.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "restaurant")
public class YelpRest implements Serializable {
    @NonNull
    @PrimaryKey
    public String name;

    public String html_url;
    public String img_url;
    public String rest_rating;
    public String rest_phone;
    public String location_city;
    public String location_address;

}
