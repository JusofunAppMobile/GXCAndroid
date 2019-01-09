package com.jusfoun.jusfouninquire.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.constant.NetConstant;
import com.jusfoun.jusfouninquire.ui.util.VolleyGetRequest;
import com.jusfoun.jusfouninquire.ui.util.VolleyUtil;

import netlib.util.EncryptUtil;


/**
 * @author henzil
 * @version create time:2017/8/1_上午11:23
 * @Description 测试用的一个页面
 */

public class TestUrlGetActivity extends BaseInquireActivity {

    private EditText editText, headEditText;
    private TextView textView;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_test_url_get);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        editText.setText("https://www.qichacha.com/firm_de88d04171a0353dff0d77f04ffbc0db");

        headEditText = (EditText) findViewById(R.id.headEditText);
    }

    @Override
    protected void initWidgetActions() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doGetUrl();

            }
        });
        textView.append("head输入框 用法：以## 分割每个head值；以@@ 分割每个head的key  value；比如：cooki@@123456##mdk@@gb2312");
        textView.append("\n");
//        textView.append(EncryptUtil.encryptAES("111111") + "\n" + EncryptUtil.decryptAES("RJdRK4zXU46pBJdL27v2MA=="));
        textView.append(EncryptUtil.encryptAES("中国移动") + "\n" + EncryptUtil.decryptAES("RJdRK4zXU46pBJdL27v2MA=="));
        Log.e("tag","encryptAES(\"111111\") = " +EncryptUtil.encryptAES("中国移动"));
        Log.e("tag","decryptAES(\"RJdRK4zXU46pBJdL27v2MA==\") = " +EncryptUtil.decryptAES("RJdRK4zXU46pBJdL27v2MA=="));

        textView.append("\n");
        Log.e("tag","encryptAESNoIvParameterSpec(\"111111\") = " +EncryptUtil.encryptAESNoIvParameterSpec("111111"));
        textView.append("\n" + EncryptUtil.encryptAESNoIvParameterSpec("111111"));
        textView.append("\n");
        textView.append("\n"+EncryptUtil.decryptAESNoIvParameterSpec("uAqCEL/ZyZ7VhVkqUtp6JA=="));
    }

    private void doGetUrl(){
        String urlStr = editText.getText().toString();
        if(TextUtils.isEmpty(urlStr)){
            return;
        }
        String headStr = headEditText.getText().toString();
        VolleyGetRequest<String> getRequest=new VolleyGetRequest<String>(
                urlStr, headStr
                , String.class, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textView.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },this){
            @Override
            public Priority getPriority() {
                return Priority.HIGH;
            }
        };
        //        getRequest.setShouldCache(false);

        getRequest.setShouldCache(false);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(NetConstant.TIMEOUT, 1, 1.0f));
        getRequest.setTag(this.TAG);
        VolleyUtil.getQueue(this).add(getRequest);


    }







}
