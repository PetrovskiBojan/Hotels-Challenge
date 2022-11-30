package com.example.hotelchallenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.VAO.Hotel;
import com.example.hotelchallenge.databinding.ActivityHotelBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;

public class AddHotelActivity extends AppCompatActivity implements Serializable {
    ActivityHotelBinding binding;
    String name,description,latitude,longitude;
    FirebaseDatabase database;
    DatabaseReference reference;
    Uri imageURI;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Add new hotel
        binding.addHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=binding.nameInput.getText().toString();
                description=binding.descriptionInput.getText().toString();
                latitude=binding.latitudeInput.getText().toString();
                longitude=binding.longitudeInput.getText().toString();

                //Check the user input and add new hotel in database
                if(!name.isEmpty() && !description.isEmpty() && !latitude.isEmpty() && !longitude.isEmpty()){
                    Hotel newHotel = new Hotel(name,description,latitude,longitude);

                    //Get the database instance in firebase
                    database = FirebaseDatabase.getInstance();
                    //Get the hotels table from database
                    reference = database.getReference("Hotels");

                    //Add new hotel in database
                    reference.child(name).setValue(newHotel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            binding.nameInput.setText("");
                            binding.descriptionInput.setText("");
                            binding.latitudeInput.setText("");
                            binding.longitudeInput.setText("");
                            Toast.makeText(AddHotelActivity.this,"Successfuly Added a Hotel",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddHotelActivity.this,HotelsListActivity.class));
                        }
                    });
                }
            }
        });
        //Open the selecting image window
        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        //Open the selecting image window
        binding.uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Upload the selected image from storage
    private void uploadImage() {
        storageReference= FirebaseStorage.getInstance().getReference("images/"+binding.nameInput.getText().toString());
        //Create progress dialog while uploading file
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Uploading file...");
        progressDialog.show();

        //Put the image in firebase storage
        storageReference.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddHotelActivity.this,"Successfully uploaded",Toast.LENGTH_SHORT).show();
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(AddHotelActivity.this,"Failed to upload",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Select image from storage
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    //Check for the activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && data !=null && data.getData() !=null){
            imageURI = data.getData();
            binding.imageView.setImageURI(imageURI);
        }
    }
}