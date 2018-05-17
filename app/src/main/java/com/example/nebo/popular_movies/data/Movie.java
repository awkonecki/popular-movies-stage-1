package com.example.nebo.popular_movies.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    // Keep count of the total number of movie objects created.
    private static int mMovieCount = 0;
    // Save the instance of the count to the object created.
    private final int mMovieInstance;

    // Actual movie attributes.
    private String mTitle;
    private double mVoteAverage;
    private int mID;
    private double mPopularity;
    private String mPosterPath;
    private String mBackdropPath;
    private String mOverview;
    private String mReleaseDate;

    /**
     * @brief Attribute detailed constructor.
     * @param title String that represents the title of the movie.
     * @param id Integer that represents the external id of the movie.
     * @param vote Double that indicates the current movie score.
     * @param popularity Double that indicates popularity.
     * @param posterPath String that indicates the endpoint path location for the movie's poster.
     * @param backdropPath String that indicates the backdrop path location for the movie's
     *                     backdrop.
     * @param overview String that provides a description of the movie.
     * @param date String that indicates the release date of the movie.
     */
    public Movie(String title,
                 int id,
                 double vote,
                 double popularity,
                 String posterPath,
                 String backdropPath,
                 String overview,
                 String date) {

        this.mTitle = title;
        this.mID = id;
        this.mPopularity = popularity;
        this.mVoteAverage = vote;
        this.mPosterPath = posterPath;
        this.mBackdropPath = backdropPath;
        this.mOverview = overview;
        this.mReleaseDate = date;
        Movie.mMovieCount++;
        this.mMovieInstance = Movie.mMovieCount;
    }

    private Movie(Parcel in) {
        this.mMovieInstance = Movie.mMovieCount;
        if (in != null) {
            this.mID = in.readInt();
            this.mTitle = in.readString();
            this.mOverview = in.readString();
            this.mBackdropPath = in.readString();
            this.mPosterPath = in.readString();
            this.mReleaseDate = in.readString();
            this.mPopularity = in.readDouble();
            this.mVoteAverage = in.readDouble();
        }
    }

    //**********************************************************************************************
    // OVERRIDDEN METHODS
    //**********************************************************************************************
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.mTitle).append(" ").append(this.mID).append(" ").append(this.mPosterPath);

        return sb.toString();
    }

    //**********************************************************************************************
    // CLASS ACCESSORS
    //**********************************************************************************************

    public String getTitle() {
        return this.mTitle;
    }

    public String getPosterPath() {
        return this.mPosterPath;
    }

    public String getBackdropPath() {
        return this.mBackdropPath;
    }

    public String getOverview() {
        return this.mOverview;
    }

    public String getReleaseDate() {
        return this.mReleaseDate;
    }

    public double getVote() {
        return this.mVoteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mID);
        dest.writeString(this.mTitle);
        dest.writeString(this.mOverview);
        dest.writeString(this.mBackdropPath);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mReleaseDate);
        dest.writeDouble(this.mPopularity);
        dest.writeDouble(this.mVoteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie [] newArray(int size) {
            return new Movie[size];
        }
    };
}
