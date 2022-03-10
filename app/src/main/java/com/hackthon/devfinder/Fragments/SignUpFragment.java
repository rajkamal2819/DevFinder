package com.hackthon.devfinder.Fragments;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackthon.devfinder.MainActivity;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.User;
import com.hackthon.devfinder.UserInfo;

import java.util.concurrent.Executor;


public class SignUpFragment extends Fragment {
    private static final String LOG_TAG = "1";
    EditText signup_email, signup_password, signup_username,signup_d,signup_i;
    Button signup;
    String su_email, su_password, su_username;
    private DatabaseReference mDatabase;


    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);
        signup_email = v.findViewById(R.id.emailId_signUp);
        signup_password = v.findViewById(R.id.password_signUp);
        signup_username = v.findViewById(R.id.userName);
        signup_d = v.findViewById(R.id.description);
        signup_i = v.findViewById(R.id.interests);
        signup = v.findViewById(R.id.signUpButton);
        su_email = signup_email.getText().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                su_password = signup_password.getText().toString();
                su_username = signup_username.getText().toString();
                su_email = signup_email.getText().toString();

                if (TextUtils.isEmpty(su_email) || TextUtils.isEmpty(su_password)
                        || TextUtils.isEmpty(su_username)) {
                    Toast.makeText(getView().getContext(), "Please fill all requirements", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(su_email, su_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        User user1 = new User(su_username, su_email, su_password,signup_d.getText().toString(),signup_i.getText().toString());

                                        String id = task.getResult().getUser().getUid();
                                        firebaseDatabase.getReference().child("Users").child(id).setValue(user1);
                                         Toast.makeText(getContext(),"Acc created",Toast.LENGTH_LONG).show();
                                        Intent x = new Intent(getView().getContext(), MainActivity.class);
                                        startActivity(x);
                                       /* Log.i(LOG_TAG, "Update UI Working");*/

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }
        });
        return v;
    }

    private void signup() {

    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent x = new Intent(getView().getContext(), MainActivity.class);
            startActivity(x);
            Log.i(LOG_TAG, "Update UI Working");
        }

    }
}