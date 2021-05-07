package com.example.mu_cs335_21_2pl_a_zejjj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mu_cs335_21_2pl_a_zejjj.classes.DBManager;
import com.example.mu_cs335_21_2pl_a_zejjj.fragments.ContactsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.Manifest.*;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0;
    EditText etPhone;
    Button btSend;

    GPSTrack gps;

    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Assign Variable
        btSend = findViewById(R.id.emergency_button);

        gps = new GPSTrack(this);


        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if(ContextCompat.checkSelfPermission(MainActivity.this, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //When permission is granted
            //Create Method

            gps.getLocation();
        }
        else{
            //When permission is not granted
            //Request Permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.ACCESS_FINE_LOCATION}, 100);
        }









        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check condition
                if(ContextCompat.checkSelfPermission(MainActivity.this, permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create Method

                    sendMessage();
                }
                else{
                    //When permission is not granted
                    //Request Permission
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }

            }


        });


        BottomNavigationView navView = findViewById(R.id.nav_home);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_account, R.id.navigation_home, R.id.navigation_contacts)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void debugDB(View v) {
        DBManager db = new DBManager(FirebaseFirestore.getInstance(), "users");
        String uid = "";
        try {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (Exception e) {
            uid = "aBcDeFgH1234";
        }
        DocumentReference document = db.db.collection("users").document(uid);


        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String uid = "";
                try {
                    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                } catch (Exception e) {
                    uid = "aBcDeFgH1234";
                }
                if (task.isSuccessful()) {
                    DocumentSnapshot snap_document = task.getResult();
                    if (!snap_document.exists()) {
                        Map<String, Object> data = new HashMap<>();
                        data.put("uid", uid);
                        data.put("contacts", new LinkedList<String>());
                        document.set(data);
                    } else {
                        Map<String, Object> data = snap_document.getData();
                        if (data.containsKey("contacts")) {
                            List<String> contacts = (List<String>) data.get("contacts");
                            String number = ((EditText) findViewById(R.id.addcontact)).getText().toString().trim();
                            contacts.add(number);
                            db.db.collection("users").document(uid).update("contacts", contacts);
                            ContactsFragment frag = (ContactsFragment) FragmentManager.findFragment(v);
                            frag.updateList(v);
                        }
                    }
                }
            }
        });
    }

    public void logout (View v) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, landingActivity.class);
        startActivity(intent);
    }

    private void sendMessage(){
        gps.getLocation();
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        DBManager db = new DBManager(FirebaseFirestore.getInstance(), "users");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference document = db.db.collection("users").document(uid);
        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snap_document = task.getResult();
                    if (snap_document.exists()) {
                        Map<String, Object> data = snap_document.getData();
                        List<String> contacts = (List<String>)data.get("contacts");
                        for (int i = 0; i < contacts.size(); i++) {
                            String phone = contacts.get(i).trim();

                            String sMessage = "Testing, My Location is: http://maps.google.com/?q=" + latitude + "," + longitude;

                            //Check Condition
                            if (!phone.equals("")) {
                                //When both edit text value so not equal to blank
                                //Initialise sms manager
                                SmsManager smsManager = SmsManager.getDefault();


                                if (gps.canGetLocation() == true) {
                                    longitude = gps.getLongitude();
                                    latitude = gps.getLatitude();

                                    //Send text
                                    smsManager.sendTextMessage(phone, null, sMessage, null, null);
                                    Toast.makeText(getApplicationContext(), "SMS Sent Successfully", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                //When edit text value is blank
                                //Display toast
                                Toast.makeText(getApplicationContext(), "Enter Value First", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
}   