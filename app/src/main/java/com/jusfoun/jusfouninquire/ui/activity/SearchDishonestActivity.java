package com.jusfoun.jusfouninquire.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.database.DBUtil;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.HotWordItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchDisHonestModel;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchHotModel;
import com.jusfoun.jusfouninquire.net.route.GetHotSearchRoute;
import com.jusfoun.jusfouninquire.net.route.SearchDishonestRoute;
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
import com.jusfoun.jusfouninquire.TimeOut;

/**
 * SearchDishonestActivity
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/30
 * @Description :搜索失信页面
 */
public class SearchDishonestActivity extends BaseInquireActivity{

    private static final int RESULT=1000;
    public static final int RESULT_FINISH=1001;
    public static final int RESULT_CLEAR=1002;
    private LinearLayout line;
    private CommonSearchTitleView mTitle;
    private SearchGuideView mSearchGuideView;
    private ListView mHotWordsList;


    private SearchHotWordsAdapter mHotAdapter;

    private String mCurrentType = SearchHistoryItemModel.SEARCH_DISHONEST;



    @Override
    protected void initData() {
        super.initData();
        mHotAdapter = new SearchHotWordsAdapter(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search_dishonest);
        mTitle = (CommonSearchTitleView) findViewById(R.id.search_title_view);
        line=(LinearLayout) findViewById(R.id.line5);
        mSearchGuideView = (SearchGuideView) findViewById(R.id.search_guide_view);
        mHotWordsList = (ListView) findViewById(R.id.hot_words_list);

    }

    @Override
    protected void initWidgetActions() {
        mHotWordsList.setAdapter(mHotAdapter);
        mTitle.setTitleListener(new CommonSearchTitleView.TitleListener() {
            @Override
            public void onTextChange(String key) {
                searchHotWords(key);
            }

            @Override
            public void onDoSearch(String key) {
                KeyBoardUtil.hideSoftKeyboard((Activity)mContext);
                DoSearchEvent event = new DoSearchEvent();
                event.setSearchKey(key);
                EventBus.getDefault().post(event);
            }
        });

        mTitle.setEditHint(getString(R.string.type_search_dishonest));

        mHotWordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getTag() instanceof SearchHotWordsAdapter.ViewHolder) {
                    SearchHotWordsAdapter.ViewHolder holder = (SearchHotWordsAdapter.ViewHolder) view.getTag();
                    if (holder == null) {
                        return;
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
    }


    /**
     * 初始化搜索记录和热门搜索区域
     */
    private void initGuideView() {
        initHistorySearch();
        initHotSearch();

    }

    /**
     * 初始化 历史搜索显示
     */
    private void initHistorySearch() {
        //TODO 读取本地数据库，调用 SearchGuideView 的 setHistorySearch（）方法进行显示
        List<SearchHistoryItemModel> historyItemModels = new ArrayList<>();
        historyItemModels.addAll(DBUtil.getSortList(getApplicationContext(),mCurrentType));
        if (historyItemModels.size() > 0){
            mSearchGuideView.setHistorySearch(historyItemModels,mCurrentType);
        }
    }


    /**
     * 初始化 热门搜索 区域 显示
     */
    private void initHotSearch() {
        //TODO 网络请求，调用 SearchGuideView 的 setHotSearch（）方法进行显示
        HashMap<String, String> params = new HashMap<>();
        params.put("type",mCurrentType);
        GetHotSearchRoute.getHotSearch(mContext, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof SearchHotModel){
                    SearchHotModel model = (SearchHotModel) data;
                    if (model.getResult() == 0){
                        if (model.getHotlist() != null && model.getHotlist().size() > 0){
                            mSearchGuideView.setHotSearch(model.getHotlist(),mCurrentType);
                        }
                    }else {
                        //TODO 错误逻辑待定
                    }
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }


    /**
     * 保存历史搜索记录
     */
    private void saveSearchHistory(String key){
        SearchHistoryItemModel model = new SearchHistoryItemModel();
        model.setType(mCurrentType);
        model.setSearchkey(key);
        model.setTimestamp(System.currentTimeMillis());
        DBUtil.insertItem(mContext,model);
    }


    /**
     * TODO 失信维度的热词搜索
     */
    private void searchHotWords(String key){
        if (TextUtils.isEmpty(key)){
            mSearchGuideView.setVisibility(View.VISIBLE);
            line.setVisibility(View.GONE);
            mHotWordsList.setVisibility(View.GONE);
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("searchkey",key);
        GetHotSearchRoute.getDishonestHotwords(mContext, params, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof SearchHotModel){
                    SearchHotModel model = (SearchHotModel) data;
                    if (model.getResult() == 0){
                        if (model.getHotlist() != null && model.getHotlist().size() > 0){
                            mSearchGuideView.setVisibility(View.GONE);
                            mHotWordsList.setVisibility(View.VISIBLE);
                            line.setVisibility(View.VISIBLE);
                            mHotAdapter.refresh(model.getHotlist());

                        }else {
                            mSearchGuideView.setVisibility(View.VISIBLE);
                            mHotWordsList.setVisibility(View.GONE);
                        }
                    }else {
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
        HashMap<String, String> params = new HashMap<>();
        if (LoginSharePreference.getUserInfo(mContext) != null
                && !TextUtils.isEmpty(LoginSharePreference.getUserInfo(mContext).getUserid())) {
            params.put("userid", LoginSharePreference.getUserInfo(mContext).getUserid());
        } else {
            params.put("userid", "");
        }

        TimeOut timeOut = new TimeOut(mContext);
        params.put("searchkey", key);
        params.put("type", "1");
        params.put("pageSize", "20");
        params.put("pageIndex", "1");
        params.put("province", "");
        params.put("t", timeOut.getParamTimeMollis()+"");
        params.put("m", timeOut.MD5time()+"");
        showLoading();
        SearchDishonestRoute.searchDishonest(this, this.getLocalClassName().toString(),params,  new NetWorkCallBack() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                if (data instanceof SearchDisHonestModel){
                    SearchDisHonestModel model = (SearchDisHonestModel) data;
                    if (model.getResult() == 0){
                        if (model.getDishonestylist() != null && model.getDishonestylist().size() > 0){
//                            if (!isDestroyed()){
                                //TODO 跳转失信搜索结果页面
                                Intent intent=new Intent(mContext,DishonestResultActivity.class);
                                intent.putExtra(DishonestResultActivity.SEARCHKEY,key);
                                intent.putExtra(DishonestResultActivity.MODEL,model);
                                startActivityForResult(intent,RESULT);
//                            }

                        }else {
                            showToast(getString(R.string.search_result_none));
                        }
                    }else {
                        showToast(model.getMsg());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(error);
            }
        });
    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
        if (event instanceof DoSearchEvent){
            DoSearchEvent doSearchEvent = (DoSearchEvent) event;
            if (!TextUtils.isEmpty(doSearchEvent.getSearchKey())){
                //接收到搜索事件之后，保存搜索记录并进行搜索
                saveSearchHistory(doSearchEvent.getSearchKey());
                searchNet(doSearchEvent.getSearchKey());
            }
        }else if (event instanceof GoTypeSearchEvent){
            //业务逻辑：收到此事件，根据事件携带的关键字进行热词检索
            GoTypeSearchEvent goTypeSearchEvent = (GoTypeSearchEvent) event;
            mTitle.setEditText(goTypeSearchEvent.getKey());
        }else if (event instanceof GoHomeEvent){
            finish();
        }else if (event instanceof ReSearchEvent){
            mTitle.setEditText("");
            mSearchGuideView.setVisibility(View.VISIBLE);
            mHotWordsList.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHistorySearch();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==-1)
            return;

        switch (requestCode){
            case RESULT:
                if (resultCode==RESULT_FINISH){
                    finish();
                }else if (resultCode==RESULT_CLEAR){
                    mTitle.setEditText("");
                    mSearchGuideView.setVisibility(View.VISIBLE);
                    mHotWordsList.setVisibility(View.GONE);
                }
                break;
        }
    }
}
