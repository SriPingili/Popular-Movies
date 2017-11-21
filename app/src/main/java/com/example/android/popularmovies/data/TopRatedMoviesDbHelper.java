package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies.data.TopRatedMoviesContract.TopRatedMoviesEntry;

/**
 * SQLiteOpenHelper class for the Top rated movies table
 */
public class TopRatedMoviesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "toprated.db";
    private static final int DATABASE_VERSION = 1;

    public TopRatedMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + TopRatedMoviesEntry.TABLE_NAME + " (" +
                TopRatedMoviesEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, " +
                TopRatedMoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                TopRatedMoviesEntry.COLUMN_POSTER_URL + " TEXT NOT NULL, " +
                TopRatedMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                TopRatedMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                TopRatedMoviesEntry.COLUMN_USER_RATING + " INTEGER NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TopRatedMoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}