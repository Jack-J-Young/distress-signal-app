package com.example.mu_cs335_21_2pl_a_zejjj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView1);
            text = itemView.findViewById(R.id.textView1);
        }
    }
}
