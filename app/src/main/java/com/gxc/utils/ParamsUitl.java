package com.gxc.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.TimeOut;

import java.util.HashMap;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1414:06
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class ParamsUitl {
    public static HashMap<String,Object> getParams(Context mContxt,HashMap<String, Object> map){
        TimeOut timeOut = new TimeOut(mContxt);
        map.put("t",timeOut.getParamTimeMollis()+"");
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("data",new Gson().toJson(map));
        hashMap.put("m",timeOut.MD5GXCtime(new Gson().toJson(map)));
        return hashMap;
    }
}
