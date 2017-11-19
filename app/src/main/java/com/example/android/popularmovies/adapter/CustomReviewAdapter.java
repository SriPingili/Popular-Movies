package com.example.android.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Review;

import java.util.ArrayList;

/**
 * Created by sp051821 on 11/12/17.
 */

public class CustomReviewAdapter extends RecyclerView.Adapter<CustomReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> reviews;
    private Context context;

    public CustomReviewAdapter(ArrayList<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_recycler_view_item, parent, false);

        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.textView.setText(reviews.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.review_name_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            openReview(context, reviews.get(position).getReviewUrl());
        }

        public void openReview(Context context, String url) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(webIntent);
        }
    }
}