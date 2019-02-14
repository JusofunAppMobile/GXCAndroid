package com.gxc.retrofit;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpEngine;
import okio.Buffer;
import okio.BufferedSource;

public class LoggingInterceptor implements Interceptor {

    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();

        String body = null;

        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }

        String requestHeaders = parseHeaders(request.headers());

        Logger.e("发送请求 method：%s\nurl:       %s\nheaders:  %s\nbody:     %s", request.method(), request.url(), requestHeaders, getDecode(body));

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        String rBody = null;

        if (HttpEngine.hasBody(response)) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }
            rBody = buffer.clone().readString(charset);
        }

        Logger.e("收到响应 响应码：%s 响应时间：%ss\n请求url:        %s\n请求body:       %s\n请求header:     %s\n响应body:       %s",
                response.code(), tookMs, response.request().url(), getDecode(body), requestHeaders, getDecode(rBody));

        Logger.json(rBody);

        return response;
    }

    public String parseHeaders(Headers headers) {
        if (headers != null && headers.size() > 0) {
            JSONObject obj = new JSONObject();
            for (String name : headers.names()) {
                try {
                    obj.put(name, headers.get(name));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return obj.toString();
        }
        return null;
    }

    private String getDecode(String value) {
        try {
            return URLDecoder.decode(value);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return "图片上传，body隐藏不显示输出"; // 图片decode会失败，长度太大，不显示
    }
}


