package com.baehw1107.sharepark1;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by baehw_000 on 2017-05-17.
 */

public interface ITService {

    //로그인
    @FormUrlEncoded
    @POST("/login")
    Call<LoginResult> getLoginResult(@Field("userID") String userID, @Field("userPassword") String userPassword);


    //담은영화 리스트 가져오기
    /*@GET("/mypage/movie/cart")
    Call<PackResultResult> getMoviePackResult(@Header("member_token") String member_token);
    */


}
