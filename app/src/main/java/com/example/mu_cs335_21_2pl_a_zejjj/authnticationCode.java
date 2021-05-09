package com.example.mu_cs335_21_2pl_a_zejjj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class authnticationCode extends AppCompatActivity {

    Button submitAuth;

    /* NOT USED */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authntication_code);

        //linking to popupnotification
        submitAuth = findViewById(R.id.submitAuth);

        submitAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(authnticationCode.this, succRegistered.class));
            }
        });
    }
}