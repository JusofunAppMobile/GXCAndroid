package com.gxc.retrofit;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
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

    @FormUrlEncoded
    @POST("/app/mineManager/upUserInfo")
    Observable<NetModel> updateInfo(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/UserLogin/ExitApp")
    Observable<NetModel> logout(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Home/GetHomeData")
    Observable<NetModel> getHomeData(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/mineManager/upTelphone")
    Observable<NetModel> updatePhone(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Home/IndustryInformation")
    Observable<NetModel> moreNews(@FieldMap Map<String, Object> params);

//    @FormUrlEncoded
    @POST("/app/sys/fileupload")
    Observable<NetModel> upload(@Body RequestBody Body);


    @FormUrlEncoded
    @POST("/app/CreditService/GetHomeInfo")
    Observable<NetModel> getCreditService(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("/app/CorporateInfo/CreditReport")
    Observable<NetModel> getCreditReport(@FieldMap Map<String, Object> params);
}
