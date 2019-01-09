package com.jusfoun.jusfouninquire.ui.view.PropagandaView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jusfoun.jusfouninquire.net.model.AdItemModel;

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
public class PropagandaAdapter extends LoopPagerAdapter{

    private Context mContext;
    private List<AdItemModel> datalist;

    public PropagandaAdapter(Context context,RollPagerView viewPager) {
        super(viewPager);
        this.mContext = context;
        this.datalist = new ArrayList<>();
    }


    public void refresh(List<AdItemModel> datalist){
        this.datalist.clear();
        this.datalist.addAll(datalist);
        notifyDataSetChanged();
    }

    @Override
    public View getView(ViewGroup container, int position) {
        PropagandaItemView view = new PropagandaItemView(container.getContext());
        AdItemModel model = datalist.get(position);
        switch (position){
            case 0:
                model.setUmeng("Home08");
                break;
            case 1:
                model.setUmeng("Home09");
                break;
            case 2:
                model.setUmeng("Home10");
                break;
            case 3:
                model.setUmeng("Home11");
                break;
            case 4:
                model.setUmeng("Home12");
                break;
        }
        view.setData(model);
        return view;
    }

    @Override
    protected int getRealCount() {
        return datalist.size();
    }
}
