package com.example.t_ste.resumekings;


import android.preference.PreferenceActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by t_ste on 11/19/2016.
 */

public class UseWebAPI {
    public void PostNewResume(Applicant_Profile AP) throws JSONException {
        JSONObject JO= new JSONObject();
        StringEntity entity = null;
        JO.put("Name",AP.getUserName());
        JO.put("Email",AP.getEmail());
        JO.put("Number",AP.getPhoneNumber());
        JO.put("Notes",AP.getNotes());
        JO.put("Resume",AP.getResumePicture());
        JO.put("Picture",AP.getProfilePicture());
        JO.put("Rating",AP.getStars());

        try {
             entity = new StringEntity(JO.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        Web_Rest_API.post("" , entity,  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    System.out.println(response.getString("Id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    public void getNewResume() throws JSONException {
        Web_Rest_API.get("", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    System.out.println(response.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}