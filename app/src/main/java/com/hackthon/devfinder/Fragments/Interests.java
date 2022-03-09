package com.hackthon.devfinder.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.hackthon.devfinder.R;

import java.util.ArrayList;


public class Interests extends Fragment {

    private Button add,done;
    ChipGroup chipGroup;
    EditText desc,tag;
    ArrayList<String> tags;
    String description;

    public Interests() {
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

        View view = inflater.inflate(R.layout.fragment_interests, container, false);

        add =  view.findViewById(R.id.btn_add);
        done =  view.findViewById(R.id.btn_done);
        chipGroup = view.findViewById(R.id.tag_group);
        desc = view.findViewById(R.id.editTextTextPostalAddress);
        tag = view.findViewById(R.id.interest_edit);
        tags = new ArrayList<String>();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tags.add(tag.getText().toString());
                Chip chip = new Chip(view.getContext());

                ChipDrawable drawable = ChipDrawable.createFromAttributes(getContext(),null,
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



        return view;
    }
}