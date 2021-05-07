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

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

}   