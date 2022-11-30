package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.VAO.Hotel;

import com.example.Interfaces.HotelViewInterface;
import com.example.hotelchallenge.R;


import java.io.Serializable;
import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyViewHolder> implements Serializable {

    private final HotelViewInterface hotelViewInterface;
    Context context;
    ArrayList<Hotel> hotels;

    public HotelAdapter(Context context, ArrayList<Hotel> hotels,HotelViewInterface hotelViewInterface) {
        this.context = context;
        this.hotels = hotels;
        this.hotelViewInterface=hotelViewInterface;
    }

    @NonNull
    @Override
    public HotelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotel_item, parent, false);
        return new HotelAdapter.MyViewHolder(view,hotelViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.MyViewHolder holder, int position) {

        Hotel hotel = hotels.get(position);
        holder.name.setText(hotel.getName());
        holder.description.setText(hotel.getDescription());

    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,description;

        public MyViewHolder(@NonNull View itemView, HotelViewInterface hotelViewInterface){
            super(itemView);
            //Show hotel parameters in view
            name= itemView.findViewById(R.id.nameRV);
            description= itemView.findViewById(R.id.descriptionRV);

            // Set on click listener that opens the detailed view for the clicked hotel
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hotelViewInterface !=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            hotelViewInterface.onIntemClick(position);
                        }
                    }
                }
            });

        }
    }
}
