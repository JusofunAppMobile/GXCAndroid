package com.gxc.retrofit;


import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface Api {

    @POST("/app/UserLogin/sendMesCode")
    Observable<NetModel> sendMesCode(@QueryMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("/app/UserLogin/loginApp")
    Observable<NetModel> loginApp(@FieldMap Map<String, Object> params);

}
