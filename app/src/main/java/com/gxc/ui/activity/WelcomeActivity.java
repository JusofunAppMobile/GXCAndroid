package com.gxc.ui.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.constants.Constants;
import com.jusfoun.jusfouninquire.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import netlib.util.PreferenceUtils;

/**
 * @author liuguangdan
 * @version create at 2019/1/21/021 10:20
 * @Description ${欢迎页面}
 */
public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tvStart)
    TextView tvStart;

    @Override
    protected int getLayoutId() {
        return R.layout.act_welcome;
    }

    @Override
    public void initActions() {
        List<View> list = new ArrayList<>();
        list.add(createView(R.drawable.img_ad1));
        list.add(createView(R.drawable.img_ad2));
        list.add(createView(R.drawable.img_ad3));
        MyPagerAdapter adapter = new MyPagerAdapter(list);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);
        PreferenceUtils.setBoolean(this, Constants.IS_FIRST_RUN, false);
    }

    private View createView(int id) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setImageResource(id);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @OnClick(R.id.tvStart)
    public void click() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean isSetStatusBar() {
        return false;
    }

    @Override
    public boolean isBarDark() {
        return false;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        tvStart.setVisibility(i == 2 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class MyPagerAdapter extends PagerAdapter {

        private List<View> datas;

        public MyPagerAdapter(List<View> list) {
            datas = list;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = datas.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(datas.get(position));
        }
    }


}
