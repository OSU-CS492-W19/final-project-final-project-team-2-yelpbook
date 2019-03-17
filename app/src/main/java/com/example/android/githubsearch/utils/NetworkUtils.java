package com.example.android.githubsearch.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.util.Log;



public class NetworkUtils {

    private static final OkHttpClient mHTTPClient = new OkHttpClient();

    public static String doHTTPGet(String url) throws IOException {
        Log.d("URL!!!:", url);
        Request req = new Request.Builder()
                                 .url(url)
                                 .addHeader("Authorization", "Bearer T8sYLQv4QbDIJQd1p5Fsug16y-s1MBEL47Zcg7FIchN4JbzzfohWuC1aWm6PJJKAv_4mDMRXNAKgPRScAil39XOnkyY3Wv94C3SaQXG3d_jUCOeNcT9rckXn43CNXHYx")
                                 .build();
        Response res = mHTTPClient.newCall(req).execute();

        Log.d("!!!!!!!!!!", res.body().string());
        try {
            return res.body().string();
        } finally {
            res.close();
        }
    }
}

