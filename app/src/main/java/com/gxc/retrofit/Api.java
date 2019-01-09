package com.gxc.retrofit;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface Api {

    //    9	首页-热门学校、专业
    @POST("/College/GetHotCollege")
    Observable<NetModel> getHotCollege(@QueryMap Map<String, Object> params);

}
