package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.model.FilterContentItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * FilterContentAdapter
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/10
 * @Description :具体某个筛选条件的适配器
 */
public class FilterContentAdapter extends BaseAdapter {
    //TODO 不同类型的筛选条件显示可能会不同，此处的显示及逻辑稍后实现
    private Context mContext;
    private List<FilterContentItemModel> mData;
    private int type;
    private Drawable drawable;

    private boolean isUnfold;

    public FilterContentAdapter(Context mContext) {
        this.mContext = mContext;
        this.mData = new ArrayList<>();
        drawable = mContext.getResources().getDrawable(R.mipmap.location);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getCount() {
        if (mData.size() <= 6)
            return mData.size();
        return isUnfold ? mData.size() : 0;
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
    public View getView(int position, View view, ViewGroup viewGroup) {


//        Log.e("tag","getViewgetViewgetView=");
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_fiflter, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.updateView(mData.get(position), position);
        return view;
    }

    public void setUnfold(boolean isUnfold) {
        this.isUnfold = isUnfold;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        private TextView textView;

        public ViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.tv);
//            if (type==FilterListAdapter.TYPE_INDUSTRY){
//                textView= (TextView) convertView.findViewById(R.id.item_name_industry);
//            }else {
//                textView = (TextView) convertView.findViewById(R.id.item_name);
//            }
//            textView.setVisibility(View.VISIBLE);
        }

        public void updateView(final FilterContentItemModel model, int position) {

//            if (type==FilterListAdapter.TYPE_CITY){
//                if (model.isLocation()){
//                    textView.setCompoundDrawables(null,null,drawable,null);
//                }else {
//                    textView.setCompoundDrawables(null,null,null,null);
//                }
//            }

            textView.setSelected(model.isSelect());
//            if (model.isSelect()){
//                textView.setBackgroundColor(Color.parseColor("#f6f1e9"));
//                textView.setTextColor(Color.parseColor("#ff6935"));
//            }else {
//                textView.setBackgroundColor(Color.parseColor("#f4f4f4"));
//                textView.setTextColor(Color.parseColor("#666666"));
//            }
            textView.setText(model.getItemname());
        }
    }

    public FilterContentItemModel select(int position) {
        FilterContentItemModel model = mData.get(position);
        if (model == null)
            return null;
        if (model.isSelect()) {
            model.setSelect(false);
        } else {
            for (FilterContentItemModel filterContentItemModel : mData) {
                filterContentItemModel.setSelect(false);
            }
            model.setSelect(true);
        }
        notifyDataSetChanged();
        return model;
    }


    public void refresh(List<FilterContentItemModel> mData, boolean isUnfold) {
        this.mData.clear();
        this.isUnfold = isUnfold;
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }
}
