package com.jusfoun.jusfouninquire.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.route.WageInfoModel;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;

import java.util.List;


/**
 * QuestionnairDialog
 *
 * @author : Albert
 * @Email : liubinhou007@163.com
 * @date : 2017/1/16
 * @Description : version 2.0.6问卷调查对话框
 */
public class QuestionnairDialog extends Dialog{
    private Context mContext;
    private TextView title, contentText;
    private ImageView showImage;
    private Button agree, hope;
    private List<WageInfoModel> wageInfoModelList;
    private SimpleDraweeView contentImg;
    private String url;
    private WageInfoModel model;

    private onQuestionActionListener listener;

    public QuestionnairDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        initView();
        initWidgetAction();
    }

    private void initView() {
        setContentView(R.layout.wage_query_popup_box);
        title = (TextView) findViewById(R.id.title);
        TextPaint tp = title.getPaint();
        tp.setFakeBoldText(true);
        contentText = (TextView) findViewById(R.id.text_content);
        hope = (Button) findViewById(R.id.positiveButton);
        agree = (Button) findViewById(R.id.negativeButton);
        contentImg = (SimpleDraweeView) findViewById(R.id.image_content);


        Window window = this.getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (PhoneUtil.getDisplayWidth(mContext) * 0.9);
        lp.height = (int) (PhoneUtil.getDisplayHeight(mContext) * 0.8);
        window.setGravity(Gravity.CENTER);

    }

    private void initWidgetAction() {
        hope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null){
                    listener.onJoin(model.getDataResult().getId(),model.getDataResult().getHtmlurl());
                }



            }
        });
        agree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null){
                    listener.onCancel();
                }


            }
        });
    }

    public void setData(WageInfoModel model) {

        if (model == null || model.getDataResult() == null) {
            return;
        }
        this.model = model;
        title.setText(model.getDataResult().getTitle());

        contentText.setText(model.getDataResult().getContent());
        hope.setText(model.getDataResult().getJoin());
        agree.setText(model.getDataResult().getCancle());
        url = model.getDataResult().getHtmlurl();
        if (model.getDataResult().getImageurl() != null) {
            contentImg.setImageURI(Uri.parse(model.getDataResult().getImageurl()));
        }


    }


    public void setListener(onQuestionActionListener listener) {
        this.listener = listener;
    }

    public interface onQuestionActionListener{
        void onCancel();
        void onJoin(String id,String url);
    }
}
