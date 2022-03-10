package com.hackthon.devfinder.QueryUtils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.hackthon.devfinder.Models.RepositoryMod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class GithubQuery {

    private static String LOG_TAG = GithubQuery.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<RepositoryMod> fetchRepoData(String requestUrl,int uniqueL) {
        // Create URL object
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);         //makeHttpRequest is taking url object
            Log.i(LOG_TAG, "JsonResponse had been taken by httpReq");
        } catch (IOException e) {
            Log.i(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<RepositoryMod> repoInfo = extractFeatureFromJson(jsonResponse,uniqueL);

        // Return the {@link Event}
        return repoInfo;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            Log.i(LOG_TAG, "Successfully Url object created");
        } catch (MalformedURLException e) {
            Log.i(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoInput(true);

            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.i(LOG_TAG, "Http Request Successfully initiated");
            } else {
                Log.i(LOG_TAG,"Header: "+urlConnection.getHeaderField("Authorization"));
                Log.i(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.i(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        Log.i(LOG_TAG, "Reading from Stream");
        //  Log.d(LOG_TAG,output.toString());
        return output.toString();
    }

    private static ArrayList<RepositoryMod> extractFeatureFromJson(String jsonResponse,int uniqueL) {
        // If the JSON string is empty or null, then return early.
        ArrayList<RepositoryMod> coursesArrayList = new ArrayList<>();
        if (TextUtils.isEmpty(jsonResponse)) {
            return coursesArrayList;
        }

        try {
            JSONArray items = null;
            if(uniqueL == 1) {
                JSONObject baseJasonObject = new JSONObject(jsonResponse);
                items = baseJasonObject.getJSONArray("items");
            } else{
                items = new JSONArray(jsonResponse);
            }

            for (int i = 0;i<items.length();i++){

                JSONObject curObj =  items.getJSONObject(i);
                RepositoryMod model = new RepositoryMod();

                if(curObj.has("name")){
                    model.setRepoName(curObj.getString("name"));
                }

                if(curObj.has("owner")){
                    model.setDevName(curObj.getJSONObject("owner").getString("login"));
                    model.setDevAvatar(curObj.getJSONObject("owner").getString("avatar_url"));
                }

                if(curObj.has("html_url")){
                    model.setGitHubLink(curObj.getString("html_url"));
                }

                if(curObj.has("description")){
                    model.setDescription(curObj.getString("description"));
                }

                if(curObj.has("commits_url")){
                    String commits_url = curObj.getString("commits_url");
                    commits_url = commits_url.substring(0,commits_url.length()-6);
                    model.setCommits_url(commits_url);
                }

                if(curObj.has("language")){
                    model.setLanguage(curObj.getString("language"));
                }

                if(curObj.has("forks")){
                    model.setStarredCount(curObj.getInt("forks"));
                }

                if(curObj.has("watchers")){
                    model.setWatchersCount(curObj.getInt("watchers"));
                }

                coursesArrayList.add(model);

            }

            return coursesArrayList;
        } catch (JSONException e) {
            Log.i(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return coursesArrayList;

    }
}
