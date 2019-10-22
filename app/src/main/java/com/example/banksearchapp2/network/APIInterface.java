package com.example.banksearchapp2.network;

import com.example.banksearchapp2.model.Banks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("banks")

    Call<List<Banks>> getBanks(@Query("city") String city);
}
