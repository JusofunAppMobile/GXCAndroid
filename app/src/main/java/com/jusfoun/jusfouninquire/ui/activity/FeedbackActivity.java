package com.jusfoun.jusfouninquire.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.route.PersonCenterHelper;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.widget.DisableMenuEditText;

import java.util.HashMap;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午4:26
 * @Email zyp@jusfoun.com
 * @Description ${意见反馈}
 */
public class FeedbackActivity extends BaseInquireActivity {
    /**常量*/

    /**
     * 组件
     */
    private DisableMenuEditText contentFeedBackEdit,contactFeedbackEdit;
    private Button submitBtn;
    /**变量*/
    private String content,feedbackContact;
    private String userId = "";

    private TitleView titleView;

    /**
     * 对象
     */

    @Override
    protected void initData() {
        if(InquireApplication.getUserInfo() == null){
            userId = "";
        }else {
            userId = TextUtils.isEmpty(InquireApplication.getUserInfo().getUserid()) ? "" : InquireApplication.getUserInfo().getUserid();
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_feedback);

        contentFeedBackEdit = (DisableMenuEditText) findViewById(R.id.feekback_contact);
        contactFeedbackEdit = (DisableMenuEditText) findViewById(R.id.contact_person);
        submitBtn = (Button) findViewById(R.id.submitFeedback);

        titleView = (TitleView) findViewById(R.id.titleView);
        titleView.setTitle("意见反馈");
        titleView.setRightText("发送");
        titleView.setRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick(View v) {
                feedBackContent();
            }
        });

    }

    @Override
    protected void initWidgetActions() {
        contentFeedBackEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void afterTextChanged(Editable s) {
                if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
                    if (s.length()>=2000)
                        Toast.makeText(FeedbackActivity.this,"最多输入"+2000+"个字",Toast.LENGTH_SHORT).show();
                    return;
                }
                int maxLine=-1;
                InputFilter filter[]=contactFeedbackEdit.getFilters();
                for (InputFilter inputFilter : filter) {
                    if (inputFilter instanceof InputFilter.LengthFilter)
                        maxLine=((InputFilter.LengthFilter)inputFilter).getMax();
                }
                if (maxLine!=-1&&s.length()>=maxLine)
                    Toast.makeText(FeedbackActivity.this,"最多输入"+maxLine+"个字",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void feedBackContent(){
        content = contentFeedBackEdit.getText().toString().trim();
        feedbackContact = contactFeedbackEdit.getText().toString().trim();

        if(TextUtils.isEmpty(content)){
            showToast("请告诉我们您宝贵的建议");
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("userid",userId);
        map.put("content",content);
        map.put("phone",feedbackContact+"");
        map.put("Type","1");

        showLoading();
        PersonCenterHelper.doNetPostFeedBack(mContext, map,getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel model = (BaseModel) data;
                if(model.getResult() == 0){
                    showToast(model.getMsg()+"");
                    finish();
                }else {
                    showToast(model.getMsg()+"");
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
