package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.VAO.User;
import com.example.hotelchallenge.R;
import com.example.hotelchallenge.databinding.UserItemBinding;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private UserItemBinding binding;
    Context context;
    ArrayList<User> users;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());
    }

    //Get the number of items in view
    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView username,email;//,password;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            //Show parameters
            username= itemView.findViewById(R.id.usernameRV);
            email= itemView.findViewById(R.id.emailRV);
        }
    }

}
