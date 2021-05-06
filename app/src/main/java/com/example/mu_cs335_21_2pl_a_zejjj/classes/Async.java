package com.example.mu_cs335_21_2pl_a_zejjj.classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Async extends Thread {
    public boolean hold = false;
    FirebaseFirestore db;
    String collection;
    Map<String, Object> data;
    public Async(FirebaseFirestore db, String collection) {
        this.db = db;
        this.collection = collection;
    }

    public void run() {
        db.collection(collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data = document.getData();
                                hold = true;
                                Log.d("SYNC", document.getId() + " => " + document.getData());
                            }
                        } else {
                            hold = true;
                            Log.w("SYNC", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
