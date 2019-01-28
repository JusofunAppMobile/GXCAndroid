package com.jusfoun.jusfouninquire.ui.view;

import android.util.Log;
import android.widget.ImageView;

import com.siccredit.guoxin.R;

/**
 * @author zhaoyapeng
 * @version create time:17/1/610:20
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class HomeSecondeRefreshAnimation {
    private ImageView mImageView;
    private int[] mDurations;
    private int mDuration;

    private int mLastFrameNo;
    private long mBreakDelay;
    private boolean isStop = true;
    private int[] mFrameRess = {R.mipmap.circke_1_00001, R.mipmap.circke_1_00002, R.mipmap.circke_1_00003, R.mipmap.circke_1_00004,
            R.mipmap.circke_1_00005, R.mipmap.circke_1_00006, R.mipmap.circke_1_00007, R.mipmap.circke_1_00008, R.mipmap.circke_1_00009, R.mipmap.circke_1_000010,
            R.mipmap.circke_1_000011, R.mipmap.circke_1_000012, R.mipmap.circke_1_000013, R.mipmap.circke_1_000014, R.mipmap.circke_1_000015, R.mipmap.circke_1_000016, R.mipmap.circke_1_000017
            , R.mipmap.circke_1_00008, R.mipmap.circke_1_000019, R.mipmap.circke_1_000020, R.mipmap.circke_1_000021, R.mipmap.circke_1_000022, R.mipmap.circke_1_000023, R.mipmap.circke_1_000024,
            R.mipmap.circke_1_000025, R.mipmap.circke_1_000026, R.mipmap.circke_1_000027, R.mipmap.circke_1_000028, R.mipmap.circke_1_000029, R.mipmap.circke_1_000030, R.mipmap.circke_1_000031,
            R.mipmap.circke_1_000032, R.mipmap.circke_1_000033, R.mipmap.circke_1_000034, R.mipmap.circke_1_000035, R.mipmap.circke_1_000036, R.mipmap.circke_1_000037, R.mipmap.circke_1_000038,
            R.mipmap.circke_1_000039, R.mipmap.circke_1_000040, R.mipmap.circke_1_000041, R.mipmap.circke_1_000042, R.mipmap.circke_1_000043, R.mipmap.circke_1_000044, R.mipmap.circke_1_000045,
            R.mipmap.circke_1_000046, R.mipmap.circke_1_000047, R.mipmap.circke_1_000048, R.mipmap.circke_1_000049, R.mipmap.circke_1_000050, R.mipmap.circke_1_000051, R.mipmap.circke_1_000052,
            R.mipmap.circke_1_000053, R.mipmap.circke_1_000054, R.mipmap.circke_1_000055, R.mipmap.circke_1_000056, R.mipmap.circke_1_000057, R.mipmap.circke_1_000058, R.mipmap.circke_1_000059,
            R.mipmap.circke_1_000060, R.mipmap.circke_1_000061, R.mipmap.circke_1_000062};


    public HomeSecondeRefreshAnimation(ImageView pImageView, int pDuration) {
        this.mImageView = pImageView;
        this.mDuration = pDuration;
        this.mLastFrameNo = mFrameRess.length - 1;

    }

    private void playConstant(final int pFrameNo) {
        if (!isStop) {
//            Log.e("tag","isUnderTouch3="+pFrameNo);
            mImageView.postDelayed(new Runnable() {
                public void run() {
//                    Log.e("tag","isUnderTouch4="+pFrameNo);

                    if (pFrameNo >= mLastFrameNo) {
                        if(pFrameNo==mLastFrameNo){
                            mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
                        }
                        playConstant(0);
                    } else {
                        mImageView.setBackgroundResource(mFrameRess[pFrameNo]);
                        playConstant(pFrameNo + 1);
                    }
                }
            }, pFrameNo == mLastFrameNo && mBreakDelay > 0 ? mBreakDelay : mDuration);

        }
    }

    public void start(int postion) {

//        Log.e("tag","isUnderTouch2");
        isStop = false;
        mImageView.setBackgroundResource(mFrameRess[postion]);
        playConstant(postion + 1);
    }


    public void setCurrentImage(int postion) {
        mImageView.setBackgroundResource(mFrameRess[postion]);
    }


    public void stop() {
        Log.e("tag", "stop");
        isStop = true;
    }

    public boolean isStop() {
        return isStop;
    }
}
