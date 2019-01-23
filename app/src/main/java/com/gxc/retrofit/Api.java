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

    @POST("/app/CreditService/CompanyInfoReportingH5")
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
    @POST("/app/sys/hotSearchWord")
    Observable<NetModel> searchWord(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/sys/insertSearchWord")
    Observable<NetModel> insertSearchWord(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/mineManager/CollectionList")
    Observable<NetModel> collectionList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/mineManager/BrowseHistoryList")
    Observable<NetModel> myHistory(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/mineManager/delBrowseHistory")
    Observable<NetModel> delBrowseHistory(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/mineManager/AddOrCancelCollection")
    Observable<NetModel> addOrCancelCollection(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("/app/Company/CompanyInfoEditor")
    Observable<NetModel> CompanyInfoEditor(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/productEditor")
    Observable<NetModel> productEditor(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/honorEditor")
    Observable<NetModel> honorEditor(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/partnerEditor")
    Observable<NetModel> partnerEditor(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/CompanyEmpEditor")
    Observable<NetModel> CompanyEmpEditor(@FieldMap Map<String, Object> params);


    @POST("/app/Company/getCompanyMsg")
    Observable<NetModel> getCompanyMsg();

    @FormUrlEncoded
    @POST("/app/CreditService/CorporateInfoChange")
    Observable<NetModel> CorporateInfoChange(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/CreditService/visitorRecord")
    Observable<NetModel> visitorRecord(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/CorporateInfo/createCreditWord")
    Observable<NetModel> createCreditWord(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/UserLogin/GetVersionNumber")
    Observable<NetModel> checkUpdate(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/CreditService/uploadLetter")
    Observable<NetModel> uploadLetter(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/CreditService/getTemplateByEmail")
    Observable<NetModel> getTemplateByEmail(@FieldMap Map<String, Object> params);

    @POST("/app/errorTypeService/getErrorTypeList")
    Observable<NetModel> getErrorTypeList();

    @FormUrlEncoded
    @POST("/app/CorporateInfo/ObjectionError")
    Observable<NetModel> ObjectionError(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("/app/CreditService/creditError")
    Observable<NetModel> creditError(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/getCompanyInfo")
    Observable<NetModel> getCompanyInfo(@FieldMap Map<String, Object> params);


    @FormUrlEncoded
    @POST("/app/Company/getProductList")
    Observable<NetModel> getProductList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/getHonorList")
    Observable<NetModel> getHonorList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/getpartnerList")
    Observable<NetModel> getpartnerList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Company/getEmployerList")
    Observable<NetModel> getEmployerList(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("/app/Home/GetH5Address")
    Observable<NetModel> getH5Address(@FieldMap Map<String, Object> params);


}