package com.gxc.retrofit;


import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface Api {

    @FormUrlEncoded
    @POST("/app/UserLogin/sendMesCode")
    Observable<NetModel> sendMesCode(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/UserLogin/loginApp")
    Observable<NetModel> loginApp(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/UserLogin/RegisterApp")
    Observable<NetModel> registerApp(@FieldMap Map<String, Object> params);

}
