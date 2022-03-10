package com.hackthon.devfinder.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hackthon.devfinder.Models.GithubProfile;
import com.hackthon.devfinder.Models.RepositoryMod;
import com.hackthon.devfinder.QueryUtils.GitProfileQuery;
import com.hackthon.devfinder.QueryUtils.GithubQuery;
import com.hackthon.devfinder.R;
import com.hackthon.devfinder.databinding.ActivityUserDetailsBinding;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UserDetails extends AppCompatActivity {

    private static String JsonResponseLink = "";
    ActivityUserDetailsBinding binding;
    private String githubLink;
    private String starredRepoLink = "";
    private String allRepoLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String devName = getIntent().getStringExtra("name");
        String devAvatar = getIntent().getStringExtra("image");
        if (devAvatar != null) {
            try {
                Glide.with(getApplicationContext())
                        .load(new URL(devAvatar))
                        .into(binding.prfoileImage);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.user).into(binding.prfoileImage);
        }
        // https://api.github.com/users/rajkamal2819/repos
        JsonResponseLink = "https://api.github.com/users/"+devName;
        UserAsyncTask task = new UserAsyncTask();
        task.execute();

        binding.githubLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse(githubLink));
                startActivity(i);
            }
        });

        binding.allRepositeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RepositoryResults.class);
                i.putExtra("link",allRepoLink);
                startActivity(i);
            }
        });

    }

    protected void updateUi(ArrayList<GithubProfile> list){

        GithubProfile model = list.get(0);

        binding.name.setText(model.getName());
        binding.bio.setText(model.getBio());
        githubLink = model.getHtmlLink();
        starredRepoLink = model.getStarredUrl();
        allRepoLink = model.getAllRepoLink();


    }

    private class UserAsyncTask extends AsyncTask<URL, Void, ArrayList<GithubProfile>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<GithubProfile> doInBackground(URL... urls) {
            ArrayList<GithubProfile> event = GitProfileQuery.fetchInfo(JsonResponseLink);            //also we can use  urls[0]
            return event;
        }

        @Override
        protected void onPostExecute(ArrayList<GithubProfile> event) {

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