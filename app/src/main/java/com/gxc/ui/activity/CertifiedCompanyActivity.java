package com.gxc.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.gxc.base.BaseActivity;
import com.gxc.inter.OnSimpleCompressListener;
import com.gxc.ui.view.CorporateIInfoImgItemView;
import com.gxc.ui.view.CorporateInfoItemView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.LogUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhaoyapeng
 * @version create time:2019/1/717:43
 * @Email zyp@jusfoun.com
 * @Description ${认证企业}
 */
public class CertifiedCompanyActivity extends BaseActivity {
    @BindView(R.id.text_tip)
    TextView textTip;
    @BindView(R.id.view_name)
    CorporateInfoItemView viewName;
    @BindView(R.id.view_code)
    CorporateInfoItemView viewCode;
    @BindView(R.id.view_zhiwei)
    CorporateInfoItemView viewZhiwei;
    @BindView(R.id.view_phone)
    CorporateInfoItemView viewPhone;
    @BindView(R.id.view_email)
    CorporateInfoItemView viewEmail;
    @BindView(R.id.img_yyzz)
    CorporateIInfoImgItemView imgYyzz;
    @BindView(R.id.img_idfen)
    CorporateIInfoImgItemView imgIdfen;
    @BindView(R.id.title_bar)
    TitleView titleBar;
    @BindView(R.id.btn_commit)
    Button btnCommit;


    private final int PHOTO_YINGYE = 10001;//营业执照
    private final int PHOTO_IDENTITY = 10002;//身份证

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certified_company;
    }

    @Override
    public void initActions() {
        viewName.setData("企业名称");
        viewCode.setData("身  份  证");
        viewZhiwei.setData("职        位");
        viewPhone.setData("手机号码");
        viewEmail.setData("邮        箱");
        imgYyzz.setData("营业执照", getString(R.string.text_img_carme_tip), PHOTO_YINGYE);
        imgIdfen.setData("本人身份证", getString(R.string.text_img_carme_top_identity), PHOTO_IDENTITY);

        titleBar.setTitle("认证企业");
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String imagePath = null;
        if (resultCode == RESULT_OK) {

            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList != null && !selectList.isEmpty()) {
                LocalMedia media = selectList.get(0);
                if (media.isCut()) {
                    imagePath = media.getCutPath();
                } else {
                    imagePath = media.getPath();
                }

            }

            if (!TextUtils.isEmpty(imagePath)) {
                File file = new File(imagePath);
                LogUtils.e("图片：" + file.getAbsolutePath() + ",大小" + AppUtils.byteToMB(file.length()));

                AppUtils.compress(activity, file, new OnSimpleCompressListener() {
                    @Override
                    public void complete(String path) {
                        if (!TextUtils.isEmpty(path)) {
                            if (PHOTO_YINGYE==requestCode) {
                                imgYyzz.setImageSrc(path);
                            }else if(PHOTO_IDENTITY==requestCode){
                                imgIdfen.setImageSrc(path);
                            }
                        }
                    }
                });
            }
        }
    }

}
