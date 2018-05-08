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
    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int MIN_PAGE_NUM = 1;
    private static final int MAX_PAGE_NUM = 1000;
    private static final String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3";
    private static final String KEY_PARAM = "api_key";
    private static final String PAGE_PARAM = "page";
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
        return MovieDataModel.buildUrl(MovieDataModel.POPULAR_MOVIE_ENDPOINT, 1);
    }

    /**
     * @brief Construct a valid URL with the popular movie endpoint specifying a desired page.
     * @param page int that indicates the desired page of results, an invalid page number will
     *             result in the usage of the default page.
     * @return URL with the popular movie as the endpoint.
     */
    public static URL buildPopularURL(int page) {
        if (page > MovieDataModel.MAX_PAGE_NUM || page < MovieDataModel.MIN_PAGE_NUM) {
            Log.d("Popular URL", "Page value out of range " + page + " using default.");
            page = MovieDataModel.DEFAULT_PAGE_NUM;
        }

        return MovieDataModel.buildUrl(MovieDataModel.POPULAR_MOVIE_ENDPOINT, page);
    }

    /**
     * @brief Construct a valid URL with the top rated movie endpoint.
     * @return URL with the top rated moive as the endpoint.
     */
    public static URL buildTopRatedURL() {
        return MovieDataModel.buildUrl(MovieDataModel.TOP_RATED_ENDPOINT, 1);
    }

    /**
     * @brief Construct a valid URL with the top rated movie endpoint specifying a desired page.
     * @param page int that indicates the desired page of results, an invalid page number will
     *             result in the usage of the default page.
     * @return URL with the top rated movie as the endpoint.
     */
    public static URL buildTopRatedURL(int page) {
        if (page > MovieDataModel.MAX_PAGE_NUM || page < MovieDataModel.MIN_PAGE_NUM) {
            Log.d("Top Rated URL", "Page value out of range " + page + " using default.");
            page = MovieDataModel.DEFAULT_PAGE_NUM;
        }

        return MovieDataModel.buildUrl(MovieDataModel.TOP_RATED_ENDPOINT, page);
    }

    /**
     * @brief Build the URL that hosts the 'themoviedb' endpoint API.
     * @param path String that contains a detailed path that is to be appended to the base bath.
     * @return Non-null URL upon success, otherwise null.
     */
    private static URL buildUrl(@NonNull final String path, int page) {
        Uri uri = null;
        URL url = null;

        // Defensive check if options are not enforcing non-null checks.
        if (path == null) {
            return url;
        }

        // Build the URI resource.
        uri = Uri.parse(MovieDataModel.THE_MOVIE_DB_BASE_URL).buildUpon().
                appendEncodedPath(path).
                appendQueryParameter(MovieDataModel.KEY_PARAM,  MovieDataModel.API_KEY).
                appendQueryParameter(MovieDataModel.PAGE_PARAM, Integer.toString(page)).build();

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
