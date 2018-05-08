package com.example.nebo.popular_movies.data;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @TODO Will want to figure out if at time of building the URL or URI if the parameters need to be
 * encrypted.  This is beyond the scope of this project however.
 */
public class MovieDataModel {
    private static final String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3";
    private static final String KEY_PARAM = "api_key";
    private static final String POPULAR_MOVIE_ENDPOINT = "movie/popular";
    private static final String TOP_RATED_ENDPOINT = "movie/top_rated";
    private static final String API_KEY =
            // !!! Place key below.
            "XXX";

    /**
     * @brief Construct a valid URL with the popular movie endpoint.
     * @return URL with the popular movie as the endpoint.
     */
    public static URL buildPopularURL() {
        return MovieDataModel.buildUrl(MovieDataModel.POPULAR_MOVIE_ENDPOINT);
    }

    /**
     * @brief Construct a valid URL with the top rated movie endpoint.
     * @return URL with the top rated moive as the endpoint.
     */
    public static URL buildTopRatedURL() {
        return MovieDataModel.buildUrl(MovieDataModel.TOP_RATED_ENDPOINT);
    }

    /**
     * @brief Build the URL that hosts the 'themoviedb' endpoint API.
     * @param path String that contains a detailed path that is to be appended to the base bath.
     * @return Non-null URL upon success, otherwise null.
     */
    private static URL buildUrl(@NonNull final String path) {
        Uri uri = null;
        URL url = null;

        // Defensive check if options are not enforcing non-null checks.
        if (path == null) {
            return url;
        }

        // Build the URI resource.
        uri = Uri.parse(MovieDataModel.THE_MOVIE_DB_BASE_URL).buildUpon().
                appendPath(path).
                appendQueryParameter(MovieDataModel.KEY_PARAM,  MovieDataModel.API_KEY).build();

        try {
            // Attempt to construct a valid URL.
            url = new URL(uri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            url = null;
        }

        return url;
    }
}
