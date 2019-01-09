package com.jusfoun.jusfouninquire.ui.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.jusfoun.jusfouninquire.net.volley.VolleyHttpHeaderParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author henzil
 * @version create time:15/1/21_下午4:21
 * @Description 重写关于volley get请求方法
 */
public class VolleyGetRequest<T extends String> extends Request<T> {
    private static final int DEFAULT_NET_TYPE = 0;

    private final Response.Listener<T> mListener;
    private Class<T> modelClass;
    private Context mContext;

    private String headStr;

    /**
     * 重新定义缓存key
     *
     * @return
     */
    @Override
    public String getCacheKey() {
        String params = "";
        try {
            params = getParams().toString();
        } catch (Exception e) {

        }
        return getUrl() + params;
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    /**
     * null 正常逻辑
     * 可以重写此方法，返回json数据
     * TODO
     *
     * @return
     */
    public String getDemoRespose() {
        return null;
    }

    public VolleyGetRequest(String url, String headStr, Class<T> modelClass, Response.Listener<T> listener,
                            Response.ErrorListener errorListener, Context mContext) {
        super(Method.GET, url, errorListener);
        Log.e("TAG", "request_url:========" + url);
        this.headStr = headStr;
        mListener = listener;
        this.mContext = mContext;
        this.modelClass = modelClass;
    }


    private Response<T> responseWrapper;

    /**
     * 解析 response
     *
     * @param response
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        final String parsed;
        try {

             parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "UTF-8"));
            Log.e("tag", "网络请求结果=" + parsed);

                    string2File(parsed, PathUtil.getFilePath(mContext)+"firm.txt");


            responseWrapper = Response
                    .success( (T) parsed, VolleyHttpHeaderParser.volleyParseCacheHeaders(response));
            return responseWrapper;
        } catch (UnsupportedEncodingException e) {
            Log.e("error", e.toString());
            return Response.error(new ParseError(e));
        }

    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put("Connection", "keep-alive");

        String[] key_values = headStr.split("##");
        if(key_values != null){
            for(String key_value : key_values){
                try {
                    headerParams.put(key_value.split("@@")[0], key_value.split("@@")[1]);
                } catch (Exception e){

                }
            }
        }

        Log.e("tag", "headerParams=" + headerParams.toString());
        return headerParams;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    public static boolean string2File(String res, String filePath) {
        Log.e("tag","filePath="+filePath);
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            Log.e("tag","IOException="+e);
            e.printStackTrace();
            flag = false;
            return flag;
        }  catch (Exception e) {
            Log.e("tag","Exception="+e);
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}

