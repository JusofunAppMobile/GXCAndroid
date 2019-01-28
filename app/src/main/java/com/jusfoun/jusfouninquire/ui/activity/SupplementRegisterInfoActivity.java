package com.jusfoun.jusfouninquire.ui.activity;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.siccredit.guoxin.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.constant.LoginConstant;
import com.jusfoun.jusfouninquire.net.model.LoginModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.service.event.CompleteLoginEvent;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.service.event.RefreshHomeEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LastLoginSharePreference;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.util.Md5Util;
import com.jusfoun.jusfouninquire.ui.util.PictureUtil;
import com.jusfoun.jusfouninquire.ui.util.crawl.TakePhotoEvent;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import de.greenrobot.event.EventBus;
import netlib.util.EventUtils;
import netlib.util.LibIOUtil;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import simplecropimage.CropImage;

import static com.jusfoun.bigdata.NearMapActivity.ACTION_APPLICATION_DETAILS_SETTINGS_CODE;
import static com.jusfoun.jusfouninquire.ui.util.AppUtil.isPermissionDenied;
import static com.jusfoun.jusfouninquire.ui.util.AppUtil.showPermissionCameraDialog;

/**
 * @author zhaoyapeng
 * @version create time:15/10/30下午4:10
 * @Email zyp@jusfoun.com
 * @Description ${注册 ，补充信息页面}
 */
@RuntimePermissions
public class SupplementRegisterInfoActivity extends BaseInquireActivity {
    /**
     * 常量
     */

    public static final String PHONE_NUM_KEY = "phoneNum";
    public static final String REGISTER_TAG = "isFromRegister";

    private static final int SET_COMPANY_RESULT_CODE = 1;
    private static final int SET_JOB_RESULT_CODE = 2;
    public static final int SET_PHONE_NUM = 30;

    /**
     * 组件
     */
    private TitleView titleView;
    private EditText inputPwdEdit, my_nameEdit;
    private ViewGroup phone_number_bind_layout;
    private TextView my_company_Text, my_job_Text, my_BindphoneNumText, bindText;
    private SimpleDraweeView headImageView;

    /**
     * 变量
     */
    private String phoneNum = "";
    private boolean isFromRegister = false;
    private String currentPath;
    private boolean isHasPwd = false;//是否已经设置过密码

    private String nickName = "", password = "", companyName = "",
            companyId = "", job = "", jobId = "", headPhoneUrl = "";

    /**
     * 对象
     */
    private PictureUtil pictureUtil;

    @Override
    protected void initData() {
        super.initData();
        phoneNum = getIntent().getStringExtra(PHONE_NUM_KEY);
        isFromRegister = getIntent().getBooleanExtra(REGISTER_TAG, false);
        pictureUtil = new PictureUtil(mContext);
        pictureUtil.setType(true);

        // 设置 图片裁剪 大小，如果 x y 一样大，则是等比。
        pictureUtil.setASPECT_X(400);
        pictureUtil.setASPECT_Y(400);
//        mSwipeBackLayout.setEnableGesture(true);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_supplement);
        titleView = (TitleView) findViewById(R.id.titleView);
//        titleView.setLeftImage(R.mipmap.back_image);
//        if(isFromRegister){
//            titleView.setTitle("注册");
//        }else{
        titleView.setTitle("补充信息");
//        }

        my_BindphoneNumText = (TextView) findViewById(R.id.my_BindphoneNumText);
        bindText = (TextView) findViewById(R.id.bindText);

        phone_number_bind_layout = (ViewGroup) findViewById(R.id.phone_number_bind_layout);

        inputPwdEdit = (EditText) findViewById(R.id.my_inputPwdText);
        my_nameEdit = (EditText) findViewById(R.id.my_nameText);
        my_company_Text = (TextView) findViewById(R.id.my_company_Text);
        my_job_Text = (TextView) findViewById(R.id.my_job_Text);
        headImageView = (SimpleDraweeView) findViewById(R.id.headImageView);
    }

    @Override
    protected void initWidgetActions() {
        Log.d("TAG", "phoneNum:::" + phoneNum);
        if (!TextUtils.isEmpty(phoneNum)) {
            my_BindphoneNumText.setText(phoneNum + "");
            bindText.setText("已绑定");
            phone_number_bind_layout.setEnabled(false);
        } else {
            my_BindphoneNumText.setText("请绑定您的手机号");
            bindText.setText("去绑定");
            phone_number_bind_layout.setEnabled(true);
        }
        phone_number_bind_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterPhoneActivity.class);
                intent.putExtra(RegisterPhoneActivity.REGISTER_TAG, false);
                startActivityForResult(intent, SET_PHONE_NUM);
            }
        });

        my_company_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SetCompanyActivity.class);
                intent.putExtra(SetCompanyActivity.COMPANY_KEY, companyName);
                startActivityForResult(intent, SET_COMPANY_RESULT_CODE);
            }
        });
        my_job_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SetJobActivity.class);
                intent.putExtra(SetJobActivity.TYPE, SetJobActivity.TYPE_JOB_VALUE);
                startActivityForResult(intent, SET_JOB_RESULT_CODE);
            }
        });
        findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFromRegister) {
                    Log.d("TAG", "这是注册");
                    register();
                } else {
                    Log.d("TAG", "这是修改信息");
                    updateUserInfo();
                }
            }
        });

        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPath = LibIOUtil.getUploadCameraPath(mContext, System.currentTimeMillis() + "");
                pictureUtil.showImageClickDailog(currentPath);
            }
        });

        setAlreadExistingUserInfo();
    }

    /**
     * 完善信息时，将已有信息带过来
     */
    private void setAlreadExistingUserInfo() {
        if (!isFromRegister) {
            UserInfoModel model = LoginSharePreference.getUserInfo(mContext);
            if (model == null) {
                return;
            }
            isHasPwd = model.issetpwd();
            if (model != null && !TextUtils.isEmpty(model.getMobile())) {
                isHasPwd = true;
            } else {
                isHasPwd = false;
            }

            if (isHasPwd) {
                String hasPwd = "*************************************************";
                inputPwdEdit.setHint(hasPwd);
            } else {
                inputPwdEdit.setHint("请输入密码");
            }

            if (!TextUtils.isEmpty(model.getNickname())) {
                nickName = model.getNickname();
                my_nameEdit.setText(nickName);
                my_nameEdit.setSelection(nickName.length());
            } else {
                nickName = "";
            }

            if (!TextUtils.isEmpty(model.getCompany())) {
                companyName = model.getCompany();
                my_company_Text.setText(companyName);
            } else {
                companyName = "";
            }
            if (!TextUtils.isEmpty(model.getCompanyid())) {
                companyId = model.getCompanyid();
            } else {
                companyId = "";
            }
            if (!TextUtils.isEmpty(model.getJob())) {
                job = model.getJob();
                my_job_Text.setText(job);
            } else {
                job = "";
            }
            if (!TextUtils.isEmpty(model.getJobid())) {
                jobId = model.getJobid();
            } else {
                jobId = "";
            }

            if (!TextUtils.isEmpty(model.getPhoto())) {
                headImageView.setImageURI(Uri.parse(model.getPhoto().toString()));
            }
        }
    }

    /**
     * 注册
     */
    private void register() {
        nickName = my_nameEdit.getText().toString().trim();
        password = inputPwdEdit.getText().toString().trim();

        if (TextUtils.isEmpty(nickName)) {
            showToast("请输入姓名");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("phonenumber", phoneNum);
        map.put("nickname", nickName);
        map.put("password", Md5Util.getMD5Str(password));
        map.put("companyid", companyId);
        map.put("company", companyName);
        map.put("photo", headPhoneUrl);
        map.put("job", job);
        map.put("jobid", jobId);
        showLoading();
        LoginRegisterHelper.doNetPOSTToRegister(mContext, map, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                LoginModel model = (LoginModel) data;
                if (model.getResult() == 0) {
                    EventUtils.event(mContext, EventUtils.REGISTERED);

                    LastLoginSharePreference.saveUserAccount(phoneNum, mContext);
                    LoginSharePreference.saveUserInfo(model.getUserinfo(), mContext);
                    InquireApplication.setUserInfo(model.getUserinfo());
                    EventBus.getDefault().post(new CompleteLoginEvent(LoginConstant.REGISTER));
                    EventBus.getDefault().post(new RefreshHomeEvent());
                    finish();
                } else {
                    showToast(model.getMsg() + "");
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(R.string.net_error);
            }
        });

    }

    /**
     * 修改个人信息
     */
    private void updateUserInfo() {
        nickName = my_nameEdit.getText().toString().trim();
        password = inputPwdEdit.getText().toString().trim();

        if (TextUtils.isEmpty(nickName)) {
            showToast("请输入姓名");
            return;
        }

        if (!TextUtils.isEmpty(password)) {
            if (password.length() > 0 && password.length() <= 50) {
                if (TextUtils.isEmpty(phoneNum)) {
                    showToast("请绑定手机号");
                    return;
                }
            } else {
                showToast("请输入密码长度在6-50之间");
                return;
            }
        }

        if (!TextUtils.isEmpty(phoneNum)) {
            if (!isHasPwd) {
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
            }
        }

        HashMap<String, String> map = new HashMap<>();

        map.put("userid", InquireApplication.getUserInfo().getUserid() + "");
        map.put("phonenumber", phoneNum);
        map.put("nickname", nickName);
        if (isHasPwd) {
            if (TextUtils.isEmpty(password)) {
                map.put("password", "");
            } else {
                map.put("password", Md5Util.getMD5Str(password));
            }
        } else {
            if (TextUtils.isEmpty(password)) {
                map.put("password", "");
            } else {
                map.put("password", Md5Util.getMD5Str(password));
            }
        }
        map.put("companyid", companyId);
        map.put("companyname", companyName);
        map.put("photo", headPhoneUrl);
        map.put("job", job);
        map.put("jobid", jobId);
        showLoading();
        LoginRegisterHelper.doNetPostUpdateUserInfo(mContext, map, getLocalClassName(), new NetWorkCallBack() {
            @Override
            public void onSuccess(Object data) {
                hideLoadDialog();
                LoginModel model = (LoginModel) data;
                if (model.getResult() == 0) {
                    LoginSharePreference.saveUserInfo(model.getUserinfo(), mContext);
                    InquireApplication.setUserInfo(model.getUserinfo());
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    showToast(model.getMsg() + "");
                }
            }

            @Override
            public void onFail(String error) {
                hideLoadDialog();
                showToast(R.string.net_error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTION_APPLICATION_DETAILS_SETTINGS_CODE){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SupplementRegisterInfoActivityPermissionsDispatcher.getPermissionWithPermissionCheck(SupplementRegisterInfoActivity.this);
                }
            }, 300);
            return;
        }
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case SET_COMPANY_RESULT_CODE:
                companyId = data.getStringExtra("companyid");
                companyName = data.getStringExtra("companyname");
                my_company_Text.setText(companyName);
                break;
            case SET_JOB_RESULT_CODE:
                jobId = data.getStringExtra("jobid");
                job = data.getStringExtra("jobname");

                Log.d("TAG", "jobId:::" + jobId + "jobName::" + job);
                my_job_Text.setText(job);
                break;
            case SET_PHONE_NUM:
                phoneNum = data.getStringExtra(PHONE_NUM_KEY);
                my_BindphoneNumText.setText(phoneNum + "");
                bindText.setText("已绑定");
                phone_number_bind_layout.setEnabled(false);

                break;
            case PictureUtil.REQUEST_CODE_GALLERY:
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(currentPath);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    pictureUtil.startCropImage(currentPath);
                } catch (Exception e) {
                    Log.e(TAG, "Error while creating temp file", e);
                }
                break;
            case PictureUtil.REQUEST_CODE_TAKE_PICTURE:
                pictureUtil.startCropImage(currentPath);
                break;
            case PictureUtil.REQUEST_CODE_CROP_IMAGE:
                String path = data.getStringExtra(CropImage.IMAGE_PATH);
                if (path == null) {
                    return;
                }
                // TODO 未完成，等待服务器端，
                headPhoneUrl = data.getStringExtra(CropImageViewAndUpload.IMAGE_NET_URL);
                Log.d("TAG", "上传头像的headPhoneUrl" + headPhoneUrl);
//              Bitmap bitmap = ImageUtil.getBitmapFromMedia(this, currentPath);
//              headImageView.setImageBitmap(bitmap);
                headImageView.setImageURI(Uri.parse("file://" + currentPath));
                break;

            default:
                break;
        }
    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    private String path;

    @NeedsPermission(Manifest.permission.CAMERA)
    void getPermission() {
        if (isPermissionDenied(this, AppOpsManager.OPSTR_CAMERA)) {
            showPermissionCameraDialog(this);
            return;
        }
        AppUtil.takePhoto(this, path);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationale(final PermissionRequest request) {
        showPermissionCameraDialog(this, request);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDenied() {
        showPermissionCameraDialog(this);
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAsk() {
        showPermissionCameraDialog(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SupplementRegisterInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onEvent(IEvent event) {
        if (event instanceof TakePhotoEvent) {
            path = ((TakePhotoEvent) event).path;
            SupplementRegisterInfoActivityPermissionsDispatcher.getPermissionWithPermissionCheck(this);
        }
    }
}
