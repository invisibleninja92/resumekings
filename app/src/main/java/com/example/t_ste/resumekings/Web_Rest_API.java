package com.example.t_ste.resumekings;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by t_ste on 11/19/2016.
 */


public class Web_Rest_API {



    private static final String BASE_URL = "rip";
    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * This is written to get all the applicants that are saved in the database
     * and to place them in the cache. is uses the AsyncHttpClient.get() function sending it
     * the BASE_URL+ any additional URL we sent it
     *
     * @param url IP:port/Resume/
     * @param params
     * @param responseHandler
     */
    static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * This is called when we are creating a new applicant.
     * uses AsyncHttpClient.post() to send a stringEntity which is the Applicant Profile information as json
     *
     * @param url IP:Port/Resume/
     * @param entity Contains JSON of the Applicant_Profile with Images
     * @param responseHandler
     */
    static void post(String url, StringEntity entity , AsyncHttpResponseHandler responseHandler) {
        client.post(null,getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    /**
     *
     *  This will send the URL to the API telling it to delete a specific ID.
     *  When calling delete we place the ID in the URL to append it to the BASE_URL.
     *
     * @param url IP:Port/Resume/ID
     * @param responseHandler
     */
    static void delete(String url, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(url), responseHandler);
    }

    /**
     *
     *  Update gives a StringEntity of the Applicant_Profile information and calls the update URL
     *  When updating we add the ID to the end of the URL
     *
     * @param url IP:Port/Resume/ID
     * @param entity Applicant_Profile JSON object with updated info
     * @param responseHandler
     */
    static void update(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler){
        client.put(null, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    /**
     *
     * Returns the Base URL which is IP:Port/Resume/Resume plus the relativeURL which is the ID if
     * one is specified
     *
     * @param relativeUrl
     * @return
     */
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
