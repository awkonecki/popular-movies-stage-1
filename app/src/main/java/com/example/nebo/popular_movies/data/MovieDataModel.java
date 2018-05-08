package com.example.nebo.popular_movies.data;

import android.net.Uri;
import android.util.Log;
import java.net.MalformedURLException;
import java.net.URL;

public class MovieDataModel {

    private static final String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/550";
    private static final String KEY_PARAM = "api_key";

    public static URL buildUrl(String query) {

        Uri uri = Uri.parse(MovieDataModel.THE_MOVIE_DB_BASE_URL).buildUpon().appendQueryParameter(MovieDataModel.KEY_PARAM, query).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("URL", url.toString());
        return url;
    }
}
