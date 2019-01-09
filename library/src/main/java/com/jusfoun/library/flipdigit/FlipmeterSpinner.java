package com.jusfoun.library.flipdigit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.jusfoun.library.R;

/**
 * @author VinayrajSingh
 * 
 */
public class FlipmeterSpinner extends RelativeLayout {

	private Context context;
	private View flipMeterSpinner = null;

	private int mCurrentDigit;

	private FlipDigit flipDigit = null;

	private OnSpinerIsopsephy onSpinerIsopsephy = new OnSpinerIsopsephy() {
		@Override
		public void onSpinerIsopsephy(int id) {

		}
	};



	/*
	 * Simple constructor used when creating a view from code.
	 */
	public FlipmeterSpinner(Context context) {
		super(context);
		this.context = context;
		initialize();
	}

	/*
	 * This is called when a view is being constructed from an XML file, supplying attributes that were specified in the
	 * XML file.
	 */
	public FlipmeterSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initialize();
	}

	/*
	 * Perform inflation from XML and apply a class-specific base style. This constructor of View allows subclasses to
	 * use their own base style when they are inflating.
	 */
	public FlipmeterSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initialize();
	}

	private void inflateLayout() {
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		flipMeterSpinner = layoutInflater.inflate(R.layout.view_flipmeter_spinner, this);

	}

	public View getFlipmeterSpinner() {
		return flipMeterSpinner;
	}

	public void setDigit(long animateTo, boolean withAnimation,OnSpinerIsopsephy onSpinerIsopsephy) {
		this.onSpinerIsopsephy = onSpinerIsopsephy;
		flipDigit.setDigit(animateTo, withAnimation);

	}

	public void reset(){
		flipDigit.reset();
	}

	public void setStaticDigit(int digit){
		flipDigit.setStaticDigit(digit);
	}

	/*
	 * Initialize all of our class members and variables
	 */
	private void initialize() {
		inflateLayout();

		flipDigit = new FlipDigit(context, getId(), flipMeterSpinner, onAnimationComplete,onIsopsephy);


	}

	FlipDigit.OnAnimationComplete onAnimationComplete = new FlipDigit.OnAnimationComplete() {
		@Override
		public void onComplete(int id) {
			if (id == R.id.widget_flipmeter_spinner_1) {

			} else if (id == R.id.widget_flipmeter_spinner_10){

			}else if (id == R.id.widget_flipmeter_spinner_100){

			}else if (id == R.id.widget_flipmeter_spinner_1k){

			}else if (id == R.id.widget_flipmeter_spinner_10k){

			}else if (id == R.id.widget_flipmeter_spinner_100k){

			}
		}
	};

	private FlipDigit.OnIsopsephy onIsopsephy = new FlipDigit.OnIsopsephy() {
		@Override
		public void onDigitIsopsephy(int id) {
			if (onSpinerIsopsephy != null){
				onSpinerIsopsephy.onSpinerIsopsephy(id);
			}
		}
	};

	public interface OnSpinerIsopsephy {
		public void onSpinerIsopsephy(int id);
	}

	public int getCurrentDigit() {
		return mCurrentDigit;
	}

}
