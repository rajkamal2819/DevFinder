package com.hackthon.devfinder.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.hackthon.devfinder.Activities.UserDetails;
import com.hackthon.devfinder.Adapters.UserAdapter;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.User;
import com.hackthon.devfinder.databinding.FragmentUsersBinding;

import java.util.ArrayList;

public class UsersFragment extends Fragment {

    FirebaseUser firebaseUser;
   ArrayList<User> list = new ArrayList<>();
   RecyclerView rv;
    FirebaseAuth auth;
    FirebaseDatabase database;
    public UsersFragment() {
        // Required empty public constructor
    }

    FragmentUsersBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();

        UserAdapter userAdapter = new UserAdapter(list,getContext());
        binding.recyclerViewUser.setAdapter(userAdapter);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
       // Log.i(LOG_TAG,"FireBaseUI: "+firebaseUser.getUid());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerViewUser.setLayoutManager(layoutManager);

        binding.doneSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), UserDetails.class);
                i.putExtra("devName",binding.edittextInterests.getText().toString().trim());
                startActivity(i);
            }
        });

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User users = dataSnapshot.getValue(User.class);
                   // Log.i(LOG_TAG,"UserId: "+users.getUserId());
                    // users.setStatus(false);
                    list.add(users);
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}