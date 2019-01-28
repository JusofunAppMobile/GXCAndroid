package com.jusfoun.jusfouninquire.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.service.event.SearhHistoryEvent;
import com.jusfoun.jusfouninquire.ui.activity.WebActivity;
import com.jusfoun.jusfouninquire.ui.adapter.SearchHistoryAdapter;
import com.jusfoun.jusfouninquire.ui.widget.GeneralDialog;
import com.jusfoun.library.animationadapter.adapter.AnimationAdapter;
import com.jusfoun.library.animationadapter.adapter.ScaleInAnimationAdapter;

import de.greenrobot.event.EventBus;
import netlib.util.AppUtil;
import netlib.util.EventUtils;

/**
 * Created by Albert on 2015/11/9.
 * Mail : lbh@jusfoun.com
 * TODO :搜索记录fragment
 */
public class SearchHistoryFragment extends BaseInquireFragment {

    private ListView mSearchHistoryList;
    private View mNoSearchHistoryView,mClearHistoryView;

    private TextView searchTipTitle;

    private SearchHistoryAdapter mSearchHistoryAdapter;
    private AnimationAdapter mAnimAdapter;


    private GeneralDialog mDeleteDialog;

    @Override
    protected void initData() {
        mSearchHistoryAdapter = new SearchHistoryAdapter(mContext);
        mAnimAdapter = new ScaleInAnimationAdapter(mSearchHistoryAdapter);

        mDeleteDialog = new GeneralDialog(mContext,R.style.my_dialog);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_history_fragment_layout, container, false);
        mSearchHistoryList = (ListView)view.findViewById(R.id.search_history_xlist);
        mClearHistoryView = View.inflate(mContext,R.layout.clear_history_layout,null);


        mNoSearchHistoryView = view.findViewById(R.id.no_search_history);
        searchTipTitle = (TextView)mNoSearchHistoryView.findViewById(R.id.no_search_history_text);

        return view;
    }

    @Override
    protected void initWeightActions() {
        mClearHistoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteDialog.setMessageText("确定清空所有搜索记录吗？");
                mDeleteDialog.show();
                /*mSearchHistoryPreference.removeAll();
                mSearchHistoryList.setVisibility(View.GONE);
                mNoSearchHistoryView.setVisibility(View.VISIBLE);*/
            }
        });

        mDeleteDialog.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteDialog.dismiss();
            }
        });

        mDeleteDialog.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteDialog.dismiss();
                //SearchHistoryPreference.removeAll(mContext);
                //DBUtil.deleteAll(mContext);
                mSearchHistoryList.setVisibility(View.GONE);
                mNoSearchHistoryView.setVisibility(View.VISIBLE);
            }
        });

        mSearchHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("tag", "setOnItemClickListener1="+System.currentTimeMillis());
                SearchHistoryAdapter.ViewHolder holder = (SearchHistoryAdapter.ViewHolder) view.getTag();
                if (holder == null) {
                    return;
                }
                SearchHistoryItemModel model = holder.getData();
                if (model != null) {
                    Log.e("tag", "setOnItemClickListener2="+System.currentTimeMillis());
                    SearhHistoryEvent event = new SearhHistoryEvent();
                    event.setmSearchKey(model.getSearchkey());
                    event.setmSearchScopeID(model.getScope());
                    event.setmSearchScopeName(model.getScopename());
                    EventBus.getDefault().post(event);
                }
            }
        });

        mNoSearchHistoryView.findViewById(R.id.tip_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventUtils.event(mContext,EventUtils.SEARCH03);
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra(WebActivity.URL, mContext.getString(R.string.req_url)
                        + mContext.getString(R.string.search_tip_url)+"?appType=0&version="+ AppUtil.getVersionName(mContext));
                intent.putExtra(WebActivity.TITLE,"搜索小贴士");
                startActivity(intent);
            }
        });

        mSearchHistoryList.addFooterView(mClearHistoryView);
        mAnimAdapter.setAbsListView(mSearchHistoryList);
        mSearchHistoryList.setAdapter(mAnimAdapter);

        SpannableString spannableString   = new SpannableString(searchTipTitle.getText());
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.company_map_un_click_text)), 0,
                searchTipTitle.getText().length() - 8, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.search_table_background)), searchTipTitle.getText().length() - 8,
                searchTipTitle.getText().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        searchTipTitle.setText(spannableString);

        refreshHistory();


    }

    private void refreshHistory(){
//        if (DBUtil.getSortList(mContext) != null){
//            if (DBUtil.getSortList(mContext).size() > 0){
//                mSearchHistoryList.setVisibility(View.VISIBLE);
//                mNoSearchHistoryView.setVisibility(View.GONE);
//                mSearchHistoryAdapter.refresh(DBUtil.getSortList(mContext));
//            }else {
//                mSearchHistoryList.setVisibility(View.GONE);
//                mNoSearchHistoryView.setVisibility(View.VISIBLE);
//            }
//        }

    }



    @Override
    public void onResume() {
        super.onResume();
        refreshHistory();
    }
}
