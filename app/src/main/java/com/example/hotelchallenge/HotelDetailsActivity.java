package com.example.hotelchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.VAO.Hotel;
import com.example.hotelchallenge.databinding.ActivityHotelViewBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class HotelDetailsActivity extends AppCompatActivity implements Serializable {
    ActivityHotelViewBinding binding;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.hotelName.setText(getIntent().getStringExtra("name"));
        binding.hotelDescription.setText(getIntent().getStringExtra("description"));
        binding.hotelLatitude.setText(getIntent().getStringExtra("latitude"));
        binding.hotelLongitude.setText(getIntent().getStringExtra("longitude"));

        //Show all reviews for the hotel passed
        binding.toReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddReviewActivity.class);
                Hotel hotel =(Hotel) getIntent().getSerializableExtra("hotel");
                intent.putExtra("hotel",hotel);
                startActivity(intent);
            }
        });

        //Get the hotel image from database
        binding.getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create progress dialog so user know that process is running
                progressDialog= new ProgressDialog(HotelDetailsActivity.this);
                progressDialog.setTitle("Loading image");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String hotelName=binding.hotelName.getText().toString();
                storageReference= FirebaseStorage.getInstance().getReference("images/"+ hotelName);

                try {
                    File localFile = File.createTempFile("tempfile",".jpeg");
                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            //Close the dialog and show image
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                binding.hotelImage.setImageBitmap(bitmap);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Close the dialog and inform user about failure
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Toast.makeText(HotelDetailsActivity.this,"Failed to load image",Toast.LENGTH_SHORT);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Show the view for all reviews of the hotel
        binding.toReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), ReviewsListActivity.class);
                Hotel hotel =(Hotel) getIntent().getSerializableExtra("hotel");
                next.putExtra("reviews",hotel);
                startActivity(next);
            }
        });
    }
}