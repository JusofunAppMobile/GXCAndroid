package com.gxc.ui.dialog;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gxc.model.VersionModel;
import com.gxc.utils.AppUtils;
import com.gxc.utils.ToastUtils;
import com.jusfoun.jusfouninquire.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liuguangdan
 * @version create at 2019/1/8/008 16:42
 * @Email lgd@jusfoun.com
 * @Description ${版本更新对话框}
 */
public class VersionDialog extends BaseDialog {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvInfor)
    TextView tvInfor;

    private VersionModel model;

    public VersionDialog(Activity activity, VersionModel model) {
        super(activity);
        this.model = model;
        setContentView(R.layout.dialog_version);
        ButterKnife.bind(this);
        setWindowStyle(8, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        tvTitle.setText("版本功能（V" + model.versionname + "）");
        tvInfor.setText(Html.fromHtml(model.describe));
    }

    private void loadApp(String version) {
        try {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(model.url));
            //设置在什么网络情况下进行下载
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            //设置通知栏标题
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            }
            request.setTitle(AppUtils.getApplicationName(activity));
            request.setDescription("正在下载最新版本");
            /*设置类型为.apk*/
            request.setMimeType("application/vnd.android.package-archive");
            // 移动网络情况下是否允许漫游
            request.setAllowedOverRoaming(false);

            int random = (int) (Math.random() * 900) + 100; // 生成三位随机数

            //设置文件存放目录
            request.setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS, "v" + version + "_" + random + ".apk");

            DownloadManager downManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
            long id = downManager.enqueue(request);
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
            sp.edit().putLong(DownloadManager.EXTRA_DOWNLOAD_ID, id).commit();
            ToastUtils.show("正在后台下载，完成后自动运行安装");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showHttpError();
        }
    }

    @OnClick({R.id.bt, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt:
                loadApp(model.versionname);
                dismiss();
                break;
            case R.id.ivClose:
                dismiss();
                break;
        }
    }
}
