package com.hackthon.devfinder.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackthon.devfinder.MainActivity;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.User;

import java.util.ArrayList;


public class Interests extends AppCompatActivity {

    Button add,done;
    ChipGroup chipGroup;
    EditText desc,tag;
    ArrayList<String> tags;
    String description;
    private DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    Button btn;
    String interest;
    public Interests() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        add =  findViewById(R.id.add);
        done =  findViewById(R.id.btn_done);
        chipGroup = findViewById(R.id.tag_group);
        desc = findViewById(R.id.editTextTextPostalAddress);
        tag = findViewById(R.id.interest_edit);
        tags = new ArrayList<String>();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tags.add(tag.getText().toString());
                Chip chip = new Chip(getApplicationContext());

                ChipDrawable drawable = ChipDrawable.createFromAttributes(getApplicationContext(),null,
                        0,R.style.Widget_MaterialComponents_Chip_Entry);

                chip.setChipDrawable(drawable);
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setPadding(10,10,60,10);
                chip.setText(tag.getText().toString());
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chipGroup.removeView(chip);
                    }
                });
                chipGroup.addView(chip);
                tag.setText("");
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description=desc.getText().toString();
            }
        });

        for(int i =0;i<tags.size();i++)
        {
            interest = interest+tags.toString();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((description!=null)&&(interest!=null))
                {
                    writeNewUser(description,interest);
                }

            }
        });
    }

    public void writeNewUser(String description,String list) {


        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase.child("Users").child(user.getUid()).child("description").setValue(description);
        mDatabase.child("Users").child(user.getUid()).child("interest").setValue(list);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }



}