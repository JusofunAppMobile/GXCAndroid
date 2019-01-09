package com.jusfoun.jusfouninquire.ui.widget;

import android.view.View;
import android.widget.OverScroller;

/**
 * Author  wangchenchen
 * CreateDate 2016/10/17.
 * Email wcc@jusfoun.com
 * Description 滑动工具类
 */
public abstract class SwipeHorizontal {

    public static final int LEFT_DIRECTION=1;
    public static final int RIGHT_DIRECTION=-1;

    private int direction;
    private View menuView;
    protected Checker mChecker;

    public SwipeHorizontal(int direction, View menuView) {
        this.direction = direction;
        this.menuView = menuView;
        mChecker = new Checker();
    }

    public abstract boolean isMenuOpen(final int scrollX);

    public abstract boolean isMenuOpenNotEqual(final int scrollX);

    public abstract void autoOpenMenu(OverScroller scroller, int scrollX, int duration);

    public abstract void autoCloseMenu(OverScroller scroller, int scrollX, int duration);

    public abstract Checker checkXY(int x, int y);

    public abstract boolean isClickOnContentView(int contentViewWidth, float x);

    public int getDirection() {
        return direction;
    }

    public View getMenuView() {
        return menuView;
    }

    public int getMenuWidth() {
        return menuView.getWidth();
    }

    public static final class Checker {
        public int x;
        public int y;
        public boolean shouldResetSwipe;
    }
}
