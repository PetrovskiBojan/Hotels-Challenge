package com.example.hotelchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.Adapters.HotelAdapter;
import com.example.VAO.Hotel;
import com.example.Interfaces.HotelViewInterface;
import com.example.hotelchallenge.databinding.ActivityHotelsListBinding;
import com.example.hotelchallenge.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class HotelsListActivity extends AppCompatActivity implements HotelViewInterface, Serializable {

    RecyclerView recyclerView;
    DatabaseReference database;
    HotelAdapter hotelAdapter;
    ArrayList<Hotel> hotels;
    ActivityHotelsListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.hotelsList);

        //Get the hotels table from database
        database = FirebaseDatabase.getInstance().getReference("Hotels");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.newHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(getApplicationContext(), AddHotelActivity.class);
                startActivity(reg);
            }
        });

        hotels = new ArrayList<>();

        //Create adapter that will show pass and show hotels in recyclerview
        hotelAdapter = new HotelAdapter(this,hotels,this);
        recyclerView.setAdapter(hotelAdapter);

        //For every change in databse, update the list view
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hotels.clear();

                //Add hotels to the view
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Hotel hotel = dataSnapshot.getValue(Hotel.class);
                    hotels.add(hotel);
                    Collections.sort(hotels);
                }
                hotelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //On click open the detailed hotel view passing the hotel clicked
    @Override
    public void onIntemClick(int position) {
        Hotel myHotel = hotels.get(position);
        Intent newIntent = new Intent(this, HotelDetailsActivity.class);
        newIntent.putExtra("name",myHotel.getName());
        newIntent.putExtra("description",myHotel.getDescription());
        newIntent.putExtra("latitude",myHotel.getLatitude());
        newIntent.putExtra("longitude",myHotel.getLongitute());
        newIntent.putExtra("hotel",myHotel);
        startActivity(newIntent);
    }
}