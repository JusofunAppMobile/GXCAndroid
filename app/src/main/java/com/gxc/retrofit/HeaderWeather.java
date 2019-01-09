package com.gxc.retrofit;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderWeather implements Interceptor {

    private String Version="Version";
    private String VersionCode="VersionCode";
    private String AppType="AppType";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request().newBuilder()
                /**********添加头文件**********/
//                .addHeader(Version, AppUtils.getVersionName(MyApplication.context))
//                .addHeader(VersionCode,AppUtils.getVersionCode(MyApplication.context)+"")
//                .addHeader(AppType,"0")
                .build();

        return chain.proceed(request);
    }
}
