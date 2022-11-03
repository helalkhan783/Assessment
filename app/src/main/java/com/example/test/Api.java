package com.example.test;

import com.example.test.response.CountryResponse;

import retrofit2.Call;

import retrofit2.http.GET;


public interface Api {

    @GET("countries/population/cities")
    Call<CountryResponse> getCountries();
}
