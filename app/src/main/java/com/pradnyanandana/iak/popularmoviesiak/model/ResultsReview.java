package com.pradnyanandana.iak.popularmoviesiak.model;

/**
 * Created by pradn on 23/08/2017.
 */

class ResultsReview {
    private String id;
    private String author;
    private String Content;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
