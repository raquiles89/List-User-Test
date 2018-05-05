package com.test.list_user.helper;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class RetrofitHelper {

    public static final int CLIENT_CONNECTION_TIMEOUT = 20000;
    public static final int CLIENT_READ_TIMEOUT = 100000;

    CookieManager cookieManager;

    public RetrofitHelper(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    /**
     * Get Retrofit object building by OkHttpClient
     */
    public Retrofit getRetrofitBase(final String url, final OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    /**
     * Get OkHttpClient with no header personalization
     */
    public OkHttpClient getOkHttpClient() {
        return this.getOkHttpClientBase();
    }

    /**
     * Get OkHttpClient with header personalization
     */
    public OkHttpClient getOkHttpClientHeaderSession(final String token, final String authToken) {
        OkHttpClient httpClient = this.getOkHttpClientBase();
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.addHeader("Cookie", "JSESSIONID=" + token);
                requestBuilder.addHeader("x-auth-token", authToken);
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        return httpClient;
    }

    /**
     * Get OkHttpClient with time personalization
     */
    private OkHttpClient getOkHttpClientBase() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.setConnectTimeout(CLIENT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        httpClient.setReadTimeout(CLIENT_READ_TIMEOUT, TimeUnit.MILLISECONDS);
        this.cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        httpClient.setCookieHandler(this.cookieManager);
        return httpClient;
    }

    /**
     * Get a CookieManager
     */
    public CookieManager getCookieManager() {
        return cookieManager;
    }

    public static DownloadManager.Request getRequest(final String urlSpec, final String fileName,
                                                     final String authToken, final String xToken) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlSpec));
        request.addRequestHeader("Cookie", "JSESSIONID=" + authToken);
        request.addRequestHeader("X-Auth-Token", xToken);
        request.setDescription("Archivo adjunto de caso");
        request.setTitle("Descarga Flux");

        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        return request;
    }
}
