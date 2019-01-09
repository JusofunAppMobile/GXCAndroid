package com.jusfoun.jusfouninquire.ui.util;

import android.os.CountDownTimer;
import android.os.Message;
import android.util.Log;

/**
 * @author lee
 * @version create time:2016/1/2214:13
 * @Email email
 * @Description 首页计时器
 */

public class TimerClockUtil{

    private static TimerClockUtil timerUtil;

    private int remainingTime;
    private CountDownTimer timer;
    private TimerImpl timerImpl;



    public static TimerClockUtil getInstance(){
        if(timerUtil == null){
            timerUtil = new TimerClockUtil();
        }
        return timerUtil;
    }

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link } until the countdown is done and {@link #}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *
     */
    public void start(long millisInFuture, long countDownInterval){
        timer = new CountDownTimer( millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int) millisUntilFinished;
                timerImpl.changeRemainingTime(remainingTime);
            }

            @Override
            public void onFinish() {
                timerImpl.timerFinish();
            }
        };
        timer.start();
    }

    public void cancel(){
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

    public void setRemainingTimeListener(TimerImpl timerImpl){
        this.timerImpl = timerImpl;
    }


    public interface TimerImpl{
        void changeRemainingTime(int time);

        void timerFinish();
    }
}
