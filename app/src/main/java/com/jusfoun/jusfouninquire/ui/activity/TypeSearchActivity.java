package com.jusfoun.jusfouninquire.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gxc.event.CompanySelectEvent;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.activity.WebActivity;
import com.gxc.utils.ToastUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.TimeOut;
import com.jusfoun.jusfouninquire.database.DBUtil;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.BaseModel;
import com.jusfoun.jusfouninquire.net.model.HotWordItemModel;
import com.jusfoun.jusfouninquire.net.model.RecruitmentModel;
import com.jusfoun.jusfouninquire.net.model.SearchContactListModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchHotModel;
import com.jusfoun.jusfouninquire.net.model.SearchListModel;
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
        intent.putExtra(TypeSearchActivity.SEARCH_TYPE, String.valueOf(menuType));
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

//        btn_yuyin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if( null == mIat ){
//                    // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
//                   showToast( "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化" );
//                    return;
//                }
//
//                // 移动数据分析，收集开始听写事件
//                FlowerCollector.onEvent(TypeSearchActivity.this, "iat_recognize");
//
//                mTitle.setEditText("");// 清空显示内容
//                mIatResults.clear();
//                // 设置参数
//                setParam();
//                boolean isShowDialog = mSharedPreferences.getBoolean(
//                        getString(R.string.pref_key_iat_show), true);
//                if (isShowDialog) {
//                    // 显示听写对话框
//                    mIatDialog.setListener(mRecognizerDialogListener);
//                    mIatDialog.show();
//                    showToast(getString(R.string.text_begin));
//                }
////                else {
////                    // 不显示听写对话框
////                    ret = mIat.startListening(mRecognizerListener);
////                    if (ret != ErrorCode.SUCCESS) {
////                        showTip("听写失败,错误码：" + ret);
////                    } else {
////                        showTip(getString(R.string.text_begin));
////                    }
////                }
//            }
//        });


//        btn_yuyin.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        if (null == mIat) {
//                            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
//                            showToast("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
//                            return false;
//                        }
//                        downY = motionEvent.getY();
//                        // 移动数据分析，收集开始听写事件
//                        FlowerCollector.onEvent(TypeSearchActivity.this, "iat_recognize");
//
//                        mTitle.setEditText("");// 清空显示内容
//                        mIatResults.clear();
//                        // 设置参数
//                        setParam();
//                        boolean isShowDialog = mSharedPreferences.getBoolean(
//                                getString(R.string.pref_key_iat_show), true);
////                        if (isShowDialog) {
////                            // 显示听写对话框
////                            mIatDialog.setListener(mRecognizerDialogListener);
////                            mIatDialog.show();
////                            showToast(getString(R.string.text_begin));
////                        } else {
//                        // 不显示听写对话框
//                        int ret;
//                        ret = mIat.startListening(mRecognizerListener);
//                        if (ret != ErrorCode.SUCCESS) {
//                            showToast("听写失败,错误码：" + ret);
//                        } else {
////                                showToast(getString(R.string.text_begin));
//                            voiceDialog.setVisibility(View.VISIBLE);
//
//                        }
//                        break;
////                        }
//                    case MotionEvent.ACTION_UP:
//                        mIat.stopListening();
//                        Log.e("tag", "voiceResult=" + voiceResult);
////                        mTitle.setEditText(voiceResult);
////                        voiceResult = "";
//                        voiceDialog.setVisibility(View.GONE);
//                        downY = 0;
//                        voiceDialog.downState();
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        if (downY - motionEvent.getY() > 40) {
//                            voiceDialog.upState();
//                        } else if (downY - motionEvent.getY() < -40) {
//                            voiceDialog.downState();
//                        }
//
//
//                }
//                return true;
//            }
//        });
//        initXF();

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
     * 初始化 热门搜索 区域 显示
     */
//    private void initHotSearch() {
//        //TODO 网络请求，调用 SearchGuideView 的 setHotSearch（）方法进行显示
//        HashMap<String, String> params = new HashMap<>();
//        params.put("type", mCurrentType);
//        GetHotSearchRoute.getHotSearch(mContext, params, getLocalClassName(), new NetWorkCallBack() {
//            @Override
//            public void onSuccess(Object data) {
//                if (data instanceof SearchHotModel) {
//                    SearchHotModel model = (SearchHotModel) data;
//                    if (model.getResult() == 0) {
//                        if (model.getHotlist() != null && model.getHotlist().size() > 0) {
////                            mSearchGuideView.setHotSearch(model.getHotlist(), mCurrentType);
//                        }
//                    } else {
//                        //TODO 错误逻辑待定
//                    }
//                }
//            }
//
//            @Override
//            public void onFail(String error) {
//
//            }
//        });
//    }


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
    private void searchNet(final String key) {
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
        if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT))
            params.put("type", "6");
        else if (isBlurrySearch(mCurrentType)) {
            params.put("type", SearchHistoryItemModel.SEARCH_COMMON);
        } else {
            params.put("type", mCurrentType);
        }
        params.put("pageSize", "20");
        params.put("pageIndex", "1");
        if (SearchHistoryItemModel.SEARCH_CONTACT.equals(params.get("type"))) {
            params.put("sequence", "6");// 企业通讯录，默认注册时间降序
        } else {
            params.put("sequence", "2");
        }

        params.put("city", "");
        params.put("province", "");
        params.put("registeredcapital", "");
        params.put("regtime", "");
        params.put("industryid", "");
        showLoading();
        EventUtils.event(mContext, EventUtils.SEARCH29);
        SearchRoute.searchNet(this, params, this.getLocalClassName().toString(), new NetWorkCallBack() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                BaseModel bm = (BaseModel) data;

                if (bm.getResult() == 0) {
                    if (SearchHistoryItemModel.SEARCH_CONTACT.equals(mCurrentType)) {
                        SearchContactListModel model = (SearchContactListModel) data;
                        if (model.data != null && !model.data.isEmpty()) {
                            Intent intent = new Intent(mContext, SearchResultActivity.class);
                            intent.putExtra(SearchResultActivity.SEARCH_RESULT, model);
                            intent.putExtra(SearchResultActivity.SEARCH_KEY, key);
                            intent.putExtra(SearchResultActivity.SEARCH_TYPE, mCurrentType);
                            startActivity(intent);
                        } else {
                            EventUtils.event(mContext, EventUtils.SEARCH30);
                            showToast(getString(R.string.search_result_none));
                        }
                    } else {
                        SearchListModel model = (SearchListModel) data;
                        if (model.getBusinesslist() != null) {
                            Intent intent = new Intent(mContext, SearchResultActivity.class);
                            intent.putExtra(SearchResultActivity.SEARCH_RESULT, model);
                            intent.putExtra(SearchResultActivity.SEARCH_KEY, key);
                            intent.putExtra(SearchResultActivity.SEARCH_TYPE, mCurrentType);
                            startActivity(intent);
                        } else {
                            EventUtils.event(mContext, EventUtils.SEARCH30);
                            showToast(getString(R.string.search_result_none));
                        }

                    }
                } else {
                    if (bm.getResult() != -1) {
                        if (!TextUtils.isEmpty(bm.getMsg())) {
                            showToast(bm.getMsg());
                        } else {
                            showToast("网络不给力，请稍后重试");
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
                    startActivity(WebActivity.getIntent(this, getIntent().getStringExtra("menuName"), Integer.parseInt(mCurrentType), doSearchEvent.getSearchKey()));
                    return;
                } else {
                    searchNet(doSearchEvent.getSearchKey());
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


//    private SpeechRecognizer mIat;
//     语音听写UI
//    private RecognizerDialog mIatDialog;
//     用HashMap存储听写结果
//    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

//    private Toast mToast;
//    private SharedPreferences mSharedPreferences;
    // 引擎类型
//    private String mEngineType = SpeechConstant.TYPE_CLOUD;
//    private boolean mTranslateEnable = false;

//    private void initXF() {
//
//        Log.e("tag", "appId=" + getString(R.string.app_id));
//        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
//        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
//
//        Log.e("tag", "SpeechUtility=" + SpeechUtility.getUtility());
//
//        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
//        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
//        mIatDialog = new RecognizerDialog(this, mInitListener);
//
//        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME,
//                Activity.MODE_PRIVATE);
//        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
//
//    }


    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showToast("初始化失败，错误码：" + code);
            }
        }
    };

//    public void setParam() {
//        // 清空参数
//        mIat.setParameter(SpeechConstant.PARAMS, null);
//
//        // 设置听写引擎
//        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
//        // 设置返回结果格式
//        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
//
//        this.mTranslateEnable = mSharedPreferences.getBoolean(this.getString(R.string.pref_key_translate), false);
//        if (mTranslateEnable) {
//            Log.i(TAG, "translate enable");
//            mIat.setParameter(SpeechConstant.ASR_SCH, "1");
//            mIat.setParameter(SpeechConstant.ADD_CAP, "translate");
//            mIat.setParameter(SpeechConstant.TRS_SRC, "its");
//        }
//
//        String lag = mSharedPreferences.getString("iat_language_preference",
//                "mandarin");
//        if (lag.equals("en_us")) {
//            // 设置语言
//            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
//            mIat.setParameter(SpeechConstant.ACCENT, null);
//
//            if (mTranslateEnable) {
//                mIat.setParameter(SpeechConstant.ORI_LANG, "en");
//                mIat.setParameter(SpeechConstant.TRANS_LANG, "cn");
//            }
//        } else {
//            // 设置语言
//            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
//            // 设置语言区域
//            mIat.setParameter(SpeechConstant.ACCENT, lag);
//
//            if (mTranslateEnable) {
//                mIat.setParameter(SpeechConstant.ORI_LANG, "cn");
//                mIat.setParameter(SpeechConstant.TRANS_LANG, "en");
//            }
//        }
//
//        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
//        mIat.setParameter(SpeechConstant.VAD_BOS, "10000");
//
//        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
//        mIat.setParameter(SpeechConstant.VAD_EOS, "10000");
//
//        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
//        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));
//
//        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
//        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
//    }

//    /**
//     * 听写UI监听器
//     */
//    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
//        public void onResult(RecognizerResult results, boolean isLast) {
//            if (mTranslateEnable) {
//                printTransResult(results);
//            } else {
//                printResult(results, isLast);
//            }
//
//        }
//
//        /**
//         * 识别回调错误.
//         */
//        public void onError(SpeechError error) {
//            if (mTranslateEnable && error.getErrorCode() == 14002) {
//                showToast(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
//            } else {
//                showToast(error.getPlainDescription(true));
//            }
//        }
//
//    };

//    private void printTransResult(RecognizerResult results) {
////        String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
////        String oris = JsonParser.parseTransResult(results.getResultString(),"src");
////
////        if( TextUtils.isEmpty(trans)||TextUtils.isEmpty(oris) ){
////            showToast( "解析结果失败，请确认是否已开通翻译功能。" );
////        }else{
////            mResultText.setText( "原始语言:\n"+oris+"\n目标语言:\n"+trans );
////        }
//
//    }

//    private String voiceResult = "";

//    private void printResult(RecognizerResult results, boolean isLast) {
//
//        String text = com.jusfoun.jusfouninquire.ui.util.xf.JsonParser.parseIatResult(results.getResultString());
//
//        String sn = null;
//        // 读取json结果中的sn字段
//        try {
//            JSONObject resultJson = new JSONObject(results.getResultString());
//            sn = resultJson.optString("sn");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        mIatResults.put(sn, text);
//
//        StringBuffer resultBuffer = new StringBuffer();
//        for (String key : mIatResults.keySet()) {
//            resultBuffer.append(mIatResults.get(key));
//        }
//
//        voiceResult = resultBuffer.toString();
//        Log.e("tag", "voiceResult1=" + voiceResult);
//        if (isLast) {
//            mTitle.setEditText(voiceResult);
//            voiceResult = "";
//        }
//    }

//    /**
//     * 听写监听器。
//     */
//    private RecognizerListener mRecognizerListener = new RecognizerListener() {
//
//        @Override
//        public void onBeginOfSpeech() {
//            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
////            showToast("开始说话");
//        }
//
//        @Override
//        public void onError(SpeechError error) {
//            // Tips：
//            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
//            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
//            if (mTranslateEnable && error.getErrorCode() == 14002) {
//                showToast(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
//            } else {
//                showToast(error.getPlainDescription(true));
//            }
//        }
//
//        @Override
//        public void onEndOfSpeech() {
//            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
////            showToast("结束说话");
//        }
//
//        @Override
//        public void onResult(RecognizerResult results, boolean isLast) {
//            Log.d(TAG, results.getResultString());
//            if (mTranslateEnable) {
//                printTransResult(results);
//            } else {
//                printResult(results, isLast);
//            }
//
//            Log.e("tag", "isLast===" + isLast);
//
//            if (isLast) {
//                // TODO 最后的结果
//            }
//        }
//
//        @Override
//        public void onVolumeChanged(int volume, byte[] data) {
//            Log.e("tag", "volume=" + volume);
////            showToast("当前正在说话，音量大小：" + volume);
////            Log.d(TAG, "返回音频数据："+data.length);
//            voiceDialog.setImageState(volume);
//        }
//
//        @Override
//        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
//            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
//            // 若使用本地能力，会话id为null
//            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
//            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
//            //		Log.d(TAG, "session id =" + sid);
//            //	}
//        }
//    };

//    private void goTaxIdSearch(HotWordItemModel model) {
//        Intent intent;
//        if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_TAXID)) {
//            intent = new Intent(mContext, TaxIdSearchActivity.class);
//            if (model != null) {
//                intent.putExtra("searchKey", model.getSearchkey());
//            }
//            startActivity(intent);
//        } else if (mCurrentType.equals(SearchHistoryItemModel.SEARCH_RECRUITMENT)) {
//            intent = new Intent(mContext, RecruitmentSearchActivity.class);
//            if (model != null) {
//                intent.putExtra("searchKey", model.getSearchkey());
//            }
//            startActivity(intent);
//        }
//    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


    /**
     * 是否为模糊搜索，国信查大多业务使用模糊搜索
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
