package com.jusfoun.jusfouninquire.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author zhaoyapeng
 * @version create time:16/8/2717:35
 * @Email zyp@jusfoun.com
 * @Description ${问卷调查 SharePreference}
 */
public class QuestionnaireSharePreference {
    private final static String QUESTIONNAIRE_NAME = "questionnair_name";
    private final static String QUESTIONNAIRE_URL = "questionnair_url";




    public static void setQuestionnaireUrl(Context context,String path){
        SharedPreferences noticePreference = context.getSharedPreferences(QUESTIONNAIRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = noticePreference.edit();
        editor.putString(QUESTIONNAIRE_URL, path);
        editor.commit();
    }


    public static String getQuestionnaireUrl(Context context){
        SharedPreferences noticePreference = context.getSharedPreferences(QUESTIONNAIRE_NAME, Context.MODE_PRIVATE);
        String btnUrl = noticePreference.getString(QUESTIONNAIRE_URL, "");
        return btnUrl;
    }
}
