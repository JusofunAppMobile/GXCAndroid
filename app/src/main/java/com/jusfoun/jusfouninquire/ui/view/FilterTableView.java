package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.jusfoun.jusfouninquire.R;

/**
 * FilterTableView
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/10
 * @Description : 抽屉筛选条件的 item condition view
 */
public class FilterTableView extends RelativeLayout{
    private Context mContext;

    private LinearLayout mTableLayout;
    public FilterTableView(Context context) {
        super(context);
        initData(context);
        initView();
        initWidgetAction();
    }

    public FilterTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        initWidgetAction();
    }

    public FilterTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FilterTableView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
        initView();
        initWidgetAction();
    }

    private void initData(Context context) {
        this.mContext  = context;
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_my_title, this, true);
        mTableLayout = (LinearLayout) view.findViewById(R.id.filter_layout);

    }

    private void initWidgetAction() {

    }
}
