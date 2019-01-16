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

    @FormUrlEncoded
    @POST("/app/MonitoringDynamics/GetmonitoringDynamics")
    Observable<NetModel> monitorList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/MonitoringDynamics/AddOrCancelMonitor")
    Observable<NetModel> monitorUpdate(@FieldMap Map<String, Object> params);

    @POST("/app/sys/fileupload")
    Observable<NetModel> upload(@Body RequestBody Body);


    @POST("/app/CreditService/GetHomeInfo")
    Observable<NetModel> getCreditService();


    @FormUrlEncoded
    @POST("/app/CorporateInfo/CreditReport")
    Observable<NetModel> getCreditReport(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/subCompanyMsg")
    Observable<NetModel> subCompanyMsg(@FieldMap Map<String, Object> params);

    @POST("/app/CreditService/CompanyInfoReporting")
    Observable<NetModel> companyInfoReporting();


    @FormUrlEncoded
    @POST("/app/order/getOrderMsg")
    Observable<NetModel> getOrderMsg(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/order/orderPay")
    Observable<NetModel> orderPay(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/getIdentVip")
    Observable<NetModel> getIdentVip(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/MonitoringDynamics/GetFilterCondition")
    Observable<NetModel> getFilterCondition(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/MonitoringDynamics/DynamicDetails")
    Observable<NetModel> dynamicDetails(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/mineManager/OrderList")
    Observable<NetModel> orderList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/mineManager/monitorList")
    Observable<NetModel> myMonitorList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/CorporateInfo/GetCorporateInfo")
    Observable<NetModel> GetCorporateInfo(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/sys/searchWord")
    Observable<NetModel> searchWord(@FieldMap Map<String, Object> params);
}
