package com.jusfoun.bigdata;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jusfoun.jusfouninquire.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lee
 * @version create time:2016/4/1314:08
 * @Email email
 * @Description $名企列表
 */

public class ShowFamousCompanyDialog extends Dialog {
    private Context mContext;
    private ListView mListView;
    private TextView cancelBtn;
    private RelativeLayout outLayout;
    private FamousCompanyAdapter adapter;
    private List<FamousCompanyModel> mList;

    public ShowFamousCompanyDialog(Context context) {
        super(context);
        mContext = context;
    }

    public ShowFamousCompanyDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    protected ShowFamousCompanyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_famous_company);

        initView();

        initWeightAction();
    }

    private void initView(){
        outLayout = (RelativeLayout) findViewById(R.id.outLayout);
        mListView = (ListView) findViewById(R.id.famousCompanyList);
        cancelBtn = (TextView) findViewById(R.id.cancelBtn);
    }

    private void initWeightAction(){
        adapter = new FamousCompanyAdapter(mContext);
        mListView.setAdapter(adapter);

        outLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view.getTag() instanceof FamousCompanyAdapter.ViewHolder){
                    FamousCompanyModel model = ((FamousCompanyAdapter.ViewHolder) view.getTag()).model;
                    listener.onListItem(model);
                }
            }
        });
    }

    public void setList(List<FamousCompanyModel> list, boolean isCenter){
        mList = new ArrayList<>();
        if(adapter != null && list != null){
            mList.addAll(list);
            adapter.refresh(list,isCenter);
        }
    }

    /**
     * 是否获取到数据
     */
    public boolean getAdapterIsHasData(){
        return mList != null && mList.size() > 0;
    }

    private OnListItemListener listener;

    public void setListItemListener(OnListItemListener listener){
        this.listener = listener;
    }

    public interface OnListItemListener{
        void onListItem(FamousCompanyModel model);
    }

}
