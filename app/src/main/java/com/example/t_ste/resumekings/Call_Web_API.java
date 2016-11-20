package com.example.t_ste.resumekings;

import android.os.AsyncTask;

import org.json.JSONException;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by t_ste on 11/19/2016.
 */

public class Call_Web_API extends AsyncTask<String,Void,Void> {

        protected Void doInBackground(Applicant_Profile AP, String... strings) {
            UseWebAPI tw = new UseWebAPI();
            try {

            switch (strings[0]) {
                case "Post":
                        tw.PostNewResume(AP);
                        break;
                case "Get":
                        tw.getNewResume();
                        break;

            }}catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

    @Override
    protected Void doInBackground(String... strings) {
        return null;
    }
}
