package com.example.mu_cs335_21_2pl_a_zejjj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mu_cs335_21_2pl_a_zejjj.fragments.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.MyViewHolder> {
    List<String> numbers;

    public NumberAdapter(List<String> numbers) {
        this.numbers = numbers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        MyViewHolder pv = new MyViewHolder(view);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.image.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
        holder.text.setText(numbers.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = "";
                try {
                    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                } catch (Exception e) {
                    uid = "aBcDeFgH1234";
                }
                DocumentReference document = FirebaseFirestore.getInstance().collection("users").document(uid);

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
                                if (data.containsKey("contacts")) {
                                    List<String> contacts = (List<String>) data.get("contacts");
                                    contacts.remove(position);
                                    FirebaseFirestore.getInstance().collection("users").document(uid).update("contacts", contacts);
                                    AccountFragment frag = (AccountFragment) FragmentManager.findFragment(v);
                                    frag.updateList(v);
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView1);
            text = itemView.findViewById(R.id.textView1);
            button = itemView.findViewById(R.id.remove_btn);
        }
    }
}
