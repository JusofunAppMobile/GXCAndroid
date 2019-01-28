package com.jusfoun.jusfouninquire.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;
import com.jusfoun.jusfouninquire.sharedpreference.AdvertiseSharePreference;

import netlib.util.EventUtils;


/**
 * @author lee
 * @version create time:2015/11/1214:34
 * @Email email
 * @Description $广告页面
 */

public class AdvertisementActivity extends BaseInquireActivity {
    /**常量*/
    private static final int DELAY_TIME = 3*1000;
    /**
     * 组件
     */
    private ImageView incomeWebBtn, jumpWebBtn;
    private SimpleDraweeView draweeView;

    /**
     * 变量
     */
    private String adverUrl = null;

    /**对象*/
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                goActivity(MainActivity.class);
                finish();
            }

        }
    };

    private boolean isClickAdver =false;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_advertisement);
        incomeWebBtn = (ImageView) findViewById(R.id.income_adver);
        jumpWebBtn = (ImageView) findViewById(R.id.jump_this_adver);
        draweeView = (SimpleDraweeView) findViewById(R.id.ada);

    }

    @Override
    protected void initWidgetActions() {

        if (!TextUtils.isEmpty(AdvertiseSharePreference.getAdvertiseUrl(mContext))) {
            adverUrl = AdvertiseSharePreference.getAdvertiseUrl(mContext);
        }


        jumpWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ADVERTISEMENT02);
                handler.removeMessages(1);
                goActivity(MainActivity.class);
                finish();
            }
        });

        incomeWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.ADVERTISEMENT03);
//                Intent intent = new Intent(mContext, AdvertisementWebActivity.class);
                if (adverUrl != null && !adverUrl.equals("")) {
                    isClickAdver = true;
                    handler.removeMessages(1);
//                    intent.putExtra(AdvertisementWebActivity.WEB_URL, adverUrl);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(adverUrl);
                    intent.setData(content_url);
                    startActivity(intent);
                }
//                startActivity(intent);
//                finish();
            }
        });

        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext, EventUtils.ADVERTISEMENT01);
            }
        });

        Log.e("tag","AdvertiseSharePreference="+AdvertiseSharePreference.getImageUrl(this));
        if (adverUrl != null && !adverUrl.equals("")) {
//            draweeView.setImageURI(Uri.parse(AdvertiseSharePreference.getImageUrl(this)));
            draweeView.setImageURI(Uri.parse("file://"+AdvertiseSharePreference.getImageUrl(this)));
        } else {
            draweeView.setImageURI(Uri.parse("res:///" + R.mipmap.ic_launcher));
        }

        handler.sendEmptyMessageDelayed(1,DELAY_TIME);

        TouchUtil.createTouchDelegate(jumpWebBtn,40);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isClickAdver){
            goActivity(MainActivity.class);
            finish();
        }
    }
}
