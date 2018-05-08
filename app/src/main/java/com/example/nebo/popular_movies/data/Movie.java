package com.example.nebo.popular_movies.data;

public class Movie {
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
     * @brief default constructor that will not populate any of the actual movie attributes.
     */
    public Movie() {
        Movie.mMovieCount++;
        this.mMovieInstance = Movie.mMovieCount;
    }

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

    //**********************************************************************************************
    // CLASS ACCESSORS
    //**********************************************************************************************
    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setID(int id) {
        this.mID = id;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public void setPopularity(double value) {
        this.mPopularity = value;
    }

    public void setVote(double value) {
        this.mVoteAverage = value;
    }

    public void setPosterPath(String path) {
        this.mPosterPath = path;
    }

    public void setBackdropPath(String path) {
        this.mBackdropPath = path;
    }

    public void setDate(String date) {
        this.mReleaseDate = date;
    }

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

    public int getMovieCount() {
        return Movie.mMovieCount;
    }

    public int getMovieInstance() {
        return this.mMovieInstance;
    }

    public int getMovieID() {
        return this.mID;
    }

    public double getPopularity() {
        return this.mPopularity;
    }

    public double getVote() {
        return this.mVoteAverage;
    }
}
