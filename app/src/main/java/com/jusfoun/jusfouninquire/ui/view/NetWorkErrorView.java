package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;

/**
 * Created by Albert on 2015/11/27.
 * Mail : lbh@jusfoun.com
 * TODO :
 * Description:网络异常或者服务器返回值异常View
 */
public class NetWorkErrorView extends RelativeLayout {
    private Context mContext;

    private TextView tvEmpty, tvError, tvReload;
    private View vEmpty;
    private SceneAnimation sceneAnimation;
    private ImageView imageView;
    public NetWorkErrorView(Context context) {
        super(context);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public NetWorkErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public NetWorkErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NetWorkErrorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    private void initData() {

    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_net_error, this, true);
//        mErrorImage = (ImageView) findViewById(R.id.error_image);
//        mErrorText = (TextView)findViewById(R.id.error_text);
//        mRefreshLayout = (RelativeLayout) findViewById(R.id.net_refresh_layout);
        vEmpty = findViewById(R.id.vEmpty);
        tvEmpty = (TextView) findViewById(R.id.tvEmpty);
        tvError = (TextView) findViewById(R.id.tvError);
        tvReload = (TextView) findViewById(R.id.tvReload);
        imageView = findViewById(R.id.loading_img_net);
    }

    private void initWidgetAction() {
        sceneAnimation = new SceneAnimation(imageView, 75);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnNetRefresh();
                }
                if(gxcListener!=null){
                    gxcListener.OnNetRefresh();
                    vEmpty.setVisibility(GONE);
                    sceneAnimation.start();
                }
            }
        });

    }

    /**国信查start**/
    public void showLoading(){
        setVisibility(VISIBLE);
        vEmpty.setVisibility(View.GONE);
        sceneAnimation.start();
    }

    public void success() {
        sceneAnimation.stop();
        setVisibility(GONE);
    }


    public void error() {
        sceneAnimation.stop();
        showEmptyView(true);
    }

    private OnGXCRefreshListener gxcListener;

    public interface OnGXCRefreshListener {
        public void OnNetRefresh();
    }

    public void setListener(OnGXCRefreshListener listener) {
        this.gxcListener = listener;
    }

    /**国信查end**/


    /**
     *   原企信宝 错误逻辑
     * */
    public void setNetWorkError() {
        sceneAnimation.stop();
        showEmptyView(true);
    }
    /**
     *   原企信宝 错误逻辑
     * */
    public void setServerError() {
        sceneAnimation.stop();
        showEmptyView(true);
    }

    private OnRefreshListener listener;

    public void setListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public interface OnRefreshListener {
        public void OnNetRefresh();
    }

    protected void showEmptyView(boolean isError) {
        String text = isError ? getHttpErrorTip() : getEmptyTipText();
        vEmpty.setVisibility(View.VISIBLE);
        if (tvEmpty != null) {
            tvEmpty.setText(text);
        }
        if (isError) {
            tvError.setVisibility(View.VISIBLE);
            tvReload.setVisibility(View.VISIBLE);
        } else {
            tvError.setVisibility(View.GONE);
            tvReload.setVisibility(View.GONE);
        }
    }

    protected String getEmptyTipText() {
        return "没有相关数据";
    }

    protected String getHttpErrorTip() {
        return "刷新试试吧~";
    }

}
