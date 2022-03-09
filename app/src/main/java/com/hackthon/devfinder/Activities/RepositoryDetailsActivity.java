package com.hackthon.devfinder.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hackthon.devfinder.Adapters.CommitItemAdapter;
import com.hackthon.devfinder.Adapters.RepoResAdapter;
import com.hackthon.devfinder.Models.CommitDetails;
import com.hackthon.devfinder.Models.RepositoryMod;
import com.hackthon.devfinder.QueryUtils.CommitQuery;
import com.hackthon.devfinder.QueryUtils.GithubQuery;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.databinding.ActivityRepositoryDetailsBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RepositoryDetailsActivity extends AppCompatActivity {

    ActivityRepositoryDetailsBinding binding;
    private static String JsonResponseLink = "";
    private static String LogTag = RepositoryDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRepositoryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String devName = getIntent().getStringExtra("devName");
        String devAvatar = getIntent().getStringExtra("devAvatar");
         JsonResponseLink = getIntent().getStringExtra("commitLink");

        binding.devNameDetails.setText(devName);
        if (devAvatar != null) {
            try {
                Glide.with(getApplicationContext())
                        .load(new URL(devAvatar))
                        .into(binding.profileImageDetails);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.user).into(binding.profileImageDetails);
        }

        DetailsAsyncTask task = new DetailsAsyncTask();
        task.execute();

        binding.viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    protected void updateUi(ArrayList<CommitDetails> list){

        CommitItemAdapter adapter = new CommitItemAdapter(list, binding.recyclerView2, getApplicationContext());
        binding.recyclerView2.setAdapter(adapter);
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter.notifyDataSetChanged();
        Log.i(LogTag,JsonResponseLink);
        Log.i(LogTag,"The size is : "+list.size());

    }

    private class DetailsAsyncTask extends AsyncTask<URL, Void, ArrayList<CommitDetails>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<CommitDetails> doInBackground(URL... urls) {
            ArrayList<CommitDetails> event = CommitQuery.fetchInfo(JsonResponseLink);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<CommitDetails> event) {

            binding.progressBar.setVisibility(View.GONE);


            if (event == null) {
                // Log.i("AllFlights", "NULL EVENT");
                //  binding.emptyTextView.setVisibility(View.VISIBLE);
                return;
            }

            updateUi(event);

        }

    }

}