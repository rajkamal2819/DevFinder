package com.hackthon.devfinder.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackthon.devfinder.Activities.Authentications;
import com.hackthon.devfinder.MainActivity;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.User;
import com.hackthon.devfinder.UserInfo;


public class MoreFragment extends Fragment {

    Button signout;
    FirebaseUser firebaseUser;
    TextView email ,username,interests,description;

    FirebaseAuth auth;
    FirebaseDatabase database;

    public MoreFragment() {
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
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username1);
        interests = view.findViewById(R.id.interest);
        description = view.findViewById(R.id.description);
        auth = FirebaseAuth.getInstance();
        signout = view.findViewById(R.id.sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();

                Intent i = new Intent(getContext(), Authentications.class);
                startActivity(i);
            }
        });

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User users = snapshot.getValue(User.class);
                        /*  Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.user_profile_img).into(binding.editProfilePhoto);*/
                        email.setText(users.getEmail());
                        username.setText(users.getUsername());
                        interests.setText(users.getInterests());
                        description.setText(users.getDescription());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return view;
    }



    }
