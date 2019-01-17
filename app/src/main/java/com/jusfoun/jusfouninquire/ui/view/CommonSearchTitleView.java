package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.util.TouchUtil;

/**
 * CommonSearchTitleView
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/15
 * @Description :搜索页面title 显示
 */
public class CommonSearchTitleView extends RelativeLayout {
    protected TextView commonSearchTextview;
    private Context mContext;
    private TitleListener titleListener;

    private ImageView mLeft;
    private EditText mSearchEdit;


    public CommonSearchTitleView(Context context) {
        super(context);
        initData(context);
        initView();
        initWidgetAction();
    }

    public CommonSearchTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        initWidgetAction();
    }

    public CommonSearchTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommonSearchTitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
        initView();
        initWidgetAction();
    }

    private void initData(Context context) {
        this.mContext = context;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_common_search_title, this, true);
        mLeft = (ImageView) findViewById(R.id.common_left_text);
        mSearchEdit = (EditText) findViewById(R.id.common_search_edittext);
        commonSearchTextview = (TextView) findViewById(R.id.common_search_textview);
    }

    public void autoFocus(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSearchEdit.requestFocus();
                AppUtils.showSoftInput((Activity) mContext);
            }
        }, 100);
    }

    private void initWidgetAction() {
        mLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) mContext).finish();
            }
        });

        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (titleListener != null) {
                    titleListener.onTextChange(editable.toString());
                    if (!TextUtils.isEmpty(editable)) {
                        //mSearchEdit.setSelection(editable.toString().length());
                    }

                }
            }
        });

        mSearchEdit.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (mSearchEdit.getImeOptions() == EditorInfo.IME_ACTION_SEARCH) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return false;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        return false;
                    }

                    if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (mSearchEdit.getText() == null || mSearchEdit.getText().length() < 2) {
                            Toast.makeText(mContext, "请输入两个关键字", Toast.LENGTH_SHORT).show();
                            if (!TextUtils.isEmpty(mSearchEdit.getText()))
                                mSearchEdit.setSelection(mSearchEdit.getText().length());
                            return false;
                        }
                        if (titleListener != null) {
                            titleListener.onDoSearch(mSearchEdit.getText().toString());
                        }
                    }
                }
                return false;
            }
        });

        commonSearchTextview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)mContext).finish();
            }
        });
        TouchUtil.createTouchDelegate(mLeft, 20);
    }

    /**
     * 设置输入框的提示字段 ， 分类搜索页面   和  搜索页面  只有这一个区分
     *
     * @param notice
     */
    public void setEditHint(String notice) {

        mSearchEdit.setHint(notice);
        commonSearchTextview.setText(notice);
    }

    /**
     *
     */
    public void setEditText(String key) {
        mSearchEdit.setText(key);
        mSearchEdit.setSelection(mSearchEdit.getText().length());

    }

    public void setTitleListener(TitleListener titleListener) {
        this.titleListener = titleListener;
    }

    public interface TitleListener {
        void onTextChange(String key);

        void onDoSearch(String key);
    }

    public void showSearchText() {
        commonSearchTextview.setVisibility(VISIBLE);
        mSearchEdit.setVisibility(GONE);
    }
}
