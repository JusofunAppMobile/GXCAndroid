package com.jusfoun.jusfouninquire.ui.util.crawl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * @author zhaoyapeng
 * @version create time:17/8/210:15
 * @Email zyp@jusfoun.com
 * @Description ${存储任务 任务结果}
 */
public class TaskSharedpreference {

    private static String TASK = "task";

    private static String TASKDATA = "taskdata";

    private static String TASKRESULT = "taskresult";

    /**
     * 存储任务
     */
    public static void saveTask(Context mContext, TaskModel model) {
        String task = new Gson().toJson(model);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(TASK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TASKDATA, task);

        // 提交数据有两种方法 apply()  commit()
        // 区别在于 apply() 会将数据先存储在内存中，然后异步存储至磁盘，commit() 同步存储到 磁盘
        // 此处采用commit，确保 存储成功之后 才进行下一操作
        editor.commit();
    }


    /**
     * 存储本次任务结果
     */
    public static void saveTaskResult(Context mContext, String result) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(TASK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TASKRESULT, result);
        editor.commit();
    }


    /**
     * 获取任务
     * */
    public static TaskModel getTask(Context mContext) {
        String task;
        TaskModel model;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(TASK, Context.MODE_PRIVATE);
        task = sharedPreferences.getString(TASKDATA, "");
        if(TextUtils.isEmpty(task)){
            return null;
        }
        model = new Gson().fromJson(task, TaskModel.class);

        return model;
    }


    /**
     *  获取任务结果
     * */
    public static ResultModel getTaskResult(Context mContext){
        String task;
        ResultModel model;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(TASK, Context.MODE_PRIVATE);
        task = sharedPreferences.getString(TASKRESULT, "");
        if(TextUtils.isEmpty(task)){
            return null;
        }
        model = new Gson().fromJson(task, ResultModel.class);

        return model;
    }

    /**
     *  清空
     * */
    public static void clearTask(Context mContext){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(TASK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }



}
