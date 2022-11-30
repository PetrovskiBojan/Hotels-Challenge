package com.example.hotelchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.Adapters.UserAdapter;
import com.example.VAO.Hotel;
import com.example.VAO.Review;
import com.example.VAO.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

        RecyclerView recyclerView;
        DatabaseReference database;
        UserAdapter userAdapter;
        ArrayList<User> users;
        Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        //Get the view where you show the items
        recyclerView = findViewById(R.id.usersList);
        //Get the users location in database
        database = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        users = new ArrayList<>();
        //Get the review for which you want to see users
        review = (Review) getIntent().getSerializableExtra("passedReview");

        //Get the hotel for which you want to see reviews
        Hotel tempHotel = (Hotel) getIntent().getSerializableExtra("hotelReview");
        review.setHotel(tempHotel);

        // Check if the user want to see likes or dislikes and show them
        Boolean likes = getIntent().getBooleanExtra("likes",false);
        if(likes==true) {
            if (review.getLikes() != null) {
                users = review.getLikes();
            } else users = new ArrayList<User>();
            userAdapter = new UserAdapter(this, users);
            recyclerView.setAdapter(userAdapter);
        }
        else  {
            if (review.getDislikes() != null) {
                users = review.getDislikes();
            } else users = new ArrayList<User>();
            userAdapter = new UserAdapter(this, users);
            recyclerView.setAdapter(userAdapter);
        }

    }
}