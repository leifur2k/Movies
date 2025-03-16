package com.leif2k.movies;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.leif2k.movies.review_pojo.Review;
import com.leif2k.movies.trailer_pogo.Trailer;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>{

    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewsAdapter.ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Review review = reviews.get(position);

        holder.textViewReviewAuthor.setText(review.getAuthor());
        holder.textViewReview.setText(review.getReview());

        String type = review.getType();
        int color;
        if (type.equals("Позитивный")) {
            color = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_dark);
            holder.cardViewReviews.setCardBackgroundColor(color);
        }
        else if (type.equals("Негативный")) {
            color = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_red_light);
            holder.cardViewReviews.setCardBackgroundColor(color);
        }
        else {
            color = ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_orange_dark);
            holder.cardViewReviews.setCardBackgroundColor(color);
        }

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }


    public static class ReviewsViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardViewReviews;
        private final TextView textViewReviewAuthor;
        private final TextView textViewReview;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewReviews = itemView.findViewById(R.id.cardViewReviews);
            textViewReviewAuthor = itemView.findViewById(R.id.textViewReviewAuthor);
            textViewReview = itemView.findViewById(R.id.textViewReview);
        }

    }



}
