package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.BidsModel;
import com.jusfoun.jusfouninquire.net.model.PublishModel;
import com.jusfoun.jusfouninquire.net.model.StockModel;

import java.util.ArrayList;
import java.util.List;

import static com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant.TYPE_BIDS;
import static com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant.TYPE_PUBLISH;
import static com.jusfoun.jusfouninquire.ui.constant.CompanyDetailTypeConstant.TYPE_STOCK;

public class CompanyAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mList = new ArrayList<>();
    private int type;

    private Gson gson = new Gson();

    public CompanyAdapter(Context context, int type) {
        mContext = context;
        this.type = type;
    }

    public void addDatas(List<T> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = null;
        if (convertView == null) {
            int resid = 0;
            if (type == TYPE_PUBLISH) // 行政处罚
                resid = R.layout.item_publish;
            else if (type == TYPE_STOCK) // 股权出质
                resid = R.layout.item_stock;
            else if (type == TYPE_BIDS) // 招标
                resid = R.layout.item_bids;

            convertView = LayoutInflater.from(mContext).inflate(resid, null);
            if (type == TYPE_PUBLISH) // 行政处罚
                holder = new PublishHolder(convertView);
            else if (type == TYPE_STOCK) // 股权出质
                holder = new StockHolder(convertView);
            else if (type == TYPE_BIDS) // 招标
                holder = new BidsHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (BaseViewHolder) convertView.getTag();
        }
        holder.update(position);
        return convertView;
    }

    public void cleanAllData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public class BaseViewHolder {
        public void update(int position) {
        }
    }

    // 行政处罚
    public class PublishHolder extends BaseViewHolder {

        private TextView tvWriter,tvType, tvDate;

        public PublishHolder(View view) {
            tvWriter = (TextView) view.findViewById(R.id.tvWriter);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
        }

        @Override
        public void update(int position) {
            super.update(position);
            PublishModel model = gson.fromJson(gson.toJson(mList.get(position)), PublishModel.class);
            tvWriter.setText(model.bookNumber);
            tvType.setText(model.legalType);
            tvDate.setText(model.punishmentDate);
        }
    }

    // 股权出质
    public class StockHolder extends BaseViewHolder {

        private TextView tvNo,tvPledgor, tvPledgee;

        public StockHolder(View view) {
            tvNo = (TextView) view.findViewById(R.id.tvNo);
            tvPledgor = (TextView)view.findViewById(R.id.tvPledgor);
            tvPledgee = (TextView) view.findViewById(R.id.tvPledgee);
        }

        @Override
        public void update(int position) {
            super.update(position);
            StockModel model = gson.fromJson(gson.toJson(mList.get(position)), StockModel.class);
            tvNo.setText(model.registrationNo);
            tvPledgor.setText(model.pledgor);
            tvPledgee.setText(model.pledgee);
        }
    }

    // 招标
    public class BidsHolder extends BaseViewHolder {

        private TextView tvName,tvArea,tvDate,tvType;

        public BidsHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvArea = (TextView) view.findViewById(R.id.tvArea);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvType = (TextView) view.findViewById(R.id.tvType);
        }

        @Override
        public void update(int position) {
            super.update(position);
            BidsModel model = gson.fromJson(gson.toJson(mList.get(position)), BidsModel.class);
            tvName.setText(model.procurementName);
            tvArea.setText(model.administrativeRegion);
            tvDate.setText(model.announcementTime);
            tvType.setText(model.announcementType);
        }
    }

//    public class ViewHolder {
//        public TextView tvName;
//        public TextView tvStatus;
//        public TextView tvType;
//        public SimpleDraweeView ivIcon;
//
//        public ViewHolder(View view) {
//            tvName = (TextView) view.findViewById(R.id.tvName);
//            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
//            tvType = (TextView) view.findViewById(R.id.tvType);
//            ivIcon = (SimpleDraweeView) view.findViewById(R.id.ivIcon);
//        }
//
//
//        public void update(int position) {
//            BrandModel model = mList.get(position);
//            tvName.setText(model.name);
//            tvStatus.setText(model.stauts);
//            tvType.setText(model.category);
//            if (!TextUtils.isEmpty(model.imgPath)) {
//                ivIcon.setImageURI(Uri.parse(model.imgPath));
//            } else {
//                Uri uri = Uri.parse("res://" +
//                        mContext.getPackageName() +
//                        "/" + R.mipmap.img_brand_def);
//                ivIcon.setImageURI(uri);
//            }
//        }
//    }
}
