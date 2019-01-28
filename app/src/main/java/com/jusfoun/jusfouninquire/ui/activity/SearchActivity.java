package com.jusfoun.jusfouninquire.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.SearchEvent;
import com.jusfoun.jusfouninquire.service.event.SearchFinishEvent;
import com.jusfoun.jusfouninquire.service.event.SearhHistoryEvent;
import com.jusfoun.jusfouninquire.sharedpreference.FirstSearchPreference;
import com.jusfoun.jusfouninquire.sharedpreference.GuideSearchSharedPreference;
import com.jusfoun.jusfouninquire.ui.util.AlphaAnimUtil;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.jusfoun.jusfouninquire.ui.view.BackAndRightImageTitleView;
import com.jusfoun.jusfouninquire.ui.view.CommonSearchView;
import com.jusfoun.jusfouninquire.ui.view.SelectProvinceGuideView;
import com.jusfoun.jusfouninquire.ui.view.TypeSearchView;

import de.greenrobot.event.EventBus;
import netlib.util.AppUtil;
import netlib.util.EventUtils;


/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午3:46
 * @Email zyp@jusfoun.com
 * @Description ${搜索页面}
 */
public class SearchActivity extends BaseInquireActivity {
    private Button detailBtn, advancedBtn;
    private BackAndRightImageTitleView mTitleView;


    private TypeSearchView mTypeSearchView;
    private CommonSearchView mCommonSearchView;
    private EditText mEditText;
    private ImageView mClearEdit;

    private SelectProvinceGuideView mGuideView;


    private String mSearchScope,mSearchScopeName;
    private boolean isCommonSearch;
    private int mSearchType;

    public final static int INDUSTRY = 0;
    public final static int LEGAL_SHAREHOLDER = 1;
    public final static int DISHONEST = 2;
    public final static String ISCOMMON = "is_common";
    public final static String SEARCH_TYPE = "search_type";

    private FirstSearchPreference mFirstSearchPreference;
    private GuideSearchSharedPreference mGuideSearchPreference;
    private AlphaAnimUtil alphaAnimUtil,mGuideAnimUtil;

    private FrameLayout mGuideSearchLayout;


    @Override
    protected void initData() {
        super.initData();
//        overridePendingTransition(R.anim.silde_in_to_top, R.anim.admin_state);
        mFirstSearchPreference = new FirstSearchPreference(mContext);

        mGuideSearchPreference = new GuideSearchSharedPreference(mContext);
        if (getIntent() != null){
            isCommonSearch = getIntent().getBooleanExtra(ISCOMMON, true);
            mSearchType = getIntent().getIntExtra(SEARCH_TYPE,0);
        }

        mSearchScope = "0";
        mSearchScopeName = "全国";
//        mSwipeBackLayout.setEnableGesture(true);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search);
        mTitleView = (BackAndRightImageTitleView) findViewById(R.id.search_activity_title);
        mTitleView.setRightViewBackgroundResource(R.mipmap.question);
        detailBtn = (Button) findViewById(R.id.btn_detail);
        advancedBtn = (Button) findViewById(R.id.btn_advanced);
        mTypeSearchView = (TypeSearchView) findViewById(R.id.type_search_view);
        mCommonSearchView = (CommonSearchView) findViewById(R.id.common_search_view);
        mEditText = (EditText) findViewById(R.id.search_activity_edit_text);
        if (mSearchType == 1){
            mEditText.setHint(R.string.home_search_legal);
        }else if (mSearchType == 2){
            mEditText.setHint(R.string.home_search_dishonest);
        }
        mClearEdit = (ImageView)findViewById(R.id.clear_search);

        mGuideView = (SelectProvinceGuideView) findViewById(R.id.guide_view);
        mGuideSearchLayout = (FrameLayout) findViewById(R.id.first_in_search_shadow);

        if (mGuideSearchPreference.isFirstSearch()){
            mGuideAnimUtil = new AlphaAnimUtil(mGuideSearchLayout);
            mGuideAnimUtil.start();
            mGuideSearchPreference.setIsFirstSearch(false);
        }
//        mTypeSearchView = new TypeSearchView(this);
//        ((RelativeLayout)findViewById(R.id.layout_root)).addView(mTypeSearchView);
//        mTypeSearchView.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void initWidgetActions() {
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(CompanyDetailActivity.class);
            }
        });

        advancedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(AdvancedSearchActivity.class);
            }
        });

        mTitleView.setmLeftClickListener(new BackAndRightImageTitleView.LeftClickListener() {
            @Override
            public void onClick() {
                Log.d("TAG", "返回按钮点击");
                KeyBoardUtil.hideSoftKeyboard((Activity) mContext);
                finish();
            }
        });

        mTitleView.setmRightClickListener(new BackAndRightImageTitleView.RightClickListener() {
            @Override
            public void onClick() {
                EventUtils.event(mContext, EventUtils.SEARCH01);
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra(WebActivity.URL, getString(R.string.req_url) +
                        getString(R.string.search_tip_url)+"?appType=0&version="+ AppUtil.getVersionName(mContext));
                intent.putExtra(WebActivity.TITLE, "搜索小贴士");
                startActivity(intent);
            }
        });

        mTypeSearchView.setmOnTableClickListener(new TypeSearchView.OnTableClickListener() {
            @Override
            public void onClick(int index) {
                mSearchType = index;

            }
        });


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() >= 2) {
//                    mTypeSearchView.setmViewPagerShow();
                    sendSearchEvent();
                } else if (s.toString().trim().length() == 0) {
                    mCommonSearchView.setVisibility(View.VISIBLE);
                    mTypeSearchView.setVisibility(View.INVISIBLE);
                    mTypeSearchView.setSelectedTab(mSearchType);
                }

            }
        });

        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (mEditText.getImeOptions() == EditorInfo.IME_ACTION_SEARCH){
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return false;
                    }
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        return false;
                    }

                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                        if (mEditText.getText().toString().trim().length() < 2){
                            showToast("搜索条件不能少于两个字");
                        }else {
                            sendSearchEvent();
                        }
                    }

                }
                return false;
            }
        });

        mClearEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                mTypeSearchView.setVisibility(View.GONE);
                mCommonSearchView.setVisibility(View.VISIBLE);
            }
        });

        mGuideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGuideView.setVisibility(View.GONE);
            }
        });

        mGuideSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGuideSearchLayout.setVisibility(View.GONE);
            }
        });

    }

    private void sendSearchEvent() {
        mCommonSearchView.setVisibility(View.INVISIBLE);
        mTypeSearchView.setVisibility(View.VISIBLE);
        SearchEvent event = new SearchEvent();
        event.setScope(mSearchScope);
        event.setScopename(mSearchScopeName);
        event.setSearchkey(mEditText.getText().toString().trim());
        event.setType(mSearchType);
        EventBus.getDefault().post(event);
    }


    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof SearchFinishEvent) {
            SearchFinishEvent ev = (SearchFinishEvent) event;
            if (!TextUtils.isEmpty(ev.getSearchkey())) {

            }
            if ((ev.getResultcount() > 50) && (mFirstSearchPreference.isFirstSearch())){
//                mGuideView.setVisibility(View.VISIBLE);
                alphaAnimUtil = new AlphaAnimUtil(mGuideView);
                alphaAnimUtil.start();
                mFirstSearchPreference.setIsFirstSearch(false);
            }

        }else if (event instanceof  SearchEvent){
            mTypeSearchView.setVisibility(View.VISIBLE);
            SearchEvent searchEvent  = (SearchEvent) event;
            if (!TextUtils.isEmpty(searchEvent.getScope())){
                mSearchScope = searchEvent.getScope();
            }

            if (!TextUtils.isEmpty(searchEvent.getScopename())){
                mSearchScopeName = searchEvent.getScopename();
            }
            mCommonSearchView.setVisibility(View.INVISIBLE);

            mTypeSearchView.setSelectedTab(mSearchType);

            //mEditText.setText(searchEvent.getSearchkey());
            /*SearchHistoryItemModel model = new SearchHistoryItemModel();
            model.setSearchkey(mEditText.getText().toString().trim());
            model.setScope(mSearchScope);
            model.setScopename(mSearchScopeName);
            model.setTimestamp(System.currentTimeMillis());
            searchHistoryPreference.saveItem(model);*/
        }else if (event instanceof SearhHistoryEvent){
            mTypeSearchView.setVisibility(View.VISIBLE);
            SearhHistoryEvent ev = (SearhHistoryEvent) event;
            if (!TextUtils.isEmpty(ev.getmSearchScopeID())){
                mSearchScope = ev.getmSearchScopeID();
            }

            if (!TextUtils.isEmpty(ev.getmSearchScopeName())){
                mSearchScopeName = ev.getmSearchScopeName();
            }

            mEditText.setText(ev.getmSearchKey());
            Log.e("tag", "setOnItemClickListener3=" + System.currentTimeMillis());
            mCommonSearchView.setVisibility(View.GONE);

            Log.e("tag", "setOnItemClickListener4=" + System.currentTimeMillis());
            mTypeSearchView.setSelectedTab(mSearchType);
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            finish();
//            overridePendingTransition(R.anim.admin_state, R.anim.silde_out_to_bottom);
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
