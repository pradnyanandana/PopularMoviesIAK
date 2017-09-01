package com.pradnyanandana.iak.popularmoviesiak.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pradnyanandana.iak.popularmoviesiak.model.FavoriteMovies;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pradn on 31/08/2017.
 */

public class FavoriteRepo {
    private FavoriteDBHelper dbHelper;

    public FavoriteRepo(Context context)
    {
        dbHelper = new FavoriteDBHelper(context);
    }

    public int insert(FavoriteMovies favoriteMovies) {//panggil class pengeluaran dan jadikan parameter

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteMovies.KEY_title, favoriteMovies.favorite_title);//masukan data student.age ke Student.Key_age(di kolom database)
        values.put(FavoriteMovies.KEY_movie_id, favoriteMovies.favorite_movie_id);
        values.put(FavoriteMovies.KEY_release, favoriteMovies.favorite_release);
        values.put(FavoriteMovies.KEY_rate, favoriteMovies.favorite_rate);
        values.put(FavoriteMovies.KEY_overview, favoriteMovies.favorite_overview);
        values.put(FavoriteMovies.KEY_poster, favoriteMovies.favorite_poster);
        values.put(FavoriteMovies.KEY_backdrop, favoriteMovies.favorite_backdrop);

        // Inserting Row
        long favorite_movies_Id = db.insert(FavoriteMovies.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) favorite_movies_Id;
    }

    public void delete(int favorite_movies_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(FavoriteMovies.TABLE, FavoriteMovies.KEY_ID + "= ?", new String[] { String.valueOf(favorite_movies_Id) });
        db.close(); // Closing database connection
    }


    public void update(FavoriteMovies favoriteMovies) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteMovies.KEY_movie_id, favoriteMovies.favorite_movie_id);
        values.put(FavoriteMovies.KEY_title, favoriteMovies.favorite_title);
        values.put(FavoriteMovies.KEY_release, favoriteMovies.favorite_release);
        values.put(FavoriteMovies.KEY_rate, favoriteMovies.favorite_rate);
        values.put(FavoriteMovies.KEY_overview, favoriteMovies.favorite_overview);
        values.put(FavoriteMovies.KEY_poster, favoriteMovies.favorite_poster);
        values.put(FavoriteMovies.KEY_backdrop, favoriteMovies.favorite_backdrop);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(FavoriteMovies.TABLE, values, FavoriteMovies.KEY_ID + "= ?", new String[] { String.valueOf(favoriteMovies.favorite_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getFavoriteMoviesList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                FavoriteMovies.KEY_ID + "," +
                FavoriteMovies.KEY_movie_id + "," +
                FavoriteMovies.KEY_title + "," +
                FavoriteMovies.KEY_release + "," +
                FavoriteMovies.KEY_rate + "," +
                FavoriteMovies.KEY_overview + "," +
                FavoriteMovies.KEY_poster + "," +
                FavoriteMovies.KEY_backdrop +
                " FROM " + FavoriteMovies.TABLE + " ORDER BY " + FavoriteMovies.KEY_ID + " DESC";

        //Student student = new Student();
        ArrayList<HashMap<String, String>> FavoriteMoviesList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> favorite_movies = new HashMap<String, String>();
                favorite_movies.put("id", cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_ID)));
                favorite_movies.put("movie_id", cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_movie_id)));
                favorite_movies.put("title", cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_title)));
                favorite_movies.put("release", cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_release)));
                favorite_movies.put("rate", String.valueOf(cursor.getDouble(cursor.getColumnIndex(FavoriteMovies.KEY_rate))));
                favorite_movies.put("overview", cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_overview)));
                favorite_movies.put("poster", cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_poster)));
                favorite_movies.put("backdrop", cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_backdrop)));
                FavoriteMoviesList.add(favorite_movies);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return FavoriteMoviesList;

    }

    public FavoriteMovies getFavoriteMoviesById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                FavoriteMovies.KEY_ID + "," +
                FavoriteMovies.KEY_movie_id + "," +
                FavoriteMovies.KEY_title + "," +
                FavoriteMovies.KEY_release + "," +
                FavoriteMovies.KEY_rate + "," +
                FavoriteMovies.KEY_overview + "," +
                FavoriteMovies.KEY_poster + "," +
                FavoriteMovies.KEY_backdrop +
                " FROM " + FavoriteMovies.TABLE
                + " WHERE " +
                FavoriteMovies.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        FavoriteMovies favoriteMovies = new FavoriteMovies();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                favoriteMovies.favorite_ID =cursor.getInt(cursor.getColumnIndex(FavoriteMovies.KEY_ID));//ambil ID dari listview
                favoriteMovies.favorite_movie_id =cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_movie_id));
                favoriteMovies.favorite_title =cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_title));
                favoriteMovies.favorite_release  =cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_release));
                favoriteMovies.favorite_rate  =cursor.getDouble(cursor.getColumnIndex(FavoriteMovies.KEY_rate));
                favoriteMovies.favorite_overview =cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_overview));
                favoriteMovies.favorite_poster =cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_poster));
                favoriteMovies.favorite_backdrop =cursor.getString(cursor.getColumnIndex(FavoriteMovies.KEY_backdrop));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favoriteMovies;
    }

}
