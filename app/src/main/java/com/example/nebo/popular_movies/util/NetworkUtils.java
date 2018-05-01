package com.example.nebo.popular_movies.util;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/550";
    private static final String KEY_PARAM = "api_key";

    public static URL buildUrl(String query) {

        Uri uri = Uri.parse(NetworkUtils.THE_MOVIE_DB_BASE_URL).buildUpon().appendQueryParameter(NetworkUtils.KEY_PARAM, query).build();
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

    public static String getResponseFromHttpsUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        // urlConnection.setRequestProperty("Accept", "application/json");

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }

        // return null;
    }
}
