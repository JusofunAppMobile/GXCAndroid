package com.gxc.retrofit;


import com.gxc.model.UserModel;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.InquireApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderWeather implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder()
                /**********添加头文件**********/
                .addHeader("Version", AppUtils.getVersionName(InquireApplication.application))
                .addHeader("VersionCode", String.valueOf(AppUtils.getVersionCode(InquireApplication.application)))
                .addHeader("AppType", String.valueOf(0))
                .addHeader("Channel", "test")
                .addHeader("Deviceid", "test");

        UserModel model = AppUtils.getUser();
        if (model != null)
            builder.addHeader("AccessToken", "");
        return chain.proceed(builder.build());
    }
}
