package com.example.t_ste.resumekings;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.loopj.android.http.Base64;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.FileEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;

/**
 * Created by t_ste on 11/19/2016.
 */

public class UseWebAPI {
    public void PostNewResume(Applicant_Profile AP) throws JSONException, IOException {
        JSONObject JO= new JSONObject();
        StringEntity entity = null;

        File ProfilePic = File.createTempFile("TempProfile","png");
        File ResumePic= File.createTempFile("TempResume","png");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();

        JO.put("Name",AP.getUserName());
        JO.put("Email",AP.getEmail());
        JO.put("Number",AP.getPhoneNumber());
        JO.put("Notes",AP.getNotes());
        JO.put("Resume","");
        JO.put("Picture","");
        JO.put("Rating",AP.getStars());
        //create a file to write bitmap data


//Convert bitmap to byte array
        AP.getProfilePicture().compress(Bitmap.CompressFormat.PNG, 0 , bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(ProfilePic);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();

//Convert bitmap to byte array
        AP.getResumePicture().compress(Bitmap.CompressFormat.PNG, 0 , bos2);
        byte[] bitmapdata2 = bos2.toByteArray();

//write the bytes in file
        FileOutputStream fos2 = new FileOutputStream(ResumePic);
        fos2.write(bitmapdata2);
        fos2.flush();
        fos2.close();



        FileEntity ProfilePicEntity = new FileEntity(ProfilePic);
        FileEntity ResumePicEntity = new FileEntity(ResumePic);



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
        //upload Resume
        Web_Rest_API.post("/upload/resume" , ResumePicEntity ,  new JsonHttpResponseHandler() {
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
        //upload Profile
        Web_Rest_API.post("/upload/profile" , ProfilePicEntity ,  new JsonHttpResponseHandler() {
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

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
            return cachedApplicantProfiles; //return the array of size 1 else do below
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
//                            AP.setProfilePicture(StringToBitMap(Applicant.getString("Picture")));
//                            AP.setResumePicture(StringToBitMap(Applicant.getString("Resume")));
                            AP.setStars(Integer.parseInt(Applicant.getString("Rating")));
                            cachedApplicantProfiles.add(AP);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            return cachedApplicantProfiles; //return big array of a lot of the items.
        }

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

}