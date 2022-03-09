package com.hackthon.devfinder.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hackthon.devfinder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {


    Button add,cancel,addtag;
    ImageView linkedin,stackoverflow,github;
    Dialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        addtag = add.findViewById(R.id.addTag);
        Toast.makeText(getContext(), "On create", Toast.LENGTH_SHORT).show();
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

      /*  addtag = (Button) getActivity().findViewById(R.id.addTag);
        linkedin = (ImageView) getActivity().findViewById(R.id.linkedin_img);
        stackoverflow = (ImageView) getActivity().findViewById(R.id.stackoverflow_img);
        github = (ImageView) getActivity().findViewById(R.id.github_img);*/

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