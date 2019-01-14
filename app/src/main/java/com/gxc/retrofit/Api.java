package com.gxc.retrofit;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
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

//    @FormUrlEncoded
    @POST("/app/sys/fileupload")
    Observable<NetModel> upload(@Body RequestBody Body);

}
