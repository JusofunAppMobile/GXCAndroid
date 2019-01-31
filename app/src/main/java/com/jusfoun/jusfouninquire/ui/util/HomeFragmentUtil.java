package com.jusfoun.jusfouninquire.ui.util;

import com.jusfoun.jusfouninquire.ui.fragment.BaseInquireFragment;
import com.jusfoun.jusfouninquire.ui.fragment.NewHomeFragment;
import com.jusfoun.jusfouninquire.ui.fragment.PersonalFragment;
import com.jusfoun.jusfouninquire.ui.fragment.SetFragement;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午2:52
 * @Email zyp@jusfoun.com
 * @Description $ fragemnt 工具类
 */
public class HomeFragmentUtil {
    private static int TYPE_SET = 0;
    private static int TYPE_HOME = 1;
    private static int TYPE_PERSONAL = 2;

    public static BaseInquireFragment getInstance(int index, int padding) {
        BaseInquireFragment fragment = null;
        if (index == TYPE_SET) {
            fragment = SetFragement.getInstance(padding);
        } else if (index == TYPE_HOME) {
            fragment = NewHomeFragment.getInstance(padding);

        } else if (index == TYPE_PERSONAL) {
            fragment = PersonalFragment.getInstance(padding);
        }

        return fragment;
    }
}
