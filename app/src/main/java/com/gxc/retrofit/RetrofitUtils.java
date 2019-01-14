package com.gxc.retrofit;


import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitUtils {

    private int TIMEOUT = 30000;
    private OkHttpClient okHttpClient;
    public Retrofit retrofit;
    public Api api;

    private static class SingletonHolder {
        private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    }

    public static Api getApi() {
        return SingletonHolder.INSTANCE.api;
    }

    private RetrofitUtils() {
        File cacheFile = new File(InquireApplication.application.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//100Mb

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new CommonInterceptor())
                .addInterceptor(new HeaderWeather())
                .addInterceptor(new CacheInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .cache(cache)
                .build();


        retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .client(okHttpClient)
                .baseUrl(InquireApplication.application.getString(R.string.http_host))
                .build();

        api = retrofit.create(Api.class);
    }
}
