package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmovies.data.PopularMoviesContract.PopularMoviesEntry;

/**
 * Created by sp051821 on 11/18/17.
 */

public class PopularMoviesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "moviess.db";
    private static final int DATABASE_VERSION = 1;


    public PopularMoviesDbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + PopularMoviesEntry.TABLE_NAME + " ("+
                PopularMoviesEntry.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, " +
                PopularMoviesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                PopularMoviesEntry.COLUMN_POSTER_URL + " TEXT NOT NULL, "+
                PopularMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                PopularMoviesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "+
                PopularMoviesEntry.COLUMN_USER_RATING + " INTEGER NOT NULL"+
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PopularMoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
