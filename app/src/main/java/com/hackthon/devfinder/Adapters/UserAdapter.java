package com.hackthon.devfinder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackthon.devfinder.Models.RepositoryMod;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.User;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ArrayList<com.hackthon.devfinder.User> list;

    private RecyclerView recyclerView;
    private Context context;
    public UserAdapter(ArrayList<User> list, RecyclerView recyclerView, Context context) {
        this.list = list;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        holder.setDetails(list.get(position));
    }

    @Override
    public int getItemCount() {
       return list.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView profileimage;
        TextView username;
        TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            profileimage = itemView.findViewById(R.id.dp);
            username = itemView.findViewById(R.id.usernm);
            description = itemView.findViewById(R.id.description);

        }

        public void setDetails(User model){
           username.setText(model.getUsername());

        }

    }



}
