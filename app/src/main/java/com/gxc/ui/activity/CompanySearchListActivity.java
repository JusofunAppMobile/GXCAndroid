package com.gxc.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gxc.base.BaseActivity;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.HotWordItemModel;
import com.jusfoun.jusfouninquire.net.model.SearchHotModel;
import com.jusfoun.jusfouninquire.net.route.GetHotSearchRoute;
import com.jusfoun.jusfouninquire.ui.adapter.SearchHotWordsAdapter;
import com.jusfoun.jusfouninquire.ui.view.CommonSearchTitleView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * @author liuguangdan
 * @version create at 2019/1/14/014 10:49
 * @Description ${}
 */
public class CompanySearchListActivity extends BaseActivity {

    @BindView(R.id.searchTitleView)
    CommonSearchTitleView searchTitleView;
    @BindView(R.id.listView)
    ListView listView;
    private SearchHotWordsAdapter mHotAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.act_company_search;
    }

    private void loadData(String key) {
        if (!TextUtils.isEmpty(key)) {
            HashMap<String, String> params = new HashMap<>();
            params.put("searchkey", key);
            GetHotSearchRoute.getRelatedHotwords(this, params, getLocalClassName(), new NetWorkCallBack() {
                @Override
                public void onSuccess(Object data) {
                    if (data instanceof SearchHotModel) {
                        SearchHotModel model = (SearchHotModel) data;
                        if (model.getResult() == 0) {
                            if (model.getHotlist() != null && model.getHotlist().size() > 0) {
                                mHotAdapter.refresh(model.getHotlist());
                            } else {
                                mHotAdapter.refresh(new ArrayList<HotWordItemModel>());
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
        } else {
            mHotAdapter.refresh(new ArrayList<HotWordItemModel>());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarRed();
    }

    @Override
    public void initActions() {
        mHotAdapter = new SearchHotWordsAdapter(this);
        listView.setAdapter(mHotAdapter);

        searchTitleView.setTitleListener(new CommonSearchTitleView.TitleListener() {
            @Override
            public void onTextChange(String key) {
                loadData(key);
            }

            @Override
            public void onDoSearch(String key) {
                loadData(key);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public boolean isBarDark() {
        return false;
    }
}
