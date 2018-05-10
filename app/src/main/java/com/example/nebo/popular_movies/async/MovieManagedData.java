package com.example.nebo.popular_movies.async;

import com.example.nebo.popular_movies.data.Movie;
import com.example.nebo.popular_movies.util.MovieURLUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieManagedData {
    private List<Movie> mMovies = new ArrayList<Movie>();
    private int mPage = MovieURLUtils.DEFAULT_PAGE_NUM;
    private int mFirstVisible = 0;

    public void setFirstVisible(int position) {
        this.mFirstVisible = position;
    }

    public int getFirstVisible() {
        return this.mFirstVisible;
    }

    public void incrementPage() {
        this.mPage++;
    }

    public void decrementPage() {
        this.mPage--;
    }

    public int getPage() {
        return this.mPage;
    }

    public List<Movie> getMovies() {
        return this.mMovies;
    }

    public int addMovies(List<Movie> movies) {
        int count = 0;

        if (movies != null) {
            for (Movie movie : movies) {
                if (movie != null) {
                    this.mMovies.add(movie);
                    count++;
                }
            }
        }

        return count;
    }
}
