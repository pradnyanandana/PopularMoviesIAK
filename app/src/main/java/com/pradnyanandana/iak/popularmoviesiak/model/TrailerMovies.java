package com.pradnyanandana.iak.popularmoviesiak.model;

import java.util.List;

/**
 * Created by pradn on 23/08/2017.
 */

public class TrailerMovies {
    private int id;
    private List<TrailerResults> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TrailerResults> getResults() {
        return results;
    }

    public void setResults(List<TrailerResults> results) {
        this.results = results;
    }
}
