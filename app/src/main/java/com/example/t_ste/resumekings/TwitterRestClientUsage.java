package com.example.t_ste.resumekings;
import android.preference.PreferenceActivity;

import org.json.*;
import com.loopj.android.http.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import static com.example.t_ste.resumekings.TwitterRestClient.*;

class TwitterRestClientUsage {

    public void getPublicTimeline() throws JSONException, NoSuchAlgorithmException, KeyManagementException {

        TwitterRestClient.get(null, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) throws JSONException {

                String tweetText = response.getString("Hello");
                System.out.println(tweetText);
            }
        });
    }
}