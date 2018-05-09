package com.example.nebo.popular_movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nebo.popular_movies.data.Movie;
import com.example.nebo.popular_movies.util.MovieURLUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static int mViewHolderCount = 0;

    // Cache the instance of the onClickListener desired by the application.
    private MovieAdatperOnClickListener mMovieAdatperOnClickListener = null;

    // Adapter copy of the Movies
    private List<Movie> mMovies = null;

    public MovieAdapter(MovieAdatperOnClickListener listener, List<Movie> movies) {
        this.mMovieAdatperOnClickListener = listener;
        this.mMovies = movies;
        this.mViewHolderCount = 0;
    }

    public interface MovieAdatperOnClickListener {
        public void OnClick(int position);
    }

    @Override
    public int getItemCount() {
        // Required for inheritance from RecyclerView.Adapter due to abstract definition.
        if (this.mMovies == null) {
            return 0;
        }
        else {
            return this.mMovies.size();
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Required for inheritance from RecyclerView.Adapter due to abstract definition.

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grid_item, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        Log.d("onCreateViewHolder", Integer.toString(this.mViewHolderCount++));

        // movieViewHolder.numberView.setText("View " + Integer.toString(this.mViewHolderCount));

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // Required for inheritance from RecyclerView.Adapter due to abstract definition.
        Log.d("Onbind called", "On bind called " + Integer.toString(position));

        if (this.mMovies != null && position < this.mMovies.size()) {
            holder.bind(this.mMovies.get(position).getPosterPath());
        }
        else {
            holder.bind("junk");
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView view = null;
        private TextView numberView = null;
        private ImageView poster = null;

        public MovieViewHolder(View itemView) {
            super(itemView);
            this.view = (TextView) itemView.findViewById(R.id.tv_item_text);
            this.numberView = (TextView) itemView.findViewById(R.id.tv_view_index);
            this.poster = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        public void bind(String imageURL) {
            Picasso.get().load(imageURL).error(R.drawable.image_placeholder).into(this.poster);
            // this.view.setText(viewData);
        }

        @Override
        public void onClick(View v) {

            Log.d("OnClick",
                    "Adapter Position " + Integer.toString(getAdapterPosition()) +
                            " Layout Position " + Integer.toString(getLayoutPosition()) +
                            " Item Id " + Long.toString(getItemId()));
            MovieAdapter.this.onClick(0);
        }
    }

    public void onClick(int position) {
        this.mMovieAdatperOnClickListener.OnClick(position);
    }
}
