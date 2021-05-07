package com.example.mu_cs335_21_2pl_a_zejjj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class userLogin extends AppCompatActivity {

    Button regbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
    }

    public void signup(View v) {
        Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_userRegister2);
    }

    public void login(View v) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = ((EditText) this.findViewById(R.id.enter_email_login)).getText().toString().trim();
        String password = ((EditText) this.findViewById(R.id.enter_password)).getText().toString().trim();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_mainActivity);
                }
            }
        });
    }
}