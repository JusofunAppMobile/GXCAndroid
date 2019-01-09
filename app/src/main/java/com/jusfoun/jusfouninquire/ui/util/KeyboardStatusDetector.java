package com.jusfoun.jusfouninquire.ui.util;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 软键盘弹出、隐藏监听处理
 */
public class KeyboardStatusDetector {

    private static final int SOFT_KEY_BOARD_MIN_HEIGHT = 100;
    private OnKeyboardListener listener;

    boolean keyboardVisible = false;

    public KeyboardStatusDetector registerFragment(Fragment f) {
        return registerView(f.getView());
    }

    public KeyboardStatusDetector registerActivity(Activity a) {
        return registerView(a.getWindow().getDecorView().findViewById(android.R.id.content));
    }

    public KeyboardStatusDetector registerView(final View v) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                v.getWindowVisibleDisplayFrame(r);

                int heightDiff = v.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > SOFT_KEY_BOARD_MIN_HEIGHT) { // if more than 100 pixels, its probably a keyboard...
                    if (!keyboardVisible) {
                        keyboardVisible = true;
                        if (listener != null) {
                            listener.onVisibilityChanged(true);
                        }
                    }
                } else {
                    if (keyboardVisible) {
                        keyboardVisible = false;
                        if (listener != null) {
                            listener.onVisibilityChanged(false);
                        }
                    }
                }
            }
        });

        return this;
    }

    public KeyboardStatusDetector setListener(OnKeyboardListener listener) {
        this.listener = listener;
        return this;
    }

    public interface OnKeyboardListener {
        void onVisibilityChanged(boolean keyboardVisible);
    }
}
