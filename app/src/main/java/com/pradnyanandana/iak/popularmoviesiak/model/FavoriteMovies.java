package com.pradnyanandana.iak.popularmoviesiak.model;

/**
 * Created by pradn on 31/08/2017.
 */

public class FavoriteMovies {

    public static final String TABLE = "FavoritMovies";

    // Labels Table Columns names
    public static final String KEY_ID = "id";//akan digunakan sebagai deklarasi nama kolom tabel database
    public static final String KEY_title = "title";
    public static final String KEY_release = "release";
    public static final String KEY_rate = "rate";
    public static final String KEY_overview = "overview";
    public static final String KEY_poster = "poster";
    public static final String KEY_backdrop = "backdrop";

    public String favorite_ID;
    public String favorite_title;
    public String favorite_release;
    public double favorite_rate;
    public String favorite_overview;
    public String favorite_poster;
    public String favorite_backdrop;
}
