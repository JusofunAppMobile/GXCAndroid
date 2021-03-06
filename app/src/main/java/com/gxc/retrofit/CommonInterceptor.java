package com.gxc.retrofit;


import com.google.gson.Gson;
import com.gxc.model.UserModel;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.TimeOut;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl.Builder builder = request.url()
                .newBuilder().scheme(request.url().scheme())
                .host(request.url().host());

        FormBody.Builder formBody = new FormBody.Builder();
        HashMap<String, Object> map = new HashMap<>();
        TimeOut timeOut = new TimeOut(InquireApplication.application);
        UserModel model = AppUtils.getUser();
        if (model != null)
            map.put("userId", model.userId);
        else
            map.put("userId", "");
        map.put("t", timeOut.getParamTimeMollis() + "");
        if (request.body() instanceof FormBody) {
            FormBody oldRormBpody = (FormBody) request.body();
            for (int i = 0; i < oldRormBpody.size(); i++) {
                map.put(oldRormBpody.name(i), oldRormBpody.value(i));
            }
            formBody.add("data", new Gson().toJson(map));
            formBody.add("m", timeOut.MD5GXCtime(new Gson().toJson(map)));
        } else if (!(request.body() instanceof MultipartBody)) {
            formBody.add("data", new Gson().toJson(map));
            formBody.add("m", timeOut.MD5GXCtime(new Gson().toJson(map)));
        } else {
            return chain.proceed(request);
        }
        Request newRequest = request.newBuilder()
                .method(request.method(), formBody.build())
                .url(builder.build())
                .build();
        return chain.proceed(newRequest);
    }
}
