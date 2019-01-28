package com.jusfoun.jusfouninquire.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.route.WageInfoModel;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import java.util.List;

import netlib.util.EventUtils;

/**
 * Created by lsq on 2016/8/9.
 * 有奖问答的dialog和工资调查不一样
 */
public class QuestionnaireInvestigationDialog extends Dialog {
    private Context mContext;
    private TextView title, contentText;
    private SimpleDraweeView showImage;
    private Button agree, hope;
    private List<WageInfoModel> wageInfoModelList;

    public QuestionnaireInvestigationDialog(Context context) {
        super(context);
        mContext = context;
        initViews();
        initAction();
    }

    public QuestionnaireInvestigationDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        initViews();
        initAction();
    }

    protected QuestionnaireInvestigationDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initViews();
        initAction();
    }

    private void initViews() {
        setContentView(R.layout.questionnaire_investigation);
        title = (TextView) findViewById(R.id.title);
        contentText = (TextView) findViewById(R.id.text_content);
        showImage = (SimpleDraweeView) findViewById(R.id.image_content);
        hope = (Button) findViewById(R.id.positiveButton);
        agree = (Button) findViewById(R.id.negativeButton);

        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext) * 0.9);
        lp.height = (int) (PhoneUtil.getDisplayHeight(mContext) * 0.9);

        contentText = (TextView) findViewById(R.id.text_content);
    }

    private void initAction() {

        hope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.ADVERT26);
                dismiss();

//                mContext.startActivity(new Intent(mContext, TaskScheduleActivity.class));
            }
        });
        agree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.ADVERT27);
                dismiss();
                // TODO 暂时跳转到一个输入url ，抓取内容单独页面。
//                mContext.startActivity(new Intent(mContext, TestUrlGetActivity.class));

            }
        });
    }

    public void setData(WageInfoModel model) {

        if (model == null || model.getDataResult() == null) {
            return;
        }
        title.setText(model.getDataResult().getTitle());

        contentText.setText(model.getDataResult().getContent());
        hope.setText(model.getDataResult().getJoin());
        agree.setText(model.getDataResult().getCancle());

        if (model.getDataResult().getImageurl() != null) {
            showImage.setImageURI(Uri.parse(model.getDataResult().getImageurl()));
        }


    }


}
