package com.example.t_ste.resumekings;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by t_ste on 11/19/2016.
 */

                                    //AsyncTask<Param, something, return>
    //aditional changes
public class Call_Web_API extends AsyncTask<String,Void ,ArrayList<Applicant_Profile>> {


     ArrayList<Applicant_Profile> cachedApplicantProfiles = new ArrayList<>();

    /**
     * Sends the first String through a swtich case to determine what we are trying to do
     * Get= grabbing all applicant profiles from the cloud server
     * Post= sending a new applicant profile to the API to be added to the cloud server
     * Put= Sends a applicant profile that has been updated to the API to be updated in the database on the cloud server
     * Delete= sends an applicant profile ID to find and delete that profile from the cloud database.
     *
     * @param AP if we are using the post, put, or delete we pass this function an applicant profile
     * @param strings
     * @return
     */
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
                    UseAPI.UpdateResume(AP); //this get function works perfectly :)
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
//Not sure why we have to have this interface below but its here..... oh well :) We have to have it or it gets all mad

    /**
     * We have to have this function or android studio gets mad at us....It does nothing.
     * @param strings
     * @return
     */
    @Override
    protected ArrayList<Applicant_Profile> doInBackground(String... strings) {
        return null;
    }
}
