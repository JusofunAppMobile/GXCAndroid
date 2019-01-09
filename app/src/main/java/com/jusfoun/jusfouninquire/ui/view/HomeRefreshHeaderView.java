package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * @author zhaoyapeng
 * @version create time:17/1/1613:44
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HomeRefreshHeaderView extends FrameLayout implements PtrUIHandler {

    private Context mContext;
    private ImageView image;
    //    private PPTVLoading loadingView;
    private TextView textView;

    private HomeRefreshSceneAnimation sceneAnimation;
    private HomeSecondeRefreshAnimation sceneAnimation1;
    private RelativeLayout rootLayout;

    public HomeRefreshHeaderView(Context context) {
        super(context);
        mContext = context;
        initView();
        initActions();
    }

    public HomeRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initActions();
    }

    public HomeRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        mContext = context;
        initView();
        initActions();
    }


    private void initView() {


        LayoutInflater.from(mContext).inflate(R.layout.home_refresh_head_view_layout, this, true);
        image = (ImageView) findViewById(R.id.image);
        rootLayout = (RelativeLayout)findViewById(R.id.layout_root);
    }

    private void initActions() {
        sceneAnimation = new HomeRefreshSceneAnimation(image, 50);
        sceneAnimation1 = new HomeSecondeRefreshAnimation(image, 70);



    }

    public void setTxt(String txt) {
        textView.setText(txt);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
//        loadingView.reSet();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

//        loadingView.startAnim();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        sceneAnimation1.stop();
        sceneAnimation.stop();
//        loadingView.reSet();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {


        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();


        if (isUnderTouch) {
            sceneAnimation.stop();
            if (currentPos > dip2px(mContext,30) && sceneAnimation1.isStop()) {
//                Log.e("tag", "isUnderTouch1");
                sceneAnimation1.start(currentPos / 10 % 62);
            }
            else if (currentPos <= dip2px(mContext,30) ) {
//                Log.e("tag", "sceneAnimation1=" + currentPos);
                sceneAnimation1.setCurrentImage(currentPos / 10 % 62);
            }
        } else {

            if(currentPos<dip2px(mContext,60) ) {
                frame.refreshComplete();
                sceneAnimation1.stop();
                sceneAnimation.stop();
            }else{
                sceneAnimation1.stop();
                sceneAnimation.start();
            }

        }
//
//        if (currentPos/2>300) {
//            sceneAnimation.setCurrentImage(61);
//        } else {
//            sceneAnimation.setCurrentImage(currentPos/10 % 62);
//        }


//        Log.e("tag", "mOffsetToRefresh=" + mOffsetToRefresh);
//        Log.e("tag", "currentPos=" + currentPos);
//        Log.e("tag", "lastPos=" + lastPos);
//        Log.e("tag", "isUnderTouch=" + isUnderTouch);
//
      /*  Log.e("tag", "MyHeaderView-currentPos=" + currentPos);
        Log.e("tag", "MyHeaderView-mOffsetToRefresh=" + mOffsetToRefresh);
        Log.e("tag", "MyHeaderView-getRatioOfHeaderToHeightRefresh=" + frame.getRatioOfHeaderToHeightRefresh());*/

//        if(currentPos==0){
//            loadingView.reSet();
//        }
//
//        if (currentPos < mOffsetToRefresh) {
//            float offset = currentPos / (mOffsetToRefresh*1.0f);
//          //  Log.e("tag", "--------1");
//            loadingView.setOffset(offset, true);
//        } else {
//            loadingView.setOffset(1f, false);  //Log.e("tag", "--------2");
//        }
    }


    public  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setData(int padding){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            rootLayout.setPadding(0, padding, 0, 0);
        }
    }
}
