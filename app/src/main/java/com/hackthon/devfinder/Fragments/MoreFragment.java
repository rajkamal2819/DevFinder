package com.hackthon.devfinder.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.hackthon.devfinder.MainActivity;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.UserInfo;
import com.hackthon.devfinder.databinding.FragmentMoreBinding;


public class MoreFragment extends Fragment {


    Button add,cancel,addtag;
    ImageView linkedin,stackoverflow,github;
    Dialog dialog;
   Button m;
   FragmentMoreBinding binding;

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
        binding = FragmentMoreBinding.inflate(getLayoutInflater());

       /* m = view.findViewById(R.id.user);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(getContext(), UserInfo.class);
                startActivity(x);

            }
        });*/

        binding.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),UserInfo.class));
            }
        });

        return binding.getRoot();
    }
}