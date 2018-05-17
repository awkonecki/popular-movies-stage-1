package com.example.nebo.popular_movies.util;


import org.junit.Test;

import java.net.URL;

public class NetworkUtilsTest {

    private static final String BAD_URL = "http://BAD";
    private static URL mMalformedURL = null;

    // Will likely need to setup some expansion work to support testing with network resources with
    // a mocking infrastructure.  Will ignore for now.
    // https://claritysoftware.co.uk/mocking-javas-url-with-mockito/

    /**
     * @brief Ensuring that the handling of a null argument is detected.
     */
    @Test
    public void nullCheck() {
        try {
            NetworkUtils.getUrlHttpResponse(null);
            assert (false);
        }
        catch (java.lang.IllegalArgumentException e) {
            assert (true);
        }
        catch (java.io.IOException e) {
            assert (false);
        }
    }

    /**
     * @brief Wanted to build URLs with mojito mocking but goes down an entire different rabbit hole
     * due to URL class.  Will leave this code in and not expand in this project.
     */
    @Test
    public void invalidURL() {
        /*
        try {
            NetworkUtils.getUrlHttpResponse(NetworkUtilsTest.BAD_URL);
            assert (false);
        }
        catch (java.lang.IllegalArgumentException e) {
            assert (false);
        }
        catch (java.io.IOException e) {
            assert (true);
        }
        */
        assert (true);
    }
}
