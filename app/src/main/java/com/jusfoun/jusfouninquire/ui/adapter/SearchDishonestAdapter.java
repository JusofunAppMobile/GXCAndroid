package com.jusfoun.jusfouninquire.ui.adapter;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jusfoun.jusfouninquire.net.model.DisHonestItemModel;
import com.siccredit.guoxin.R;

/**
 * Created by Albert on 2015/11/11.
 * Mail : lbh@jusfoun.com
 * TODO :搜索失信适配器
 */
public class SearchDishonestAdapter extends BaseQuickAdapter<DisHonestItemModel, BaseViewHolder> {

    public SearchDishonestAdapter() {
        super(R.layout.search_dishonest_item_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, DisHonestItemModel data) {
        TextView mName = helper.getView(R.id.dishonest_name);
        TextView mIDValue = helper.getView(R.id.id_value);
        TextView mLocationValue = helper.getView(R.id.location_value);
        TextView mLegalNumber = helper.getView(R.id.number_value);
        ImageView id_image = helper.getView(R.id.id_image);

        if ("0".equals(data.getType())) {
            id_image.setImageResource(R.mipmap.icon_dis_person);
        } else {
            id_image.setImageResource(R.mipmap.icon_dis_company);
        }
        mName.setText(Html.fromHtml(data.getName()));
        mIDValue.setText(data.getCredentials());
        mLocationValue.setText(data.getLocation());
        mLegalNumber.setText(data.getNumbering());
    }
}
