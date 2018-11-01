package com.welson.artcleeveryday.retrofit;

import com.welson.artcleeveryday.entity.MainDatas;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("today?dev=1")
    Observable<MainDatas> getTodayData();

}
