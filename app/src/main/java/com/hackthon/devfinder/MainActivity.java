package com.hackthon.devfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.hackthon.devfinder.Fragments.HomeFragment;
import com.hackthon.devfinder.Fragments.MoreFragment;
import com.hackthon.devfinder.Fragments.UsersFragment;
import com.hackthon.devfinder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
        homeTransaction.replace(R.id.main_content,new HomeFragment());
        homeTransaction.commit();

        /* ------------------------Bottom normal Navigation Bar replacing with Bubble navigation bar----------------------*/

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (item.getItemId()){
                    case R.id.home:
                        transaction.replace(R.id.main_content,new HomeFragment());
                        break;

                    case R.id.users:
                        transaction.replace(R.id.main_content,new UsersFragment());
                        break;

                    case R.id.more:
                        transaction.replace(R.id.main_content,new MoreFragment());
                        break;

                }

                transaction.commit();
                return true;

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}