package com.example.mu_cs335_21_2pl_a_zejjj;


import com.example.mu_cs335_21_2pl_a_zejjj.sender.DataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.Manifest.*;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


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

        mAuth = FirebaseAuth.getInstance();
        //Assign Variable
        etPhone = findViewById(R.id.enter_Phone);
        btSend = findViewById(R.id.emergency_button);

        gps = new GPSTrack(this);



        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        //PERMS
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

        //Phone verif
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d("PHONE", "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("PHONE", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("PHONE", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
    }

    public void emergencyButton(View v) {
        //Check condition
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
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

    public void register(View v) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("FID", "aaa");

        DataBase db = new DataBase(userData, "users");
        db.sendData();
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
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, sPhone);
            signInWithPhoneAuthCredential(credential);
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

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        FirebaseAuth.getInstance().getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("PHONE", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("PHONE", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}