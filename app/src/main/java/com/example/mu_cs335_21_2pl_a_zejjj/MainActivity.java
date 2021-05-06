package com.example.mu_cs335_21_2pl_a_zejjj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ConstraintHorizontalLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mu_cs335_21_2pl_a_zejjj.classes.DBManager;
import com.example.mu_cs335_21_2pl_a_zejjj.fragments.AccountFragment;
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
                            AccountFragment frag = (AccountFragment) FragmentManager.findFragment(v);
                            frag.updateList(v);
                        }
                    }
                }
            }
        });
    }

    public void addxml(View v) {
        //ConstraintLayout view = (ConstraintLayout) LayoutInflater.from(this).inflate(R.layout.fragment_account, null);

        /*ConstraintLayout myButton = (ConstraintLayout) v.findViewById(R.id.ognumber);
        ConstraintLayout numberContainer = new ConstraintLayout(this);
        ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
        numberContainer.setLayoutParams(lp);
        numberContainer.setBackgroundColor(0x11FF11);
        numberContainer.setMinimumHeight(100);
        numberContainer.setMinimumWidth(100);
        LayoutInflater.from(getBaseContext()).inflate(R.layout.ognumber, null);*/

        /* <androidx.constraintlayout.widget.ConstraintLayout
                android:id="@+id/ognumber"
                android:layout_width="match_parent"
                android:layout_height="60dp"
                android:background="#FFC9C9">

                <com.google.android.material.chip.Chip
                    android:id="@+id/chip2"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginEnd="8dp"
                    android:onClick="addxml"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintEnd_toEndOf="parent"
                    app:layout_constraintTop_toTopOf="parent" />
            </androidx.constraintlayout.widget.ConstraintLayout>*/
        /*ConstraintLayout numberContainer = new ConstraintLayout(this);
        //numberContainer.defa
        numberContainer.setLayoutParams(new ConstraintLayout.LayoutParams(100, 100));
        numberContainer.setBackgroundColor(0xFFC9C9);


        LinearLayout mainView = (LinearLayout)this.findViewById(R.id.numberContainer);
        mainView.addView(numberContainer);
        mainView.invalidate();
        Snackbar.make(v, mainView.getChildCount() + "", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
    }

    public void logout (View v) {
        FirebaseAuth.getInstance().signOut();
    }

}   