package com.example.hotelchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.VAO.Hotel;
import com.example.VAO.Review;
import com.example.hotelchallenge.databinding.ActivityAddReviewBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class AddReviewActivity extends AppCompatActivity implements Serializable {
    ActivityAddReviewBinding binding;
    FirebaseDatabase database;
    DatabaseReference reference;
    Hotel hotel;
    String author,description;
    int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Get the hotels table from database
        hotel=(Hotel)getIntent().getSerializableExtra("hotel");
        binding.hotelReview.setText(hotel.getName());

        //Add review for the hotel passed
        binding.addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotel = (Hotel) getIntent().getSerializableExtra("hotel");
                author = binding.authorReview.getText().toString();
                rating = Integer.parseInt( binding.ratingReview.getText().toString());
                description = binding.descriptionReview.getText().toString();

                //Check the user input for new review and add new review
                if(!author.isEmpty() && !description.isEmpty() && rating >0 && hotel != null){
                    Review review = new Review(hotel,author,description,rating);
                    Hotel newHotel = new Hotel(hotel.getName(),hotel.getDescription(),hotel.getLongitute(),hotel.getLatitude());
                    newHotel.addReview(review);
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Hotels");
                    String name = newHotel.getName();
                    reference.child(name).setValue(newHotel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            hotel.addReview(review);
                            Toast.makeText(AddReviewActivity.this,"Successfuly Added",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddReviewActivity.this,HotelsListActivity.class));
                        }
                    });
                }

            }
        });

        binding.backReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}