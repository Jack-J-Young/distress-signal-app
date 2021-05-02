package com.example.mu_cs335_21_2pl_a_zejjj.sender;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    protected Map<String, Object> data;
    protected String collection;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DataBase() {
        Map<String, Object> data = new HashMap<>();
        data.put("noData", "Don't use general constructor");
        this.data = new HashMap<>();
        this.data.put("sender", "null");
        this.data.put("data", data);
        collection = "debug";
    }

    public DataBase(Map<String, Object> data, String collection) {
        this.data = data;
        this.collection = collection;
    }

    public void sendData() {
        db.collection(collection).add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("DB", "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("DB", "Error adding document", e);
            }
        });
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public void setDB(FirebaseFirestore db) {
        this.db = db;
    }
}
