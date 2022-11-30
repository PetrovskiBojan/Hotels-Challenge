package com.example.hotelchallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.VAO.Review;
import com.example.VAO.User;
import com.example.hotelchallenge.databinding.ActivityReviewViewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

public class ReviewDetailsActivity extends AppCompatActivity {

    ActivityReviewViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the review passed for detailed show
        Review showReview =(Review) getIntent().getSerializableExtra("review");
        //Show the review
        binding.hotelNameReview.setText(showReview.getHotel().getName());
        binding.reviewDescription.setText(showReview.getDescription());
        binding.reviewRating.setText(""+showReview.getRating());

        //Open the likes view passing the users who liked review
        binding.likesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showLikes = new Intent(getApplicationContext(), UsersListActivity.class);
                Review review =(Review) getIntent().getSerializableExtra("review");
                ArrayList<User>likes = review.getLikes();
                if(likes==null){
                    likes = new ArrayList<User>();
                    review.setLikes(likes);
                }
                showLikes.putExtra("passedReview",review);
                showLikes.putExtra("likes",true);
                startActivity(showLikes);
            }
        });
        //Open the dislikes view passing the users who disliked review
        binding.dislikesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showLikes = new Intent(getApplicationContext(), UsersListActivity.class);
                Review review =(Review) getIntent().getSerializableExtra("review");
                ArrayList<User>dislikes = review.getDislikes();
                if(dislikes==null){
                    dislikes = new ArrayList<User>();
                    review.setDislikes(dislikes);
                }
                showLikes.putExtra("passedReview",review);
                showLikes.putExtra("likes",false);
                startActivity(showLikes);
            }
        });

        //Like review
        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent likeReview = new Intent(getApplicationContext(), UsersListActivity.class);
                Review review =(Review) getIntent().getSerializableExtra("review");
                ArrayList<User>likes = review.getLikes();
                if(likes==null){
                    likes = new ArrayList<User>();
                }
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                User userLike = new User(user.getEmail(),user.getDisplayName(),user.getUid());
                likes.add(userLike);
                review.setLikes(likes);

                likeReview.putExtra("passedReview",review);
                likeReview.putExtra("likes",true);
                startActivity(likeReview);
            }
        });
        //dislike review
        binding.dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dislikeReview = new Intent(getApplicationContext(), UsersListActivity.class);
                Review review =(Review) getIntent().getSerializableExtra("review");
                ArrayList<User>dislikes = review.getDislikes();
                if(dislikes==null){
                    dislikes = new ArrayList<User>();
                }
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                User userLike = new User(user.getEmail(),user.getDisplayName(),user.getUid());
                dislikes.add(userLike);
                review.setDislikes(dislikes);

                dislikeReview.putExtra("passedReview",review);
                dislikeReview.putExtra("likes",false);
                startActivity(dislikeReview);
            }
        });

    }
}