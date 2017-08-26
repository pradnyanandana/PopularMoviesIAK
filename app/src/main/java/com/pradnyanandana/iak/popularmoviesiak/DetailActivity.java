package com.pradnyanandana.iak.popularmoviesiak;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.pradnyanandana.iak.popularmoviesiak.adapter.MoviesAdapter;
import com.pradnyanandana.iak.popularmoviesiak.model.Results;
import com.pradnyanandana.iak.popularmoviesiak.utilities.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = DetailActivity.class.getSimpleName();

    private String jsonData;
    private int position;
    private Results results;
    private Gson gson = new Gson();

    @BindView(R.id.iv_detail_backdrop)ImageView backdrop;
    @BindView(R.id.iv_detail_poster) ImageView poster;
    @BindView(R.id.tv_detail_release)TextView release;
    @BindView(R.id.tv_detail_title) TextView title;
    @BindView(R.id.tv_detail_rating) TextView rate;
    @BindView(R.id.tv_detail_overview) TextView overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        jsonData = getIntent().getStringExtra("data");
        position = getIntent().getIntExtra("position", 0);

        if (jsonData != null) {
            results = gson.fromJson(jsonData, Results.class);
            bindData();
        } else {
            Log.e(TAG, "Data is null");
        }
    }

    private void bindData() {
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
        title.setText(results.getTitle());
        rate.setText(String.valueOf(results.getVote_average()));
        overview.setText(results.getOverview());
    }
}
