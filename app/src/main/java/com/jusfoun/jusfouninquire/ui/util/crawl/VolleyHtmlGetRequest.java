package com.jusfoun.jusfouninquire.ui.util.crawl;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.jusfoun.jusfouninquire.net.volley.VolleyHttpHeaderParser;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;

import java.io.UnsupportedEncodingException;


/**
 * @author henzil
 * @version create time:15/1/21_下午4:21
 * @Description 重写关于volley get请求方法
 */
public class VolleyHtmlGetRequest extends Request<ResultModel> {

    private final Response.Listener<ResultModel> mListener;
    private Context mContext;

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

    public VolleyHtmlGetRequest(String url, Response.Listener<ResultModel> listener,
                                Response.ErrorListener errorListener, Context mContext) {
        super(Method.GET, url, errorListener);
        LogUtil.e("TAG", "request_url:========" + url);
        mListener = listener;
        this.mContext = mContext;
    }


    private Response<ResultModel> responseWrapper;

    /**
     * 解析 response
     *
     * @param response
     * @return
     */
    @Override
    protected Response<ResultModel> parseNetworkResponse(NetworkResponse response) {
        final String parsed;
        try {
            LogUtil.e("tag","response.data="+response.statusCode);
             parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "UTF-8"));

            ResultModel model = new ResultModel();
            model.setCode(response.statusCode);
            model.setResult(parsed);
            responseWrapper = Response
                    .success(model, VolleyHttpHeaderParser.volleyParseCacheHeaders(response));

            return responseWrapper;
        } catch (UnsupportedEncodingException e) {
            Log.e("error", e.toString());
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(ResultModel response) {
        mListener.onResponse(response);
    }


}

