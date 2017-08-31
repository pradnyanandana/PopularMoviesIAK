package com.pradnyanandana.iak.popularmoviesiak;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.pradnyanandana.iak.popularmoviesiak.adapter.MoviesAdapter;
import com.pradnyanandana.iak.popularmoviesiak.adapter.ReviewsAdapter;
import com.pradnyanandana.iak.popularmoviesiak.adapter.TrailersAdapter;
import com.pradnyanandana.iak.popularmoviesiak.model.Results;
import com.pradnyanandana.iak.popularmoviesiak.model.ResultsTrailer;
import com.pradnyanandana.iak.popularmoviesiak.model.ResultsReview;
import com.pradnyanandana.iak.popularmoviesiak.model.ReviewMovies;
import com.pradnyanandana.iak.popularmoviesiak.model.TrailerMovies;
import com.pradnyanandana.iak.popularmoviesiak.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.ItemClickListener {

    public static final String TAG = DetailActivity.class.getSimpleName();
    public List<ResultsTrailer> resultsTrailersList = new ArrayList<>();
    public List<ResultsReview> resultsReviewsList = new ArrayList<>();

    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private String jsonData;
    private Results results;
    private Gson gson = new Gson();
    private MenuItem SharedContent;

    @BindView(R.id.rv_trailers) RecyclerView trailersRecyclerView;
    @BindView(R.id.rv_reviews) RecyclerView reviewsRecyclerView;
    @BindView(R.id.iv_detail_backdrop)ImageView backdrop;
    @BindView(R.id.iv_detail_poster) ImageView poster;
    @BindView(R.id.tv_detail_release)TextView release;
    @BindView(R.id.tv_detail_rating) TextView rate;
    @BindView(R.id.tv_detail_overview) TextView overview;
//    @BindView(R.id.ib_favorit) ImageButton favorit_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        trailersAdapter = new TrailersAdapter(resultsTrailersList, this);
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        reviewsAdapter = new ReviewsAdapter(resultsReviewsList);
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this);

        trailersRecyclerView.setLayoutManager(trailerLayoutManager);
        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setAdapter(trailersAdapter);
        reviewsRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setAdapter(reviewsAdapter);

        jsonData = getIntent().getStringExtra("data");

        if (jsonData != null) {
            results = gson.fromJson(jsonData, Results.class);
            bindData();
        } else {
            Log.e(TAG, "Data is null");
        }

        getDataTrailerFromAPI();
        getDataReviewFromAPI();

//        favorit_movie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                favorit_movie.setImageResource(R.drawable.star_on);
//            }
//        });

    }

    private void getDataReviewFromAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constant.URL_API_MOVIE
                + results.getId()
                + Constant.REVIEWS
                + Constant.PARAM_API_KEY
                + Constant.API_KEY;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ReviewMovies reviewMovies = gson.fromJson(response, ReviewMovies.class);
                            for (ResultsReview item : reviewMovies.getResults()) {
                                resultsReviewsList.add(item);
                            }
                            reviewsAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.e(TAG, error.getMessage());
                        } else {
                            Log.e(TAG, "Something error happened!");
                        }
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    private void getDataTrailerFromAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Constant.URL_API_MOVIE
                + results.getId()
                + Constant.VIDEOS
                + Constant.PARAM_API_KEY
                + Constant.API_KEY;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            TrailerMovies trailerMovies = gson.fromJson(response, TrailerMovies.class);
                            for (ResultsTrailer item : trailerMovies.getResults()) {
                                resultsTrailersList.add(item);
                            }
                            trailersAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.e(TAG, error.getMessage());
                        } else {
                            Log.e(TAG, "Something error happened!");
                        }
                    }
                }
        );
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(ResultsTrailer data, int position) {
        Uri uri = Uri.parse(Constant.YOUTUBE_URL + data.getKey());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void bindData() {
        setTitle(results.getTitle());
        Glide.with(this)
                .load(Constant.BACKDROP_PATH + results.getBackdrop_path())
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(backdrop);
        Glide.with(this)
                .load(Constant.POSTER_PATH + results.getPoster_path())
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(poster);
        release.setText(results.getRelease_date());
        rate.setText(String.valueOf(results.getVote_average()));
        overview.setText(results.getOverview());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_detail, menu);
//
//        SharedContent = (MenuItem) menu.findItem(R.id.action_share).getActionView();
//        SharedContent.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBody = "[Filmy] \nMovie name : " + results.getTitle() + "\n\n" +
//                        overview.getText().toString() + "\n\n" +
//                        "\u2605" + rate.getText().toString() + "\n" +
//                        release.getText().toString() + "\n";
//
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Filmy");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share in your friends"));
//                return true;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

}
