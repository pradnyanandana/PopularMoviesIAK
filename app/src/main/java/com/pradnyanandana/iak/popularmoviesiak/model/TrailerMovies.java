package com.pradnyanandana.iak.popularmoviesiak.model;

import java.util.List;

/**
 * Created by pradn on 23/08/2017.
 */

public class TrailerMovies {
    private String id;
    private List<ResultsTrailer> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ResultsTrailer> getResults() {
        return results;
    }

    public void setResults(List<ResultsTrailer> results) {
        this.results = results;
    }
}
