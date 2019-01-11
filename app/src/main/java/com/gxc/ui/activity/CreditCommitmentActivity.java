package com.gxc.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gxc.base.BaseActivity;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.utils.AppUtils;
import com.gxc.utils.GlideRoundTransform;
import com.gxc.utils.LogUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/1115:11
 * @Email zyp@jusfoun.com
 * @Description ${信用承诺书}
 */
public class CreditCommitmentActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleView titlebar;
    @BindView(R.id.text_get_moban)
    TextView textGetMoban;
    @BindView(R.id.img_photo)
    ImageView imgPhoto;

    private String imagePath;
    private RequestOptions requestOptions;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_commitment;
    }

    @Override
    public void initActions() {
        titlebar.setTitle("信用承诺");
        requestOptions = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.color.white)
                .error(R.color.white)
                .transform(new GlideRoundTransform(activity,5));

    }

    @OnClick({R.id.text_get_moban, R.id.img_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_get_moban:
                break;
            case R.id.img_photo:
                AppUtils.pictureSelect(activity, false, 1, null);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && !selectList.isEmpty()) {
                        LocalMedia media = selectList.get(0);
                        if (media.isCut())
                            imagePath = media.getCutPath();
                        else
                            imagePath = media.getPath();
                        if (!TextUtils.isEmpty(imagePath)) {
                            File file = new File(imagePath);
                            LogUtils.e("图片：" + file.getAbsolutePath() + ",大小" + AppUtils.byteToMB(file.length()));

                            AppUtils.compress(activity, file, new OnSimpleCompressListener() {
                                @Override
                                public void complete(String path) {
                                    if(!TextUtils.isEmpty(path)){
                                        Glide.with(activity).load(path).apply(requestOptions).into(imgPhoto);
                                    }
                                }
                            });
                        }
                    }
                    break;
            }
        }
    }
}
