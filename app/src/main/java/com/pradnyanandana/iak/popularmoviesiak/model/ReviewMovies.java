package com.pradnyanandana.iak.popularmoviesiak.model;

import java.util.List;

/**
 * Created by pradn on 23/08/2017.
 */

public class ReviewMovies {
    private String id;
    private List<ResultsReview> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ResultsReview> getResults() {
        return results;
    }

    public void setResults(List<ResultsReview> results) {
        this.results = results;
    }
}
