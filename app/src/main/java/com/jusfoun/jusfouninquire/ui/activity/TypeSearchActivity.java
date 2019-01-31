package com.jusfoun.jusfouninquire.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gxc.event.CompanySelectEvent;
import com.gxc.model.UserModel;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.activity.WebActivity;
import com.gxc.utils.AppUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.database.DBUtil;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.HotWordItemModel;
import com.jusfoun.jusfouninquire.net.model.RecruitmentModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchHotModel;
import com.jusfoun.jusfouninquire.net.model.TaxationDataModel;
import com.jusfoun.jusfouninquire.net.route.GetHotSearchRoute;
import com.jusfoun.jusfouninquire.net.route.SearchRoute;
import com.jusfoun.jusfouninquire.service.event.DoSearchEvent;
import com.jusfoun.jusfouninquire.service.event.GoHomeEvent;
import com.jusfoun.jusfouninquire.service.event.GoTypeSearchEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.ReSearchEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.adapter.SearchHotWordsAdapter;
import com.jusfoun.jusfouninquire.ui.util.KeyBoardUtil;
import com.jusfoun.jusfouninquire.ui.view.CommonSearchTitleView;
import com.jusfoun.jusfouninquire.ui.view.SearchGuideView;
import com.siccredit.guoxin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;
import netlib.util.EventUtils;

/**
 * TypeSearchActivity
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/9
 * @Description : 企信宝 2.0 版本 分类搜索页面
 */
public class TypeSearchActivity extends BaseInquireActivity {
    public static final String SEARCH_TYPE = "search_type";

    private String mCurrentType;//搜索类型

    private CommonSearchTitleView mTitle;
    private SearchGuideView mSearchGuideView;
    private ListView mHotWordsList;

    private SearchHotWordsAdapter mHotAdapter;

    @Override
    protected void initData() {
        super.initData();
        if (getIntent() != null) {
            mCurrentType = getIntent().getStringExtra(SEARCH_TYPE);
        }
        mHotAdapter = new SearchHotWordsAdapter(this);
    }

    /**
     * @param context
     * @param menuType 0 : 模糊查询类型；首页
     * @return
     */
    public static Intent getIntent(Context context, int menuType) {
        return getIntent(context, menuType, null);
    }

    public static Intent getIntent(Context context, int menuType, String menuName) {
        Intent intent = new Intent(context, TypeSearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra(TypeSearchActivity.SEARCH_TYPE, AppUtils.parseToQxb(menuType));
        intent.putExtra("menuName", menuName);
        intent.putExtra("menuType", String.valueOf(menuType));
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarRed();
        loadNewHotKeys();
    }

    private String getMenuType() {
        String menuType = getIntent().getStringExtra("menuType");
        if (TextUtils.isEmpty(menuType))
            return "0";
        return menuType;
    }

    /**
     * 新版搜索热搜关键字
     */
    private void loadNewHotKeys() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("menuType", getMenuType());
        RxManager.http(RetrofitUtils.getApi().searchWord(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                if (model.success()) {
                    List<String> list = model.dataToList("keywords", String.class);
                    if (list != null && !list.isEmpty()) {
                        List<HotWordItemModel> hot = new ArrayList<>();
                        for (String value : list)
                            hot.add(new HotWordItemModel(value, value));
                        mSearchGuideView.setHotSearch(hot, mCurrentType);
                    }
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                ToastUtils.showHttpError();
            }
        });
    }

    private void insertSearchKey(String key) {
        UserModel user = AppUtils.getUser();
        if (user == null) // 未登录用户不记录
            return;

        HashMap<String, Object> map = new HashMap<>();
        map.put("menuType", getMenuType());
        map.put("keyWord", key);
        RxManager.http(RetrofitUtils.getApi().insertSearchWord(map), new ResponseCall() {
            @Override
            public void success(NetModel model) {

            }

            @Override
            public void error() {

            }
        });
    }

    @Override
    public boolean isBarDark() {
        return false;
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_type_search);
        mTitle = (CommonSearchTitleView) findViewById(R.id.search_title_view);
        mSearchGuideView = (SearchGuideView) findViewById(R.id.search_guide_view);
        mHotWordsList = (ListView) findViewById(R.id.hot_words_list);
    }

    @Override
    protected void initWidgetActions() {
        mHotWordsList.setAdapter(mHotAdapter);
        mTitle.autoFocus();
        mTitle.setTitleListener(new CommonSearchTitleView.TitleListener() {
            @Override
            public void onTextChange(String key) {
                searchHotWords(key);
            }

            @Override
            public void onDoSearch(String key) {
                KeyBoardUtil.hideSoftKeyboard((Activity) mContext);
                DoSearchEvent event = new DoSearchEvent();
                event.setSearchKey(key);
                EventBus.getDefault().post(event);
                insertSearchKey(key);
            }
        });

        if (!TextUtils.isEmpty(mCurrentType)) {
            if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_COMMON)) {
                mTitle.setEditHint(getString(R.string.common_search_hint));
            } else {
                if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_PRODUCT)) {
                    mTitle.setEditHint(getString(R.string.type_search_product));
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_SHAREHOLDER)) {
                    mTitle.setEditHint(getString(R.string.type_search_shareholder));
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_ADDRESS)) {
                    mTitle.setEditHint(getString(R.string.type_search_address));
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_INTERNET)) {
                    mTitle.setEditHint(getString(R.string.type_search_internet));
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_TAXID)) {
                    mTitle.setEditHint(getString(R.string.type_search_taxid));
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_RECRUITMENT)) {
                    mTitle.setEditHint(getString(R.string.type_search_zhaopin));
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_CONTACT)) {
                    mTitle.setEditHint(getString(R.string.type_search_contact));
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT)) {
                    mTitle.setEditHint(getString(R.string.type_search_contact));
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_DISHONEST)) {
                    mTitle.setEditHint(getString(R.string.type_search_dishonest));
                }
            }
        }

        mHotWordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getTag() instanceof SearchHotWordsAdapter.ViewHolder) {
                    SearchHotWordsAdapter.ViewHolder holder = (SearchHotWordsAdapter.ViewHolder) view.getTag();
                    if (holder == null) {
                        return;
                    }

                    if (SearchHistoryItemModel.SEARCH_COMMON.equals(mCurrentType)) {
                        EventUtils.event(mContext, EventUtils.SEARCH33);
                    } else if (SearchHistoryItemModel.SEARCH_PRODUCT.equals(mCurrentType)) {
                        EventUtils.event(mContext, EventUtils.SEARCH36);
                    } else if (SearchHistoryItemModel.SEARCH_SHAREHOLDER.equals(mCurrentType)) {
                        EventUtils.event(mContext, EventUtils.SEARCH39);
                    } else if (SearchHistoryItemModel.SEARCH_ADDRESS.equals(mCurrentType)) {
                        EventUtils.event(mContext, EventUtils.SEARCH42);
                    } else if (SearchHistoryItemModel.SEARCH_INTERNET.equals(mCurrentType)) {
                        EventUtils.event(mContext, EventUtils.SEARCH45);
                    }
                    HotWordItemModel model = holder.getData();
                    if (model != null) {
                        DoSearchEvent event = new DoSearchEvent();
                        event.setSearchKey(model.getSearchkey());
                        EventBus.getDefault().post(event);
                    }

                }
            }
        });


        initGuideView();

        String key = getIntent().getStringExtra("key");
        if (!TextUtils.isEmpty(key))
            mTitle.setEditText(key);
    }


    /**
     * 初始化搜索记录和热门搜索区域
     */
    private void initGuideView() {
        mSearchGuideView.setSearchType(mCurrentType);
        initHistorySearch();
//        initHotSearch();

    }

    /**
     * 初始化 历史搜索显示
     */
    private void initHistorySearch() {
        //TODO 读取本地数据库，调用 SearchGuideView 的 setHistorySearch（）方法进行显示
        List<SearchHistoryItemModel> historyItemModels = new ArrayList<>();
        historyItemModels.addAll(DBUtil.getSortList(getApplicationContext(), mCurrentType));
        if (historyItemModels.size() > 0) {
            mSearchGuideView.setHistorySearch(historyItemModels, mCurrentType);
        }
    }


    /**
     * 保存历史搜索记录
     */
    private void saveSearchHistory(String key) {
        SearchHistoryItemModel model = new SearchHistoryItemModel();
        model.setType(mCurrentType);
        model.setSearchkey(key);
        model.setTimestamp(System.currentTimeMillis());
        DBUtil.insertItem(mContext, model);
    }


    /**
     * 搜索热词
     */
    private void searchHotWords(String key) {
        if (TextUtils.isEmpty(key)) {
            mSearchGuideView.setVisibility(View.VISIBLE);
            mHotWordsList.setVisibility(View.GONE);
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("searchkey", key);
        GetHotSearchRoute.getRelatedHotwords(mContext, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof SearchHotModel) {
                    SearchHotModel model = (SearchHotModel) data;
                    if (model.getResult() == 0) {
                        if (model.getHotlist() != null && model.getHotlist().size() > 0) {
                            mSearchGuideView.setVisibility(View.GONE);
                            mHotWordsList.setVisibility(View.VISIBLE);
                            mHotAdapter.refresh(model.getHotlist());

                        } else {
                            mSearchGuideView.setVisibility(View.VISIBLE);
                            mHotWordsList.setVisibility(View.GONE);
                        }
                    } else {
                        //TODO 接口异常处理逻辑待定
                    }
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    /**
     * 搜索网络请求
     */
    private void starNext(final String key) {
        Class clazz = SearchResultActivity.class;
        if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_DISHONEST)) { // 失信查询
            clazz = DishonestResultActivity.class;
        }
        Intent intent = new Intent(mContext, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("menuName", getIntent().getStringExtra("menuName"));
        intent.putExtra(SearchResultActivity.SEARCH_KEY, key);
        intent.putExtra(SearchResultActivity.SEARCH_TYPE, mCurrentType);
        startActivity(intent);
    }

    /**
     * 搜索税号
     */
    private void searchTaxidNet(final String key) {
        TimeOut timeOut = new TimeOut(mContext);
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }
        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");

        params.put("searchkey", key);
        params.put("type", mCurrentType);
        params.put("pageSize", "20");
        params.put("pageIndex", "1");
        showLoading();
        EventUtils.event(mContext, EventUtils.SEARCH29);
        SearchRoute.searchTaxIdNet(this, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                if (data instanceof TaxationDataModel) {
                    TaxationDataModel model = (TaxationDataModel) data;
                    if (model.getResult() == 0) {
                        Intent intent = new Intent(mContext, TaxIdSearchActivity.class);
                        intent.putExtra(TaxIdSearchActivity.SEARCH_RESULT, model);
                        intent.putExtra("searchKey", key);
                        startActivity(intent);
                    } else {
                        if (model.getResult() != -1) {
                            if (!TextUtils.isEmpty(model.getMsg())) {
                                showToast(model.getMsg());
                            } else {
                                showToast("网络不给力，请稍后重试");
                            }
                        }

                    }
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast("网络不给力，请稍后重试");
            }
        });
    }

    /**
     * 搜索招聘
     */
    private void searchRecruitmentNet(final String key) {
        TimeOut timeOut = new TimeOut(mContext);
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }
        params.put("t", timeOut.getParamTimeMollis() + "");
        params.put("m", timeOut.MD5time() + "");

        params.put("entName", key);
        params.put("pageSize", "20");
        params.put("pageIndex", "1");
        showLoading();
        EventUtils.event(mContext, EventUtils.SEARCH29);
        SearchRoute.searchRecruitmentNet(this, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                if (data instanceof RecruitmentModel) {
                    RecruitmentModel model = (RecruitmentModel) data;
                    if (model.getResult() == 0) {
                        Intent intent = new Intent(mContext, RecruitmentSearchActivity.class);
                        intent.putExtra(RecruitmentSearchActivity.SEARCH_RESULT, model);
                        intent.putExtra("searchKey", key);
                        startActivity(intent);
                    } else {
                        if (model.getResult() != -1) {
                            if (!TextUtils.isEmpty(model.getMsg())) {
                                showToast(model.getMsg());
                            } else {
                                showToast("网络不给力，请稍后重试");
                            }
                        }

                    }
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast("网络不给力，请稍后重试");
            }
        });
    }


    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof DoSearchEvent) {
            DoSearchEvent doSearchEvent = (DoSearchEvent) event;
            if (!TextUtils.isEmpty(doSearchEvent.getSearchKey())) {
                //接收到搜索事件之后，保存搜索记录并进行搜索
                if (doSearchEvent.getSearchKey().length() >= 2) // 关键词长度大于等于2才保存到历史记录中
                    saveSearchHistory(doSearchEvent.getSearchKey());
                if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_TAXID)) {
                    searchTaxidNet(doSearchEvent.getSearchKey());
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_RECRUITMENT)) {
                    searchRecruitmentNet(doSearchEvent.getSearchKey());
                } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_WINNING_BID) ||
                        mCurrentType.equals(SearchHistoryItemModel.SEARCH_REFEREE) ||
                        mCurrentType.equals(SearchHistoryItemModel.SEARCH_ADMINISTRATIVE) ||
                        mCurrentType.equals(SearchHistoryItemModel.SEARCH_TRADEMARK)) {
                    Intent intent = WebActivity.getIntent(this, getIntent().getStringExtra("menuName"), AppUtils.parseToGxMenuType(mCurrentType), doSearchEvent.getSearchKey());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    return;
                } else {
                    starNext(doSearchEvent.getSearchKey());
                }
                insertSearchKey(doSearchEvent.getSearchKey());
            }
        } else if (event instanceof GoTypeSearchEvent) {
            //业务逻辑：收到此事件，根据事件携带的关键字进行热词检索
            GoTypeSearchEvent goTypeSearchEvent = (GoTypeSearchEvent) event;
            mTitle.setEditText(goTypeSearchEvent.getKey());
        } else if (event instanceof GoHomeEvent) {
            finish();
        } else if (event instanceof ReSearchEvent) {
            mTitle.setEditText("");
            mSearchGuideView.setVisibility(View.VISIBLE);
            mHotWordsList.setVisibility(View.GONE);
        } else if (event instanceof CompanySelectEvent)
            finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //bugfree 18588,返回页面后刷新搜索历史
        initHistorySearch();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


    /**
     * 是否为模糊搜索，国信大多业务使用模糊搜索
     */
    public static boolean isBlurrySearch(String mSearchType) {
        if (mSearchType.equals(SearchHistoryItemModel.SEARCH_WINNING_BID) ||
                mSearchType.equals(SearchHistoryItemModel.SEARCH_REFEREE) ||
                mSearchType.equals(SearchHistoryItemModel.SEARCH_ADMINISTRATIVE) ||
                mSearchType.equals(SearchHistoryItemModel.SEARCH_TRADEMARK) ||
                mSearchType.equals(SearchHistoryItemModel.SEARCH_RISK) ||
                mSearchType.equals(SearchHistoryItemModel.SEARCH_RELATION)) {
            return true;

        }
        return false;
    }
}
