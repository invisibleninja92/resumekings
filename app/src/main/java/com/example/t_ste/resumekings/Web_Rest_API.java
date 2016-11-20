package com.example.t_ste.resumekings;

import android.content.Context;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by t_ste on 11/19/2016.
 */
import com.loopj.android.http.*;

public class Web_Rest_API {

    private static final String BASE_URL = "http://10.0.2.2:3002/resume";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, StringEntity entity , AsyncHttpResponseHandler responseHandler) {
        client.post(null,getAbsoluteUrl(url), entity,"application/json", responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
