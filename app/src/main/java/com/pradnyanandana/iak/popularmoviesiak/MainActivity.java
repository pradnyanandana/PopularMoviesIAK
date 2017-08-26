package com.pradnyanandana.iak.popularmoviesiak;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pradnyanandana.iak.popularmoviesiak.adapter.MoviesAdapter;
import com.pradnyanandana.iak.popularmoviesiak.model.Movies;
import com.pradnyanandana.iak.popularmoviesiak.model.Results;
import com.pradnyanandana.iak.popularmoviesiak.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MoviesAdapter.ItemClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    public List<Results> moviesItemList;
    Movies movies;
    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    private Gson gson = new Gson();
    private DividerItemDecoration mDividerItemDecoration;
    @BindView(R.id.line_network_retry)
    LinearLayout mLinearLayoutRetry;
    @BindView(R.id.iv_error_message)
    ImageView mImageErrorMessage;
    @BindView(R.id.tv_error_message)
    TextView mDisplayErrorMessage;
    private String FilmCategory;
    private int indeks = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ButterKnife.bind(this);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);

        if (isNetworkConnected() || isWifiConnected()) {
            CreateList();
//            requestJsonObject(indeks);
        } else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mLinearLayoutRetry.setVisibility(View.VISIBLE);
        }

        ActionBar toolbar_menu = getSupportActionBar();
        if (toolbar_menu != null) {
            toolbar_menu.setElevation(0);
        }

    }

    private void CreateList() {
        requestJsonObject(indeks);

        moviesItemList = new ArrayList<>();
        mAdapter = new MoviesAdapter(moviesItemList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getDataFromAPI(String url) {
//        moviesItemList = new ArrayList<>();
//        mAdapter = new MoviesAdapter(moviesItemList, this);
//        mRecyclerView.setAdapter(mAdapter);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Movies movies = gson.fromJson(response, Movies.class);
                            for (Results item : movies.getResults()) {
                                moviesItemList.add(item);
                            }
                            mAdapter.notifyDataSetChanged();
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

    private void requestJsonObject(int i){
        if (i == 1) {
            setTitle("Popular Movie");
            FilmCategory = "popular";
        } else if (i == 2) {
            setTitle("Top Rated Movie");
            FilmCategory = "top_rated";
        } else if (i == 3) {
            setTitle("Coming Soon");
            FilmCategory = "upcoming";
        }
        String FullURL = Constant.URL_API_MOVIE
                + FilmCategory
                + Constant.PARAM_API_KEY
                + Constant.API_KEY;
        getDataFromAPI(FullURL);
    }

    @Override
    public void onItemClick(Results data, int position) {
        Intent startDetailActivity = new Intent(this, DetailActivity.class);
        startDetailActivity.putExtra("data", gson.toJson(data));
        startDetailActivity.putExtra("position", position);
        startActivity(startDetailActivity);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected() && (ConnectivityManager.TYPE_WIFI == networkInfo.getType());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_popular) {
            indeks = 1;
        } else if (id == R.id.nav_top_rated) {
            indeks = 2;
        } else if (id == R.id.nav_favorit) {
            indeks = 3;
        }

        CreateList();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
