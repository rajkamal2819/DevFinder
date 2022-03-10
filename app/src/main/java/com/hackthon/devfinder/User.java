package com.hackthon.devfinder;


import android.media.Image;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;


@IgnoreExtraProperties
public class User {
            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String password;
    public String username;
    public String email;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String description;
    public String interests;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String password,String description,String interests) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.description = description;
        this.interests = interests;

    }

}