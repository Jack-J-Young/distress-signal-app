package com.example.mu_cs335_21_2pl_a_zejjj.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mu_cs335_21_2pl_a_zejjj.R;

public class LoginFragment extends Fragment {
    private int mContainerId;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private final static String TAG = "DashBoardActivity";

    private FragmentManager supportFragmentManager;



    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }

}
