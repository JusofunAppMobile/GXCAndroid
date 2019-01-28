package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.siccredit.guoxin.R;


/**
 * @author zhaoyapeng
 * @version create time:18/1/310:57
 * @Email zyp@jusfoun.com
 * @Description ${企业雷达 数字 view}
 */
public class RadarCountView extends BaseView {
    protected LinearLayout layoutRadar;
    protected ImageView imgRadar1;
    protected ImageView imgRadar2;
    protected ImageView imgRadar3;
    protected ImageView imgRadar4;
    protected ImageView imgRadar5;
    protected ImageView imgRadar6;
    protected ImageView imgRadar7;
    protected ImageView imgRadar8;
    protected ImageView imgRadar9;
    protected ImageView imgRadar10;
    protected ImageView imgRadar11;
    protected ImageView imgRadar12;
    protected ImageView imgRadar13;

    public RadarCountView(Context context) {
        super(context);
    }

    public RadarCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_count_radar, this, true);
        layoutRadar = (LinearLayout) findViewById(R.id.layout_radar);
        initView(this);
    }

    @Override
    protected void initActions() {

    }


    public void setData(String radarCount) {
        Log.e("tag", "setData==" + radarCount);
//        layoutRadar.removeAllViews();
        int count = radarCount.length();
        setAllGone();
        for (int i = 0; i < count; i++) {
            try {
                String src = radarCount.substring(i, i + 1);
                int index = Integer.valueOf(src);
                setPage(i,index);
            } catch (Exception E) {

            }
        }
    }

    private void setPage(int index,int value){

        switch (index) {
            case 0:
                imgRadar1.setVisibility(View.VISIBLE);
                imgRadar1.setImageResource(getResId(value));
                break;
            case 1:
                imgRadar2.setVisibility(View.VISIBLE);
                imgRadar2.setImageResource(getResId(value));
                break;
            case 2:
                imgRadar3.setVisibility(View.VISIBLE);
                imgRadar3.setImageResource(getResId(value));
                break;
            case 3:
                imgRadar4.setVisibility(View.VISIBLE);
                imgRadar4.setImageResource(getResId(value));
                break;
            case 4:
                imgRadar5.setVisibility(View.VISIBLE);
                imgRadar5.setImageResource(getResId(value));
                break;
            case 5:
                imgRadar6.setVisibility(View.VISIBLE);
                imgRadar6.setImageResource(getResId(value));
                break;
            case 6:
                imgRadar7.setVisibility(View.VISIBLE);
                imgRadar7.setImageResource(getResId(value));
                break;
            case 7:
                imgRadar8.setVisibility(View.VISIBLE);
                imgRadar8.setImageResource(getResId(value));
                break;
            case 8:
                imgRadar9.setVisibility(View.VISIBLE);
                imgRadar9.setImageResource(getResId(value));
                break;
            case 9:
                imgRadar10.setVisibility(View.VISIBLE);
                imgRadar10.setImageResource(getResId(value));
                break;
            case 10:
                imgRadar11.setVisibility(View.VISIBLE);
                imgRadar11.setImageResource(getResId(value));
                break;
            case 11:
                imgRadar12.setVisibility(View.VISIBLE);
                imgRadar12.setImageResource(getResId(value));
                break;
            case 12:
                imgRadar13.setVisibility(View.VISIBLE);
                imgRadar13.setImageResource(getResId(value));
                break;
        }
    }

    private void setAllGone(){
        imgRadar1.setVisibility(View.GONE);
        imgRadar2.setVisibility(View.GONE);
        imgRadar3.setVisibility(View.GONE);
        imgRadar4.setVisibility(View.GONE);
        imgRadar5.setVisibility(View.GONE);
        imgRadar6.setVisibility(View.GONE);
        imgRadar7.setVisibility(View.GONE);
        imgRadar8.setVisibility(View.GONE);
        imgRadar9.setVisibility(View.GONE);
        imgRadar10.setVisibility(View.GONE);
        imgRadar11.setVisibility(View.GONE);
        imgRadar12.setVisibility(View.GONE);
        imgRadar13.setVisibility(View.GONE);

    }

    private int getResId(int index) {

        switch (index) {
            case 0:
                return R.drawable.img_radar_0;
            case 1:
                return R.drawable.img_radar_1;
            case 2:
                return R.drawable.img_radar_2;
            case 3:
                return R.drawable.img_radar_3;
            case 4:
                return R.drawable.img_radar_4;
            case 5:
                return R.drawable.img_radar_5;
            case 6:
                return R.drawable.img_radar_6;
            case 7:
                return R.drawable.img_radar_7;
            case 8:
                return R.drawable.img_radar_8;
            case 9:
                return R.drawable.img_radar_9;

        }
        return -1;
    }

    private void initView(View rootView) {
        imgRadar1 = (ImageView) rootView.findViewById(R.id.img_radar1);
        imgRadar2 = (ImageView) rootView.findViewById(R.id.img_radar2);
        imgRadar3 = (ImageView) rootView.findViewById(R.id.img_radar3);
        imgRadar4 = (ImageView) rootView.findViewById(R.id.img_radar4);
        imgRadar5 = (ImageView) rootView.findViewById(R.id.img_radar5);
        imgRadar6 = (ImageView) rootView.findViewById(R.id.img_radar6);
        imgRadar7 = (ImageView) rootView.findViewById(R.id.img_radar7);
        imgRadar8 = (ImageView) rootView.findViewById(R.id.img_radar8);
        imgRadar9 = (ImageView) rootView.findViewById(R.id.img_radar9);
        imgRadar10 = (ImageView) rootView.findViewById(R.id.img_radar10);
        imgRadar11 = (ImageView) rootView.findViewById(R.id.img_radar11);
        imgRadar12 = (ImageView) rootView.findViewById(R.id.img_radar12);
        imgRadar13 = (ImageView) rootView.findViewById(R.id.img_radar13);
        layoutRadar = (LinearLayout) rootView.findViewById(R.id.layout_radar);
    }
}
