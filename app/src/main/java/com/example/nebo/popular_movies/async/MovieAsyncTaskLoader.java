package com.example.nebo.popular_movies.async;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.nebo.popular_movies.R;
import com.example.nebo.popular_movies.util.MovieURLUtils;
import com.example.nebo.popular_movies.util.NetworkUtils;

import java.net.URL;

public class MovieAsyncTaskLoader extends AsyncTaskLoader<String> {

    // Provide arguments that can be used by the Async Task Loader.
    private final Bundle mArgs;

    public MovieAsyncTaskLoader(Context context, final Bundle args) {
        super(context);
        this.mArgs = args;
    }

    /**
     * @brief Provides the actual set of work to be accomplished by the async task loader.
     * @return String of a serialized JSON response from the desired endpoint.
     */
    @Nullable
    @Override
    public String loadInBackground() {
        // Response from the GET response will be a serialized JSON response as a string.
        String response = null;
        String type;
        int page = MovieURLUtils.DEFAULT_PAGE_NUM;
        Resources resources = getContext().getResources();

        // Need to determine the type of work the task is expected to perform.
        // Type of work that can be performed is as follows.
        // 1. Fetch popular movies using the default page (1).
        // 2. Fetch popular movies using a specified page.
        // 3. Fetch top rated movies using the default page (1).
        // 4. Fetch top rated movies using a specified page.
        // 5. Search for movies specified by the user. @TODO

        if (this.mArgs != null) {
            page = this.mArgs.getInt(resources.getString(R.string.bk_page_number),
                    MovieURLUtils.DEFAULT_PAGE_NUM);
            type = this.mArgs.getString(resources.getString(R.string.bk_request_type),
                    resources.getString(R.string.bv_request_type_popular));
        }
        else {
            type = resources.getString(R.string.bv_request_type_popular);
        }

        URL url;

        if (type.equals(resources.getString(R.string.bv_request_type_popular))) {
            url = MovieURLUtils.buildPopularURL(page);
        }
        else if (type.equals(resources.getString(R.string.bv_request_type_top_rated))) {
            url = MovieURLUtils.buildTopRatedURL(page);
        }
        else if (type.equals(resources.getString(R.string.bv_request_type_search))) {
            Log.d("loadInBackground", "Search not supported yet.");
            return null;
        }
        else {
            Log.e("loadInBackground", "Unsupported task.");
            return null;
        }

        try {
            response = NetworkUtils.getUrlHttpResponse(url);
            Log.d("Network Result", response);
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public void deliverResult(@Nullable String data) {
        super.deliverResult(data);
    }
}
