package com.example.t_ste.resumekings;


import android.graphics.Bitmap;


import com.loopj.android.http.Base64;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by t_ste on 11/19/2016.
 */

public class UseWebAPI {


    public void PostNewResume(Applicant_Profile AP) throws JSONException, IOException {
        JSONObject JO= new JSONObject();
        StringEntity entity = null;
        System.out.println(BitMapToString(AP.getProfilePicture()));
        System.out.println(BitMapToString(AP.getResumePicture()));

        JO.put("Name",AP.getUserName());
        JO.put("Email",AP.getEmail());
        JO.put("Number",AP.getPhoneNumber());
        JO.put("Notes",AP.getNotes());
        JO.put("Resume", BitMapToString(AP.getResumePicture()));
        JO.put("Profile", BitMapToString(AP.getProfilePicture()));
        JO.put("Rating",AP.getStars());
        //create a file to write bitmap data

      try {
             entity = new StringEntity(JO.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //upload JSON
       Web_Rest_API.post("" , entity,  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    System.out.println(response.getString("Id"));//Returns the ID WE dont need a return though
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
    public ArrayList<Applicant_Profile> getResumes() throws JSONException {
        final ArrayList<Applicant_Profile> cachedApplicantProfiles = new ArrayList<>();

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
                            AP.setStars(Integer.parseInt(Applicant.getString("Rating")));
                            AP.setProfilePictureURL(Applicant.getString("Profile"));
                            AP.setResumePictureURL(Applicant.getString("Resume"));
                            AP.setID(Applicant.getString("Id"));
                            cachedApplicantProfiles.add(AP);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            return cachedApplicantProfiles; //return big array of a lot of the items.


    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteResume(String ID) throws JSONException {
        if(ID!=null){
            Web_Rest_API.delete(ID, new JsonHttpResponseHandler() { //just delete the thing at that ID.
            });
        }}

    public void updateResume(String ID) {

    }


    public String BitMapToString(Bitmap bitmap){
                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte [] b=baos.toByteArray();
                String temp=Base64.encodeToString(b, Base64.DEFAULT);
                return temp;
            }
}