//
//package com.jusfoun.jusfouninquire.ui;
//
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.view.View;
//
//import com.jusfoun.library.swipebacklayout.SwipeBackActivityBase;
//import com.jusfoun.library.swipebacklayout.SwipeBackActivityHelper;
//import com.jusfoun.library.swipebacklayout.SwipeBackLayout;
//import com.jusfoun.library.swipebacklayout.Utils;
//
//
//public class SwipeBackActivity extends ActionBarActivity implements SwipeBackActivityBase {
//    private SwipeBackActivityHelper mHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mHelper = new SwipeBackActivityHelper(this);
//        mHelper.onActivityCreate();
//    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        mHelper.onPostCreate();
//    }
//
//    @Override
//    public View findViewById(int id) {
//        View v = super.findViewById(id);
//        if (v == null && mHelper != null)
//            return mHelper.findViewById(id);
//        return v;
//    }
//
//    @Override
//    public SwipeBackLayout getSwipeBackLayout() {
//        return mHelper.getSwipeBackLayout();
//    }
//
//    @Override
//    public void setSwipeBackEnable(boolean enable) {
//        getSwipeBackLayout().setEnableGesture(enable);
//    }
//
//    @Override
//    public void scrollToFinishActivity() {
//        Utils.convertActivityToTranslucent(this);
//        getSwipeBackLayout().scrollToFinishActivity();
//    }
//}
