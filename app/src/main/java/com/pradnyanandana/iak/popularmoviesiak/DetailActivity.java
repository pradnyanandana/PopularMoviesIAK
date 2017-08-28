package com.pradnyanandana.iak.popularmoviesiak;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.pradnyanandana.iak.popularmoviesiak.adapter.TrailersAdapter;
import com.pradnyanandana.iak.popularmoviesiak.model.Results;
import com.pradnyanandana.iak.popularmoviesiak.model.ResultsTrailer;
import com.pradnyanandana.iak.popularmoviesiak.model.TrailerMovies;
import com.pradnyanandana.iak.popularmoviesiak.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.ItemClickListener{

    public static final String TAG = DetailActivity.class.getSimpleName();
    public List<ResultsTrailer> resultsTrailersList = new ArrayList<>();

    private TrailersAdapter trailersAdapter;
    private String jsonData;
    private int position;
    private Results results;
    private Gson gson = new Gson();

    @BindView(R.id.rv_trailers) RecyclerView trailersRecyclerView;
    @BindView(R.id.iv_detail_backdrop)ImageView backdrop;
    @BindView(R.id.iv_detail_poster) ImageView poster;
    @BindView(R.id.tv_detail_release)TextView release;
    @BindView(R.id.tv_detail_rating) TextView rate;
    @BindView(R.id.tv_detail_overview) TextView overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        trailersAdapter = new TrailersAdapter(resultsTrailersList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        trailersRecyclerView.setLayoutManager(layoutManager);
        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setAdapter(trailersAdapter);

        jsonData = getIntent().getStringExtra("data");
        position = getIntent().getIntExtra("position", 0);

        if (jsonData != null) {
            results = gson.fromJson(jsonData, Results.class);
            bindData();
        } else {
            Log.e(TAG, "Data is null");
        }

        getDataFromAPI();
    }

    private void getDataFromAPI() {
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
}
