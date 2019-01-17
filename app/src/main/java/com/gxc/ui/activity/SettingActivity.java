package com.gxc.ui.activity;

import android.os.Environment;
import android.view.View;

import com.gxc.base.BaseActivity;
import com.gxc.retrofit.NetModel;
import com.gxc.retrofit.ResponseCall;
import com.gxc.retrofit.RetrofitUtils;
import com.gxc.retrofit.RxManager;
import com.gxc.ui.widgets.ItemView;
import com.gxc.utils.AppUtils;
import com.gxc.utils.DataCleanManager;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 19:18
 * @Email lgd@jusfoun.com
 * @Description ${设置}
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.vCache)
    ItemView vCache;
    @BindView(R.id.vLogout)
    View vLogout;

    @Override
    protected int getLayoutId() {
        return R.layout.act_setting;
    }

    @Override
    public void initActions() {
        titleView.setTitle("设置");
        vLogout.setVisibility(AppUtils.getUser() != null ? View.VISIBLE : View.GONE);

        loadCache();
    }

    private void loadCache() {
        try {
            //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
            long cacheSize = DataCleanManager.getFolderSize(getCacheDir());
            //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
//                cacheSize += DataCleanManager.getFolderSize(getExternalCacheDir());
            vCache.setValue(DataCleanManager.getFormatSize(cacheSize));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCache() {
        //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
        DataCleanManager.cleanInternalCache(activity);
        //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        DataCleanManager.cleanFiles(activity);

        DataCleanManager.deleteFilesByDirectory(getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            DataCleanManager.deleteFilesByDirectory(getExternalCacheDir());
            //下面两句清理webview网页缓存.但是每次执行都报false,我用的是魅蓝5.1的系统，后来发现且/data/data/应用package目录下找不到database文///件夹 不知道是不是个别手机的问题，
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        }

        DataCleanManager.cleanApplicationData(InquireApplication.application);
    }

    @OnClick(R.id.vLogout)
    public void logout() {
        showLoading();
        HashMap<String, Object> map = new HashMap<>();
        map.put("a", "b");
        RxManager.http(RetrofitUtils.getApi().logout(map), new ResponseCall() {

            @Override
            public void success(NetModel model) {
                hideLoadDialog();
                if (model.success()) {
                    AppUtils.logout();
                    vLogout.setVisibility(View.GONE);
                } else {
                    showToast(model.msg);
                }
            }

            @Override
            public void error() {
                hideLoadDialog();
                ToastUtils.showHttpError();
            }
        });

    }

    @OnClick({R.id.vPush, R.id.vCache, R.id.vService, R.id.vSecret, R.id.vAbout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.vPush:
                startActivity(PushSetActivity.class);
                break;
            case R.id.vCache:
                deleteCache();
                showToast("缓存数据已清除");
                loadCache();
                break;
            case R.id.vService:
                startActivity(WebActivity.getIntent(this, "服务协议", AppUtils.TEST_URL, false));
                break;
            case R.id.vSecret:
                startActivity(WebActivity.getIntent(this, "隐私政策", AppUtils.TEST_URL, false));
                break;
            case R.id.vAbout:
                startActivity(AboutActivity.class);
                break;
        }
    }
}
