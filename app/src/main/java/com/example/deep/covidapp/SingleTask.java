package com.example.deep.covidapp;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleTask extends Application {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://coronavirus-19-api.herokuapp.com";

    @Override
    public void onCreate() {
        super.onCreate();
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

    }

    public Retrofit getRetrofit(){
        return retrofit;
    }

}
