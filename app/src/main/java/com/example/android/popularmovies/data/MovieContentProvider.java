package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.android.popularmovies.data.FavoriteMoviesContract.FavoriteMovieEntry;
import com.example.android.popularmovies.data.PopularMoviesContract.PopularMoviesEntry;
import com.example.android.popularmovies.data.TopRatedMoviesContract.TopRatedMoviesEntry;

/**
 * Content Provider class for the popular movies app
 */

public class MovieContentProvider extends ContentProvider {

    private FavoriteMoviesDbHelper favoriteMoviesDbHelper;
    private PopularMoviesDbHelper popularMoviesDbHelper;
    private TopRatedMoviesDbHelper topRatedMoviesDbHelper;

    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;

    public static final int POPULAR_MOVIES = 200;
    public static final int POPULAR_MOVIES_WITH_ID = 201;

    public static final int TOP_MOVIES = 300;
    public static final int TOP_MOVIES_WITH_ID = 301;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY, FavoriteMoviesContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(FavoriteMoviesContract.AUTHORITY, FavoriteMoviesContract.PATH_MOVIES + "/#", MOVIES_WITH_ID);
        uriMatcher.addURI(PopularMoviesContract.AUTHORITY, PopularMoviesContract.PATH_POPULAR_MOVIES, POPULAR_MOVIES);
        uriMatcher.addURI(PopularMoviesContract.AUTHORITY, PopularMoviesContract.PATH_POPULAR_MOVIES + "/#", POPULAR_MOVIES_WITH_ID);
        uriMatcher.addURI(TopRatedMoviesContract.AUTHORITY, TopRatedMoviesContract.PATH_TOP_MOVIES, TOP_MOVIES);
        uriMatcher.addURI(TopRatedMoviesContract.AUTHORITY, TopRatedMoviesContract.PATH_TOP_MOVIES + "/#", TOP_MOVIES_WITH_ID);
        return uriMatcher;
    }

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        Context context = getContext();
        favoriteMoviesDbHelper = new FavoriteMoviesDbHelper(context);
        popularMoviesDbHelper = new PopularMoviesDbHelper(context);
        topRatedMoviesDbHelper = new TopRatedMoviesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase;
        int match = sUriMatcher.match(uri);
        Cursor toReturnCursor;
        switch (match) {
            case MOVIES:
                sqLiteDatabase = favoriteMoviesDbHelper.getReadableDatabase();
                toReturnCursor = sqLiteDatabase.query(FavoriteMovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case POPULAR_MOVIES:
                sqLiteDatabase = popularMoviesDbHelper.getReadableDatabase();
                toReturnCursor = sqLiteDatabase.query(PopularMoviesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case TOP_MOVIES:
                sqLiteDatabase = topRatedMoviesDbHelper.getReadableDatabase();
                toReturnCursor = sqLiteDatabase.query(TopRatedMoviesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        toReturnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return toReturnCursor;
    }


    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase sqLiteDatabase;
        int match = sUriMatcher.match(uri);
        int rowsToBeReturned = 0;
        switch (match) {
            case POPULAR_MOVIES:
                sqLiteDatabase = popularMoviesDbHelper.getWritableDatabase();
                for (ContentValues contentValues : values) {
                    long id = sqLiteDatabase.insert(PopularMoviesEntry.TABLE_NAME, null, contentValues);
                    if (id != -1)
                        rowsToBeReturned++;
                }
                return rowsToBeReturned;
            case TOP_MOVIES:
                sqLiteDatabase = topRatedMoviesDbHelper.getWritableDatabase();
                for (ContentValues contentValues : values) {
                    long id = sqLiteDatabase.insert(TopRatedMoviesEntry.TABLE_NAME, null, contentValues);
                    if (id != -1)
                        rowsToBeReturned++;
                }
                return rowsToBeReturned;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase database = favoriteMoviesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri uriToBeReturned = null;
        switch (match) {
            case MOVIES:

                long id = database.insert(FavoriteMovieEntry.TABLE_NAME, null, contentValues);

                if (id > 0) {
                    uriToBeReturned = ContentUris.withAppendedId(FavoriteMovieEntry.CONTENT_URI, id);
                } else {
                    Toast.makeText(getContext(), "Failure", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return uriToBeReturned;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        final SQLiteDatabase database = favoriteMoviesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted = 0;
        switch (match) {
            case MOVIES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                rowsDeleted = database.delete(FavoriteMovieEntry.TABLE_NAME, FavoriteMovieEntry.COLUMN_MOVIE_ID + "=" + id, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
