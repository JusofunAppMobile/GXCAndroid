package com.gxc.retrofit;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.net.model.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * model基类
 *
 * @时间 2017/6/29
 * @作者 LiuGuangDan
 */

public class NetModel extends BaseModel implements Serializable {

    private static final long serialVersionUID = 5268625605268545266L;

//    public int result;

//    public String msg;

    public Object data;

    /**
     * 判断是否为成功状态
     *
     * @return
     */
    public boolean success() {
        return result == 0;
    }

    public <T> T dataToObject(String key, Class<T> clazz) {
        if (data == null) return null;
        Gson gson = new Gson();
        try {
            JSONObject obj = new JSONObject(gson.toJson(data));
            return new Gson().fromJson(obj.getString(key), clazz);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T dataToObject(Class<T> clazz) {
        if (data == null) return null;
        return new Gson().fromJson(new Gson().toJson(data), clazz);
    }



    public <T> List<T> dataToList(String key, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            JSONObject obj = new JSONObject(gson.toJson(data));
            JSONArray array = obj.getJSONArray(key);
            if (array.length() > 0) {
                List<T> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++)
                    list.add(gson.fromJson(array.getString(i), clazz));
                return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public <T> T fromJSONObject(JSONObject obj, Class<T> clazz) {
        return new Gson().fromJson(obj.toString(), clazz);
    }

    public JSONObject getDataJSONObject() {
        try {
            return new JSONObject(new Gson().toJson(this)).getJSONObject("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
