package com.jusfoun.jusfouninquire.ui.view;

/**
 * @author zhaoyapeng
 * @version create time:2015-6-2上午10:55:47
 * @Email zyp@jusfoun.com
 * @Description TODO
 */

import android.widget.ImageView;

import com.jusfoun.jusfouninquire.R;

public class HomeRefreshSceneAnimation {
    private ImageView mImageView;
    private int[] mDurations;
    private int mDuration;

    private int mLastFrameNo;
    private long mBreakDelay;
    private boolean isStop = false;

    private int[] mFrameRess1 = {R.mipmap.circke_1_000062, R.mipmap.circke_1_000063, R.mipmap.circke_1_000064, R.mipmap.circke_1_000065, R.mipmap.circke_1_000066, R.mipmap.circke_1_000067,
            R.mipmap.circke_1_000068, R.mipmap.circke_1_000069, R.mipmap.circke_1_000070, R.mipmap.circke_1_000071, R.mipmap.circke_1_000072,
            R.mipmap.circke_1_000073, R.mipmap.circke_1_000074, R.mipmap.circke_1_000075, R.mipmap.circke_1_000076, R.mipmap.circke_1_000077, R.mipmap.circke_1_000078, R.mipmap.circke_1_000079,
            R.mipmap.circke_1_000080, R.mipmap.circke_1_000081, R.mipmap.circke_1_000082, R.mipmap.circke_1_000083, R.mipmap.circke_1_000084, R.mipmap.circke_1_000085, R.mipmap.circke_1_000086, R.mipmap.circke_1_000087,
            R.mipmap.circke_1_000088, R.mipmap.circke_1_000089, R.mipmap.circke_1_000090, R.mipmap.circke_1_000091, R.mipmap.circke_1_000092, R.mipmap.circke_1_000093, R.mipmap.circke_1_000094,
            R.mipmap.circke_1_000095, R.mipmap.circke_1_000097, R.mipmap.circke_1_000097, R.mipmap.circke_1_000098, R.mipmap.circke_1_000099, R.mipmap.circke_1_0000100};

//	private int[] mFrameRess ;

    public HomeRefreshSceneAnimation(ImageView pImageView, int pDuration) {
        this.mImageView = pImageView;
        this.mDuration = pDuration;
        this.mLastFrameNo = mFrameRess1.length - 1;

    }

    private void playConstant(final int pFrameNo) {
        if (!isStop) {
            mImageView.postDelayed(new Runnable() {
                public void run() {
                    mImageView.setBackgroundResource(mFrameRess1[pFrameNo]);

                    if (pFrameNo == mLastFrameNo)
                        playConstant(0);
                    else
                        playConstant(pFrameNo + 1);
                }
            }, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay : mDuration);

        }
    }

    public void start() {
        isStop = false;
        mImageView.setBackgroundResource(mFrameRess1[0]);
        playConstant(1);
    }




    public void stop() {
        isStop = true;
    }
}
