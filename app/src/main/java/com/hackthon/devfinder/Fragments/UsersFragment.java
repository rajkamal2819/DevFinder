package com.hackthon.devfinder.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackthon.devfinder.Adapters.UserAdapter;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.User;

import java.util.ArrayList;

public class UsersFragment extends Fragment {

    FirebaseUser firebaseUser;
   ArrayList<User> userArray = new ArrayList<>();
   RecyclerView rv;
    FirebaseAuth auth;
    FirebaseDatabase database;
    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        rv = view.findViewById(R.id.recyclerViewUser);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Uses");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for(DataSnapshot snapshots : snapshot.getChildren()){
                      User users = snapshot.getValue(User.class);
                      userArray.add(users);
                  }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        UserAdapter ua = new UserAdapter(userArray,rv,view.getContext());
        rv.setAdapter(ua);
        rv.setLayoutManager( new LinearLayoutManager(view.getContext())) ;
        ua.notifyDataSetChanged();
        return view;
    }
}