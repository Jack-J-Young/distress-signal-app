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

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }
}
