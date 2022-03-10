package com.hackthon.devfinder.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hackthon.devfinder.Adapters.RepoResAdapter;
import com.hackthon.devfinder.Models.RepositoryMod;
import com.hackthon.devfinder.QueryUtils.GithubQuery;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.databinding.ActivityRepositoryResultsBinding;

import java.net.URL;
import java.util.ArrayList;

public class RepositoryResults extends AppCompatActivity {

    ActivityRepositoryResultsBinding binding;
    private static String JsonLink = "";
    private static String LogTag = RepositoryResults.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRepositoryResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        JsonLink = getIntent().getStringExtra("link");
        HomeAsyncTask task = new HomeAsyncTask();
        task.execute();


    }


    protected void updateUi(ArrayList<RepositoryMod> list){

        RepoResAdapter flightAdapter = new RepoResAdapter(list, binding.recyclerView3, getApplicationContext());
        binding.recyclerView3.setAdapter(flightAdapter);
        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        flightAdapter.notifyDataSetChanged();
        Log.i(LogTag,JsonLink);

    }

    private class HomeAsyncTask extends AsyncTask<URL, Void, ArrayList<RepositoryMod>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<RepositoryMod> doInBackground(URL... urls) {
            ArrayList<RepositoryMod> event = GithubQuery.fetchRepoData(JsonLink,2);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<RepositoryMod> event) {

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