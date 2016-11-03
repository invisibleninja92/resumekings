package com.example.t_ste.resumekings;

/**
 * Created by t_ste on 11/2/2016.
 */
import com.loopj.android.http.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HttpContext;

public class TwitterRestClient {
    private static SSLContext sslContext;
    private static String BASE_URL = "https://wqpum6myib.execute-api.us-east-1.amazonaws.com/test1_deploy/hellostring?name=fuck&email=fuck@fuck.com&number=2544851458&notes=fuckingfuck";
    String cert="MIIC6TCCAdGgAwIBAgIJAK5xY8fND45OMA0GCSqGSIb3DQEBCwUAMDQxCzAJBgNVBAYTAlVTMRAwDgYDVQQHEwdTZWF0dGxlMRMwEQYDVQQDEwpBcGlHYXRld2F5MB4XDTE2MTEwMzE2NDQwOVoXDTE3MTEwMzE2NDQwOVowNDELMAkGA1UEBhMCVVMxEDAO BgNVBAcTB1NlYXR0bGUxEzARBgNVBAMTCkFwaUdhdGV3YXkwggEiMA0GCSqGSIb3 DQEBAQUAA4IBDwAwggEKAoIBAQC9mjC6oj+ZSVh9QSm2l1803Zk763sVeZmp4s3W ab+FJCkwxpU9CGMjbddrm7GCBc07eIDlX65O0W2M6hPJwkewD+2AMK42Pyh0Jp+5 lNaBc4s16H4DHWg4uIyUTrorUTTqPM/knC1VDALP1dyb1lIv1p5ap1j27ZUkE1LU si7vaZyaFvNXHRbYfvEZg10TzBYzRhPf0seKOB4hG+PQ1aMIRMn/PbJLFPMi1tBj GRyPNbLYJplYOosX1ih37UeUjYAKAngV0k2RvfkDtCHUGIuqTBXuns/9Uhf2040z b4SAPHxX1PP1BhYsSsYF2WYlp7xngGyxszRaZRldRzvIlkGNAgMBAAEwDQYJKoZI hvcNAQELBQADggEBAIZrGHHlHFcbQk/ybr755mT6Fdaw8JG3BfoWO1ZpN1V5ck// GQ6saGdDylIjT/N6s2Gii+gBxn+La8PUkWbZV03VpiPCkXphbimG1ujqF46ZKFj5 kZ6N6FMDWsu0jstvQi4/wVoImsE+zgW3bYY4yckPy4To32lESKDtZA4SHTCVuikQ mnhcZE5Pv5uSxmV9Tc9bv9gro+UGok7LjOLiq5Uks6eqghoi2TmCyEOw7JRWViWB pqjBM3Vrrue3u8DAYP4FFBdex7E8F3iiZk5Z1KoSb8QALAFJYpgDcDtF4WUMW76u M+LO0xPSFQalXW3uPGl/9WHDAXH6FRhMHtPN0kY=";
    private static AsyncHttpClient client = new AsyncHttpClient();
    static TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    } };


    public static SSLContext getSslContext() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("TLS");

        try {
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslContext;
    }




    public static void get(RequestParams params, AsyncHttpResponseHandler responseHandler) throws KeyManagementException, NoSuchAlgorithmException {
        client.setSSLSocketFactory(new SSLSocketFactory(getSslContext(),
                SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));
        client.get(BASE_URL, params, responseHandler);
    }




}
