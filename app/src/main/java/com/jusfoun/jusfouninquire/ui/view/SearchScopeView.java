package com.jusfoun.jusfouninquire.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.AreaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 2015/11/10.
 * Mail : lbh@jusfoun.com
 * TODO :搜索区域view
 */
public class SearchScopeView extends RelativeLayout {
    private Context mContext;

    private TextView mSearchScope,mSelectProvinceTip;
    private ImageView mSelectProvinceImage;

    private RelativeLayout mScopeSelect;

    private List<AreaModel> mSearchScopeList;



    public SearchScopeView(Context context) {
        super(context);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public SearchScopeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    public SearchScopeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchScopeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initData();
        initView();
        initWidgetAction();
    }

    private void initData(){

        mSearchScopeList = new ArrayList<>();
        try {
//            String value = LibIOUtil.convertStreamToJson(mContext.getResources().openRawResource(R.raw.provice_city_area));
//            Type listType = new TypeToken<ArrayList<AreaModel>>() {
//            }.getType();
//            List<AreaModel> list = new Gson().fromJson(value, listType);
//            for (AreaModel model : list){
//                mSearchScopeList.add(model);
//            }

            AreaModel country = new AreaModel();
            country.setName("全国");
            country.setId("0");
            mSearchScopeList.add(0,country);

        } catch (Exception exception) {

        } finally {

        }
    }

    private void initView(){
        LayoutInflater.from(mContext).inflate(R.layout.search_scope_view_layout, this, true);
        mSearchScope = (TextView)findViewById(R.id.search_scope_province);
        mSelectProvinceTip = (TextView)findViewById(R.id.select_province_tip);
        mSelectProvinceImage = (ImageView)findViewById(R.id.select_province_image);
        mScopeSelect = (RelativeLayout) findViewById(R.id.scope_layout);

    }

    private void initWidgetAction(){

        mScopeSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.OnShowScopeList();
                }
            }
        });

    }

    public void setSearchScope(String scope){
        mSearchScope.setText(scope);
    }

    public void setSelectProvinceTip(String tip){
        mSelectProvinceTip.setText(tip);
    }

    public String getSearchScope(){
        return mSearchScope.getText().toString();
    }

    private OnShowScopeListListener mListener;

    public void setShowScopeListener(OnShowScopeListListener listener){
        mListener = listener;
    }

    public interface OnShowScopeListListener{
        public void OnShowScopeList();
    }


}
