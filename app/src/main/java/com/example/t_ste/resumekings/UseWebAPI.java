package com.example.t_ste.resumekings;


import android.preference.PreferenceActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Applicant_Profile> getNewResume(String ID) throws JSONException {
        final ArrayList<Applicant_Profile> cachedApplicantProfiles = new ArrayList<>();

        if (ID != "") { //if its not null we are getting just 1
            Web_Rest_API.get(ID, null, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    try {
                        Applicant_Profile AP = new Applicant_Profile();
                        AP.setUserName(response.getString("Name"));
                        AP.setEmail(response.getString("Email"));
                        AP.setPhoneNumber(response.getString("Number"));
                        AP.setNotes(response.getString("Notes"));
                        AP.setProfileBLOB(response.getString("Picture"));
                        AP.setResumeBLOB(response.getString("Resume"));
                        AP.setStars(Integer.parseInt(response.getString("Rating")));
                        cachedApplicantProfiles.add(AP);

                        System.out.println(AP.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
            return cachedApplicantProfiles;
        } else { //if it is null we are getting all of them
            Web_Rest_API.get("", null, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject Applicant = null;
                        try {
                            Applicant_Profile AP = new Applicant_Profile();
                            Applicant = (JSONObject) response.get(i);
                            AP.setUserName(Applicant.getString("Name"));
                            AP.setEmail(Applicant.getString("Email"));
                            AP.setPhoneNumber(Applicant.getString("Number"));
                            AP.setNotes(Applicant.getString("Notes"));
                            AP.setProfileBLOB(Applicant.getString("Picture"));
                            AP.setResumeBLOB(Applicant.getString("Resume"));
                            AP.setStars(Integer.parseInt(Applicant.getString("Rating")));
                            cachedApplicantProfiles.add(AP);
                            System.out.println(AP.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            return cachedApplicantProfiles;
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteResume(String ID) throws JSONException {
        if(ID!=null){
            Web_Rest_API.delete(ID, new JsonHttpResponseHandler() {
            });
        }}


}