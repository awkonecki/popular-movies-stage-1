package com.example.nebo.popular_movies.sync;

import android.content.Context;
import android.util.Log;

import com.example.nebo.popular_movies.util.MovieURLUtils;
import com.example.nebo.popular_movies.util.NetworkUtils;

import java.net.URL;

public class MovieSyncTask {
    private static boolean isSyncing = false;

    public static String retrieveMovieResponse() {
        return null;
    }

    // Dont know if will operate on a context or not.
    // Likely don't need it to be synchronized.
    synchronized public static void syncMovies(Context context) {
        // network stuff

        String response = null;
        Log.d("LoadInBackground", "in background task");
        URL url = MovieURLUtils.buildPopularURL(); // NetworkUtils.buildUrl("xxx");
        try {
            response = NetworkUtils.getUrlHttpResponse(url);

                            /*
                            List<Movie> movies = JsonUtils.parseJsonResponse(response);

                            for (Movie movie : movies) {
                                Log.d("Movie", movie.toString());
                            }
                            */
            Log.d("Network Result", response);
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
