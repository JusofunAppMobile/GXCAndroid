package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.database.DBUtil;
import com.jusfoun.jusfouninquire.net.model.HotWordItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.service.event.DoSearchEvent;

import java.util.List;

import de.greenrobot.event.EventBus;
import netlib.util.EventUtils;

/**
 * SearchGuideView
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/9
 * @Description :搜索页面，搜索历史和热门搜索显示view
 */
public class SearchGuideView extends LinearLayout {
    private Context mContext;

    private FlowLayout mHistory, mHot;
    private TextView mDeleteHistory;
    private RelativeLayout mHistoryLayout, mHotLayout;


    private String mCurrentType;

    private TextView hisTitleText, hotTitleText;

    public SearchGuideView(Context context) {
        super(context);
        initData(context);
        initView();
        initWidgetAction();
    }

    public SearchGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initView();
        initWidgetAction();
    }

    public SearchGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchGuideView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context);
        initView();
        initWidgetAction();
    }

    private void initData(Context context) {
        this.mContext = context;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_search_guide, this, true);
        mHistory = (FlowLayout) findViewById(R.id.search_history_content);
        mHot = (FlowLayout) findViewById(R.id.hot_search_content);
        mDeleteHistory = (TextView) findViewById(R.id.delete_search_history);
        mHistoryLayout = (RelativeLayout) findViewById(R.id.search_history_layout);
        mHotLayout = (RelativeLayout) findViewById(R.id.search_hot_layout);
        hisTitleText = (TextView) findViewById(R.id.history_title);
        hotTitleText = (TextView) findViewById(R.id.hot_search_title);
    }

    private void initWidgetAction() {
        mDeleteHistory.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mCurrentType)) {
                    DBUtil.deleteAll(mContext, mCurrentType);
                    mHistoryLayout.setVisibility(GONE);
                }
            }
        });


        TextPaint hisPaint = hisTitleText.getPaint();
        hisPaint.setFakeBoldText(true);

        TextPaint hotPaint = hotTitleText.getPaint();
        hotPaint.setFakeBoldText(true);

    }

    /**
     * 设置 历史搜索显示
     *
     * @param history 本地数据库中的历史搜索记录
     */
    public void setHistorySearch(List<SearchHistoryItemModel> history, final String type) {
        mHistoryLayout.setVisibility(VISIBLE);
        mHistory.removeAllViews();
        mCurrentType = type;
        for (final SearchHistoryItemModel model : history) {
            View itemview = LayoutInflater.from(mContext).inflate(R.layout.item_option_item, null);
            itemview.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SearchHistoryItemModel.SEARCH_COMMON.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH31);
                    } else if (SearchHistoryItemModel.SEARCH_PRODUCT.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH34);
                    } else if (SearchHistoryItemModel.SEARCH_SHAREHOLDER.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH37);
                    } else if (SearchHistoryItemModel.SEARCH_ADDRESS.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH40);
                    } else if (SearchHistoryItemModel.SEARCH_INTERNET.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH43);
                    }
                    DoSearchEvent event = new DoSearchEvent();
                    event.setSearchKey(model.getSearchkey());
                    EventBus.getDefault().post(event);
                }
            });
            TextView optionName = (TextView) itemview.findViewById(R.id.item_option_name);
            optionName.setText(model.getSearchkey());
            optionName.setBackgroundResource(R.drawable.bg_his);
            optionName.setTextColor(0xff333333);
            mHistory.addView(itemview);
        }

    }


    /**
     * 设置 热门搜索 显示
     *
     * @param hot 服务器返回的热门搜索结果列表
     */
    public void setHotSearch(List<HotWordItemModel> hot, final String type) {
        mHotLayout.setVisibility(VISIBLE);
        for (final HotWordItemModel model : hot) {
            View itemview = LayoutInflater.from(mContext).inflate(R.layout.item_option_item, null);
            itemview.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SearchHistoryItemModel.SEARCH_COMMON.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH32);
                    } else if (SearchHistoryItemModel.SEARCH_PRODUCT.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH35);
                    } else if (SearchHistoryItemModel.SEARCH_SHAREHOLDER.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH38);
                    } else if (SearchHistoryItemModel.SEARCH_ADDRESS.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH41);
                    } else if (SearchHistoryItemModel.SEARCH_INTERNET.equals(type)) {
                        EventUtils.event(mContext, EventUtils.SEARCH44);
                    }

                    DoSearchEvent event = new DoSearchEvent();
                    event.setSearchKey(model.getHotword());
                    EventBus.getDefault().post(event);
                }
            });
            TextView optionName = (TextView) itemview.findViewById(R.id.item_option_name);
            optionName.setText(model.getHotword());
            optionName.setBackgroundResource(R.drawable.option_unselected);
            optionName.setTextColor(0xffEA6A6F);
            mHot.addView(itemview);
        }
    }

    public void setSearchType(String type) {
        mCurrentType = type;
    }

    public interface CallBack {
        void search(String key);
    }

    public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
