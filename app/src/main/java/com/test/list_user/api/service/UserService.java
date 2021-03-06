package com.test.list_user.api.service;

import com.test.list_user.model.request.UserRequest;
import com.test.list_user.model.response.UserDataResponse;
import com.test.list_user.model.response.UserResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by Rafael Quiles
 */

public interface UserService {
    @Headers({"Accept: application/json"})
    @GET("/api/users")
    Call<UserDataResponse> listUser();

    @Headers({"Accept: application/json"})
    @POST("/api/users")
    Call<UserResponse> createUser(@Body UserRequest userRequest);

}
