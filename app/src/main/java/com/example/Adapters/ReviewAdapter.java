package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.VAO.Review;

import com.example.Interfaces.ReviewViewInterface;
import com.example.hotelchallenge.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    private final ReviewViewInterface reviewViewInterface;
    Context context;
    ArrayList<Review> reviews;

    public ReviewAdapter(Context context, ArrayList<Review> reviews,ReviewViewInterface reviewViewInterface) {
        this.context = context;
        this.reviews = reviews;
        this.reviewViewInterface=reviewViewInterface;
    }

    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewAdapter.MyViewHolder(view,reviewViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position) {

        Review review = reviews.get(position);
        holder.hotelName.setText(review.getAuthor());
        holder.rating.setText(""+review.getRating());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView hotelName,rating;

        public MyViewHolder(@NonNull View itemView, ReviewViewInterface reviewViewInterface){
            super(itemView);
            //Set the parameters for hotel in view
            hotelName= itemView.findViewById(R.id.hotelRV);
            rating= itemView.findViewById(R.id.ratingRV);

            // Set on click listener that opens the detailed view for the clicked review
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(reviewViewInterface !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            reviewViewInterface.onIntemClick(position);
                        }
                    }
                }
            });

        }
    }
}
