package com.test.list_user.api.service;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.test.list_user.helper.RetrofitHelper;

import java.net.CookieManager;

import retrofit.Retrofit;

/**
 * Created by Rafael Quiles
 */

public class ServiceImpl {
    private Context context;

    public ServiceImpl(final Context context) {
        this.context = context;
    }

    protected Retrofit getClientWithHeader(final String url, final String token, final String authToken) {
        final RetrofitHelper retrofitHelper = new RetrofitHelper(new CookieManager());
        final OkHttpClient httpClient = retrofitHelper.getOkHttpClientHeaderSession(token, authToken);
        return retrofitHelper.getRetrofitBase(url, httpClient);
    }
}
