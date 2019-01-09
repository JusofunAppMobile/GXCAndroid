package com.jusfoun.bigdata;

/**
 * @author zhaoyapeng
 * @version create time:18/1/515:27
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class UrlConstant {
    public static final String GetAreaOrIndustryList = "api/JusFounBigData/GetAreaOrIndustryList";
    public static final String MycollectionOperate = "api/JusFounBigData/MycollectionOperate";
//    public static final String GetEntBasicFacts = "api/JusFounBigData/GetEntBasicFacts";
//    public static final String GetMapListV4_0_3 = "api/JusFounBigData/GetMapListV4_0_3";
//    public static final String GetMapCompanyListV4_0_3 = "/api/JusFounBigData/GetMapCompanyListV4_0_3";

    public static final String GetMapListV4_0_3 = "/api/Search/GetSearByNearLatAndLngMax";
    public static final String GetMapCompanyListV4_0_3="/api/Search/GetSearByNearLatAndLngList";

    public static final String GetMapCompanyByLaglon="/api/Search/GetSearByNearLatAndLngMin";

    // 企业概况
    public static final String GetEntBasicFacts = "/api/entdetail/MapCompanyInform";

}
