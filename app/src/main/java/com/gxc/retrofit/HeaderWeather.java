package com.gxc.retrofit;


import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.InquireApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderWeather implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request().newBuilder()
                /**********添加头文件**********/
                .addHeader("Version", AppUtils.getVersionName(InquireApplication.application))
                .addHeader("VersionCode", String.valueOf(AppUtils.getVersionCode(InquireApplication.application)))
                .addHeader("AppType", String.valueOf(0))
                .addHeader("Channel", "test")
                .addHeader("Deviceid", "test")
                .addHeader("AccessToken", "")
                .build();

        return chain.proceed(request);
    }
}
