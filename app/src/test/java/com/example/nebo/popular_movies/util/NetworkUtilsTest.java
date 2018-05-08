package com.example.nebo.popular_movies.util;

import com.example.nebo.popular_movies.util.NetworkUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class NetworkUtilsTest {
    @Test
    public void nullCheck() {


        try {
            NetworkUtils.getResponseFromHttpsUrl(null);
            assert (false);
        }
        catch (java.lang.IllegalArgumentException e) {
            assert (true);
        }
        catch (java.io.IOException e) {
            assert (false);
        }
    }
}
