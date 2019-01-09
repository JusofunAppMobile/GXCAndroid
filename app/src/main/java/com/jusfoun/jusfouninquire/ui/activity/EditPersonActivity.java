package com.jusfoun.jusfouninquire.ui.activity;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jusfoun.jusfouninquire.InquireApplication;
import com.jusfoun.jusfouninquire.R;
import com.jusfoun.jusfouninquire.net.callback.NetWorkCallBack;
import com.jusfoun.jusfouninquire.net.model.LoginModel;
import com.jusfoun.jusfouninquire.net.model.UserInfoModel;
import com.jusfoun.jusfouninquire.net.route.LoginRegisterHelper;
import com.jusfoun.jusfouninquire.service.event.IEvent;
import com.jusfoun.jusfouninquire.sharedpreference.LoginSharePreference;
import com.jusfoun.jusfouninquire.ui.util.AppUtil;
import com.jusfoun.jusfouninquire.ui.util.PictureUtil;
import com.jusfoun.jusfouninquire.ui.util.RegularUtils;
import com.jusfoun.jusfouninquire.ui.util.crawl.TakePhotoEvent;
import com.jusfoun.jusfouninquire.ui.view.PersonInfoView;
import com.jusfoun.jusfouninquire.ui.view.TitleView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

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
 * Author  wangchenchen
 * CreateDate 2016/8/12.
 * Email wcc@jusfoun.com
 * Description 修改个人信息
 */
@RuntimePermissions
public class EditPersonActivity extends BaseInquireActivity {
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
    private SimpleDraweeView headImageView;
    private PersonInfoView company, userjob, phone, sex;
    private RelativeLayout head_layout;
    private EditText username;

    /**
     * 变量
     */
    private String phoneNum = "";
    private boolean isFromRegister = false;
    private String currentPath;
    private String nickName = "", password = "", companyName = "",
            companyId = "", job = "", jobId = "", headPhoneUrl = "";

    /**
     * 对象
     */
    private PictureUtil pictureUtil;

    private TitleView titleView;

    @Override
    protected void initData() {
        super.initData();
        phoneNum = getIntent().getStringExtra(PHONE_NUM_KEY);
        isFromRegister = false;
        pictureUtil = new PictureUtil(mContext);
        pictureUtil.setType(true);

        // 设置 图片裁剪 大小，如果 x y 一样大，则是等比。
        pictureUtil.setASPECT_X(400);
        pictureUtil.setASPECT_Y(400);
//        mSwipeBackLayout.setEnableGesture(true);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_edit_person);
        phone = (PersonInfoView) findViewById(R.id.phone);
        userjob = (PersonInfoView) findViewById(R.id.job);
        company = (PersonInfoView) findViewById(R.id.company);
        sex = (PersonInfoView) findViewById(R.id.sex);
        titleView = (TitleView) findViewById(R.id.titleView);

        head_layout = (RelativeLayout) findViewById(R.id.head_layout);
        username = (EditText) findViewById(R.id.username);
        headImageView = (SimpleDraweeView) findViewById(R.id.headImageView);

        titleView.setTitle("修改资料");
        titleView.setRightText("完成");
        titleView.setRightClickListener(new TitleView.OnRightClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
    }

    @Override
    protected void initWidgetActions() {
        Log.d("TAG", "phoneNum:::" + phoneNum);
        if (!TextUtils.isEmpty(phoneNum)) {
            phone.setContentTxt(phoneNum + "");
        }

     /*   Editable editable=username.getEditableText();
        Selection.setSelection(editable,editable.length());*/

        head_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPath = LibIOUtil.getUploadCameraPath(mContext, System.currentTimeMillis() + "");
                pictureUtil.showImageClickDailog(currentPath);
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 10)
                    showToast("最多输入10个字符");
            }
        });
        username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                    return true;
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    if (TextUtils.isEmpty(username.getText())) {
                        showToast("昵称不能为空");
                        return true;
                    }
                    if (RegularUtils.checkChinese(username.getText().toString())) {
                        if (username.getText().length() > 5)
                            showToast("最多输入五个字符");
                        else {
                            nickName = username.getText().toString();
                        }
                    }
                    nickName = username.getText().toString();
                    return true;
                }
                return false;
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UpdateNumberActivity.class);
                startActivityForResult(intent, SET_PHONE_NUM);
            }
        });

        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SetCompanyActivity.class);
                intent.putExtra(SetCompanyActivity.COMPANY_KEY, companyName);
                startActivityForResult(intent, SET_COMPANY_RESULT_CODE);
            }
        });
        userjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SetJobActivity.class);
                intent.putExtra(SetJobActivity.TYPE, SetJobActivity.TYPE_JOB_VALUE);
                startActivityForResult(intent, SET_JOB_RESULT_CODE);
            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setCursorVisible(true);
            }
        });

        setAlreadExistingUserInfo();
    }

    /**
     * 将已有信息带过来
     */
    private void setAlreadExistingUserInfo() {
        UserInfoModel model = LoginSharePreference.getUserInfo(mContext);
        if (model == null) {
            return;
        }

        if (!TextUtils.isEmpty(model.getNickname())) {
            username.setText(model.getNickname());
            username.setSelection(username.getEditableText().length());
            nickName = model.getNickname();
            username.setCursorVisible(false);
        } else {
            nickName = "";
        }

        if (!TextUtils.isEmpty(model.getCompany())) {
            companyName = model.getCompany();
            company.setContentTxt(companyName);
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
            userjob.setContentTxt(job);
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

        if (!TextUtils.isEmpty(model.getMobile())) {
            phone.setContentTxt(model.getMobile());
            phoneNum = model.getMobile();
        } else {
            phoneNum = "";
        }
    }

    /**
     * 修改个人信息
     */
    private void updateUserInfo() {

        if (TextUtils.isEmpty(username.getText())) {
            showToast("昵称不能为空");
            return;
        }
        if (RegularUtils.checkChinese(username.getText().toString())) {
            if (username.getText().length() > 5)
                showToast("最多输入五个字符");
            else {
                nickName = username.getText().toString();
            }
        }
        nickName = username.getText().toString();

        HashMap<String, String> map = new HashMap<>();
        map.put("userid", InquireApplication.getUserInfo().getUserid() + "");
        map.put("phonenumber", phoneNum);
        map.put("nickname", nickName);
        map.put("password", "");
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
                    EditPersonActivityPermissionsDispatcher.getPermissionWithPermissionCheck(EditPersonActivity.this);
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
                company.setContentTxt(companyName);
                break;
            case SET_JOB_RESULT_CODE:
                jobId = data.getStringExtra("jobid");
                job = data.getStringExtra("jobname");

                Log.d("TAG", "jobId:::" + jobId + "jobName::" + job);
                userjob.setContentTxt(job);
                break;
            case SET_PHONE_NUM:
                phoneNum = data.getStringExtra(PHONE_NUM_KEY);
                phone.setContentTxt(phoneNum + "");
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

        EditPersonActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onEvent(IEvent event) {
        if (event instanceof TakePhotoEvent) {
            path = ((TakePhotoEvent) event).path;
            EditPersonActivityPermissionsDispatcher.getPermissionWithPermissionCheck(this);
        }
    }
}
