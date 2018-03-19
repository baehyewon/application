package com.baehw1107.sharepark2.network;

import com.baehw1107.sharepark2.model.Response;
import com.baehw1107.sharepark2.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by baehw_000 on 2017-05-18.
 */

public interface RetrofitInterface {
    @POST("register")
    Observable<Response> register(@Body User user);

    @POST("login")
    Observable<Response> login(@Body User user);

    @GET("users/{email}")
    Observable<User> getProfile(@Path("email") String email);

    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body User user);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body User user);
}