package com.hackthon.devfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackthon.devfinder.Fragments.HomeFragment;
import com.hackthon.devfinder.databinding.ActivityUserDetailsBinding;
import com.hackthon.devfinder.databinding.ActivityUserInfoBinding;

public class UserInfo extends AppCompatActivity {
Button signout;
    FirebaseUser firebaseUser;
    TextView email ,username,interests,description;

    FirebaseAuth auth;
    FirebaseDatabase database;
    ActivityUserInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_user_info);
         email = findViewById(R.id.email);
         username = findViewById(R.id.username1);
         interests = findViewById(R.id.interest);
        auth = FirebaseAuth.getInstance();
        signout = findViewById(R.id.sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(UserInfo.this, HomeFragment.class);
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

    }
}