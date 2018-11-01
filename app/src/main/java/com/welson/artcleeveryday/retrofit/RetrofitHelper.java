package com.welson.artcleeveryday.retrofit;

import com.google.gson.GsonBuilder;
import com.welson.artcleeveryday.constants.Constants;
import com.welson.artcleeveryday.entity.MainDatas;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private OkHttpClient client = new OkHttpClient();
    private GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private Retrofit retrofit;
    private ApiService apiService;
    private static RetrofitHelper instance = null;

    public static RetrofitHelper getInstance(){
        if (instance == null){
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private RetrofitHelper(){
        init();
    }

    private void init(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public Observable<MainDatas> getTodayData(){
        return apiService.getTodayData();
    }
}
