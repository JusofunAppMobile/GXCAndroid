package com.jusfoun.jusfouninquire.ui.widget;


import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author lee
 * @version create time:2015年4月17日_下午4:13:27
 * @Description 获取验证码的工具类（针对同种样式的按钮）
 */
public class CountDownUtils extends CountDownTimer{
	private TextView getAuthCodeBtn;

	public CountDownUtils(long millisInFuture, long countDownInterval, TextView getAuthCodeBtn) {
		super(millisInFuture, countDownInterval);
		this.getAuthCodeBtn = getAuthCodeBtn;
	}

	public CountDownUtils(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
	}

	@Override
	public void onFinish() {
		getAuthCodeBtn.setText("重新获取");
		getAuthCodeBtn.setEnabled(true);
	}

	@Override
	public void onTick(long millisUntilFinished) {
		getAuthCodeBtn.setEnabled(false);
		getAuthCodeBtn.setText(millisUntilFinished / 1000 + " 秒");
	}

	
	
}
