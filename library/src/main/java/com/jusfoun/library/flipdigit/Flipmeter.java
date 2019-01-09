package com.jusfoun.library.flipdigit;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.jusfoun.library.R;

/**
 * @author VinayrajSingh
 * 
 */
public class Flipmeter extends LinearLayout {
	private static final int NUM_DIGITS = 10;

	private long mCurrentValue, mDestValue;

	private int mRatio,mStep;
	private int animationCompleteCounter = 0;

	private FlipmeterSpinner[] mDigitSpinners;

	public Flipmeter(Context context) {
		super(context);

		initialize();
	}

	public Flipmeter(Context context, AttributeSet attrs) {
		super(context, attrs);

		initialize();
	}

	private void initialize() {
		mDigitSpinners = new FlipmeterSpinner[NUM_DIGITS];

		// Inflate the view from the layout resource.
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(infService);
		li.inflate(R.layout.widget_flipmeter, this, true);

		mDigitSpinners[0] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_1);
		mDigitSpinners[1] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_10);
		mDigitSpinners[2] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_100);
		mDigitSpinners[3] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_1k);
		mDigitSpinners[4] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_10k);
		mDigitSpinners[5] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_100k);
		mDigitSpinners[6] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_1m);
		mDigitSpinners[7] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_10m);
		mDigitSpinners[8] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_100m);
		mDigitSpinners[9] = (FlipmeterSpinner) findViewById(R.id.widget_flipmeter_spinner_1000m);

	}

	public void start(long start, long to, int ratio,int step){
		mCurrentValue = start;
		mDestValue = to;
		mRatio = ratio;
		mStep = step;
		setStaticData(start);
		if (start < to){
			Message message = new Message();
			message.what = 0;
			message.arg1 = step;
			handler.sendMessageDelayed(message,ratio);
		}
	}

	public void restart(){
		handler.removeMessages(0);
		//setStaticData(mCurrentValue);
		start(mCurrentValue, mDestValue, mRatio, mStep);
	}

	public void stop(){
		handler.removeMessages(0);
	}

	public long getCurrentValue(){
		return mCurrentValue;
	}

	public void setValue(int value, boolean withAnimation){
		reset();
		long deltaValue = value - mCurrentValue;
		mCurrentValue = value;
		mDigitSpinners[0].setDigit(deltaValue, withAnimation,onSpinerIsopsephy);

	}

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mDigitSpinners[0].setDigit(msg.arg1, true, onSpinerIsopsephy);
			mCurrentValue = mCurrentValue + msg.arg1;
			if (mCurrentValue < mDestValue){
				Message message = new Message();
				message.what = 0;
				message.arg1 = msg.arg1;
				handler.sendMessageDelayed(message,mRatio);
			}else {
				if (reachGoalListener != null){
					reachGoalListener.onReachGoal();
				}
			}
		}
	};
	private void reset(){
		for (int i = 0; i < NUM_DIGITS; i++)
			mDigitSpinners[i].reset();
	}

	public void setStaticData(long value){
		for (int i = 0; i < NUM_DIGITS; i++){
			mDigitSpinners[i].setStaticDigit(0);
		}
		String digit = value + "";
		for (int i = 0; i < digit.length(); i++){
			if (!TextUtils.isEmpty(digit.charAt(i) + "")){
				if(mDigitSpinners[digit.length() - 1 - i]!=null)
				mDigitSpinners[digit.length() - 1 - i].setStaticDigit(Integer.parseInt(digit.charAt(i) + ""));
			}

		}
		if (digit.length() > 8){
			mDigitSpinners[8].setVisibility(VISIBLE);
		}

		if (digit.length() > 9){
			mDigitSpinners[9].setVisibility(VISIBLE);
		}

	}

	public void setErrorView(){

	}

	/*public void setValue(int value, boolean withAnimation) {

		mCurrentValue = value;
		int tempValue = value;

		for (int i = 5; i > 0; --i) {
			int factor = (int) Math.pow(10, i);

			int digitVal = (int) Math.floor(tempValue / factor);
			tempValue -= (digitVal * factor);
			mDigitSpinners[i].setDigit(digitVal, withAnimation);
			//changeAnimationCompleteCounter(withAnimation);
		}

		mDigitSpinners[0].setDigit(tempValue, withAnimation);
		//changeAnimationCompleteCounter(withAnimation);

	}*/

	private synchronized int changeAnimationCompleteCounter(Boolean increment) {
		if (increment == null)
			return animationCompleteCounter;
		else if (increment)
			return ++animationCompleteCounter;
		else
			return --animationCompleteCounter;
	}

	/**
	 * @return
	 */
	public long getValue() {
		return mCurrentValue;
	}

	public interface OnValueChangeListener {
		abstract void onValueChange(Flipmeter sender, int newValue);
	}

	private FlipmeterSpinner.OnSpinerIsopsephy onSpinerIsopsephy = new FlipmeterSpinner.OnSpinerIsopsephy() {
		@Override
		public void onSpinerIsopsephy(int id) {
			if (id == R.id.widget_flipmeter_spinner_1) {
				mDigitSpinners[1].setDigit(1,true,onSpinerIsopsephy);
			}else if (id == R.id.widget_flipmeter_spinner_10) {
				mDigitSpinners[2].setDigit(1,true,onSpinerIsopsephy);
			}else if (id == R.id.widget_flipmeter_spinner_100) {
				mDigitSpinners[3].setDigit(1,true,onSpinerIsopsephy);
			}else if (id == R.id.widget_flipmeter_spinner_1k) {
				mDigitSpinners[4].setDigit(1,true,onSpinerIsopsephy);
			}else if (id == R.id.widget_flipmeter_spinner_10k) {
				mDigitSpinners[5].setDigit(1,true,onSpinerIsopsephy);
			}else if (id == R.id.widget_flipmeter_spinner_100k){
				mDigitSpinners[6].setDigit(1,true,onSpinerIsopsephy);
			}else if (id == R.id.widget_flipmeter_spinner_1m){
				mDigitSpinners[7].setDigit(1, true, onSpinerIsopsephy);
			}else if (id == R.id.widget_flipmeter_spinner_10m){
				mDigitSpinners[8].setVisibility(VISIBLE);
				mDigitSpinners[8].setDigit(1, true, onSpinerIsopsephy);
			}
		}
	};

	private ReachGoalListener reachGoalListener;

	public void setReachGoalListener(ReachGoalListener reachGoalListener) {
		this.reachGoalListener = reachGoalListener;
	}

	public interface ReachGoalListener{
		public void onReachGoal();
	}
}
