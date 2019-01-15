package com.gxc.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.jusfoun.jusfouninquire.service.event.PaySuccessEvent;
import com.jusfoun.jusfouninquire.ui.util.balipay.PayResult;
import com.jusfoun.jusfouninquire.ui.util.balipay.ThreadPoolUtil;

import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * @author liuguangdan
 * @version create at 2019/1/15/015 16:35
 * @Description ${}
 */
public class PayUtils {

    private final int SDK_PAY_FLAG = 1;

    private Activity activity;
    private CallBack callBack;

    public PayUtils(Activity activity, CallBack callBack) {
        this.activity = activity;
        this.callBack = callBack;
    }

    public void alipay(final String order) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(order, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        ThreadPoolUtil.threadPool.execute(payRunnable);
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtils.show("支付成功");
                        EventBus.getDefault().post(new PaySuccessEvent(msg.arg1 == 0));
                        if (callBack != null) {
                            callBack.paySuccess();
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.show("支付失败");
                        if (callBack != null) {
                            callBack.payFail();
                        }
                    }
                    break;
                }
            }
        }

        ;
    };

    public interface CallBack {

        void paySuccess();

        void payFail();
    }
}
