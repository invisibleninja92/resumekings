package com.example.t_ste.resumekings;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by t_ste on 11/19/2016.
 */

                                    //AsyncTask<Param, something, return>
    //aditional changes
public class Call_Web_API extends AsyncTask<String,Void ,ArrayList<Applicant_Profile>> {


     ArrayList<Applicant_Profile> cachedApplicantProfiles = new ArrayList<>();


    protected ArrayList<Applicant_Profile> doInBackground(Applicant_Profile AP, String... strings) {
            UseWebAPI UseAPI = new UseWebAPI();
        try {
            switch (strings[0]) {
                case "Get": //this get function works perfectly :)
                    cachedApplicantProfiles=UseAPI.getResumes();
                    return cachedApplicantProfiles;
                case "Post":
                    UseAPI.PostNewResume(AP); //this post (Or create new) Function works perfectly :)
                    break;
                case "Put":
                    UseAPI.UpdateResume(AP); //we need to send an ID to update here in the URL
                    break;
                case "Delete": //this delete function works perfectly :)
                    UseAPI.deleteResume(AP.getID());
                    break;
            }}catch (JSONException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
        }
//Not sure why we have to have this interface below but its here..... oh well :)
    @Override
    protected ArrayList<Applicant_Profile> doInBackground(String... strings) {
        return null;
    }
}
