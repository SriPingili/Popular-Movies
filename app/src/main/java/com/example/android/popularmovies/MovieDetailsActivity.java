package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.popularmovies.data.FavoriteMoviesContract.FavoriteMovieEntry;
import com.example.android.popularmovies.adapter.CustomReviewAdapter;
import com.example.android.popularmovies.adapter.CustomTrailerAdapter;
import com.example.android.popularmovies.data.FavoriteMoviesDbHelper;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.util.HelperUtil;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;
import com.example.android.popularmovies.data.PopularMoviesContract.PopularMoviesEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 * This Activity gets called when a movie is clicked from the
 * grid view.
 * This lists the movie title, synopsis, release date
 * vote count and movie poster
 */
public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;

    @BindView(R.id.details_title_id)
    protected TextView titleTextView;
    @BindView(R.id.details_thumbnail_id)
    protected ImageView thumbnailImageView;
    @BindView(R.id.details_overview_id)
    protected TextView overviewTextView;
    @BindView(R.id.details_release_date_id)
    protected TextView releaseDateTextView;
    @BindView(R.id.details_user_rating_id)
    protected TextView userRatingTextView;
    @BindView(R.id.favourites_button_id)
    protected ToggleButton favoritesButton;

    private TextView textView;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        //for setting the custom view in action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_textview_id);

        Intent intent = getIntent();
        if(intent.hasExtra(getString(R.string.action_bar_title_key)))
        {
             textView.setText(intent.getStringExtra(getString(R.string.action_bar_title_key)));
        }
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            movie = (Movie) intent.getSerializableExtra(Intent.EXTRA_TEXT);
            new FetchTrailerData().execute(HelperUtil.MOVIE_DB_URL + movie.getMovieId() + HelperUtil.TRAILER_URL_EXTENSION + HelperUtil.getApiKey(this));
            new FetchReviewDetails().execute(HelperUtil.MOVIE_DB_URL + movie.getMovieId() + HelperUtil.REVIEW_URL_EXTENSION + HelperUtil.getApiKey(this));


            titleTextView.setText(movie.getMovieTitle());
            Picasso.with(getApplicationContext()).load(HelperUtil.PICASO_LOAD_URL + movie.getMoviePosterUrl()).into(thumbnailImageView);
            overviewTextView.setText(movie.getMovieOverView());
            releaseDateTextView.setText(movie.getMovieReleaseDate());
            userRatingTextView.setText(movie.getMovieUserRating() + "");

            FavoriteMoviesDbHelper favoriteMoviesDbHelper = new FavoriteMoviesDbHelper(MovieDetailsActivity.this);
            mDb = favoriteMoviesDbHelper.getWritableDatabase();

            Cursor cursor = mDb.query(FavoriteMovieEntry.TABLE_NAME,null, FavoriteMovieEntry.COLUMN_MOVIE_ID+"=?",new String[]{String.valueOf(movie.getMovieId())},null,null,null);
            if(cursor.moveToFirst())
            {
                favoritesButton.setChecked(true);
            }

            favoritesButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        ContentValues cv = new ContentValues();
                        cv.put(FavoriteMovieEntry.COLUMN_MOVIE_ID, movie.getMovieId());
                        cv.put(FavoriteMovieEntry.COLUMN_MOVIE_TITLE, movie.getMovieTitle());
                        cv.put(FavoriteMovieEntry.COLUMN_POSTER_URL, movie.getMoviePosterUrl());
                        cv.put(FavoriteMovieEntry.COLUMN_OVERVIEW, movie.getMovieOverView());
                        cv.put(FavoriteMovieEntry.COLUMN_RELEASE_DATE, movie.getMovieReleaseDate());
                        cv.put(FavoriteMovieEntry.COLUMN_USER_RATING, movie.getMovieUserRating());

                        long result = mDb.insert(FavoriteMovieEntry.TABLE_NAME, null, cv);

                        if (result == movie.getMovieId()) {
                            Toast.makeText(MovieDetailsActivity.this, "successfully added to the favorites list", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        int result = mDb.delete(FavoriteMovieEntry.TABLE_NAME, FavoriteMovieEntry.COLUMN_MOVIE_ID + "=" + movie.getMovieId(), null);
                        if (result > 0) {
                            Toast.makeText(MovieDetailsActivity.this, "removed from favorites list", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    class FetchTrailerData extends AsyncTask<String, Void, ArrayList<Trailer>> {

        //TODO in preexecute show progress and hide recycler view and opposite in post execute
        //Todo think about recycler view height

        private ArrayList<Trailer> trailers;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Trailer> doInBackground(String... strings) {

            final String url = strings[0];
            final URL trailerUrl = NetworkUtils.buildUrl(url);
            trailers = new ArrayList<>();

            try {
                String trailerResponse = NetworkUtils.getResponseFromHttpUrl(trailerUrl);

                try {
                    final JSONObject jsonObjectResponse = new JSONObject(trailerResponse);

                    final JSONArray resultsJsonArray = jsonObjectResponse.getJSONArray("results");

                    for (int i = 0; i < resultsJsonArray.length(); i++) {
                        JSONObject trailerJsonObject = resultsJsonArray.getJSONObject(i);

                        final String videoKey = trailerJsonObject.getString("key");
                        final String videoName = trailerJsonObject.getString("name");
                        final String site = trailerJsonObject.getString("site");
                        final String videoType = trailerJsonObject.getString("type");

                        final Trailer trailer = new Trailer(videoKey, videoName, site, videoType);
                        trailers.add(trailer);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return trailers;
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            if (trailers!= null) {
                Log.v("abcd", trailers.get(0).getSite() + " " + trailers.get(0).getVideoKey() + " " + trailers.get(0).getVideoName() + " " + trailers.get(0).getVideoType());
                //new MainActivity().startActivityy(deatailsActivityIntent);

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.trailer_recycler_view_id);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetailsActivity.this);
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setHasFixedSize(true);

                if(trailers.size()<4)
                {
                    recyclerView.getLayoutParams().height=250;
                }
                else
                    recyclerView.getLayoutParams().height=500;

                CustomTrailerAdapter customTrailerAdapter = new CustomTrailerAdapter(trailers, MovieDetailsActivity.this);

                recyclerView.setAdapter(customTrailerAdapter);
            }
            else
            {
                Toast.makeText(MovieDetailsActivity.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }
    }

    class FetchReviewDetails extends AsyncTask<String, Void, ArrayList<Review>> {

        private ArrayList<Review> reviews;

        @Override
        protected ArrayList<Review> doInBackground(String... strings) {
            final String url = strings[0];
            final URL getReviewsUrl = NetworkUtils.buildUrl(url);
            reviews = new ArrayList<>();


            try {
                String trailerResponse = NetworkUtils.getResponseFromHttpUrl(getReviewsUrl);

                try {
                    final JSONObject jsonObjectResponse = new JSONObject(trailerResponse);

                    final JSONArray resultsJsonArray = jsonObjectResponse.getJSONArray("results");

                    for (int i = 0; i < resultsJsonArray.length(); i++) {
                        JSONObject trailerJsonObject = resultsJsonArray.getJSONObject(i);

                        final String author = trailerJsonObject.getString("author");
                        final String reviewUrl = trailerJsonObject.getString("url");

                        final Review review = new Review(author, reviewUrl);
                        reviews.add(review);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return reviews;
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviews) {
            if (reviews!=null) {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.review_recycler_view_id);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MovieDetailsActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);

                CustomReviewAdapter customReviewAdapter = new CustomReviewAdapter(reviews, MovieDetailsActivity.this);

                recyclerView.setAdapter(customReviewAdapter);
            }
            else
            {
                Toast.makeText(MovieDetailsActivity.this,"No Internet",Toast.LENGTH_LONG).show();
            }
        }
    }
}
