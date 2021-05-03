package com.example.mu_cs335_21_2pl_a_zejjj.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mu_cs335_21_2pl_a_zejjj.GPSTrack;
import com.example.mu_cs335_21_2pl_a_zejjj.R;

public class HomeFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0;
    EditText etPhone;
    Button btSend;

    GPSTrack gps;

    double latitude;
    double longitude;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        etPhone = view.findViewById(R.id.enter_Phone);
        btSend = view.findViewById(R.id.emergency_button);

        gps = new GPSTrack(this.getView().getContext());



        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        //PERMS
        if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //When permission is granted
            //Create Method

            gps.getLocation();
        }
        else{
            //When permission is not granted
            //Request Permission
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }

    public void emergencyButton(View v) {
        //Check condition
        if(ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            //When permission is granted
            //Create Method

            sendMessage();
        }
        else{
            //When permission is not granted
            //Request Permission
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.SEND_SMS}, 100);
        }
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
                Toast.makeText(gps.getApplicationContext(), "SMS Sent Successfully", Toast.LENGTH_LONG).show();
            }
        }
        else {
            //When edit text value is blank
            //Display toast
            Toast.makeText(gps.getApplicationContext(), "Enter Value First", Toast.LENGTH_SHORT).show();
        }
    }

}
