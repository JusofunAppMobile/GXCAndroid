package com.gxc.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.gxc.base.BaseActivity;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.ui.view.CorporateIRxImgView;
import com.gxc.ui.view.CorporateInfoView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/714:55
 * @Email zyp@jusfoun.com
 * @Description ${编辑自主填报页面}
 */
public class EditReportInfoActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    TitleView titlebar;
    @BindView(R.id.layout_view)
    LinearLayout layoutView;

    public static final int TYPE_INFO = 1;//企业信息
    public static final int TYPE_PRODUCE = 2;//企业产品
    public static final int TYPE_RY = 3;//企业荣誉
    public static final int TYPE_HB = 4;//企业伙伴
    public static final int TYPE_CY = 5;//企业成员

    public static String TYPE = "type";

    private int type = 1;
    private String imagePath;

    private CorporateInfoView corporateInfoView;
    private CorporateIRxImgView corporateIRxImgView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_repory_info;
    }

    @Override
    public void initActions() {
        type = getIntent().getIntExtra(TYPE, TYPE_INFO);

        if (type == TYPE_INFO) {
            corporateInfoView = new CorporateInfoView(this);
            corporateInfoView.setData(TYPE_INFO);
            layoutView.addView(corporateInfoView);
            titlebar.setTitle("企业信息");
        } else if (type == TYPE_PRODUCE) {
            corporateInfoView = new CorporateInfoView(this);
            corporateInfoView.setData(TYPE_PRODUCE);
            layoutView.addView(corporateInfoView);
            titlebar.setTitle("企业产品");
        } else if (type == TYPE_RY) {
            corporateIRxImgView = new CorporateIRxImgView(this);
            corporateIRxImgView.setData(TYPE_RY);
            layoutView.addView(corporateInfoView);
            titlebar.setTitle("企业荣誉");
        } else if (type == TYPE_HB) {
            corporateIRxImgView = new CorporateIRxImgView(this);
            corporateIRxImgView.setData(TYPE_HB);
            layoutView.addView(corporateIRxImgView);
            titlebar.setTitle("企业伙伴");
        } else if (type == TYPE_CY) {
            corporateIRxImgView = new CorporateIRxImgView(this);
            corporateIRxImgView.setData(TYPE_CY);
            layoutView.addView(corporateIRxImgView);
            titlebar.setTitle("企业成员");
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
                                    if (!TextUtils.isEmpty(path)) {
                                        if (type == TYPE_INFO || type == TYPE_PRODUCE || type == TYPE_RY) {
                                            corporateInfoView.setImageSrc(path);
                                        } else {
                                            corporateIRxImgView.setImageSrc(path);
                                        }
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
