package com.example.mu_cs335_21_2pl_a_zejjj.classes;

import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class DBManager {
    public FirebaseFirestore db;
    protected String collection;
    public Map<String, Object> data;
    protected Async readasync;
    public DBManager(FirebaseFirestore db, String collection) {
        this.db = db;
        this.collection = collection;
    }

    /*public Map<String, Object> read(String TAG) {
        Log.d("SYNC", "DEBUG");

        db.collection(collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                data = document.getData();
                                Log.d("SYNC", document.getId() + " => " + data);
                            }
                        } else {
                            Log.w("SYNC", "Error getting documents.", task.getException());
                        }
                    }
                });
        return data;

        readasync = new Async(db, collection);
        readasync.start();
        while (!readasync.hold) {
            SystemClock.sleep(100);}
        return readasync.data;
    }*/

    public void add(Object o, String target) {

    }

    public void write(Map<String, Object> data, String TAG) {
        db.collection(collection)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
