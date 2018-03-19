package com.baehw1107.sharepark1.global;

import android.app.Application;

import com.baehw1107.sharepark1.ITService;
import com.baehw1107.sharepark1.R;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by baehw_000 on 2017-05-18.
 */

public class ApplicationController extends Application{
    private static final String URL = "http//rds.c8qvf3lcuwwz.ap-northeast-2.rds.amazonaws.com";
    private static ApplicationController instance;

    private ITService itService;

    public ApplicationController(){

    }

    public static ApplicationController getInstance() {
        return instance;
    }

    @Override
    public void onCreate(){
       super.onCreate();
        if(instance == null){
           instance = new ApplicationController();
        }
        instance = this;
        
        buildITService();
    }

    private void buildITService() {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit.retrofit = builder
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        
        itService = retrofit.create(ITService.class);
    }

    public ITService getITService() {
        return ITService;
    }
}
