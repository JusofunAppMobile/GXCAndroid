package com.jusfoun.jusfouninquire.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.route.PersonCenterHelper;
import com.jusfoun.jusfouninquire.ui.widget.DisableMenuEditText;

import java.util.HashMap;

/**
 * Created by Albert on 2015/11/17.
 * Mail : lbh@jusfoun.com
 * TODO :提交人工查询页面
 * Description : 提交人工查询页面
 */
public class ManuallySearchActivity extends BaseInquireActivity {

    private DisableMenuEditText mContent,mPhone;
    private Button mSubmit;

    private String userId = "";

    @Override
    protected void initData() {
        super.initData();
        if(InquireApplication.getUserInfo() == null){
            userId = "";
        }else {
            userId = TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid()) ? "" : InquireApplication.getUserInfo().getUserid();
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_manually_search_layout);
        mContent = (DisableMenuEditText)findViewById(R.id.feekback_contact);
        mPhone = (DisableMenuEditText)findViewById(R.id.contact_person);
        mSubmit = (Button)findViewById(R.id.submitFeedback);
    }

    @Override
    protected void initWidgetActions() {
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedBackContent();
            }
        });

    }

    private void feedBackContent(){
        String content = mContent.getText().toString().trim();
        String feedbackContact = mPhone.getText().toString().trim();

        if(TextUtils.isEmpty(content)){
            showToast("请输入您想要搜索的内容");
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("userid",userId);
        map.put("content",content);
        map.put("phone",feedbackContact + "");
        map.put("Type","2");

        showLoading();
        PersonCenterHelper.doNetPostFeedBack(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if (model.getResult() == 0) {
                    showToast(model.getMsg() + "");
                    finish();
                } else {
                    showToast(model.getMsg() + "");
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(R.string.net_error);
            }
        });
    }
}
