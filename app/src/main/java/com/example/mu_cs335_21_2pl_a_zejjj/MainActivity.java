package com.example.mu_cs335_21_2pl_a_zejjj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ConstraintHorizontalLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mu_cs335_21_2pl_a_zejjj.classes.DBManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.remote.TokenResult;

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
                R.id.navigation_notifications, R.id.navigation_home, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void debugDB(View v) {
        DBManager db = new DBManager(FirebaseFirestore.getInstance(), "users");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference document = db.db.collection("users").document(uid);


        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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
                        }
                    }
                }
            }
        });
    }

    public void addxml(View v) {
        //ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.fragment_account, null);

        ConstraintLayout myButton = (ConstraintLayout) v.findViewById(R.id.ognumber);
        ConstraintLayout numberContainer = new ConstraintLayout(this);
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
        numberContainer.setLayoutParams(lp);
        numberContainer.setBackgroundColor(0x11FF11);
        numberContainer.setMinimumHeight(100);
        numberContainer.setMinimumWidth(100);
        LinearLayout mainView = (LinearLayout)this.findViewById(R.id.numberContainer);
        mainView.addView(numberContainer);
        mainView.invalidate();
        Snackbar.make(v, mainView.getChildCount() + "", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void logout (View v) {
        FirebaseAuth.getInstance().signOut();
    }

}   