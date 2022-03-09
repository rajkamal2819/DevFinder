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

import com.hackthon.devfinder.R;

public class MoreFragment extends Fragment {


    Button add,cancel,addtag;
    ImageView linkedin,stackoverflow,github;
    Dialog dialog;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        addtag = (Button) getActivity().findViewById(R.id.addTag);
        linkedin = (ImageView) getActivity().findViewById(R.id.linkedin_img);
        stackoverflow = (ImageView) getActivity().findViewById(R.id.stackoverflow_img);
        github = (ImageView) getActivity().findViewById(R.id.github_img);

        //Toast.makeText(getContext(), "On create view", Toast.LENGTH_SHORT).show();

//        dialog = new Dialog(view.getContext());
//        dialog.setContentView(R.layout.dialog_addtag);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        add = dialog.findViewById(R.id.btn_add);
//        cancel = dialog.findViewById(R.id.btn_cancel);
//
//
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Canceld", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        addtag = addtag.findViewById(R.id.addTag);
//        addtag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.show();
//            }
//        });



        return view;
    }
}