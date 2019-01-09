package com.jusfoun.bigdata;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author henzil
 * @version create time:15/9/9_下午2:55
 * @Description 附近地图 页面 工具类
 */
public class NearMapUtil {

    //*******************************李建 2015-08-10 选择行业*******************************

    //对map集合赋 更多筛选后的值
    public static HashMap<String, String> moreFilter(TypeMoreModel model, HashMap<String, String> mNearHashMap) {
        if (!TextUtils.isEmpty(model.getFounder())) {
            mNearHashMap.put("legalname", model.getFounder());
        } else {
            if (mNearHashMap.containsKey("legalname")) {
                mNearHashMap.remove("legalname");
            }
        }

        if (model.getFinancing() == 1) {
            mNearHashMap.put("has_raise", model.getFinancing() + "");
        } else {
            if (mNearHashMap.containsKey("has_raise")) {
                mNearHashMap.remove("has_raise");
            }
        }

        if (model.getMerger() == 1) {
            mNearHashMap.put("has_ma", model.getMerger() + "");
        } else {
            if (mNearHashMap.containsKey("has_ma")) {
                mNearHashMap.remove("has_ma");
            }
        }

        if (model.getOnTheMarket() == 1) {
            mNearHashMap.put("has_ipo", model.getOnTheMarket() + "");
        } else {
            if (mNearHashMap.containsKey("has_ipo")) {
                mNearHashMap.remove("has_ipo");
            }
        }
        //有，则至少有第一级，其他可有可无
        if (!TextUtils.isEmpty(model.getOneindustry())) {
            mNearHashMap.put("industryone", model.getOneindustry());
            mNearHashMap.put("industrytwo", model.getTwoindustry());
            mNearHashMap.put("industrythree", model.getThreeindustry());
            mNearHashMap.put("industryfour", model.getFourindustry());

        } else {
            if (mNearHashMap.containsKey("industryone")) {
                mNearHashMap.remove("industryone");
            }
            if (mNearHashMap.containsKey("industrytwo")) {
                mNearHashMap.remove("industrytwo");
            }
            if (mNearHashMap.containsKey("industrythree")) {
                mNearHashMap.remove("industrythree");
            }
            if (mNearHashMap.containsKey("industryfour")) {
                mNearHashMap.remove("industryfour");
            }
        }

        if (!TextUtils.isEmpty(model.getAssetValue())) {
            mNearHashMap.put("totalmoney", model.getAssetValue());
        } else {
            if (mNearHashMap.containsKey("totalmoney")) {
                mNearHashMap.remove("totalmoney");
            }
        }

        return mNearHashMap;
    }


    // wangchenchen 初始化查询参数
    public static HashMap<String, String> initNearHashMap(HashMap<String, String> map) {
        if (map.containsKey("incomemoney"))
            map.remove("incomemoney");
        if (map.containsKey("productname"))
            map.remove("productname");
        if (map.containsKey("has_raise"))
            map.remove("has_raise");
        if (map.containsKey("has_ma"))
            map.remove("has_ma");
        if (map.containsKey("has_ipo"))
            map.remove("has_ipo");
        if (map.containsKey("currentlnglat"))
            map.remove("currentlnglat");
        if (map.containsKey("sort"))
            map.remove("sort");
        if (map.containsKey("legalname"))
            map.remove("legalname");
        if (map.containsKey("industryone"))
            map.remove("industryone");
        if (map.containsKey("industrytwo"))
            map.remove("industrytwo");
        if (map.containsKey("industrythree"))
            map.remove("industrythree");
        if (map.containsKey("industryfour"))
            map.remove("industryfour");
        if (map.containsKey("totalmoney"))
            map.remove("totalmoney");
        if (map.containsKey("distance"))
            map.remove("distance");

        return map;
    }


    public static HashMap<String, String> GetGeoCoderResultMap(Context context, HashMap<String, String> mNearHashMap, ReverseGeoCodeResult result, int mapType, MapView mMapView, BaiduMap mBaiduMap){

        String province = result.getAddressDetail().province;
        String city = result.getAddressDetail().city;
        String area = result.getAddressDetail().district;


        if (mapType == 1) {

            if (mNearHashMap.containsKey("province")) {
                mNearHashMap.remove("province");
            }
            if (mNearHashMap.containsKey("city")) {
                mNearHashMap.remove("city");
            }
            if (mNearHashMap.containsKey("area")) {
                mNearHashMap.remove("area");
            }
            if (mNearHashMap.containsKey("minLat")) {
                mNearHashMap.remove("minLat");
            }
            if (mNearHashMap.containsKey("maxLng")) {
                mNearHashMap.remove("maxLng");
            }
            if (mNearHashMap.containsKey("maxLat")) {
                mNearHashMap.remove("maxLat");
            }
            if (mNearHashMap.containsKey("minLng")) {
                mNearHashMap.remove("minLng");
            }
            mNearHashMap.put("maptype", mapType + "");

        } else if (mapType == 2) {

            mNearHashMap.put("city", "");
            mNearHashMap.put("area", "");
            mNearHashMap.put("maptype", mapType + "");
            // 移除最大最小经纬度
            if (mNearHashMap.containsKey("minLat")) {
                mNearHashMap.remove("minLat");
            }
            if (mNearHashMap.containsKey("maxLng")) {
                mNearHashMap.remove("maxLng");
            }
            if (mNearHashMap.containsKey("maxLat")) {
                mNearHashMap.remove("maxLat");
            }
            if (mNearHashMap.containsKey("minLng")) {
                mNearHashMap.remove("minLng");
            }
        } else if (mapType == 3) {

            mNearHashMap.put("area", "");
            mNearHashMap.put("maptype", mapType + "");

            // 移除最大最小经纬度
            if (mNearHashMap.containsKey("minLat")) {
                mNearHashMap.remove("minLat");
            }
            if (mNearHashMap.containsKey("maxLng")) {
                mNearHashMap.remove("maxLng");
            }
            if (mNearHashMap.containsKey("maxLat")) {
                mNearHashMap.remove("maxLat");
            }
            if (mNearHashMap.containsKey("minLng")) {
                mNearHashMap.remove("minLng");
            }
        } else if (mapType == 4) {

            Map<String, LatLng> map = getScreenLatlng(context,mMapView, mBaiduMap);
            LatLng leftTopLatlng = map.get("rightTop");
            LatLng rightBottomLatlng = map.get("leftBottom");

            if (leftTopLatlng != null && rightBottomLatlng != null) {
                mNearHashMap.put("area", "");
                mNearHashMap.put("minLat", rightBottomLatlng.latitude + "");
                mNearHashMap.put("maxLng", leftTopLatlng.longitude + "");
                mNearHashMap.put("maxLat", leftTopLatlng.latitude + "");
                mNearHashMap.put("minLng", rightBottomLatlng.longitude + "");
                mNearHashMap.put("maptype", mapType + "");
            }
        }

        return mNearHashMap;
    }



    /**
     * 获取当前屏幕最大经纬度以及最小经纬度
     *
     * @return
     */
    private static HashMap<String, LatLng> getScreenLatlng(Context context, MapView mMapView, BaiduMap mBaiduMap) {
        // 地图中心坐标
        HashMap<String, LatLng> map = new HashMap<String, LatLng>();
        if(mBaiduMap==null){
            return map;
        }
        MapStatus ms = mBaiduMap.getMapStatus();
        if(ms==null){
            return map;
        }
        LatLngBounds latLngBounds = ms.bound;

        if(latLngBounds==null){
            return map;
        }
        LatLng N_Elatlng  = latLngBounds.northeast;
        LatLng S_Wlatlng = latLngBounds.southwest;


        map.put("rightTop", N_Elatlng);
        map.put("leftBottom", S_Wlatlng);
        return map;
    }

    public static HashMap<String,String> reSetMore(HashMap<String,String> map){
        if (map.containsKey("has_raise"))
            map.remove("has_raise");
        if (map.containsKey("has_ma"))
            map.remove("has_ma");
        if (map.containsKey("has_ipo"))
            map.remove("has_ipo");
        if (map.containsKey("legalname"))
            map.remove("legalname");
        if (map.containsKey("industryone"))
            map.remove("industryone");
        if (map.containsKey("industrytwo"))
            map.remove("industrytwo");
        if (map.containsKey("industrythree"))
            map.remove("industrythree");
        if (map.containsKey("industryfour"))
            map.remove("industryfour");
        if (map.containsKey("totalmoney"))
            map.remove("totalmoney");

        return map;
    }

    /**
     * 清除当前坐标参数
     */
    public static HashMap<String, String> removeCurrentLatLng(HashMap<String, String> mNearHashMap) {
        if (mNearHashMap != null) {
            if (mNearHashMap.containsKey("currentlnglat")) {
                mNearHashMap.remove("currentlnglat");
            }
        }
        return mNearHashMap;
    }

    public static String getProvince(String province, int mapType){
        String mCurrentProvince;
        if (province.equals("上海市") || province.equals("北京市") || province.equals("天津市") || province
                .equals("重庆市")) {
            mCurrentProvince = province.substring(0, 2);
        } else {
            mCurrentProvince = setmCurrentProvince(province);
        }

        return mCurrentProvince;
    }

    public static String getCity(String province, String city, String area, int mapType){
        String mCurrentCity;
        if (province.equals("上海市") || province.equals("北京市") || province.equals("天津市") || province
                .equals("重庆市")) {
            mCurrentCity = province;
        } else {
            mCurrentCity = setmCurrentCity(city, area);
        }

        return mCurrentCity;
    }

    /**
     * 对特殊省层级进行简化
     *
     * @param province
     */
    private static String setmCurrentProvince(String province) {
        String mCurrentProvince;
        if (province.contains("新疆")) {
            mCurrentProvince = "新疆";
        } else if (province.contains("西藏")) {
            mCurrentProvince = "西藏";
        } else if (province.contains("广西")) {
            mCurrentProvince = "广西";
        } else if (province.contains("宁夏")) {
            mCurrentProvince = "宁夏";
        } else if (province.contains("内蒙古")) {
            mCurrentProvince = "内蒙古";
        } else {
            mCurrentProvince = province;
        }

        return mCurrentProvince;
    }

    /**
     * 对特殊市层级进行简化
     *
     * @param city
     */
    private static String setmCurrentCity(String city, String area) {
        String mCurrentCity;
        if (city.contains("新疆维吾尔自治区直辖县级行政单位")) {
            mCurrentCity = area;
        } else {
            mCurrentCity = city;
        }

        return mCurrentCity;
    }


}
