package com.example.banksearchapp2.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    //define base url
    public static String base_url ="https://vast-shore-74260.herokuapp.com/";

    //retrofit

    public static Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

                return retrofit;
    }
    public static APIInterface apIinterface(){

        return getClient().create(APIInterface.class);
    }
}
