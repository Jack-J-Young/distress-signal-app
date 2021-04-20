package com.example.mu_cs335_21_2pl_a_zejjj.sender;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Message {
     protected Map<String, Object> data;


    public Message() {
        Map<String, Object> data = new HashMap<>();
        data.put("noData", "Don't use general constructor");
        this.data = new HashMap<>();
        this.data.put("sender", "null");
        this.data.put("data", data);
    }

    public void sendToDB(FirebaseFirestore db) {
        db.collection("debug").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("", "Error adding document", e);
            }
        });
    }
}
