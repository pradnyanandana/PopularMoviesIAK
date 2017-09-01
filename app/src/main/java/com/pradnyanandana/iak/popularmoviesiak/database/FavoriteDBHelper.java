package com.pradnyanandana.iak.popularmoviesiak.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pradnyanandana.iak.popularmoviesiak.model.FavoriteMovies;

/**
 * Created by pradn on 31/08/2017.
 */

public class FavoriteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "movie_db";

    public FavoriteDBHelper(Context context ) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_MOVIE = "CREATE TABLE " + FavoriteMovies.TABLE + "("
                + FavoriteMovies.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"//bikin tabel
                + FavoriteMovies.KEY_movie_id + " TEXT, "
                + FavoriteMovies.KEY_title + " TEXT, "
                + FavoriteMovies.KEY_release + " TEXT, "
                + FavoriteMovies.KEY_rate + " DOUBLE, "
                + FavoriteMovies.KEY_overview + " TEXT, "
                + FavoriteMovies.KEY_poster + " TEXT, "
                + FavoriteMovies.KEY_backdrop + " TEXT )";

        db.execSQL(CREATE_TABLE_MOVIE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovies.TABLE);//kalo update, hapus tabel dan bikin tabel lagi

        // Create tables again
        onCreate(db);
    }
}
