package com.example.hotelchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hotelchallenge.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //get the firebase authentication service
        mAuth= FirebaseAuth.getInstance();

        //User login
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = binding.inputEmail.getText().toString().trim();
                String inputPassword = binding.inputPassword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    //Check if the user successfuly logged in
                        if (task.isSuccessful()){
                            Intent logged = new Intent(getApplicationContext(),HotelsListActivity.class);
                            Toast.makeText(LoginActivity.this,"Seccessfuly logged in!",Toast.LENGTH_SHORT).show();
                            startActivity(logged);
                        }
                        else
                            Toast.makeText(LoginActivity.this,"Failed to log in, check credentials!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}