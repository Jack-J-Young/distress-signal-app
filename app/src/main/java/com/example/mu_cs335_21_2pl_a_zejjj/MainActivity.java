package com.example.mu_cs335_21_2pl_a_zejjj;


import com.google.firebase.firestore.FirebaseFirestore;
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
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        etPhone = findViewById(R.id.enter_Phone);
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
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.SEND_SMS}, 100);
                }

            }


        });
    }



    private void sendMessage(){
        gps.getLocation();
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        //Get values from edit text
        String sPhone = etPhone.getText().toString().trim();
        String sMessage = "Testing, My Location is: http://maps.google.com/?q="+ latitude +","+longitude;

        //Check Condition
        if (!sPhone.equals("")){
            //When both edit text value so not equal to blank
            //Initialise sms manager
            SmsManager smsManager = SmsManager.getDefault();



            if(gps.canGetLocation() == true)
            {
                longitude = gps.getLongitude();
                latitude = gps.getLatitude();

                //Send text
                smsManager.sendTextMessage(sPhone, null, sMessage, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent Successfully", Toast.LENGTH_LONG).show();
            }
        }
        else {
            //When edit text value is blank
            //Display toast
            Toast.makeText(getApplicationContext(), "Enter Value First", Toast.LENGTH_SHORT).show();
        }
    }

}