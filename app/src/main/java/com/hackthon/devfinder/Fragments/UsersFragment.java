package com.hackthon.devfinder.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    String s;
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
        rv = view.findViewById(R.id.recyclerViewUser);
        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        DatabaseReference dbref = database.getReference(auth.getUid());
        LinearLayoutManager layoutManager = new LinearLayoutManager((getContext()));
        rv.setLayoutManager(layoutManager);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User users = snapshot.getValue(User.class);

                        /*binding.email.setText(users.getEmailId());
                        binding.description.setText(users.getStatus());
                        binding.username.setText(users.getName());*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshots : snapshot.getChildren()){
                    User users = snapshots.getValue(User.class);
                    userArray.add(users);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        UserAdapter ua = new UserAdapter(userArray,getContext());
        rv.setAdapter(ua);

        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();


        return view;
    }
}