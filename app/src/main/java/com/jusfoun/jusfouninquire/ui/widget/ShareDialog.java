package com.jusfoun.jusfouninquire.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;

import netlib.util.EventUtils;

/**
 * @author lee
 * @version create time:2015/11/2417:20
 * @Email email
 * @Description 分享dialog
 */

public class ShareDialog extends Dialog {
    private RelativeLayout friendersLayout, wechatLayout, sinaLayout;
    private TextView closeBtn;
    private Context mContext;
    public ShareListener shareListener;


    public ShareDialog(Context context) {
        super(context);
        mContext = context;
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    protected ShareDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_share_dialog);
        initView();
    }

    private void initView() {

        wechatLayout = (RelativeLayout) findViewById(R.id.wechat_layout);
        friendersLayout = (RelativeLayout) findViewById(R.id.friend_layout);
        sinaLayout = (RelativeLayout) findViewById(R.id.sina_layout);

        closeBtn = (TextView) findViewById(R.id.close_shareBtn);
        initViewAction();

    }

    public void setShareListener(ShareListener listener) {
        shareListener = listener;
    }

    private void initViewAction() {
        friendersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ME90);
                dismiss();
                if (shareListener != null)
                    shareListener.onFriendersShare();
            }
        });

        wechatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ME89);
                dismiss();
                if (shareListener != null)
                    shareListener.onWechatShare();
            }
        });

        sinaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ME91);
                dismiss();
                if (shareListener != null)
                    shareListener.onSinaShare();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    public interface ShareListener {
        public void onFriendersShare();

        public void onWechatShare();

        public void onSinaShare();
    }

}
