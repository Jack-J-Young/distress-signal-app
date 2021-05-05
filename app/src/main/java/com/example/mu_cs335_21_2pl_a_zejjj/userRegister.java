package com.example.mu_cs335_21_2pl_a_zejjj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class userRegister extends AppCompatActivity {

    private String gender;
    private static final String TAG = "";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mDisplayDate = (TextView) findViewById(R.id.select_dob);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        userRegister.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDate: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        //linking to authentication_code
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(userRegister.this, authnticationCode.class));
            }
        });
    }
}