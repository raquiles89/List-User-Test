package com.test.list_user.api.service;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.test.list_user.api.exception.ServiceUnauthorizedException;
import com.test.list_user.api.listener.UserServiceListener;
import com.test.list_user.helper.RetrofitHelper;
import com.test.list_user.model.request.UserRequest;
import com.test.list_user.model.response.UserDataResponse;

import java.net.CookieManager;
import java.net.HttpURLConnection;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Rafael Quiles
 */

public class UserServiceImpl extends ServiceImpl {

    private UserServiceListener userServiceListener;

    public UserServiceImpl(final Context context) {
        super(context);
    }

    public void setUserServiceListener(UserServiceListener userServiceListener) {
        this.userServiceListener = userServiceListener;
    }

    public void listUser(final String url, final UserRequest request) {
        final RetrofitHelper retrofitHelper = new RetrofitHelper(new CookieManager());
        final OkHttpClient httpClient = retrofitHelper.getOkHttpClient();
        final Retrofit client = retrofitHelper.getRetrofitBase(url, httpClient);
        final UserService service = client.create(UserService.class);
        final Call<UserDataResponse> call = service.listUser();
        call.enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Response<UserDataResponse> response, Retrofit retrofit) {
                switch (response.code()) {
                    case HttpURLConnection.HTTP_OK:
                        userServiceListener.onUserResponse(response.body());
                        break;
                    case HttpURLConnection.HTTP_UNAUTHORIZED:
                        onFailure(new ServiceUnauthorizedException());
                        break;
                    default:
                        onFailure(new Exception());
                        break;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                userServiceListener.onUserFailure(t);
            }
        });
    }
}
