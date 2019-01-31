package com.jusfoun.jusfouninquire.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.ui.activity.TypeSearchActivity;
import com.siccredit.guoxin.R;

import netlib.util.EventUtils;

/**
 * Author  zyp
 * CreateDate 2015/11/10.
 * Description  首页 icon view
 */
public class HomeIconAdapter extends RecyclerView.Adapter {

    private Context context;


    public HomeIconAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HomeIconAdapter.HomeIconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeIconViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_icon, parent, false), context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HomeIconViewHolder) holder).updateView(position);
    }

    @Override
    public int getItemCount() {
        return 8;
    }


    class HomeIconViewHolder extends RecyclerView.ViewHolder {

        private TextView txt;
        private ImageView img;
        private ImageView vNew;
        private Context mContext;

        public HomeIconViewHolder(View itemView, Context mContext) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.company_menu_txt);
            img = (ImageView) itemView.findViewById(R.id.company_menu_img);
            vNew = (ImageView) itemView.findViewById(R.id.vNew);
            this.mContext = mContext;
        }

        public void updateView(final int position) {
            img.setAlpha(1f);
            txt.setAlpha(1f);
            txt.setTextColor(0xff333333);
            vNew.setVisibility(View.GONE);
            switch (position) {
//                case 0:
//                    img.setImageResource(R.mipmap.icon_home_fujin);
//                    txt.setText("股东穿透");
//                    img.setAlpha(0.3f);
//                    txt.setTextColor(0xff555555);
////                    txt.setAlpha(0.8f);
////                    txt.setTextColor(mContext.getResources().getColor(R.color.search_info_color));
//                    break;
                case 0:
                    img.setImageResource(R.mipmap.home_icon_gudong);
                    txt.setText("股东高管");
                    break;
                case 1:
                    img.setImageResource(R.mipmap.home_icon_zhuying);
                    txt.setText("主营产品");
                    break;
                case 2:
                    img.setImageResource(R.mipmap.home_icon_dizhi);
                    txt.setText("地址电话");
                    break;
                case 3:
                    img.setImageResource(R.mipmap.home_icon_shixin);
                    txt.setText("失信查询");
                    break;
                case 4:
                    img.setImageResource(R.mipmap.home_icon_chashiuhao);
                    txt.setText("查税号");
                    break;
                case 5:
                    img.setImageResource(R.drawable.home_icon_chaopin);
                    txt.setText("招聘");
                    break;
//                case 6:
//                    img.setImageResource(R.mipmap.home_icon_quanbu);
//                    txt.setText("全部");
//                    img.setAlpha(0.3f);
//                    txt.setTextColor(0xff555555);
////                    txt.setAlpha(0.8f);
////                    txt.setTextColor(mContext.getResources().getColor(R.color.search_info_color));
//                    break;
                case 6:
                    img.setImageResource(R.drawable.home_icon_contact);
                    txt.setText("企业通讯录");
                    break;
                case 7:
                    img.setImageResource(R.drawable.home_icon_shareholder);
                    txt.setText("股东穿透");
                    vNew.setVisibility(View.VISIBLE);
                    break;
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TypeSearchActivity.class);
                    switch (position) {
//                        case 0:
//                            mContext.startActivity(new Intent(context, NearMapActivity.class));
//                            break;
                        case 0:
                            intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_SHAREHOLDER);
                            mContext.startActivity(intent);
                            break;
                        case 1:
                            EventUtils.event(mContext, EventUtils.HOME02);
                            intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_PRODUCT);
                            mContext.startActivity(intent);
                            break;
                        case 2:
                            EventUtils.event(mContext, EventUtils.HOME04);
                            intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_ADDRESS);
                            mContext.startActivity(intent);
                            break;
                        case 3:
                            EventUtils.event(mContext, EventUtils.HOME06);
//                            Intent intentDishonest = new Intent(mContext, SearchDishonestActivity.class);
//                            mContext.startActivity(intentDishonest);
                            break;
                        case 4:
                            intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_TAXID);
                            mContext.startActivity(intent);
                            break;
                        case 5:
                            intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_RECRUITMENT);
                            mContext.startActivity(intent);
                            break;
                        case 6:
                            intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_CONTACT);
                            mContext.startActivity(intent);
                            break;
                        case 7:
                            intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_SHAREHOLDER_RIFT);
                            mContext.startActivity(intent);
                            break;
                    }
                }
            });
        }
    }
}
