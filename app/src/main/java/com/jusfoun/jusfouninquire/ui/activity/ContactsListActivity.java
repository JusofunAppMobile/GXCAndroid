package com.jusfoun.jusfouninquire.ui.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.SearchContactListModel;
import com.jusfoun.jusfouninquire.net.model.SearchListModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.ui.adapter.ContactsListAdapter;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.jusfoun.jusfouninquire.ui.view.XListView;

/**
 * @author zhaoyapeng
 * @version create time:18/5/717:04
 * @Email zyp@jusfoun.com
 * @Description ${通讯录列表 activity}
 */
public class ContactsListActivity extends BaseInquireActivity {
    protected TitleView webTitle;
    protected XListView recyclerview;

    private ContactsListAdapter adapter;
    private SearchContactListModel model;

    @Override
    protected void initData() {
        if (getIntent().getExtras() != null && getIntent().getExtras().get("model") != null) {
            model = (SearchContactListModel) getIntent().getExtras().get("model");
        }
        adapter = new ContactsListAdapter(mContext);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_contacts_list);
        webTitle = (TitleView) findViewById(R.id.web_title);
        recyclerview = (XListView) findViewById(R.id.recyclerview);
    }

    @Override
    protected void initWidgetActions() {
        recyclerview.addHeaderView(LayoutInflater.from(mContext).inflate(R.layout.view_header_constacts_list,null));

        recyclerview.setPullRefreshEnable(false);
        recyclerview.setPullLoadEnable(false);
        webTitle.setTitle("企业报告");
        webTitle.setRightImage(R.mipmap.img_ig);
        webTitle.setRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoModel model = InquireApplication.getUserInfo();
                if (model == null) {
                    goActivity(LoginActivity.class);
                    return;
                }

                if (!TextUtils.isEmpty(model.getUserid())) {

                }

            }
        });
        recyclerview.setAdapter(adapter);
        if (model.data != null) {
            adapter.refresh(model.data);
        }

        if(model!=null&&model.data!=null&&model.data.size()>5){

        }
    }
}
