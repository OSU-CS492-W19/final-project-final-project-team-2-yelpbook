package com.example.android.yelpsearch.utils;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.io.Serializable;


import com.example.android.yelpsearch.data.YelpRest;
import com.google.gson.Gson;

import java.util.ArrayList;

public class YelpUtils {
    private final static String YELP_SEARCH_BASE_URL = "https://api.yelp.com/v3/businesses/search";
    private final static String YELP_SEARCH_QUERY_PARAM = "term";
    private final static String YELP_SEARCH_LOCATION_PARAM = "location";
    private final static String YELP_SEARCH_SORT_PARAM = "sort_by";
//    private final static String YELP_SEARCH_SORT_VALUE = "sort_by";
    private final static String YELP_SEARCH_LANGUAGE_FORMAT_STR = "language:%s";
    private final static String YELP_SEARCH_USER_FORMAT_STR = "user:%s";
    private final static String YELP_SEARCH_SEARCH_IN_FORMAT_STR = "in:%s";
    private final static String YELP_SEARCH_IN_NAME = "name";
    private final static String YELP_SEARCH_IN_DESCRIPTION = "description";
    private final static String YELP_SEARCH_IN_README = "readme";

    public static final String EXTRA_YELP_REST = "YelpUtils.YelpRest";

    public static class YelpSearchResults {
        public ArrayList<YelpRest> items;
    }

    public static class GSONLocation implements Serializable {
        public String address1;
        public String city;
    }
    public static class GSONRestaurant implements Serializable{
        public String name;
        public String image_url;
        public String url;
        public String rating;
        public  GSONLocation location;
        public  String phone;

    }

    public static class GSONRestaurants{
        public GSONRestaurant[] businesses;
    }



    public static String buildYelpSearchURL(String query, String sort, String city) {
        Uri.Builder builder = Uri.parse(YELP_SEARCH_BASE_URL).buildUpon();
        builder.appendQueryParameter(YELP_SEARCH_QUERY_PARAM, query);
        if (!city.equals("")) {
            builder.appendQueryParameter(YELP_SEARCH_LOCATION_PARAM, city);

        }
        if (!sort.equals("")) {
            builder.appendQueryParameter(YELP_SEARCH_SORT_PARAM, sort);
        }

        return builder.build().toString();
//        return Uri.parse(YELP_SEARCH_BASE_URL).buildUpon()
//                .appendQueryParameter(Yelp_SEARCH_QUERY_PARAM, "starbucks")
//                .appendQueryParameter(Yelp_SEARCH_LOCATION_PARAM, "corvallis")
//                .build()
//                .toString();
    }
//
//    public static String buildYelpSearchURL(String query, String sort, String language,
//                                              String user, boolean searchInName,
//                                              boolean searchInDescription, boolean searchInReadme) {
//
//        Uri.Builder builder = Uri.parse(YELP_SEARCH_BASE_URL).buildUpon();
//
//        /*
//         * Language, username, and search-in terms are incorporated directly into the query
//         * parameter, e.g. "q=android language:java user:square".  Below, we simply fold those
//         * terms into the query parameter if they're specified.
//         */
//        if (!language.equals("")) {
//            query += " " + String.format(Yelp_SEARCH_LANGUAGE_FORMAT_STR, language);
//        }
//
//        if (!user.equals("")) {
//            query += " " + String.format(Yelp_SEARCH_USER_FORMAT_STR, user);
//        }
//
//        String searchIn = buildSearchInURLString(searchInName, searchInDescription, searchInReadme);
//        if (searchIn != null) {
//            query += " " + String.format(Yelp_SEARCH_SEARCH_IN_FORMAT_STR, searchIn);
//        }
//
//        /*
//         * Finally append the query parameters into the URL, including sort only if specified.
//         */
//        builder.appendQueryParameter(Yelp_SEARCH_QUERY_PARAM, query);
//        if (!sort.equals("")) {
//            builder.appendQueryParameter(Yelp_SEARCH_SORT_PARAM, sort);
//        }
//
//        return builder.build().toString();
//    }

//    @Nullable
//    private static String buildSearchInURLString(boolean searchInName, boolean searchInDescription,
//                                                 boolean searchInReadme) {
//        ArrayList<String> searchInTerms = new ArrayList<>();
//        if (searchInName) {
//            searchInTerms.add(Yelp_SEARCH_IN_NAME);
//        }
//        if (searchInDescription) {
//            searchInTerms.add(Yelp_SEARCH_IN_DESCRIPTION);
//        }
//        if (searchInReadme) {
//            searchInTerms.add(Yelp_SEARCH_IN_README);
//        }
//
//        if (!searchInTerms.isEmpty()) {
//            return TextUtils.join(",", searchInTerms);
//        } else {
//            return null;
//        }
//    }

    public static ArrayList<YelpRest> parseYelpSearchResults(String json) {
        Gson gson = new Gson();
        GSONRestaurants results = gson.fromJson(json, GSONRestaurants.class);
        if (results != null && results.businesses != null) {
            ArrayList<YelpRest> yelpRestItems = new ArrayList<>();
            for(GSONRestaurant restItem: results.businesses){
                YelpRest yelpRestaurant = new YelpRest();
                yelpRestaurant.name = restItem.name;
                yelpRestaurant.html_url = restItem.url;
                yelpRestaurant.rest_rating = restItem.rating;
                yelpRestaurant.rest_phone = restItem.phone;
                yelpRestaurant.location_city = restItem.location.city;
                yelpRestaurant.location_address = restItem.location.address1;
                yelpRestaurant.img_url = restItem.image_url;
                yelpRestItems.add(yelpRestaurant);
            }
            return yelpRestItems;
        } else {
            return null;
        }
    }
}
