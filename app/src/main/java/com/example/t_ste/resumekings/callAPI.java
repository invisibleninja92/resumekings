package com.example.t_ste.resumekings;

import android.os.AsyncTask;

import org.json.JSONException;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by t_ste on 11/2/2016.
 */
public class callAPI extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... strings) {
        TwitterRestClientUsage tw = new TwitterRestClientUsage();
        try {
            tw.getPublicTimeline();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}
