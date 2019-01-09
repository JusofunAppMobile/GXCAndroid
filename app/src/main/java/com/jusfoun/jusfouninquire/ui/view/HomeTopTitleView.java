package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.model.SearchHistoryItemModel;
import com.jusfoun.jusfouninquire.ui.activity.TypeSearchActivity;

/**
 * @author zhaoyapeng
 * @version create time:15/11/23下午2:07
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HomeTopTitleView extends BaseTopPublic {
    private Context mContext;
    private ImageView titleImg, image_hy1, image_hy2;
    private RelativeLayout rootLayout;

    public HomeTopTitleView(Context context) {
        super(context);
        mContext = context;
        initViews();
    }

    public HomeTopTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews();
    }

    public HomeTopTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initViews();
    }

    @Override
    public void setImgRes(int normalImgId, int selecterImgId) {

    }

    // 设置选中状态透明度
    public void setSelectAlpah(float alpah) {
//        if (alpah < 0.3f && alpah > 0.1f) {
//            alpah = 0.3f;
//        } else if (alpah < 0.1f) {
//            alpah = 0;
//        }
        titleImg.setAlpha(alpah);
        image_hy2.setAlpha(alpah);
    }

    //设置 正常态透明度
    public void setNormalAlpah(float alpah) {
//        if (alpah < 0.3f && alpah > 0.1f) {
//            alpah = 0.3f;
//        } else if (alpah < 0.1f) {
//            alpah = 0;
//        }
        image_hy1.setAlpha(alpah);
    }

    @Override
    public void setBig(float zoom, int postion) {

        image_hy1.getLayoutParams().width = (int) (width * (1 - (1 - zoom) / 6.0));
        image_hy1.requestLayout();

        image_hy2.getLayoutParams().height = (int) (width * (1 - (1 - zoom) / 6.0));
        image_hy2.requestLayout();
    }

    float width = 0;

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_top_title, this, true);
        titleImg = (ImageView) findViewById(R.id.image);
        image_hy1 = (ImageView) findViewById(R.id.image_hy_1);
        image_hy2 = (ImageView) findViewById(R.id.image_hy_2);
        rootLayout = (RelativeLayout)findViewById(R.id.layout_root);
        width = image_hy1.getDrawable().getIntrinsicWidth();
        titleImg.setAlpha(0f);
        image_hy2.setAlpha(0f);

//        rootLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, TypeSearchActivity.class);
//                intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_SHAREHOLDER);
//                mContext.startActivity(intent);
//            }
//        });
////

    }

    public int getImageTitleWidth() {
        return titleImg.getDrawable().getIntrinsicWidth();
    }

    public int getImageWidth() {
        return image_hy1.getDrawable().getIntrinsicWidth();
    }


    public void setOnClick(final ViewPager viewPager){
        image_hy1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image_hy1.getVisibility()==View.GONE){
                    Log.e("tag","setOnClick1");
                    Intent intent = new Intent(mContext, TypeSearchActivity.class);
                    intent.putExtra(TypeSearchActivity.SEARCH_TYPE, SearchHistoryItemModel.SEARCH_SHAREHOLDER);
                    mContext.startActivity(intent);
                }else{
                    Log.e("tag","setOnClick2");
                    viewPager.setCurrentItem(1);
                }


            }
        });

    }

    public void setSearchState(int state){
        image_hy1.setVisibility(state);
    }


}
