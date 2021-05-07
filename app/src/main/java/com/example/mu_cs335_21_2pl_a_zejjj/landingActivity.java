package com.example.mu_cs335_21_2pl_a_zejjj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class landingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(landingActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void login(View v) {
        Navigation.findNavController(v).navigate(R.id.action_landingFragment_to_userLogin);
    }

    public void signup(View v) {
        Navigation.findNavController(v).navigate(R.id.action_landingFragment_to_userRegister);
    }
}
