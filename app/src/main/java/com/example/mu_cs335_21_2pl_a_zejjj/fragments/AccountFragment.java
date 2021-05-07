package com.example.mu_cs335_21_2pl_a_zejjj.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mu_cs335_21_2pl_a_zejjj.NumberAdapter;
import com.example.mu_cs335_21_2pl_a_zejjj.R;
import com.example.mu_cs335_21_2pl_a_zejjj.classes.DBManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);


        ImageView user_icon = v.findViewById(R.id.user_image);
        TextView user_name = v.findViewById(R.id.name_container);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user_name.setText("Login to access user data");
        if (user == null) {
            user_icon.setImageResource(R.drawable.common_google_signin_btn_icon_dark_normal);
            user_name.setText("Login to access user data");
        } else {
            user_icon.setImageResource(R.drawable.common_google_signin_btn_icon_light_normal);
            user_name.setText("...");

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String uid = "";
            try {
                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            } catch (Exception e) {
                uid = "aBcDeFgH1234";
            }
            DocumentReference document = db.collection("users").document(uid);


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
                        if (snap_document.exists()) {
                            Map<String, Object> data = snap_document.getData();
                            if (data.containsKey("first_name") && data.containsKey("surname")) {
                                user_name.setText(data.get("first_name") + " " + data.get("surname"));
                            }
                        }
                    }
                }
            });
        }

        return v;
    }
}