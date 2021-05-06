package com.example.mu_cs335_21_2pl_a_zejjj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class userLogin extends AppCompatActivity {

    Button regbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //linking to register.form
        regbutton = findViewById(R.id.regbutton);

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userLogin.this, userRegister.class);
                startActivity(intent);
            }
        });
    }
}