package com.welson.artcleeveryday.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.welson.artcleeveryday.constants.Constants;
import com.welson.artcleeveryday.entity.MainData;
import com.welson.artcleeveryday.entity.MainDatas;
import com.welson.artcleeveryday.retrofit.RetrofitHelper;
import com.welson.artcleeveryday.util.HttpUtil;
import com.welson.artcleeveryday.util.SharedPreferenceUtil;
import com.welson.artcleeveryday.view.BaseView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainPresenter implements BasePresenter{

    private static final String TAG = MainPresenter.class.getSimpleName();
    private BaseView baseView;
    private ExecutorService singleExecutor;
    private SharedPreferenceUtil sharedPreferenceUtil;

    public MainPresenter(BaseView baseView, Context context){
        this.baseView = baseView;
        singleExecutor = Executors.newSingleThreadExecutor();
        sharedPreferenceUtil = new SharedPreferenceUtil(context);
    }

    @Override
    public void requestData() {
        //singleExecutor.execute(todayArticleRunnable); //这里okhttp获取数据总是报400 Bad Request，
        //（所以retrofit也无法正常使用）使用了HttpURLConnection能够正常获取，暂时没有搞清楚
        //TODO for okhttp
        singleExecutor.execute(todayArticleRunnableHttpURL);
        /*RetrofitHelper.getInstance().getTodayData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MainDatas>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MainDatas mMainDatas) {
                        mainDatas = mMainDatas;
                        Log.d("dingyl","mainDatas : " + mainDatas.getData().get(0).getAuthor());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("dingyl","error");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }

    public void requestDataRandom(){
        singleExecutor.execute(randomArticleRunnable);
    }

    public void requestDateBefore(){
        singleExecutor.execute(beforeArticleRunnable);
    }

    public void requestDateNext(){
        singleExecutor.execute(nextArticleRunnable);
    }

    private Runnable todayArticleRunnable = new Runnable() {
        @Override
        public void run() {
            HttpUtil.sendOkHttpRequest("https://interface.meiriyiwen.com/article/today", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    baseView.showError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                }
            });
        }
    };

    private Runnable todayArticleRunnableHttpURL = new Runnable() {
        @Override
        public void run() {
            String result = HttpUtil.getHttpURLConnection(Constants.BASE_URL+Constants.TODAY);
            if (!result.equals("")){
                baseView.showSuccess(parseJson(result));
                sharedPreferenceUtil.setTodayDate(parseJson(result).getDate().getCurr()+"");
                saveSharedPreference(result);
            }else {
                baseView.showError();
            }
        }
    };

    private Runnable randomArticleRunnable = new Runnable() {
        @Override
        public void run() {
            String result = HttpUtil.getHttpURLConnection(Constants.BASE_URL+Constants.RANDOM);
            if (!result.equals("")){
                baseView.showSuccess(parseJson(result));
                saveSharedPreference(result);
            }else {
                baseView.showError();
            }
        }
    };

    private Runnable beforeArticleRunnable = new Runnable() {
        @Override
        public void run() {
            String result = HttpUtil.getHttpURLConnection(Constants.BASE_URL
                    +Constants.DATE_ARTICLE+sharedPreferenceUtil.getBeforeDate());
            if (!result.equals("")){
                baseView.showSuccess(parseJson(result));
                saveSharedPreference(result);
            }else {
                baseView.showError();
            }
        }
    };

    private Runnable nextArticleRunnable = new Runnable() {
        @Override
        public void run() {
            String result = HttpUtil.getHttpURLConnection(Constants.BASE_URL
                    +Constants.DATE_ARTICLE+sharedPreferenceUtil.getNextDate());
            if (!result.equals("")){
                baseView.showSuccess(parseJson(result));
                saveSharedPreference(result);
            }else {
                baseView.showError();
            }
        }
    };

    private MainData parseJson(String data){
        Gson gson = new Gson();
        MainDatas mainDatas = gson.fromJson(data,MainDatas.class);
        return mainDatas.getData();
    }

    private void saveSharedPreference(String result){
        sharedPreferenceUtil.setCurrentDate(parseJson(result).getDate().getCurr()+"");
        sharedPreferenceUtil.setBeforeDate(parseJson(result).getDate().getPrev()+"");
        sharedPreferenceUtil.setNextDate(parseJson(result).getDate().getNext()+"");
    }
}
