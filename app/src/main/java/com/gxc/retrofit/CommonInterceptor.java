package com.gxc.retrofit;



import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl.Builder builder = request.url()
                .newBuilder().scheme(request.url().scheme())
                .host(request.url().host());

//        String userId = LoginSharePresenercs.getUserId(MyApplication.getContext());
//        if (!TextUtils.isEmpty(userId))
//            builder.addQueryParameter("userId", userId);

        Request newRequest = request.newBuilder()
                .method(request.method(), request.body())
                .url(builder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
