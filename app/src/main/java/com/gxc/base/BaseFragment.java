package com.gxc.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gxc.ui.dialog.LoadingDialog;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.service.event.IEvent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public abstract class BaseFragment extends Fragment {

    protected ViewGroup rootView;

    protected BaseActivity activity;
    private LoadingDialog loadingDialog;

    protected abstract int getLayoutId();

    protected abstract void initView();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialog();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = (ViewGroup) inflate(getLayoutId());
            ButterKnife.bind(this, rootView);
            initView();
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接收event 重写此方法
     * */
    public void onEvent(IEvent event){
    }

    /**
     * 显示一个Toast类型的消息
     *
     * @param msg 显示的消息
     */
    public void showToast(final String msg) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.show(msg);
                }
            });
        }
    }

    protected View findViewById(int id) {
        return rootView.findViewById(id);
    }

    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(activity, clazz);
        startActivity(intent);
    }

    protected String getValue(EditText et) {
        return et.getText().toString();
    }

    protected String getValue(TextView et) {
        return et.getText().toString();
    }

    public View inflate(int id) {
        return View.inflate(activity, id, null);
    }


    protected void initDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(activity, R.style.my_dialog);
            loadingDialog.setCancelable(true);
            loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (loadingDialog != null)
                            loadingDialog.cancel();
                    }
                    return true;
                }
            });
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void showLoading() {
        if (isDetached()){
            return;
        }

        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    // 隐藏加载对话框
    protected void hideLoadDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
    }
}
