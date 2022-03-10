package com.hackthon.devfinder.QueryUtils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.hackthon.devfinder.Models.CommitDetails;
import com.hackthon.devfinder.Models.GithubProfile;

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

public class GitProfileQuery {

    private static String LOG_TAG = GithubQuery.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<GithubProfile> fetchInfo(String requestUrl) {
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
        ArrayList<GithubProfile> infoList = extractFeatureFromJson(requestUrl);

        // Return the {@link Event}
        return infoList;
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
        Log.d(LOG_TAG,output.toString());
        return output.toString();
    }

    private static ArrayList<GithubProfile> extractFeatureFromJson(String jsonResponse) {
        // If the JSON string is empty or null, then return early.
        ArrayList<GithubProfile> commitsDetList = new ArrayList<>();
        if (TextUtils.isEmpty(jsonResponse)) {
            return commitsDetList;
        }

        try {
            JSONObject baseJsonObj = new JSONObject(jsonResponse);

            GithubProfile profile = new GithubProfile();
            profile.setName(baseJsonObj.getString("login"));
            profile.setBio(baseJsonObj.getString("bio"));
            profile.setLocation(baseJsonObj.getString("location"));
            profile.setHtmlLink(baseJsonObj.getString("html_url"));
            profile.setOrganisationUrl(baseJsonObj.getString("organizations_url"));
            profile.setTwitterName(baseJsonObj.getString("twitter_username"));
            profile.setStarredUrl(baseJsonObj.getString("starred_url"));

            commitsDetList.add(profile);

            return commitsDetList;
        } catch (JSONException e) {
            Log.i(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return commitsDetList;

    }
}