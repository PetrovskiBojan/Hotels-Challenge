package com.example.hotelchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.VAO.User;
import com.example.hotelchallenge.databinding.ActivityRegistrationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    ActivityRegistrationBinding binding;
    String username,email,password;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //get the firebase authentication service
        mAuth = FirebaseAuth.getInstance();

        //Register user
        binding.registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=binding.usernameInput.getText().toString();
                email=binding.emailInput.getText().toString();
                password=binding.passwordInput.getText().toString();

                //Check the user input and register
                if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    User newUser = new User(username,email,password);
                    mAuth.createUserWithEmailAndPassword(email,password);
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Users");
                    reference.child(username).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            binding.usernameInput.setText("");
                            binding.emailInput.setText("");
                            binding.passwordInput.setText("");

                            Toast.makeText(RegistrationActivity.this,"Successfuly Registered",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                else
                    Toast.makeText(RegistrationActivity.this,"Invalid input",Toast.LENGTH_SHORT).show();
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}