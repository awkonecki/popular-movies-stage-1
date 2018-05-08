package com.example.nebo.popular_movies.util;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * @TODO It is important to realize that data is not encrypted, thus any API keys or user provided
 * information is exposed while in transit.  If HTTPS is used then the certificate needs to be
 * verified prior to use.
 */
public class NetworkUtils {
    /***
     * @brief Obtain the response from a url connection.
     * @param url URL object that contains the well-formed URL of which to connect to.
     * @return String object of which is the response from the server.
     * @throws IOException thrown if a network error occurs.
     * @throws IllegalArgumentException thrown if a null is detected in the arguments.
     */
    public static String getUrlHttpResponse(@NonNull URL url) throws
            IOException,
            IllegalArgumentException {

        // Reference material on network usage in Java at
        // https://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests

        // Still providing guard against null if compilation options are not used to enforce.
        if (url == null) {
            throw new java.lang.IllegalArgumentException("Invalid Null Argument.");
        }

        // Should really use HTTPS for connections to aid in protecting sensitive keys, but is
        // beyond the scope of this stage of development currently.
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            // Represents an input stream of bytes.
            InputStream in = connection.getInputStream();

            // Text scanner
            Scanner scanner = new Scanner(in);

            // Break the GET response into parts based on the delimiter.
            scanner.useDelimiter("\\A");

            // Obtain the response body if it exists.
            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            connection.disconnect();
        }
    }
}
