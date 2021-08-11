package com.sumjin.peppymvvm.common.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//retrofit   网络操作
public class RetrofitManager {

    private static final RetrofitManager sInstance = new RetrofitManager();
    private final Retrofit mRetrofit;

    public static RetrofitManager getInstance() {
        return sInstance;
    }

    private RetrofitManager() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new BaseUrlInterceptor())
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}
