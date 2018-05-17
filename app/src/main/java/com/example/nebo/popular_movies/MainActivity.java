package com.example.nebo.popular_movies;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nebo.popular_movies.async.MovieAsyncTaskLoader;
import com.example.nebo.popular_movies.async.MovieManagedData;
import com.example.nebo.popular_movies.util.JsonUtils;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>,
        MovieAdapter.MovieAdatperOnClickListener {

    private static boolean mLoading = false;
    private static final int FETCH_DATA_ID = 14;
    private static final int POPULAR_MODE = 0;
    private static final int TOP_RATED_MODE = 1;
    // private static final int FAVORITE_MODE = 2;
    // private static final int SEARCH_MODE = 3;
    private static final int DEFAULT_MODE = MainActivity.POPULAR_MODE;

    private MovieAdapter mMovieAdapter = null;
    private RecyclerView mRecyclerView = null;
    private ProgressBar mProgressBar = null;
    private static Menu mMenu = null;

    private static MovieManagedData mPopularMovies = null;
    private static MovieManagedData mTopRatedMovies = null;
    private static int mMode = MainActivity.DEFAULT_MODE;

    private MovieManagedData mActiveData = null;

    /**
     * @brief Scroll listener class that when no more vertical in the downward direction can occur
     * will perform a the fetching of a new page of movies.
     * @note Although the listener itself is okay, I believe that this is not really a clean way to
     * implement this functionality.
     * @note Is still a bit jumpy when doing quick scrolling (jumps to the top or bottom directly).
     * @reference https://stackoverflow.com/questions/36127734/detect-when-recyclerview-reaches-the-bottom-most-position-while-scrolling
     */
    private class MovieScrollListener extends RecyclerView.OnScrollListener {
        /**
         * @brief If no more vertical scrolling down can occur then will attempt to fetch more data.
         * @param recyclerView The recycler view that is responsible for displaying the movies.
         * @param dx current horizontal position
         * @param dy current vertical position
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            MovieAdapter adapter = (MovieAdapter) recyclerView.getAdapter();

            int totalItems = layoutManager.getItemCount();
            int lastPosition = layoutManager.findLastVisibleItemPosition();

            // Really only needed when changing view state or life-cycle so might not need to do it
            // here.
            int firstPosition = layoutManager.findFirstVisibleItemPosition();
            adapter.getMovieData().setFirstVisible(firstPosition);

            if (lastPosition > (totalItems * 9 / 10)) {
                MainActivity.this.fetchData();
            }
        }
    }

    /**
     * @brief Set the UI element visibility during the fetch.
     */
    private void onFetch() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * @brief Set the UI element visibility after a fetch operation.
     */
    private void fetchComplete() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * @brief Fetch the data from the appropriate source based on the current active data set.
     * @TODO Account for each type of data-set.  Currently only processing for popular.
     */
    private void fetchData() {
        Bundle args;

        if (!MainActivity.mLoading) {
            args = new Bundle();
            args.putInt(getString(R.string.bk_page_number), this.mActiveData.getPage());
            args.putString(getString(R.string.bk_request_type), this.mActiveData.getType());

            MainActivity.mLoading = true;
            // Loader Manager for async tasks
            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<Cursor> movieLoader = loaderManager.getLoader(MainActivity.FETCH_DATA_ID);

            // Set the visibility due to fetch action being started.
            this.onFetch();

            if (movieLoader == null) {
                loaderManager.initLoader(MainActivity.FETCH_DATA_ID, args, this).forceLoad();
            } else {
                loaderManager.restartLoader(MainActivity.FETCH_DATA_ID, args, this).forceLoad();
            }
        }
    }

    /**
     * @brief set the class's instance of mActiveData member to the desired data set to operate on.
     */
    private void setCurrentMovieData() {
        // For the time being not going to deal with the race conidition of the mode being changed
        // with a pending background network request.
        switch(MainActivity.mMode) {
            case MainActivity.POPULAR_MODE:
                this.mActiveData = MainActivity.mPopularMovies;
                break;
            case MainActivity.TOP_RATED_MODE:
                this.mActiveData = MainActivity.mTopRatedMovies;
                break;
            default:
                break;
        }
    }

    /**
     * @brief Sets the data for the recyclerviewer adapter instance and attempts to set the correct
     * visible element.
     * @TODO the visible piece is going back to the initial upon selection changes.  Is this due to
     * the mAdapter when it populates the views setting it back to zero???
     */
    private void setView() {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) this.mRecyclerView.getLayoutManager();

        // Manage the view if the instance state exists.
        if (this.mActiveData.getMovies().size() > 0) {
            this.mMovieAdapter.setMovieData(this.mActiveData);
            gridLayoutManager.scrollToPosition(this.mActiveData.getFirstVisible());
        }
        else {
            // Attempt to fetch data.
            this.fetchData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save the instance of the progress bar.
        mProgressBar = findViewById(R.id.pb_main_progress_bar);

        // Setup of the recycler view for the main activity.
        // 1. Create an adapter.
        mMovieAdapter = new MovieAdapter(this);

        // 2. Cache the resource of the recyclerview with the class instance.
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);

        // 3. Make a new LayoutManager of the `GridLayout` type.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                2, GridLayoutManager.VERTICAL, false);

        // 4. Set the properties that the recycleviewer wil use.
        mRecyclerView.addOnScrollListener(new MovieScrollListener());
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        if (savedInstanceState != null) {
            MainActivity.mPopularMovies =
                    savedInstanceState.getParcelable(getString(R.string.bsik_popular));
            MainActivity.mTopRatedMovies =
                    savedInstanceState.getParcelable(getString(R.string.bsik_top_rated));
            MainActivity.mMode =
                    savedInstanceState.getInt(getString(R.string.bsik_mode),
                            MainActivity.DEFAULT_MODE);
        }
        else {
            mPopularMovies = new MovieManagedData(getString(R.string.bv_request_type_popular));
            mTopRatedMovies = new MovieManagedData(getString(R.string.bv_request_type_top_rated));
        }

        // Set the current active movie data.
        setCurrentMovieData();

        // Set the view with the correct movie data.
        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        MainActivity.mMenu = menu;

        // Need to set the correct UI mode for the filter.
        if (MainActivity.mMode == MainActivity.TOP_RATED_MODE) {
            MenuItem item = null;
            item = menu.findItem(R.id.menu_item_sort_popular);
            item.setChecked(false);
            item = menu.findItem(R.id.menu_item_sort_top_rated);
            item.setChecked(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();

        switch (selectedItemId) {
            case R.id.menu_item_sort_popular:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    MenuItem menuItem = MainActivity.mMenu.findItem(R.id.menu_item_sort_top_rated);
                    menuItem.setChecked(false);
                    MainActivity.mMode = MainActivity.POPULAR_MODE;
                    this.setCurrentMovieData();
                    this.setView();
                    this.setTitle(getString(R.string.popular_title));
                }
                break;
            case R.id.menu_item_sort_top_rated:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    MenuItem menuItem = MainActivity.mMenu.findItem(R.id.menu_item_sort_popular);
                    menuItem.setChecked(false);
                    MainActivity.mMode = MainActivity.TOP_RATED_MODE;
                    this.setCurrentMovieData();
                    this.setView();
                    this.setTitle(getString(R.string.top_rated_title));
                }
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnClick(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(getString(R.string.ik_movie_poster), this.mActiveData.getMovies().get(position).getPosterPath());
        intent.putExtra(getString(R.string.ik_movie_title), this.mActiveData.getMovies().get(position).getTitle());
        intent.putExtra(getString(R.string.ik_movie_backdrop), this.mActiveData.getMovies().get(position).getBackdropPath());
        intent.putExtra(getString(R.string.ik_movie_synopsis), this.mActiveData.getMovies().get(position).getOverview());
        intent.putExtra(getString(R.string.ik_user_rating), this.mActiveData.getMovies().get(position).getVote());
        intent.putExtra(getString(R.string.ik_release_date), this.mActiveData.getMovies().get(position).getReleaseDate());

        startActivity(intent);
    }

    //**********************************************************************************************
    // START ANDROID LIFE-CYCLE METHODS
    //**********************************************************************************************
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current stack of movies already queried.
        outState.putParcelable(getString(R.string.bsik_popular), MainActivity.mPopularMovies);
        outState.putParcelable(getString(R.string.bsik_top_rated), MainActivity.mTopRatedMovies);
        outState.putInt(getString(R.string.bsik_mode), mMode);
    }

    //**********************************************************************************************
    // END ANDROID LIFE-CYCLE METHODS
    //**********************************************************************************************

    //**********************************************************************************************
    // START LOADER METHODS FOR ASYNC TASKS
    //
    // Loader is to be used if tied to the activity lifecycle
    // allow for user interface changes and commuinicate with Activity
    // For this Loader is used due to population of a recycler view adapter
    //**********************************************************************************************
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, final @Nullable Bundle args) {
        switch(id) {
            case MainActivity.FETCH_DATA_ID:
                return new MovieAsyncTaskLoader(this, args);
            default:
                throw new java.lang.IllegalArgumentException("Unsupported ID value.");
        }
    }

    /**
     * @brief Responsible for cleaning up the AsyncTaskLoaders.  Ths assumption of this method is
     * that it is a single point of usage.
     * @param loader Loader that has finished.
     * @param response String of the result of the Loader itself.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String response) {
        // Only process if response contains content.
        if (response != null && !response.isEmpty()) {
            // Add the list of movies to the overall list.
            this.mActiveData.addMovies(JsonUtils.parseJsonResponse(response));

            // Inform the movie adapter of the change.
            this.mMovieAdapter.setMovieData(this.mActiveData);
            this.mActiveData.incrementPage();
        }

        // Ensure that the loading progress bar is made invisible.
        this.fetchComplete();

        // Ensure that more loading of data can occur.
        MainActivity.mLoading = false;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    //**********************************************************************************************
    // END LOADER METHODS FOR ASYNC TASKS
    //**********************************************************************************************
}
