package com.jusfoun.jusfouninquire.ui.activity;

import android.widget.FrameLayout;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.ui.fragment.SearchDishonestFragment;

/**
 * @author zhaoyapeng
 * @version create time:16/8/1519:13
 * @Email zyp@jusfoun.com
 * @Description ${失信搜索页面}
 */
public class DishonestySearchActivity extends BaseInquireActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_dishonesty_search);
    }

    @Override
    protected void initWidgetActions() {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment, new SearchDishonestFragment()).commit();
    }
}
