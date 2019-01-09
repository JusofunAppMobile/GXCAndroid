package com.jusfoun.jusfouninquire.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.util.PhoneUtil;


/**
 * Inspired by
 * http://hzqtc.github.io/2013/12/android-custom-layout-flowlayout.html
 */
public class FlowLayout extends LinearLayout {

    private static final int DEFAULT_HORIZONTAL_SPACING = 10;
    private static final int DEFAULT_VERTICAL_SPACING = 10;

    private int mVerticalSpacing;
    private int mHorizontalSpacing;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        try {
            mHorizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_horizontal_spacing,
                    PhoneUtil.dip2px(context,DEFAULT_HORIZONTAL_SPACING));
            mVerticalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_vertical_spacing,
                    PhoneUtil.dip2px(context,DEFAULT_VERTICAL_SPACING));
        } finally {
            a.recycle();
        }
    }


    public void setHorizontalSpacing(int pixelSize) {
        mHorizontalSpacing = pixelSize;
    }

    public void setVerticalSpacing(int pixelSize) {
        mVerticalSpacing = pixelSize;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int myWidth = resolveSize(0, widthMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;
        // Measure each child and put the child to the right of previous child
        // if there's enough room for it, otherwise, wrap the line and put the child to next line.
        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);
            LayoutParams childLayoutParams = (LayoutParams) childView.getLayoutParams();
            childView.measure(
                    getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childLayoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childLayoutParams.height));
            int childWidth = childView.getMeasuredWidth() + childLayoutParams.leftMargin + childLayoutParams.rightMargin;
            int childHeight = childView.getMeasuredHeight() + childLayoutParams.topMargin + childLayoutParams.bottomMargin;
            ;

            lineHeight = Math.max(childHeight, lineHeight);
            if (childLeft + childWidth + paddingRight >= myWidth) {
                childLeft = paddingLeft + childWidth + mHorizontalSpacing;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            } else {
                childLeft += childWidth + mHorizontalSpacing;
            }
        }
        int wantedHeight = childTop + lineHeight + paddingBottom + 20;
        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();


        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        for (int i = 0, childCount = getChildCount(); i < childCount; ++i) {
            View childView = getChildAt(i);
            LayoutParams childLayoutParams = (LayoutParams) childView.getLayoutParams();
            int marginLeft = childLayoutParams.leftMargin;
            int marginTop = childLayoutParams.topMargin;
            int marginRight = childLayoutParams.rightMargin;
            int marginBottom = childLayoutParams.bottomMargin;
            if (childView.getVisibility() == View.GONE) {
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight, lineHeight);
//            Log.e("tag","lineHeight1="+lineHeight);

            if (childLeft + childWidth + paddingRight + marginLeft + marginRight > myWidth) {
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight + marginTop + marginBottom;
                lineHeight = childHeight;
                currentLine++;

            }

            childView.layout(childLeft + marginLeft + marginRight, childTop + marginTop, childLeft + childWidth + marginLeft + marginRight, childTop + childHeight + marginTop + marginBottom);
            childLeft += childWidth + mHorizontalSpacing;

//            if (currentLine == 1) {
//                childView.setBackgroundResource(R.drawable.back_flow_seconde);
//            } else if (currentLine == 2) {
//                childView.setBackgroundResource(R.drawable.back_flow_third);
//            }
        }
    }

    private int currentLine = 0;

    public int getCurrentLine() {
        return currentLine;
    }
}

