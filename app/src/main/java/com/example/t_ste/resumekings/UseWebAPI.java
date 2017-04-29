package com.example.t_ste.resumekings;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

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

    /**
     * This function creates the JSONObject using the Applicant Profile created it then turns that into
     * a stringEntity that can be used by the .post function in the Web_Rest_API class.
     * We have also written in a response handler to the .post call to handle the response from the API onSuccess
     *
     * @param AP The Applicant_Profile created via the form
     * @throws JSONException
     * @throws IOException
     */
    public void PostNewResume(final Applicant_Profile AP) throws JSONException, IOException {
        JSONObject JO= new JSONObject();
        StringEntity entity = null;
        JO.put("Name",AP.getUserName());
        JO.put("Email",AP.getEmail());
        JO.put("Number",AP.getPhoneNumber());
        JO.put("Notes",AP.getNotes());

        if(AP.getResumePicture()!=null){
        JO.put("Resume", BitMapToString(AP.getResumePicture()));
        }
        if(AP.getProfilePicture()!=null){
            JO.put("Profile", BitMapToString(AP.getProfilePicture()));
        }
        if(AP.getResumeOverlay()!=null){
            JO.put("ResumeOverlay",BitMapToString(AP.getResumeOverlay()));
        }
        JO.put("Rating",AP.getStars());

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
                    AP.setID(response.getString("Id"));
                    AP.setResumePictureURL("http://s3.amazonaws.com/testbucketsource11/"+response.getString("Id")+"Resume.png");
                    AP.setProfilePictureURL("http://s3.amazonaws.com/testbucketsource11/"+response.getString("Id")+"Profile.png");
                    AP.setResumeOverlayURL("http://s3.amazonaws.com/testbucketsource11/"+response.getString("Id")+"ResumeOverlay.png");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    /**
     * This function gets all the resumes from the cloud database
     * It passes nothing but the response handler goes through the length of the applicants and gets
     * all the elements placing them in an ArrayList of applicant_profiles.then returns those profiles
     *
     * @return cachedApplicantProfiles
     * @throws JSONException
     */
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
                            AP.setID(Applicant.getString("Id"));
                            AP.setResumePictureURL("http://s3.amazonaws.com/testbucketsource11/"+Applicant.getString("Id")+"Resume.png");
                            AP.setProfilePictureURL("http://s3.amazonaws.com/testbucketsource11/"+Applicant.getString("Id")+"Profile.png");
                            AP.setResumeOverlayURL("http://s3.amazonaws.com/testbucketsource11/"+Applicant.getString("Id")+"ResumeOverlay.png");
                            cachedApplicantProfiles.add(AP);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            return cachedApplicantProfiles; //return big array of a lot of the items.


    }

    /**
     *  simply deletes the applicant profile by passing the ID to the Web_Rest_API.delete() function
     *  We do not need a response handler because the API sends nothing back to us
     *
     * @param ID
     * @throws JSONException
     */
    public void deleteResume(String ID) throws JSONException {
        if(ID!=null){
            Web_Rest_API.delete(ID, new JsonHttpResponseHandler(){});
        }}

    /**
     * Passing the applicant profile this function creates a JSONObject then turns that into a stringEntity to pass
     * to the WebRestApi.update function.
     * Here we pass the entity and the ID since the API requries us to call a different URL
     * Again we do not need a response handler since the API sends us nothing back.
     *
     *
     * @param AP
     * @throws JSONException
     */
    public void UpdateResume(Applicant_Profile AP) throws JSONException{
        JSONObject JO= new JSONObject();
        StringEntity entity = null;
        JO.put("Id",AP.getID());
        JO.put("Name",AP.getUserName());
        JO.put("Email",AP.getEmail());
        JO.put("Number",AP.getPhoneNumber());
        JO.put("Notes",AP.getNotes());
        JO.put("Rating",AP.getStars());

        if(AP.getResumeOverlay()!=null){
            JO.put("ResumeOverlay", BitMapToString(AP.getResumeOverlay()));
        }
        try {
            entity = new StringEntity(JO.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Web_Rest_API.update(AP.getID(),entity, new JsonHttpResponseHandler(){});

    }

    /**
     * To send the image to the API we must first convert it to a Base64 string
     * The API takes that string and converts it back into a PNG which is then sent the AWS S3
     *
     * @param bitmap
     * @return
     */
    public String BitMapToString(Bitmap bitmap){
                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte [] b=baos.toByteArray();
                String temp=Base64.encodeToString(b, Base64.DEFAULT);
                return temp;
            }
}