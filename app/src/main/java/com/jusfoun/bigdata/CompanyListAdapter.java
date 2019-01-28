package com.jusfoun.bigdata;

import android.content.Context;
import android.widget.TextView;

import com.siccredit.guoxin.R;

import java.util.List;

/**
 * @author lzx
 * @since 2016/8/5.
 */
public class CompanyListAdapter extends BaseRecyclerAdapter<XListDataModel> {


    public CompanyListAdapter(Context context, List<XListDataModel> beans) {
        super(context, beans);
    }

    @Override
    public void onBindData(RecyclerViewHolder holder, XListDataModel bean, int position) {
        update(holder, bean);
    }

    @Override
    protected void onItemClick(int position) {
        super.onItemClick(position);
//        XListDataModel xModel = mBeans.get(position);
//        Intent intent = new Intent(mContext, CompanyNewDetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("ent_id", xModel.getEnt_id());
//        bundle.putString("entname", xModel.getEntname());
//        intent.putExtra("SelectModel", bundle);
//        mContext.startActivity(intent);
    }

    @Override
    public int getItemLayout(int viewType) {
        return R.layout.xlistview_item_near;
    }

    public void update(RecyclerViewHolder holder, XListDataModel model) {


        TextView companyName = (TextView) holder.getView(R.id.company_name);
        TextView  companyState = (TextView) holder.getView(R.id.company_state);
        TextView companyInfo = (TextView) holder.getView(R.id.company_info);
        TextView legalText = (TextView) holder.getView(R.id.text_legal);
        TextView areaText = (TextView)holder.getView(R.id.text_area);
        TextView distanceText = (TextView)holder.getView(R.id.company_distance);



//        TextView nametextview = holder.getView(R.id.nametextview);
//        TextView area = holder.getView(R.id.area);
//        TextView server = holder.getView(R.id.server);
//        TextView assets_value = holder.getView(R.id.assets_value);
//        TextView income_value = holder.getView(R.id.income_value);
//
//        TextView distanceTextView = holder.getView(R.id.distanceTextView);
//        TextView divisionView = holder.getView(R.id.division_line);
//
//        LinearLayout listViewLayout = holder.getView(R.id.listView_layout);
//        LinearLayout noDataLayout = holder.getView(R.id.last_View_layout);
//
//
//        if (TextUtils.isEmpty(model.getEnt_id())) {
//            listViewLayout.setVisibility(View.GONE);
//            noDataLayout.setVisibility(View.VISIBLE);
//            noDataLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    Intent intent = new Intent(mContext, ExactQueryActivity.class);
////                    mContext.startActivity(intent);
//                    AboutWebActvity.startActivity(mContext, AboutWebActvity.EXACT_SEARCH);
//                }
//            });
//        } else {
//            listViewLayout.setVisibility(View.VISIBLE);
//            noDataLayout.setVisibility(View.GONE);
//            nametextview.setText(model.getEntname());
//            if (TextUtils.isEmpty(model.getArea()) || "-".equals(model.getArea())) {
//                area.setText("暂无");
////                SkinUtils.setSkinTextColor(mContext, area, "list_sub_content_lighttextcolor", R.color.list_sub_content_lighttextcolor);
//                //area.setTextColor(mContext.getResources().getColor(R.color.list_adapter_nodata_color));
//            } else {
//                area.setText(model.getArea());
////                SkinUtils.setSkinTextColor(mContext, area, "list_sub_content_lighttextcolor", R.color.list_sub_content_lighttextcolor);
//            }
//
//            /**
//             * 控制显示不显示距离
//             */
//            if (!TextUtils.isEmpty(model.getDistince()) && !TextUtils.isEmpty(model.getDistinceunit())) {
//                distanceTextView.setVisibility(View.VISIBLE);
//                divisionView.setVisibility(View.VISIBLE);
//                distanceTextView.setText(model.getDistince() + model.getDistinceunit() + "");
//            } else {
//                distanceTextView.setVisibility(View.GONE);
//                divisionView.setVisibility(View.GONE);
//            }
//
////            if (TextUtils.isEmpty(model.getZhuyinghangye()) || "-".equals(model.getZhuyinghangye())) {
////                server.setText("暂无");
////                SkinUtils.setSkinTextColor(mContext, server, "list_sub_content_lighttextcolor", R.color.list_sub_content_lighttextcolor);
////            } else {
////                server.setText(model.getZhuyinghangye());
////                SkinUtils.setSkinTextColor(mContext, server, "list_sub_content_lighttextcolor", R.color.list_sub_content_lighttextcolor);
////            }
//
//            if (TextUtils.isEmpty(model.getLegalperson()) || "-".equals(model.getLegalperson())) {
//                server.setText("暂无");
////                SkinUtils.setSkinTextColor(mContext, server, "list_sub_content_lighttextcolor", R.color.list_sub_content_lighttextcolor_n);
//            } else {
//                server.setText(model.getLegalperson());
////                SkinUtils.setSkinTextColor(mContext, server, "list_sub_content_lighttextcolor", R.color.list_sub_content_lighttextcolor_n);
//            }
//
//            if (TextUtils.isEmpty(model.getZichanzongji()) || "-".equals(model.getZichanzongji()) || "0.0".equals(model.getZichanzongji())) {
//                assets_value.setText("暂无");
////                SkinUtils.setSkinTextColor(mContext, assets_value, "list_sub_content_lighttextcolor", R.color.list_sub_content_lighttextcolor);
//            } else {
//                assets_value.setText(model.getZichanzongji() + model.getZichanzongjiunit());
////                SkinUtils.setSkinTextColor(mContext, assets_value, "list_sub_content_hightextcolor", R.color.list_sub_content_hightextcolor);
//            }
//            income_value.setTextColor(mContext.getResources().getColor(R.color.list_adapter_nodata_color));
//            if (TextUtils.isEmpty(model.getYingyeshouru()) || "-".equals(model.getYingyeshouru()) || "0.0".equals(model.getYingyeshouru())) {
//                income_value.setText("暂无");
////                SkinUtils.setSkinTextColor(mContext, income_value, "list_sub_content_lighttextcolor", R.color.list_sub_content_lighttextcolor);
//            } else {
//                income_value.setText(model.getYingyeshouru() + model.getYingyeshouruunit());
////                SkinUtils.setSkinTextColor(mContext, income_value, "list_sub_content_hightextcolor", R.color.list_sub_content_hightextcolor);
//            }

        companyName.setText(model.getEntname());
        legalText.setText(model.getLegalperson());
        companyState.setText(model.getRegisterStatus());
        companyInfo.setText(model.money);
        areaText.setText(model.getArea());

        distanceText.setText(model.distance);
    }


}
