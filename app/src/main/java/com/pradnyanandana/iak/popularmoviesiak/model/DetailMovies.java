package com.pradnyanandana.iak.popularmoviesiak.model;

import java.util.List;

/**
 * Created by pradn on 23/08/2017.
 */

public class DetailMovies {
    private int backdrop_path;
    private long budget;
    private List<GenreDetails> genres;
    private int id;
    private String original_title;
    private String overview;
    private int poster_path;
    private List<ProductionCompanies> production_companies;
    private List<ProductionCountries> production_countries;
    private String release_date;

    public int getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(int backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public List<GenreDetails> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDetails> genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(int poster_path) {
        this.poster_path = poster_path;
    }

    public List<ProductionCompanies> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(List<ProductionCompanies> production_companies) {
        this.production_companies = production_companies;
    }

    public List<ProductionCountries> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(List<ProductionCountries> production_countries) {
        this.production_countries = production_countries;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
