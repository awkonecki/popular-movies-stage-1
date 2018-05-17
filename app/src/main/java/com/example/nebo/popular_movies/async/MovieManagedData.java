package com.example.nebo.popular_movies.async;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.nebo.popular_movies.data.Movie;
import com.example.nebo.popular_movies.util.MovieURLUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieManagedData implements Parcelable{
    private final List<Movie> mMovies = new ArrayList<Movie>();
    private int mPage = MovieURLUtils.DEFAULT_PAGE_NUM;
    private int mFirstVisible = 0;
    private String mType = null;

    public MovieManagedData(String type) {
        this.mType = type;
    }

    private MovieManagedData(Parcel in) {
        if (in != null) {
            in.readTypedList(this.mMovies, Movie.CREATOR);
            this.mPage = in.readInt();
            this.mFirstVisible = in.readInt();
            this.mType = in.readString();
        }
    }

    public String getType() {
        return this.mType;
    }

    public void setFirstVisible(int position) {
        this.mFirstVisible = position;
    }

    public int getFirstVisible() {
        return this.mFirstVisible;
    }

    public void incrementPage() {
        this.mPage++;
    }

    public int getPage() {
        return this.mPage;
    }

    public List<Movie> getMovies() {
        return this.mMovies;
    }

    public void addMovies(List<Movie> movies) {
        int count = 0;

        if (movies != null) {
            for (Movie movie : movies) {
                if (movie != null) {
                    this.mMovies.add(movie);
                    count++;
                }
            }
        }

        return;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mMovies);
        dest.writeInt(this.mPage);
        dest.writeInt(this.mFirstVisible);
        dest.writeString(this.mType);
    }

    public static Creator<MovieManagedData> CREATOR = new Parcelable.Creator<MovieManagedData>() {
        @Override
        public MovieManagedData createFromParcel(Parcel source) {
            return new MovieManagedData(source);
        }

        @Override
        public MovieManagedData[] newArray(int size) {
            return new MovieManagedData[size];
        }
    };
}
