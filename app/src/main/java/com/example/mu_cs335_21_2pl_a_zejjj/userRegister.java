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
    }

    public void register(View v) {
        // get user details
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String first_name = ((EditText) this.findViewById(R.id.enter_fname)).getText().toString().trim();
        String surname = ((EditText) this.findViewById(R.id.enter_sname)).getText().toString().trim();
        String email = ((EditText) this.findViewById(R.id.enter_email)).getText().toString().trim();
        String password = ((EditText) this.findViewById(R.id.enter_password)).getText().toString().trim();
        String gender = /*((EditText) this.findViewById(R.id.enter_gender)).getText().toString().trim()*/ "WIP";

        // log user into firebase auth
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener() {

            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // add extra user data to the db
                                addUserInfo(first_name, surname, email, gender);
                            }
                        }
                    });
                }
            }
        });
    }

    /* addUserInfo: adds extra info into db */
    private void addUserInfo(String first_name, String surname, String email, String gender) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // get uid
        String uid = "";
        try {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (Exception e) {
            uid = "aBcDeFgH1234";
        }
        DocumentReference document = db.collection("users").document(uid);

        // create user document
        Map<String, Object> data = new HashMap<>();
        data.put("uid", uid);
        data.put("first_name", first_name);
        data.put("surname", surname);
        data.put("email", email);
        data.put("gender", gender);
        data.put("contacts", new LinkedList<String>());

        // set document in db
        document.set(data);
    }

}