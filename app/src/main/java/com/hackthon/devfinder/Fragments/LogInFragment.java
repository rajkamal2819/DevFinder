package com.hackthon.devfinder.Fragments;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
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
import com.hackthon.devfinder.MainActivity;
import com.hackthon.devfinder.R;

import java.util.concurrent.Executor;

public class LogInFragment extends Fragment {
    EditText pass, email;
    Button signin;
    String emailid, password;
    private FirebaseAuth mAuth;
     ProgressDialog progressDialog;
    FirebaseUser user;
    public LogInFragment() {

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        updateUI(user);
    }


/*        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_in, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Sign in");
        progressDialog.setMessage("please wait signing you in");        updateUI(user);
        pass = v.findViewById(R.id.password);
        email = v.findViewById(R.id.emailId);
        signin = v.findViewById(R.id.login);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailid = email.getText().toString();
                password = pass.getText().toString();
                if ((emailid != null) || (password != null)) {

                    signin();
                } else {
                    Toast.makeText(getView().getContext(), "Please enter email and password", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;
    }

    private void signin() {
        if (TextUtils.isEmpty(emailid) || TextUtils.isEmpty(password)) {
            Toast.makeText(getView().getContext(), "Please fill all requirements", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(emailid, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                 user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(requireView().getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Intent x = new Intent(getContext(), MainActivity.class);
            progressDialog.dismiss();
            startActivity(x);
        }

        progressDialog.dismiss();

    }
}