package com.jusfoun.jusfouninquire.ui;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.ui.widget.LoadingDialog;
import com.jusfoun.library.swipebacklayout.SwipeBackLayout;

import de.greenrobot.event.EventBus;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午3:30
 * @Email zyp@jusfoun.com
 * @Description ${提取公共方法}
 */
public abstract class BaseInquireActivity extends BaseActivity {
    private LoadingDialog loadingDialog;
    protected SwipeBackLayout mSwipeBackLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {

//        mSwipeBackLayout = getSwipeBackLayout();
//        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
//        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
//            @Override
//            public void onScrollStateChange(int state, float scrollPercent) {
//
//            }
//
//            @Override
//            public void onEdgeTouch(int edgeFlag) {
//            }
//
//            @Override
//            public void onScrollOverThreshold() {
//            }
//        });
//        Log.e("tag", "initData1");
    }

    /**
     * 页面跳转
     */
    protected void goActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void goActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示toast
     */
    protected void showToast(String msg) {
        if (msg != null && !msg.equals("")) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 显示toast
     */
    protected void showToast(int msgid) {
        Toast.makeText(this, msgid, Toast.LENGTH_SHORT).show();
    }

    /**
     * 接收event 重写此方法
     * */
    public void onEvent(IEvent event){

    }


    protected void initDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this, R.style.my_dialog);
            loadingDialog.setCancelable(true);
            loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (loadingDialog != null)
                            loadingDialog.cancel();
                    }
                    return true;
                }
            });
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            if (!this.isDestroyed() && !this.isFinishing()){
                loadingDialog.show();
            }

        }
    }

    // 隐藏加载对话框
    protected void hideLoadDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



}
