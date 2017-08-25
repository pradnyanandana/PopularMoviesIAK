package com.pradnyanandana.iak.popularmoviesiak.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pradnyanandana.iak.popularmoviesiak.MostPopularActivity;
import com.pradnyanandana.iak.popularmoviesiak.R;
import com.pradnyanandana.iak.popularmoviesiak.model.Results;
import com.pradnyanandana.iak.popularmoviesiak.utilities.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pradn on 24/08/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = MoviesAdapter.class.getSimpleName();
    private List<Results> moviesItemList = new ArrayList<>();

    private final ItemClickListener mOnClickListener;

    public interface ItemClickListener {
        void onItemClick(Results data, int position);
    }

    public MoviesAdapter(List<Results> popularItemList, ItemClickListener mOnClickListener) {
        this.moviesItemList = popularItemList;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutFromListItem = R.layout.item_movies_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutFromListItem, parent, shouldAttachToParentImmediately);
        MoviesViewHolder viewHolder = new MoviesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MoviesViewHolder) holder).bind(moviesItemList.get(position), position, mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return moviesItemList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_movie) ImageView movie_poster;
        @BindView(R.id.tv_movie) TextView movie_name;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Results data, final int position, final ItemClickListener itemClickListener) {
//            movie_poster.setImageResource(data.getPoster_path());
//            Picasso.with(itemView.getContext())
//                    .load(Constant.POSTER_PATH + data.getPoster_path())
//                    .into(movie_poster);
            Glide.with(MoviesViewHolder.this.itemView.getContext())
                    .load(Constant.POSTER_PATH + data.getPoster_path())
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(movie_poster);
            movie_name.setText(data.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(data, position);
                }
            });
        }
    }
}

//public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
//    private static final String TAG = MoviesAdapter.class.getSimpleName();
//    private List<Results> moviesItemList = new ArrayList<>();
//
//    private final ItemClickListener mOnClickListener;
//
//    public interface ItemClickListener {
//        void onItemClick(Results data, int position);
//    }
//
//    public MoviesAdapter(List<Results> moviesItemList, ItemClickListener mOnClickListener) {
//        this.moviesItemList = moviesItemList;
//        this.mOnClickListener = mOnClickListener;
//    }
//
//    @Override
//    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_layout, parent,false);
//        MoviesViewHolder vh = new MoviesViewHolder(v);
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(MoviesViewHolder holder, int position) {
//        Results results = moviesItemList.get(position);
//
//        holder.tvMovies.setText(results.getTitle());
//        Context context = holder.itemView.getContext();
//
//        String photourl = Constant.POSTER_PATH + results.getPoster_path();
//
//        Glide.with(context)
//                .load(photourl)
//                .centerCrop()
//                .crossFade()
//                .into(holder.ivMovies);
//    }
//
//    @Override
//    public int getItemCount() {
//        return moviesItemList.size();
//    }
//
//    public class MoviesViewHolder extends RecyclerView.ViewHolder {
//        public TextView tvMovies;
//        public ImageView ivMovies;
//
//        public MoviesViewHolder(View itemView) {
//            super(itemView);
//            tvMovies = (TextView) itemView.findViewById(R.id.tv_movie);
//            ivMovies = (ImageView) itemView.findViewById(R.id.iv_movie);
//        }
//    }
//}