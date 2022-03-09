package com.hackthon.devfinder.Fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackthon.devfinder.Adapters.RepoResAdapter;
import com.hackthon.devfinder.Models.RepositoryMod;
import com.hackthon.devfinder.QueryUtils.GithubQuery;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.databinding.FragmentHomeBinding;

import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private static String LogTag = HomeFragment.class.getSimpleName();
    FragmentHomeBinding binding;
    private static String JsonResponseLink = "https://api.github.com/search/repositories?q=blockchain&per_page=20";
    private static String jsonStart = "https://api.github.com/search/repositories?q=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        HomeAsyncTask task = new HomeAsyncTask();
        task.execute();

        /*binding.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = jsonStart + binding.edittextInterests.getText().toString().toString() + "per_page=20";
            }
        });*/

        return binding.getRoot();
    }

    protected void updateUi(ArrayList<RepositoryMod> list){

        RepoResAdapter flightAdapter = new RepoResAdapter(list, binding.recyclerView, getContext());
        binding.recyclerView.setAdapter(flightAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        flightAdapter.notifyDataSetChanged();
        Log.i(LogTag,JsonResponseLink);

    }

    private class HomeAsyncTask extends AsyncTask<URL, Void, ArrayList<RepositoryMod>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<RepositoryMod> doInBackground(URL... urls) {
            ArrayList<RepositoryMod> event = GithubQuery.fetchRepoData(JsonResponseLink);            //also we can use  urls[0]
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