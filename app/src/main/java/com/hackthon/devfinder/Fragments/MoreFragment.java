package com.hackthon.devfinder.Fragments;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hackthon.devfinder.Activities.Authentications;
import com.hackthon.devfinder.MainActivity;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.User;
import com.hackthon.devfinder.UserInfo;
import com.squareup.picasso.Picasso;


public class MoreFragment extends Fragment {




    int SELECT_PICTURE = 200;
    Button signout;

    TextView email ,username,interests,description;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    ImageView image_edit,image;
    Uri selectedImage;
    Button saveimg;

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
        ImageView editProfile = view.findViewById(R.id.editProfilePhoto);
        auth = FirebaseAuth.getInstance();
        signout = view.findViewById(R.id.sign_out);
        image_edit = view.findViewById(R.id.edit_pic);
        saveimg = view.findViewById(R.id.saveimg);
        image = view.findViewById(R.id.editProfilePhoto);
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
                        Picasso.get().load(users.getImageUrl()).placeholder(R.drawable.user_profile_img).into(editProfile);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        saveimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
                if(selectedImage != null) {
                    StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();



                                        User user = new User();
                                        user.setImageUrl(imageUrl);


                                        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                                .child("profilePic").setValue(user.getImageUrl()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getContext(),"Profile Updated successfully",Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                });
                            }
                        }
                    });
                }
            }
        });


        return view;
    }


    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                 selectedImage = data.getData();
                if (null != selectedImage) {
                    // update the preview image in the layout
                   image.setImageURI(selectedImage);
                }
            }
        }
    }



    }
