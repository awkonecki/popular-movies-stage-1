package com.example.nebo.popular_movies.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nebo.popular_movies.data.Movie;

import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtils {
    private static final String MOVIE_LIST_RESPONSE_KEY = "results";
    private static final String MOVIE_ID_KEY = "id";
    private static final String MOVIE_VOTE_KEY = "vote_average";
    private static final String MOVIE_TITLE_KEY = "title";
    private static final String MOVIE_POPULARITY_KEY = "popularity";
    private static final String MOVIE_POSTER_PATH_KEY = "poster_path";
    private static final String MOVIE_BACKDROP_PATH_KEY = "backdrop_path";
    private static final String MOVIE_OVERVIEW_KEY = "overview";
    private static final String MOVIE_DATE_KEY = "release_date";

    /**
     * @brief Parse the JSON response for a list of movies.
     * @param response String representing the JSON response supporting a popular and top rated end
     *                 point API.
     * @return List of movies.
     */
    public static List<Movie> parseJsonResponse(@NonNull String response) {
        List<Movie> movies = new ArrayList<Movie>();
        JSONObject jsonResponse = null;
        JSONArray jsonArrayOfMovies = null;

        if (response == null) {
            return movies;
        }

        try {
            jsonResponse = new JSONObject(response);
            jsonArrayOfMovies = jsonResponse.getJSONArray(JsonUtils.MOVIE_LIST_RESPONSE_KEY);

            for (int index = 0; index < jsonArrayOfMovies.length(); index++) {
                Movie movie = JsonUtils.parseJsonMovie(jsonArrayOfMovies.getJSONObject(index));

                if (movie != null) {
                    movies.add(movie);
                }
            }
        }
        catch (org.json.JSONException e) {
            Log.e("Parsing JSON Response", "Error in parsing the response " + response);
            e.printStackTrace();
        }

        return movies;
    }

    /**
     * @brief Parse the JSON object for the individual movie attributes.
     * @param movieJsonObject json Object that must contain the movie keys.
     * @return A movie object if successful otherwise null.
     */
    private static Movie parseJsonMovie(@NonNull JSONObject movieJsonObject) {
        Movie movie = null;

        if (movieJsonObject == null) {
            return movie;
        }

        // All movie attributes from the JSON response are at the same level.
        int id;
        String title, overview, backdropPath, posterPath, date;
        double popularity, vote;

        try {
            id = movieJsonObject.getInt(JsonUtils.MOVIE_ID_KEY);
            title = movieJsonObject.getString(JsonUtils.MOVIE_TITLE_KEY);
            overview = movieJsonObject.getString(JsonUtils.MOVIE_OVERVIEW_KEY);
            backdropPath = MovieURLUtils.buildImageUrl(movieJsonObject.getString(JsonUtils.MOVIE_BACKDROP_PATH_KEY)).toString();
            posterPath = MovieURLUtils.buildImageUrl(movieJsonObject.getString(JsonUtils.MOVIE_POSTER_PATH_KEY)).toString();
            date = movieJsonObject.getString(JsonUtils.MOVIE_DATE_KEY);
            popularity = movieJsonObject.getDouble(JsonUtils.MOVIE_POPULARITY_KEY);
            vote = movieJsonObject.getDouble(JsonUtils.MOVIE_VOTE_KEY);
        }
        catch (org.json.JSONException e) {
            Log.e("Parse Movie Json", "Error in parsing a field for a movie.");
            e.printStackTrace();
            return movie;
        }

        return new Movie(title, id, vote, popularity, posterPath, backdropPath, overview, date);
    }
}
