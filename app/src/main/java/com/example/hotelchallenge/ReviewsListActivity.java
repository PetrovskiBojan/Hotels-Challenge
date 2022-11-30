package com.example.hotelchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Adapters.ReviewAdapter;
import com.example.VAO.Hotel;
import com.example.VAO.Review;
import com.example.Interfaces.ReviewViewInterface;
import com.example.VAO.User;

import java.util.ArrayList;
import java.util.Collections;

public class ReviewsListActivity extends AppCompatActivity implements ReviewViewInterface {

    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    Hotel hotel;
    ArrayList<Review> reviews;
    Button back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_list);

        recyclerView = findViewById(R.id.reviewsList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Get the hotel where we want to add reviews
        hotel = (Hotel) getIntent().getSerializableExtra("reviews");

        //Check if the hotel already has reviews
        if(!hotel.getReviews().isEmpty()) {
            reviews = hotel.getReviews();
        }
        else reviews = new ArrayList<Review>();

        reviewAdapter = new ReviewAdapter(this,reviews,this);
        recyclerView.setAdapter(reviewAdapter);

        back = findViewById(R.id.finishButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //On click open the detailed view and pass the parameters
    @Override
    public void onIntemClick(int position) {
        Review myReview = reviews.get(position);
        myReview.setHotel(hotel);
        ArrayList<User> likes = myReview.getLikes();
        ArrayList<User> dislikes = myReview.getDislikes();
        if(likes==null){
            likes = new ArrayList<User>();
        }

        if(dislikes==null){
            dislikes = new ArrayList<User>();
        }

        Intent newIntent = new Intent(this, ReviewDetailsActivity.class);

        Collections.sort(likes);
        Collections.sort(dislikes);
        newIntent.putExtra("review",myReview);
        newIntent.putExtra("hotelReviews",hotel);
        startActivity(newIntent);
    }
}