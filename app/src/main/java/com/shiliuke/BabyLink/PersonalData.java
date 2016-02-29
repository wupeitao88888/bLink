package com.shiliuke.BabyLink;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.chooseimage.CutImageActivity;
import com.shiliuke.BabyLink.chooseimage.ImageUtil;
import com.shiliuke.BabyLink.chooseimage.upload.FormFile;
import com.shiliuke.BabyLink.chooseimage.upload.SocketHttpRequester;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.global.AppConfig;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.GlideCircleTransform;
import com.shiliuke.view.stickerview.StickerExecutor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人资料
 * Created by wupeitao on 15/11/10.
 */
public class PersonalData extends ActivitySupport implements View.OnClickListener {

    private RelativeLayout handpic_re,//用户头像
            nickname_re,//用户昵称
            updatePnumber_re,//修改手机号
            updatePW_re;//修改密码
    private ImageView handpic;
    private TextView nickname;
    private Button logout;
    private RelativeLayout updateAear_link_re;
    private RelativeLayout updateAear_re;
    private int AEAR_RESULT_CODE = 10009;
    private int LINK_AEAR_RESULT_CODE = 10010;
    private int UPDATE_NAME_RESULT_CODE = 10011;
    private TextView updateAear_text;
    private EditText updateAear_link_text;

    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    public String mImageKey;
    private View view;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogUtil.stopDialogLoading(PersonalData.this);
            switch (msg.what) {
                case SocketHttpRequester.SUCCESS:
                    AppConfig.loginInfo.setMember_avar(msg.obj+"");
                    sharedPreferencesHelper.putValue("member_avar", msg.obj + "");
                    Glide.with(context)
                            .load(AppConfig.loginInfo.getMember_avar())
                            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(context))
                            .error(R.mipmap.morentoux)
                            .crossFade()
                            .into(handpic);
//                            .placeholder(R.mipmap.morentoux)
                    showToast("修改成功");
                    break;
                case SocketHttpRequester.ERROR:
                    showToast(msg.obj.toString());
                    break;
                case SocketHttpRequester.EXCEPTION:
                    showToast("上传头像失败");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.layout_userinfo, null);
        setContentView(R.layout.layout_userinfo);
        initView();
        initPop();
    }

    private void initView() {
        handpic_re = (RelativeLayout) findViewById(R.id.handpic_re);
        nickname_re = (RelativeLayout) findViewById(R.id.nickname_re);
        updatePnumber_re = (RelativeLayout) findViewById(R.id.updatePnumber_re);
        updateAear_link_re = (RelativeLayout) findViewById(R.id.updateAear_link_re);
        updateAear_re = (RelativeLayout) findViewById(R.id.updateAear_re);
        updatePW_re = (RelativeLayout) findViewById(R.id.updatePW_re);
        handpic = (ImageView) findViewById(R.id.handpic);
        nickname = (TextView) findViewById(R.id.nickname);
        updateAear_text = (TextView) findViewById(R.id.updateAear_text);
        updateAear_link_text = (EditText) findViewById(R.id.updateAear_link_text);
        logout = (Button) findViewById(R.id.logout);
        setCtenterTitle("账号设置");
        nickname.setText(sharedPreferencesHelper.getValue("member_name"));
        Glide.with(context)
                .load(AppConfig.loginInfo.getMember_avar())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.morentoux)
                .error(R.mipmap.morentoux)
                .crossFade()
                .into(handpic);
        updateAear_text.setText("小区：" + sharedPreferencesHelper.getValue("home"));
        updateAear_link_text.setText("附属小区："+sharedPreferencesHelper.getValue("home2"));
        handpic_re.setOnClickListener(this);
        nickname_re.setOnClickListener(this);
        updatePnumber_re.setOnClickListener(this);
        updatePW_re.setOnClickListener(this);
        updateAear_link_re.setOnClickListener(this);
        updateAear_re.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.handpic_re:
                ll_popup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.activity_translate_in));
                pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.nickname_re:
                Intent name_intent = new Intent(this, UpdateNameActivity.class);
                startActivityForResult(name_intent, UPDATE_NAME_RESULT_CODE);
                break;
            case R.id.updatePnumber_re:
                mIntent(context, UpdatePnumberActivity.class);
                break;
            case R.id.updatePW_re:
                mIntent(context, UpdatePWActivity.class);
                break;
            case R.id.updateAear_link_re:
                Intent intent = new Intent(this, LocationAreaActivity.class);
                intent.putExtra("tag_id", "link");
                startActivityForResult(intent, LINK_AEAR_RESULT_CODE);
                break;
            case R.id.updateAear_re:
                Intent aear_intent = new Intent(this, LocationAreaActivity.class);
                aear_intent.putExtra("tag_id", "update");
                startActivityForResult(aear_intent, AEAR_RESULT_CODE);
                break;
            case R.id.logout:
                sharedPreferencesHelper.putBoolean("islogin", false);
                mIntent(context, LoginActivity.class);
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    private void initPop() {
        pop = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(v -> {
            pop.dismiss();
            ll_popup.clearAnimation();
        });
        bt1.setOnClickListener(v -> {
            takePhotoByCamera();
            pop.dismiss();
            ll_popup.clearAnimation();
        });
        bt2.setOnClickListener(v -> {
            takePhotoByGallery();
            pop.dismiss();
            ll_popup.clearAnimation();
        });
        bt3.setOnClickListener(v -> {
            pop.dismiss();
            ll_popup.clearAnimation();
        });
    }

    public void takePhotoByGallery() {
//        mImageKey = String.valueOf(System.currentTimeMillis() + ".jpg");
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", imagewidth);
//        intent.putExtra("aspectY", imageheight);
//        /**********************/
//        intent.putExtra("outputX", imagewidth);
//        intent.putExtra("outputY", imageheight);
//        /**********************/
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(ImageUtil.getPathForUpload(mImageKey)));
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        try {
            startActivityForResult(intent, ImageUtil.PICK_FROM_GALLERY);
        } catch (ActivityNotFoundException e) {
//            MMAlert.showToast(getActivity(), "没有找到相关处理程序");
        }
    }

    public void takePhotoByCamera() {
        mImageKey = String.valueOf(System.currentTimeMillis() + ".jpg");
        Intent cameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent
                .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(ImageUtil.getPathForCameraCrop(mImageKey)));
        cameraIntent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(cameraIntent, ImageUtil.PICK_FROM_CAMERA);
    }


    public void startCropWithOrientation() {
        File src = ImageUtil.getPathForUpload(mImageKey);
        int orientation = ImageUtil.getExifOrientation(src.getAbsolutePath());
        int rotate = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            rotate = 90;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            rotate = 180;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            rotate = 270;
        }
//        if (rotate != 0) {
        //矫正
//            redressImage(src, rotate);
//        } else {
        startCrop(src);
//        }
    }

    public void startCrop(File dest) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(Uri.fromFile(src), "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("outputX", imagewidth);
//        intent.putExtra("outputY", imageheight);
//        intent.putExtra("aspectX", imagewidth);
//        intent.putExtra("aspectY", imageheight);
//        intent.putExtra("scale", true);
//        intent.putExtra("scaleUpIfNeeded", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(dest));
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        startActivityForResult(intent, ImageUtil.PICK_FROM_GALLERY);
        Intent intent = new Intent(this, CutImageActivity.class);
        intent.putExtra("url", dest.getAbsolutePath());
        intent.putExtra("isfang", true);
        startActivityForResult(intent, ImageUtil.PICK_FROM_GALLERY_CROP);
    }

    public void redressImage(final File src, final int rotate) {
        //将原始图片角度矫正
//        showAnimLoading("", false, false, false);
        new Thread(() -> {
            Bitmap bmp = ImageUtil.getBitmap(src.getAbsolutePath(), this);
            if (bmp == null) {
                cropOnUIThread(src.getAbsolutePath());
            } else {
                Bitmap resultBmp = ImageUtil.rotateImage(bmp, rotate);
                String resultPath = ImageUtil.saveBitmap(resultBmp, String.valueOf(System.currentTimeMillis() + ".jpg"));
                cropOnUIThread(resultPath);
                if (resultBmp != null && !resultBmp.isRecycled()) resultBmp.recycle();
            }
            if (bmp != null && !bmp.isRecycled()) bmp.recycle();
        }).start();
    }

    private void cropOnUIThread(final String resultPath) {
        handler.post(() -> {
//            dismissAnimLoading();
            startCrop(ImageUtil.getPathForUpload(resultPath));
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AEAR_RESULT_CODE) {
            try {
                updateAear_text.setText("小区："+data.getExtras().getString("area_text"));
            } catch (Exception e) {
                e.printStackTrace();
                updateAear_text.setText("小区："+updateAear_text.getText().toString());
            }
        } else if (requestCode == LINK_AEAR_RESULT_CODE) {
            try {
                updateAear_link_text.setText("附属小区："+data.getExtras().getString("area_text"));
            } catch (Exception e) {
                e.printStackTrace();
                updateAear_link_text.setText("附属小区："+updateAear_link_text.getText().toString());
            }
        } else if (requestCode == UPDATE_NAME_RESULT_CODE) {
            try {
                nickname.setText(data.getExtras().getString("name_text"));
            } catch (Exception e) {
                e.printStackTrace();
                nickname.setText(nickname.getText().toString());
            }
        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ImageUtil.PICK_FROM_CAMERA:
                startCropWithOrientation();
                break;

            case ImageUtil.PICK_FROM_GALLERY:
                Intent intent = new Intent(this, CutImageActivity.class);
                intent.putExtra("url", data.getDataString());
                intent.putExtra("isfang", true);
                startActivityForResult(intent, ImageUtil.PICK_FROM_GALLERY_CROP);
                break;
            case ImageUtil.PICK_FROM_GALLERY_CROP:
                mImageKey = data.getStringExtra("key");
                uploadFiles();
                break;
        }
    }

    public void uploadFiles() {
        DialogUtil.startDialogLoading(this, false);
        FormFile file = new FormFile(mImageKey, ImageUtil.getPathForUpload(mImageKey), mImageKey, "image/*");
        final String path = "http://101.200.174.65/babyLink/mobile.php/Mobile/Member/uploadHead";
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        StickerExecutor.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketHttpRequester.post(path, params, file, handler);
                } catch (Exception e) {
                    handler.sendEmptyMessage(SocketHttpRequester.EXCEPTION);
                    e.printStackTrace();
                }
            }
        });
    }
}
