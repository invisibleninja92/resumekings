package com.example.t_ste.resumekings;

/**
 * Created by t_ste on 11/10/2016.
 */
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import android.util.Log;



public class JSONParser {

    static JSONObject json = null;

    // constructor
    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(String url) {


        String result="";
        // Making the HTTPS request
        try {
            URL url2 = new URL(url);
            URLConnection urlConnection = url2.openConnection();
            InputStream in = urlConnection.getInputStream();
            result = getStringFromInputStream(in);
            Log.d("here it is",result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return json;

    }
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
