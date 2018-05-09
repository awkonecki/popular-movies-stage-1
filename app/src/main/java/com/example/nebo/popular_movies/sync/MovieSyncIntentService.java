package com.example.nebo.popular_movies.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class MovieSyncIntentService extends IntentService {
    private static final String CLASS_NAME = "MovieSyncIntentService";

    public MovieSyncIntentService() {
        super(MovieSyncIntentService.CLASS_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        MovieSyncTask.syncMovies(this);
    }
}
