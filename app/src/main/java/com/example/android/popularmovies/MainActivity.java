package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.adapter.CustomMovieAdapter;
import com.example.android.popularmovies.data.FavoriteMoviesContract.FavoriteMovieEntry;
import com.example.android.popularmovies.data.FavoriteMoviesDbHelper;
import com.example.android.popularmovies.data.PopularMoviesContract.PopularMoviesEntry;
import com.example.android.popularmovies.data.PopularMoviesDbHelper;
import com.example.android.popularmovies.data.TopRatedMoviesContract.TopRatedMoviesEntry;
import com.example.android.popularmovies.data.TopRatedMoviesDbHelper;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.util.HelperUtil;
import com.example.android.popularmovies.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* This is the main activity. Execution of the code
* starts from here
*/
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.progress_bar_id)
    protected ProgressBar progressBar;
    @BindView(R.id.error_message_display)
    protected TextView errorMessageDisplay;
    @BindView(R.id.movie_details_grid_id)
    protected GridView gridView;
    private TextView actionBarTextView;
    private CustomMovieAdapter customMovieAdapter;
    private static String MOVIE_CATEGORY = "popular";
    private SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_gridview);
        ButterKnife.bind(this);

        //for setting the custom view in action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_custom);
        actionBarTextView = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_textview_id);

        if(savedInstanceState!=null)
        {
            Log.v("saved","in oncreate");
        }


        validateApiCalls();
    }

    private void validateApiCalls() {
        switch (MOVIE_CATEGORY) {
            case HelperUtil.TOP_RATED:
                actionBarTextView.setText(R.string.top_rated_movies_action_bar);
                if (checkNetworkConnectivity())
                    new FetchMovieData().execute(HelperUtil.MOVIE_DB_URL_TOP_RATED + HelperUtil.getApiKey(this));
                else
                    queryForMovies(new TopRatedMoviesDbHelper(this), HelperUtil.getTopRatedMovieColumns(), TopRatedMoviesEntry.TABLE_NAME);
                break;
            case HelperUtil.FAVORITE_MOVIES:
                actionBarTextView.setText(R.string.favorite_movies_action_bar);
                queryForMovies(new FavoriteMoviesDbHelper(this), HelperUtil.getFavoriteMovieColumns(), FavoriteMovieEntry.TABLE_NAME);
                break;
            default:
                actionBarTextView.setText(R.string.popular_movies_action_bar);
                if (checkNetworkConnectivity())
                    new FetchMovieData().execute(HelperUtil.MOVIE_DB_URL_POPULAR + HelperUtil.getApiKey(this));
                else
                    queryForMovies(new PopularMoviesDbHelper(this), HelperUtil.getPopularMovieColumns(), PopularMoviesEntry.TABLE_NAME);
                break;
        }
    }

    private void queryForMovies(SQLiteOpenHelper sqLiteOpenHelper, final String[] columnDetails, final String tableName) {
        final ArrayList<Movie> movies = new ArrayList<>();
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        final Cursor cursor = sqLiteDatabase.query(tableName, null, null, null, null, null, columnDetails[0]);
        if (cursor.moveToFirst()) {
            do {
                String movieTitle = cursor.getString(cursor.getColumnIndex(columnDetails[0]));
                String moviePosterUrl = cursor.getString(cursor.getColumnIndex(columnDetails[1]));
                String movieOverView = cursor.getString(cursor.getColumnIndex(columnDetails[2]));
                String movieReleaseDate = cursor.getString(cursor.getColumnIndex(columnDetails[3]));
                int movieUserRating = cursor.getInt(cursor.getColumnIndex(columnDetails[4]));
                long movieId = cursor.getLong(cursor.getColumnIndex(columnDetails[5]));

                Movie movie = new Movie(movieTitle, moviePosterUrl, movieOverView, movieReleaseDate, movieUserRating, movieId);
                movies.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
            startIntentToMovieDetails(movies);
        } else {
            showErrorMessage();
            if (MOVIE_CATEGORY.equals(HelperUtil.FAVORITE_MOVIES))
                errorMessageDisplay.setText(R.string.no_favorites_added_message);
            else
                errorMessageDisplay.setText(getString(R.string.internet_error_message));
        }

    }

    private void startIntentToMovieDetails(final ArrayList<Movie> movies) {
        progressBar.setVisibility(View.INVISIBLE);
        gridView.setVisibility(View.VISIBLE);
        if (movies != null) {
            showMovieDetails();
            customMovieAdapter = new CustomMovieAdapter(MainActivity.this, movies);
            gridView.setAdapter(customMovieAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    final Intent deatailsActivityIntent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                    deatailsActivityIntent.putExtra(Intent.EXTRA_TEXT, movies.get(position));
                    deatailsActivityIntent.putExtra(getString(R.string.action_bar_title_key), actionBarTextView.getText().toString());
                    startActivity(deatailsActivityIntent);

                }
            });
        } else {
            showErrorMessage();
        }
    }

    /*
    * Checks if the device is connected or is connecting to the
    * network.
    * Source : Android developer documentation
    * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html#DetermineType
    */
    private boolean checkNetworkConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.top_rated_id) {
            actionBarTextView.setText(R.string.top_rated_movies_action_bar);
            MOVIE_CATEGORY = HelperUtil.TOP_RATED;
            validateApiCalls();
            return true;
        }
        if (id == R.id.popular_id) {
            actionBarTextView.setText(R.string.popular_movies_action_bar);
            MOVIE_CATEGORY = HelperUtil.POPULAR_MOVIES;
            validateApiCalls();
            return true;
        }

        if (id == R.id.favorites_id) {
            actionBarTextView.setText(R.string.favorite_movies_action_bar);
            MOVIE_CATEGORY = HelperUtil.FAVORITE_MOVIES;
            validateApiCalls();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    * Async Task that does 2 things
    *   1.calls the API and parses the JSON response into the Movie object.
    *   2.sets the adapter to the grid view and handles the click event for the grid view
    */
    public class FetchMovieData extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            final String url = strings[0];
            final URL movieDbUrl = NetworkUtils.buildUrl(url);
            final ArrayList<Movie> movieDetails = new ArrayList<>();
            try {
                String movieDbRespone = NetworkUtils.getResponseFromHttpUrl(movieDbUrl);
                try {
                    final JSONObject myJsonObject = new JSONObject(movieDbRespone);
                    final JSONArray resultsJsonArray = myJsonObject.getJSONArray("results");
                    for (int i = 0; i < resultsJsonArray.length(); i++) {
                        final JSONObject tempJSONObject = resultsJsonArray.getJSONObject(i);
                        final String title = tempJSONObject.getString("title");
                        final String description = tempJSONObject.getString("overview");
                        final String imageUrl = tempJSONObject.getString("poster_path");
                        final String releaseDate = tempJSONObject.getString("release_date");
                        final int rating = tempJSONObject.getInt("vote_average");
                        final long movieId = tempJSONObject.getLong("id");
                        final Movie movie = new Movie(title, imageUrl, description, releaseDate, rating, movieId);
                        movieDetails.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
                return movieDetails;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(final ArrayList<Movie> movies) {
            if (MOVIE_CATEGORY.equals(HelperUtil.POPULAR_MOVIES))
                insertDataToMoviesTable(new PopularMoviesDbHelper(MainActivity.this), movies, HelperUtil.getPopularMovieColumns(), PopularMoviesEntry.TABLE_NAME);
            if (MOVIE_CATEGORY.equals(HelperUtil.TOP_RATED))
                insertDataToMoviesTable(new TopRatedMoviesDbHelper(MainActivity.this), movies, HelperUtil.getTopRatedMovieColumns(), TopRatedMoviesEntry.TABLE_NAME);
            startIntentToMovieDetails(movies);
        }
    }

    private void insertDataToMoviesTable(final SQLiteOpenHelper sqLiteOpenHelper, final ArrayList<Movie> movies, String[] movieTableColumnNames, final String movieTableName) {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        for (Movie movie : movies) {
            ContentValues cv = new ContentValues();
            cv.put(movieTableColumnNames[0], movie.getMovieTitle());
            cv.put(movieTableColumnNames[1], movie.getMoviePosterUrl());
            cv.put(movieTableColumnNames[2], movie.getMovieOverView());
            cv.put(movieTableColumnNames[3], movie.getMovieReleaseDate());
            cv.put(movieTableColumnNames[4], movie.getMovieUserRating());
            cv.put(movieTableColumnNames[5], movie.getMovieId());
            sqLiteDatabase.insert(movieTableName, null, cv);
        }
    }

    /*
    * Helper method that displays the grid view and hides the error text view
    *
    * Used Sunshine app as the reference for this part
    */
    private void showMovieDetails() {
        errorMessageDisplay.setVisibility(View.INVISIBLE);
        gridView.setVisibility(View.VISIBLE);
    }

    /*
   * Helper method that displays the error message and hides the grid view
   *
   * Used Sunshine app as the reference for this part
   */
    private void showErrorMessage() {
        gridView.setVisibility(View.INVISIBLE);
        errorMessageDisplay.setVisibility(View.VISIBLE);
    }

//TODO schedule a meeting with mentor, ask why onSaveInsatnce state is always null
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v("lifeCycle", "onSaveInstance");
        Log.v("lifeCycle CATEGORY",MOVIE_CATEGORY);
        outState.putString("key",MOVIE_CATEGORY);
        super.onSaveInstanceState(outState);
    }
}
