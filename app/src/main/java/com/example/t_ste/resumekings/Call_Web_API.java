package com.example.t_ste.resumekings;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by t_ste on 11/19/2016.
 */

                                    //AsyncTask<Param, something, return>
    //aditional changes
public class Call_Web_API extends AsyncTask<String,Void ,ArrayList<Applicant_Profile>> {
    final ArrayList<Applicant_Profile> cachedApplicantProfiles = new ArrayList<>();

//PUT and POST dont return any value so there is no reason to return
    protected Void doInBackground(Applicant_Profile AP, String... strings) {
            UseWebAPI tw = new UseWebAPI();
            try {
            switch (strings[0]) {
                case "Post":
                        tw.PostNewResume(AP);
                        break;
                case "PUT":
                    //tw.PutNewResume(AP); //we need to send an ID to update here in the URL
            }}catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }
    //Here is the Get and Delete Get returns an ARRAYLIST DELETE doesnt GET can return 1 person or a full array of people
    @Override
    protected ArrayList<Applicant_Profile>  doInBackground(String... strings) {
        UseWebAPI tw = new UseWebAPI();
         ArrayList<Applicant_Profile> cachedApplicantProfiles = new ArrayList<>();
        try {
            switch (strings[1]) {
                case "Get":
                    cachedApplicantProfiles=tw.getNewResume(strings[0]);
                    break;
                case "Delete":
                    tw.deleteResume(strings[0]);

            }}catch (JSONException e){
            e.printStackTrace();
        }
        return cachedApplicantProfiles;    }
}
