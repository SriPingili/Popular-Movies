package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmovies.data.FavoriteMoviesContract.FavoriteMovieEntry;

/**
 * Created by sp051821 on 11/12/17.
 */

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public FavoriteMoviesDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + FavoriteMovieEntry.TABLE_NAME + " ("+
                FavoriteMovieEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, " +
                FavoriteMovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_POSTER_URL + " TEXT NOT NULL, "+
                FavoriteMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "+
                FavoriteMovieEntry.COLUMN_USER_RATING + " INTEGER NOT NULL"+
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
