/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.gxc.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.gxc.model.GlideApp;
import com.gxc.model.HomeModel;
import com.gxc.ui.activity.WebActivity;
import com.gxc.utils.AppUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;

import java.util.List;

/**
 * ImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<HomeModel.AdverModel> imageIdList;

    private int size;
    private boolean isInfiniteLoop;

    private RequestOptions options;

    public ImagePagerAdapter(Context context, List<HomeModel.AdverModel> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = AppUtils.getSize(imageIdList);
        isInfiniteLoop = false;
        options = new RequestOptions()
                .placeholder(R.drawable.img_default_ad)
                .error(R.drawable.img_default_ad);
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : AppUtils.getSize(imageIdList);
    }

    public int getRealSize() {
        return size;
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = View.inflate(InquireApplication.application, R.layout.view_image_adapter, null);
            holder.imageView = view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(WebActivity.getIntent(context, "", imageIdList.get(getPosition(position)).webURL, imageIdList.get(getPosition(position)).webType == 1));
            }
        });
        GlideApp.with(InquireApplication.application).load(imageIdList.get(getPosition(position)).imageURL).apply(options).into(holder.imageView);
        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
