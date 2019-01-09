package com.jusfoun.jusfouninquire.ui.view.PropagandaView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.jusfoun.jusfouninquire.net.model.AdItemModel;
import com.jusfoun.jusfouninquire.net.model.HomeDataItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * PropagandaAdapter
 *
 * @author : albert
 * @Email : liubinhou007@163.com
 * @date : 16/8/2
 * @Description :首页运营活动的适配器
 */
public class NewAddAdapter extends LoopPagerAdapter{

    private Context mContext;
    private List<HomeDataItemModel> datalist;

    public NewAddAdapter(Context context, RollPagerView viewPager) {
        super(viewPager);
        this.mContext = context;
        this.datalist = new ArrayList<>();
    }


    public void refresh(List<HomeDataItemModel> datalist){
        this.datalist.clear();
        this.datalist.addAll(datalist);
        notifyDataSetChanged();
    }

    @Override
    public View getView(ViewGroup container, int position) {
        NewAddItemView view = new NewAddItemView(mContext);
        HomeDataItemModel model = datalist.get(position);
        view.setData(model);
        return view;
    }

    @Override
    protected int getRealCount() {
        return datalist.size();
    }

}
