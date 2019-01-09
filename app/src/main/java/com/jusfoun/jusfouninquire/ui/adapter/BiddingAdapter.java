package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.BiddingListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyapeng
 * @version create time:17/9/717:10
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class BiddingAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<BiddingListModel.BiddingItemModel> list;

    public BiddingAdapter(Context mContext) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        list = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BinddingViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_layout_bidding, null);
            holder = new BinddingViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (BinddingViewHolder) convertView.getTag();
        }
        holder.update(list.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        if (!list.isEmpty())
            return list.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    class BinddingViewHolder {
        private TextView titleText, areaText, timeText, typeText;

        public BinddingViewHolder(View itemView) {
            titleText = (TextView) itemView.findViewById(R.id.text_title);
            areaText = (TextView) itemView.findViewById(R.id.text_area);
            timeText = (TextView) itemView.findViewById(R.id.text_time);
            typeText = (TextView) itemView.findViewById(R.id.text_type);
        }

        public void update(BiddingListModel.BiddingItemModel model) {
            titleText.setText(model.procurementName);
            areaText.setText(model.administrativeRegion);
            timeText.setText(model.announcementTime);
            typeText.setText(model.announcementType);
        }
    }

    public void refresh(List<BiddingListModel.BiddingItemModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<BiddingListModel.BiddingItemModel> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
