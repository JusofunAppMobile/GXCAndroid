package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ListView;
/**
 * @author zhaoyapeng
 * @version create time:18/1/1614:34
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HotCompanyListView extends ListView {
    public HotCompanyListView(Context context) {
        super(context);
    }

    public HotCompanyListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HotCompanyListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
