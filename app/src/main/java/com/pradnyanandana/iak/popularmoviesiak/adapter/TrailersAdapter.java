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
import com.pradnyanandana.iak.popularmoviesiak.R;
import com.pradnyanandana.iak.popularmoviesiak.model.Results;
import com.pradnyanandana.iak.popularmoviesiak.model.ResultsTrailer;
import com.pradnyanandana.iak.popularmoviesiak.utilities.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pradn on 27/08/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = TrailersAdapter.class.getSimpleName();
    private List<ResultsTrailer> resultsTrailerList = new ArrayList<>();

    private final ItemClickListener mOnClickListener;

    public interface ItemClickListener {
        void onItemClick(ResultsTrailer data, int position);
    }

    public TrailersAdapter(List<ResultsTrailer> resultsTrailerList, ItemClickListener mOnClickListener) {
        this.resultsTrailerList = resultsTrailerList;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutFromListItem = R.layout.item_trailers_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutFromListItem, parent, shouldAttachToParentImmediately);
        TrailersViewHolder viewHolder = new TrailersViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TrailersViewHolder) holder).bind(resultsTrailerList.get(position), position, mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return resultsTrailerList.size();
    }

    public class TrailersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_detail_trailers) ImageView thumbnail;
        @BindView(R.id.tv_detail_trailers) TextView title;
        @BindView(R.id.tv_detail_trailers_type) TextView type;

        public TrailersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ResultsTrailer data, final int position, final ItemClickListener itemClickListener) {
            Glide.with(TrailersAdapter.TrailersViewHolder.this.itemView.getContext())
                    .load(Constant.YOUTUBE_THUMBNAIL + data.getKey() + Constant.YOUTUBE_DEFAULT)
                    .fitCenter()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(thumbnail);
            title.setText(data.getName());
            type.setText(data.getType());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(data, position);
                }
            });
        }
    }
}
