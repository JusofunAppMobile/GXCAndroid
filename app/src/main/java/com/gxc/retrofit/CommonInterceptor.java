package com.gxc.retrofit;


import com.google.gson.Gson;
import com.gxc.model.UserModel;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.TimeOut;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommonInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl.Builder builder = request.url()
                .newBuilder().scheme(request.url().scheme())
                .host(request.url().host());

//        String postBodyString = bodyToString(request.body());
        FormBody.Builder formBody = new FormBody.Builder();
//        if (TextUtils.isEmpty(postBodyString)) {
//        } else {

            if (request.body() instanceof FormBody) {
                FormBody oldRormBpody = (FormBody) request.body();
                HashMap<String, Object> map = new HashMap<>();
                UserModel model = AppUtils.getUser();
                if (model != null)
                    map.put("userId", model.userId);
                for (int i = 0; i < oldRormBpody.size(); i++) {
                    map.put(oldRormBpody.name(i), oldRormBpody.value(i));
//                    formBody.add(oldRormBpody.name(i),oldRormBpody.value(i));
                }
                TimeOut timeOut = new TimeOut(InquireApplication.application);
                map.put("t", timeOut.getParamTimeMollis() + "");

                formBody.add("data", new Gson().toJson(map));
                formBody.add("m", timeOut.MD5GXCtime(map));

            } else {
                return chain.proceed(request);
            }
            Request newRequest = request.newBuilder()
                    .method(request.method(), formBody.build())
                    .url(builder.build())
                    .build();
            return chain.proceed(newRequest);

//        }
//        Request newRequest = request.newBuilder()
//                .method(request.method(), request.body())
//                .url(builder.build())
//                .build();
//
//
//        return chain.proceed(newRequest);
    }

    private final Charset UTF8 = Charset.forName("UTF-8");

    private String bodyToString(final RequestBody request) {
        try {
            if (request != null) {
                okio.Buffer buffer = new okio.Buffer();
                request.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = request.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                return buffer.readString(charset);
            }
        } catch (final IOException e) {
            return "";
        }
        return "";
    }

}
