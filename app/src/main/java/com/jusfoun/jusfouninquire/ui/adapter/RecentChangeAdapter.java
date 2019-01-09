package com.jusfoun.jusfouninquire.ui.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.RecentChangeItemModel;
import com.jusfoun.jusfouninquire.ui.activity.BaseInquireActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import netlib.util.AppUtil;

/**
 * @Description $我的报告
 */

public class RecentChangeAdapter extends BaseAdapter{
    private BaseInquireActivity mContext;
    private LayoutInflater inflater;
    private List<RecentChangeItemModel.DataResultBean> mList;

    private int halfScreenWidth;

    public RecentChangeAdapter(BaseInquireActivity context){
        this.mContext = context;
        halfScreenWidth = (AppUtil.getScreenWidth(context) - AppUtil.dp2px(context, 40)) / 2  ;
        mList = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void refresh(List<RecentChangeItemModel.DataResultBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addMore(List<RecentChangeItemModel.DataResultBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void delect(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mList.size() == 0 ? 0: mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    public boolean isDataEmpty() {
        return mList == null || mList.isEmpty();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView=inflater.inflate(R.layout.item_recent_change,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        RecentChangeItemModel.DataResultBean model = mList.get(position);
        holder.updateView(model,position, halfScreenWidth);
        return convertView;
    }


    public class ViewHolder{
        private View convertView;
        private TextView tvBefore;
        private TextView tvAfter;
        private TextView tvName;
        private TextView tvTime;
        private View vLeft;

        public ViewHolder(View convertView){

            this.convertView=convertView;
            tvBefore = (TextView) convertView.findViewById(R.id.tvBefore);
            vLeft =  convertView.findViewById(R.id.vLeft);
            tvAfter = (TextView) convertView.findViewById(R.id.tvAfter);
            tvName = (TextView) convertView.findViewById(R.id.tvName);
            tvTime = (TextView) convertView.findViewById(R.id.tvTime);

        }

        public void updateView(RecentChangeItemModel.DataResultBean bean, final int position, int halfScreenWidth){

            vLeft.getLayoutParams().width = halfScreenWidth;

            tvBefore.setText(Html.fromHtml(bean.changeBefore));
            tvAfter.setText(Html.fromHtml(bean.changeAfter));
            tvName.setText(bean.ename);
            tvTime.setText(parseTime(bean.changeDate));
        }

        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        private String parseTime(long time) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            return sdf.format(calendar.getTime());
        }
    }


}
