package com.pradnyanandana.iak.popularmoviesiak.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pradnyanandana.iak.popularmoviesiak.R;
import com.pradnyanandana.iak.popularmoviesiak.model.ResultsReview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pradn on 29/08/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ReviewsAdapter.class.getSimpleName();
    private List<ResultsReview> resultsReviewList = new ArrayList<>();

//    private final ReviewsAdapter.ItemClickListener mOnClickListener;

    public interface ItemClickListener {
        void onItemClick(ResultsReview data, int position);
    }

    public ReviewsAdapter(List<ResultsReview> resultsReviewList/*, ReviewsAdapter.ItemClickListener mOnClickListener*/) {
        this.resultsReviewList = resultsReviewList;
//        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutFromListItem = R.layout.item_reviews_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutFromListItem, parent, shouldAttachToParentImmediately);
        ReviewsAdapter.ReviewsViewHolder viewHolder = new ReviewsAdapter.ReviewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ReviewsAdapter.ReviewsViewHolder) holder).bind(resultsReviewList.get(position), position/*, mOnClickListener*/);
    }

    @Override
    public int getItemCount() {
        return resultsReviewList.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_detail_reviews_author)
        TextView author;
        @BindView(R.id.tv_detail_reviews_content)
        TextView content;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ResultsReview data, final int position/*, final ReviewsAdapter.ItemClickListener itemClickListener*/) {
            author.setText(data.getAuthor());
            content.setText(data.getContent());
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    itemClickListener.onItemClick(data, position);
//                }
//            });
        }
    }
}
