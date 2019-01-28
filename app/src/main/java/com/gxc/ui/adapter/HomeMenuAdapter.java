package com.gxc.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gxc.model.GlideApp;
import com.gxc.model.HomeMenuModel;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author liuguangdan
 * @version create at 2019/1/5/005 11:40
 * @Email lgd@jusfoun.com
 * @Description ${}
 */
public class HomeMenuAdapter extends BaseQuickAdapter<HomeMenuModel, BaseViewHolder> {

    private RequestOptions options;

    Integer type[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 ,-1, -1001};
    List<Integer> types = Arrays.asList(type);

    public HomeMenuAdapter() {
        super(R.layout.item_home_menu);
        options = RequestOptions.bitmapTransform(new CircleCrop())
                .placeholder(R.drawable.img_default_icon)
                .error(R.drawable.img_default_icon);

    }

    @Override
    protected void convert(BaseViewHolder holder, HomeMenuModel homeMenuModel) {
        TextView textView = holder.getView(R.id.textView);
        ImageView image = holder.getView(R.id.image);
        textView.setText(homeMenuModel.menuName);
        if (!TextUtils.isEmpty(homeMenuModel.menuImage)) {
            GlideApp.with(InquireApplication.application).load(homeMenuModel.menuImage).apply(options).into(image);
        } else {
            if (homeMenuModel.menuImageId != 0)
                image.setImageResource(homeMenuModel.menuImageId);
            else
                image.setImageResource(R.drawable.img_default_icon);
        }

    }

    @Override
    public void setNewData(@Nullable List<HomeMenuModel> data) {
        super.setNewData(verifyMenuList(data));
    }

    private List<HomeMenuModel> verifyMenuList(List<HomeMenuModel> data) {
        if (data == null) return null;

        List<HomeMenuModel> verifyList= new ArrayList<>();
        Iterator<HomeMenuModel> iterator = data.iterator();
        while (iterator.hasNext()) {
            HomeMenuModel menu = iterator.next();
            if (isMenuTypeValid(menu)){
                verifyList.add(menu);
            }
        }
        return verifyList;
    }

    /**
     * 验证menuType 是否有效
     * 1：股东高管 2：主营产品 3：失信查询 4：查税号 5：招聘 6：企业通讯录 7：查关系
     * 8：风险分析  9:信用报告，10 信用异议，11 ：地址电话 12，信用承诺，13访客，14自主填报
     * 15 ：中标信息  16：裁判文书 17：行政处罚  18：商标查询  -1:H5   -1001 个人中心
     * 2019年1月17日16:36:35
     *
     * @param model
     * @return
     */
    private boolean isMenuTypeValid(HomeMenuModel model) {
        if (types.contains(model.menuType)) return true;
        return false;
    }

}
