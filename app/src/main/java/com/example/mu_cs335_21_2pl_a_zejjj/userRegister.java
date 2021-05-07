package com.example.mu_cs335_21_2pl_a_zejjj;

import androidx.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.TextView;

import com.example.mu_cs335_21_2pl_a_zejjj.classes.DBManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.remote.TokenResult;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

        /*//mDisplayDate = (TextView) findViewById(R.id.select_dob);

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
        });*/
    }

    public void register(View v) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String first_name = ((EditText) this.findViewById(R.id.enter_fname)).getText().toString().trim();
        String surname = ((EditText) this.findViewById(R.id.enter_sname)).getText().toString().trim();
        String email = ((EditText) this.findViewById(R.id.enter_email)).getText().toString().trim();
        String password = ((EditText) this.findViewById(R.id.enter_password)).getText().toString().trim();
        String gender = /*((EditText) this.findViewById(R.id.enter_gender)).getText().toString().trim()*/ "WIP";
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {

            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                addUserInfo(first_name, surname, email, gender);
                            }
                        }
                    });
                }
            }
        });
    }

    private void addUserInfo(String first_name, String surname, String email, String gender) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = "";
        try {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (Exception e) {
            uid = "aBcDeFgH1234";
        }
        DocumentReference document = db.collection("users").document(uid);

        Map<String, Object> data = new HashMap<>();
        data.put("uid", uid);
        data.put("first_name", first_name);
        data.put("surname", surname);
        data.put("email", email);
        data.put("gender", gender);
        data.put("contacts", new LinkedList<String>());

        document.set(data);
    }

}