package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.FilterContentItemModel;
import com.jusfoun.jusfouninquire.net.model.FilterItemModel;
import com.jusfoun.jusfouninquire.ui.util.LogUtil;
import com.jusfoun.jusfouninquire.ui.view.NoScrollGridview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netlib.util.EventUtils;

/**
 * FilterListAdapter
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/10
 * @Description :搜索结果页面，筛选条件 总 适配器
 */
public class FilterListAdapter extends BaseAdapter {

    public final static int TYPE_CITY = 1;
    public final static int TYPE_PROVICE = 2;
    public final static int TYPE_INDUSTRY = 3;
    public final static int TYPE_FUND = 4;
    public final static int TYPE_AGE = 5;
    private Context mContext;
    private List<FilterItemModel> mData;
    private HashMap<String, String> params;
    private boolean canUnfold = true;

    private Handler handler = new Handler();

    public FilterListAdapter(Context context) {
        this.mContext = context;
        this.mData = new ArrayList<>();
        params = new HashMap<>();

    }

    private FilterContentAdapter cityAdapter;

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_fliter_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.updateView(position);
        return convertView;
    }

    public void refresh(List<FilterItemModel> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();

    }

    public void refresh(List<FilterItemModel> data, boolean canUnfold) {
        this.canUnfold = canUnfold;
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();

    }

    public void reset() {
        for (FilterItemModel filterItemModel : mData) {
            if (filterItemModel.getFilterItemList() == null)
                continue;
            for (FilterContentItemModel model : filterItemModel.getFilterItemList()) {
                model.setSelect(false);
            }
        }
        params.clear();
        notifyDataSetChanged();
    }

    public void sure(HashMap<String, String> params) {
        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            LogUtil.e("entrykey", entry.getKey());
            LogUtil.e("entryvalue", entry.getValue());
            params.put(entry.getKey(), entry.getValue());
        }
    }

    public class ViewHolder {
        private TextView mSectionTitle, mSectionTitle2;//条件名称，如 “城市”、“行业”
        private ImageView mSectionFold;//展开或收起当前条件内容
        private NoScrollGridview mFilterContent;//当前筛选条件的所有内容
        private View view1, view2;
        private View vLine;

        private FilterContentAdapter mContentAdapter;//筛选内容的适配器

        public ViewHolder(View convertView) {
            mSectionTitle = (TextView) convertView.findViewById(R.id.filter_section_name);
            mSectionTitle2 = (TextView) convertView.findViewById(R.id.filter_section_name2);
            mSectionFold = (ImageView) convertView.findViewById(R.id.section_fold);
            mFilterContent = (NoScrollGridview) convertView.findViewById(R.id.filter_content);
            view1 = convertView.findViewById(R.id.view1);
            view2 = convertView.findViewById(R.id.view2);
            vLine = convertView.findViewById(R.id.vLine);

            mContentAdapter = new FilterContentAdapter(mContext);
            mFilterContent.setAdapter(mContentAdapter);
        }

        public void updateView(final int position) {

            final FilterItemModel model = mData.get(position);
            if (model == null)
                return;

            if (model.getType() == TYPE_FUND) // 注册资本
                mFilterContent.setNumColumns(2);
            else
                mFilterContent.setNumColumns(3);

            if(model.getType() == TYPE_CITY)
                cityAdapter = mContentAdapter;

            mContentAdapter.setType(model.getType());

            if (position <= 1) {
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);
            } else {
                view2.setVisibility(View.GONE);
                view1.setVisibility(View.VISIBLE);
            }

            if (position == 0)
                vLine.setVisibility(View.GONE);
            else
                vLine.setVisibility(View.VISIBLE);

            if (canUnfold) {
                if (model.getFilterItemList() == null || model.getFilterItemList().size() < 7)
                    mSectionFold.setVisibility(View.GONE);
                else {
                    if (model.isUnfold()) {
                        mSectionFold.setImageResource(R.mipmap.arrow_up);
                    } else {
                        mSectionFold.setImageResource(R.mipmap.arrow_down);
                    }
                    mSectionFold.setVisibility(View.VISIBLE);
                }
            } else {
                mSectionFold.setVisibility(View.GONE);
                model.setUnfold(true);
            }

            mFilterContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    /********统计友盟事件 begin*******/
                    if (model.getType() == TYPE_CITY) {
                        EventUtils.event(mContext, EventUtils.SEARCH48);
                    } else if (model.getType() == TYPE_PROVICE) {
                        EventUtils.event(mContext, EventUtils.SEARCH49);
                    } else if (model.getType() == TYPE_INDUSTRY) {
                        EventUtils.event(mContext, EventUtils.SEARCH50);
                    } else if (model.getType() == TYPE_FUND) {
                        EventUtils.event(mContext, EventUtils.SEARCH52);
                    } else if (model.getType() == TYPE_AGE) {
                        EventUtils.event(mContext, EventUtils.SEARCH53);
                    }
                    /********统计友盟事件 end*******/


                    if (model.getType() == TYPE_CITY
                            || model.getType() == TYPE_PROVICE) {
                        //城市和省份 单选
                        params.put("city", "");
                        params.put("province", "");
                        for (FilterItemModel filterItemModel : mData) {
                            if (filterItemModel.getType() == TYPE_CITY
                                    || filterItemModel.getType() == TYPE_PROVICE) {
                                if (filterItemModel.getFilterItemList() == null)
                                    continue;
                                for (int i = 0; i < filterItemModel.getFilterItemList().size(); i++) {
                                    FilterContentItemModel filterContentItemModel = filterItemModel.getFilterItemList().get(i);
                                    if (model.getType() == filterItemModel.getType()
                                            && i == position) {
                                        filterContentItemModel.setSelect(!filterContentItemModel.isSelect());
                                        if (!filterContentItemModel.isSelect())
                                            params.put(model.getKey(), "");
                                        else
                                            params.put(model.getKey(), filterContentItemModel.getItemvalue());
                                    } else {
                                        filterContentItemModel.setSelect(false);
                                    }
                                }
                            }

                        }
                        notifyDataSetChanged();
                        return;
                    }

                    FilterContentItemModel itemModel = mContentAdapter.select(position);
                    if (itemModel == null)
                        return;
                    if (!itemModel.isSelect())
                        params.put(model.getKey(), "");
                    else {
                        params.put(model.getKey(), itemModel.getItemvalue());
                    }
                }
            });

            mSectionTitle2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (model.getFilterItemList() == null || model.getFilterItemList().size() < 7)
                        return;

                    model.setUnfold(!model.isUnfold());

                    mContentAdapter.setUnfold(model.isUnfold());

                    if (mContentAdapter.getCount() == 0)
                        mFilterContent.setVisibility(View.GONE);
                    else
                        mFilterContent.setVisibility(View.VISIBLE);

                    mSectionFold.setImageResource(model.isUnfold() ? R.mipmap.arrow_up : R.mipmap.arrow_down);
//
//                    mContentAdapter.refresh(model.getFilterItemList(), model.isUnfold());

//                    notifyDataSetChanged();

//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mContentAdapter.refresh(model.getFilterItemList(), model.isUnfold());
//                            notifyDataSetChanged();
//                        }
//                    }, 500);

//                    if (model.isUnfold()) {
//                        model.setUnfold(false);
//                        mSectionFold.setImageResource(R.mipmap.arrow_down);
//                        mContentAdapter.refresh(model.getFilterItemList(), false);
//                        notifyDataSetChanged();
//                        mContentAdapter.setUnfold(false);
//                    } else {
//                        model.setUnfold(true);
//                        mSectionFold.setImageResource(R.mipmap.arrow_up);
//                        mContentAdapter.refresh(model.getFilterItemList(), true);
//                        notifyDataSetChanged();
//                        mContentAdapter.setUnfold(true);
//                    }

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            LogUtil.e("ABc", "-------1-----------" + mContentAdapter.getCount());
//                            mContentAdapter.notifyDataSetChanged();
//                        }
//                    }, 5000);

//                    if (mContentAdapter.getCount() == 0)
//                        mFilterContent.setVisibility(View.GONE);
//                    else
//                        mFilterContent.setVisibility(View.VISIBLE);

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            LogUtil.e("ABc", "-------2-----------" + mContentAdapter.getCount());
//                            if (mContentAdapter.getCount() == 0)
//                                mFilterContent.setVisibility(View.GONE);
//                            else
//                                mFilterContent.setVisibility(View.VISIBLE);
//                            notifyDataSetChanged();
//                        }
//                    }, 10000);
                }
            });
            mSectionTitle.setText(model.getName());
            mSectionTitle2.setText(model.getName());
            mContentAdapter.refresh(model.getFilterItemList(), model.isUnfold());
            if (mContentAdapter.getCount() == 0)
                mFilterContent.setVisibility(View.GONE);
            else
                mFilterContent.setVisibility(View.VISIBLE);

        }
    }

    public void refreshCityAdapter(){
        if(cityAdapter != null)
            cityAdapter.notifyDataSetChanged();
    }
}
