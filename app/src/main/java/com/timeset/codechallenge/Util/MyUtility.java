package com.timeset.codechallenge.Util;

import android.util.Log;

import com.timeset.codechallenge.entity.FlickerProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by rakeshh91 on 5/4/2017.
 */

public class MyUtility {

    public static String downloadJSONusingHTTPGetRequest(String urlString) {
        String jsonString=null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream stream = httpConnection.getInputStream();
                jsonString = getStringfromStream(stream);
            }
            httpConnection.disconnect();
        }  catch (UnknownHostException e1) {
            Log.d("MyDebugMsg", "UnknownHostexception in downloadJSONusingHTTPGetRequest");
            e1.printStackTrace();
        } catch (Exception ex) {
            Log.d("MyDebugMsg", "Exception in downloadJSONusingHTTPGetRequest");
            ex.printStackTrace();
        }
        return jsonString;
    }

    private static String getStringfromStream(InputStream stream){
        String line, jsonString=null;
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder out = new StringBuilder();
            try {
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();
                jsonString = out.toString();
            } catch (IOException ex) {
                Log.d("MyDebugMsg", "IOException in downloadJSON()");
                ex.printStackTrace();
            }
        }
        return jsonString;
    }

    public static List<FlickerProfile> parseJSON(String json, List<FlickerProfile> list) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray arr = jsonObject.getJSONObject("photos").getJSONArray("photo");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonpart = arr.getJSONObject(i);
                    String title = jsonpart.getString("title");
                    String ownername = jsonpart.getString("ownername");
                    String latitude = jsonpart.getString("latitude");
                    String longitude = jsonpart.getString("longitude");
                    String accuracy = jsonpart.getString("accuracy");
                    String place_id = jsonpart.getString("place_id");
                    FlickerProfile flickerProfile = new FlickerProfile();
                    flickerProfile.setAccuracy(accuracy);
                    flickerProfile.setLatitude(latitude);
                    flickerProfile.setLongitude(longitude);
                    flickerProfile.setOwnerName(ownername);
                    flickerProfile.setPlace_id(place_id);
                    flickerProfile.setTitle(title);
                    list.add(flickerProfile);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
