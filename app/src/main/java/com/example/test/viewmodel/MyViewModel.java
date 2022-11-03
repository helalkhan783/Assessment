package com.example.test.viewmodel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.RetrofitClient;
import com.example.test.response.CountryResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel extends ViewModel {

    public MutableLiveData<CountryResponse> getCountries() {
        MutableLiveData<CountryResponse> liveData = new MutableLiveData<>();


        Call<CountryResponse> call = RetrofitClient.getInstance().getApi().getCountries();

        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });

        return liveData;
    }


}
