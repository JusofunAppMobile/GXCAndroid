package com.jusfoun.jusfouninquire.ui.activity;

import android.widget.ListView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.adapter.SearchHotWordsAdapter;
import com.jusfoun.jusfouninquire.ui.view.SearchGuideView;

/**
 * CommonSearchActivity
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/10
 * @Description :企信宝2.0，模糊搜索页面
 */
public class CommonSearchActivity extends BaseInquireActivity{

    private SearchGuideView mSearchGuideView;
    private ListView mHotWordsList;



    private SearchHotWordsAdapter mHotAdapter;

    @Override
    protected void initData() {
        super.initData();
        mHotAdapter = new SearchHotWordsAdapter(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_type_search);
        mSearchGuideView = (SearchGuideView) findViewById(R.id.search_guide_view);
        mHotWordsList = (ListView) findViewById(R.id.hot_words_list);
    }

    @Override
    protected void initWidgetActions() {
        mHotWordsList.setAdapter(mHotAdapter);

        initGuideView();
    }


    /**
     * 初始化搜索记录和热门搜索区域
     */
    private void initGuideView(){
        initHistorySearch();
        initHotSearch();
    }

    /**
     * 初始化 历史搜索显示
     */
    private void initHistorySearch() {
        //TODO 读取本地数据库，调用 SearchGuideView 的 setHistorySearch（）方法进行显示
    }


    /**
     * 初始化 热门搜索 区域 显示
     */
    private void initHotSearch() {
        //TODO 网络请求，调用 SearchGuideView 的 setHotSearch（）方法进行显示
    }

}
