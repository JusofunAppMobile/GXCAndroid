package com.jusfoun.jusfouninquire.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.CompanyDetailModel;
import com.jusfoun.jusfouninquire.net.model.SlideWebModel;
import com.jusfoun.jusfouninquire.ui.activity.CompanyDetailsActivity;
import com.jusfoun.jusfouninquire.ui.adapter.FragmentAdapter;
import com.jusfoun.jusfouninquire.ui.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明
 *
 * @时间 2017/9/5
 * @作者 LiuGuangDan
 */

public class SlideFragment extends BaseInquireFragment {

    private View view;

    private PagerSlidingTabStrip slidingTabStrip;
    private ViewPager viewPager;

    private List<SlideWebModel> models = new ArrayList<>();

    private CompanyDetailModel companyDetailModel;

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            companyDetailModel = (CompanyDetailModel) arguments.getSerializable(CompanyDetailsActivity.COMPANY);
        }

        int position = arguments.getInt(CompanyDetailsActivity.POSITION, -1);

        if (position < 0 || companyDetailModel == null || companyDetailModel.getSubclassMenu() == null || companyDetailModel.getSubclassMenu().isEmpty())
            return;

        List<SlideWebModel> list = new Gson().fromJson(new Gson().toJson(companyDetailModel.getSubclassMenu().get(position).tablist), new TypeToken<List<SlideWebModel>>() {
        }.getType());
        if (list != null && !list.isEmpty())
            models.addAll(list);
    }

    public static SlideFragment getInstance(Bundle bundle) {
        SlideFragment fragment = new SlideFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = View.inflate(getActivity(), R.layout.frag_slide, null);
            slidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.vTab);
            viewPager = (ViewPager) view.findViewById(R.id.vp);

            if (models.isEmpty())
                return view;

            // 在fragment中使用getChildFragmentManager，否则会出现fragment内容不显示的问题
            FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager()) {
                @Override
                public CharSequence getPageTitle(int position) {
                    return models.get(position).title;
                }
            };
            for (SlideWebModel model : models) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(CompanyWebFragment.TAG_SLIDEWEB, true);
                bundle.putString("url", model.url);
                adapter.addFragment(getActivity(), CompanyWebFragment.class, bundle);
            }

            viewPager.setAdapter(adapter);


            slidingTabStrip.setIndicatorColor(Color.RED);// 滑动条的颜色
            slidingTabStrip.setIndicatorHeight(0);// 滑动条的高度
            slidingTabStrip.setUnderlineColor(Color.TRANSPARENT); // 滑动条下面的整条的颜色
            slidingTabStrip.setDividerColor(Color.TRANSPARENT); // 设置标签之间的间隔颜色为透明
            slidingTabStrip.setTabBackground(Color.TRANSPARENT); //  每个标签的背景
            slidingTabStrip.setShouldExpand(true); //  如果设置为true，每个标签是相同的控件，均匀平分整个屏幕，默认是false
            slidingTabStrip.setTextColor(R.drawable.selector_tab_textcolor);
            slidingTabStrip.setTextSize(R.dimen.tab_text_size, R.dimen.tab_text_size_select);
            slidingTabStrip.setTabPaddingLeftRight(30); // 标签左右边距

            slidingTabStrip.setViewPager(viewPager);
        }
        return view;
    }

    @Override
    protected void initWeightActions() {

    }
}