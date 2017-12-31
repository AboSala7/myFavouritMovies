package com.example.mohamed.popularmoviesstage1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by mohamed on 5/19/2017.
 */

public class FavMovies extends ContentProvider {
    public static final String AUTHORITY = "com.example.mohamed.popularmoviesstage1";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY) ;
    public static final String PATH_TASKS = "movies";
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

    public static final String ID = "ID";
    public static final String originalTitle = "originalTitle";
    public static final String plotSynopsis = "plotSynopsis";
    public static final String userRating = "userRating";
    public static final String releaseDate = "releaseDate";
    public static final String poster_path = "poster_path";
    public static final String key1 = "key1";
    public static final String key2 = "key2";
    public static final String reviewUrl1 = "reviewUrl1";
    public static final String reviewUrl2 = "reviewUrl2";

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "movies.db";
    static final String TABLE = "movies";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE IF NOT EXISTS " +FavMovies.TABLE+
                    " ("+ FavMovies.ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FavMovies.originalTitle+" TEXT NOT NULL, " +
                    FavMovies.plotSynopsis+ " TEXT NOT NULL, " +
                    FavMovies.userRating+" TEXT NOT NULL, " +
                    FavMovies.releaseDate+" TEXT NOT NULL, " +
                    FavMovies.poster_path+" TEXT NOT NULL, " +
                    FavMovies.key1+" TEXT," +
                    FavMovies.key2+" TEXT, " +
                    FavMovies.reviewUrl1+" TEXT , " +
                    FavMovies.reviewUrl2+" TEXT );";

    DatabaseHelper dbHelper ;


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE);
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DatabaseHelper(context);
        return true ;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor retCursor =  db.query(FavMovies.TABLE,
                strings,
                s,
                strings1,
                null,
                null,
                s1);
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor ;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values){

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri ;

        long id = db.insert(FavMovies.TABLE, null, values);
        if ( id > 0 ) {
            returnUri = ContentUris.withAppendedId(FavMovies.CONTENT_URI, id);
        } else {
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        return returnUri ;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int tasksDeleted;
        String id = uri.getPathSegments().get(1);
        tasksDeleted = db.delete(FavMovies.TABLE, "_id=?", new String[]{id});
        if (tasksDeleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return tasksDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String s, String[] strings) {
        int count = 0 ;
        count = db.update(TABLE, values,s ,strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

}
