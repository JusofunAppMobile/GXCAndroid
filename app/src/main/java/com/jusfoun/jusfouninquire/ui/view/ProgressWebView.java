package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.animation.SceneAnimation;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Author  JUSFOUN
 * CreateDate 2015/12/1.
 * Description
 */
public class ProgressWebView extends WebView {

    private ProgressBar progressBar;
    private NetWorkErrorView webError;
    private View web, loadingView;
    private SceneAnimation sceneAnimation;

    public ProgressWebView(Context context) {
        super(context);
        initView(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context){
        progressBar=new ProgressBar(context,null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,3));
        progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.bg_progress_web));
        addView(progressBar);
        setWebChromeClient(new CustomWebChromeClient());
    }

    class CustomWebChromeClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);

            if (newProgress==100){
                progressBar.setVisibility(GONE);
            }else {
                if (progressBar.getVisibility()==GONE)
                    progressBar.setVisibility(VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

}
