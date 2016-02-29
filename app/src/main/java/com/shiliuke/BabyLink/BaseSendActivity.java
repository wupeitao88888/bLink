package com.shiliuke.BabyLink;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.shiliuke.BabyLink.chooseimage.CutImageActivity;
import com.shiliuke.BabyLink.chooseimage.ImageUtil;
import com.shiliuke.BabyLink.chooseimage.upload.FormFile;
import com.shiliuke.BabyLink.chooseimage.upload.SocketHttpRequester;
import com.shiliuke.adapter.ChooseImageAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.utils.AppUtil;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.view.stickerview.StickerExecutor;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by wangzhi on 15/12/2.
 */
public abstract class BaseSendActivity extends ActivitySupport {

    public static final String SENDSHOWSUCCESS = "com.send.show.success";
    public static final String SENDCHANGESUCCESS = "com.send.change.success";
    public static final String SENDTOPICSUCCESS = "com.send.topic.success";
    public static final String SENDISSUESUCCESS = "com.send.issue.success";


    public boolean isFang = true;
    public static int imageNum = 1;
    public String mImageKey;
    private Handler handler;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    public ArrayList<String> mImageKeyList;
    private ChooseImageAdapter mChooseImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void intBase(boolean isFang, int imageNum, Handler hd) {
        this.isFang = isFang;
        this.imageNum = imageNum;
        this.handler = hd;
        mImageKeyList = new ArrayList<>();
        mChooseImageAdapter = new ChooseImageAdapter(this, mImageKeyList);
        getGridView().setAdapter(mChooseImageAdapter);
        getGridView().setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            AppUtil.closeSoftInput(this);
            if (arg2 == mImageKeyList.size()) {
                ll_popup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.activity_translate_in));
                pop.showAtLocation(getParentView(), Gravity.BOTTOM, 0, 0);
            } else {
                showAskDialog(arg2);
            }
        });
//        getGridView().setOnItemLongClickListener((parent, view, position, id) -> {
//            return true;
//        });
        initPop();
    }

    public void uploadFiles() {
        DialogUtil.startDialogLoading(this, false);
        if (mImageKeyList.isEmpty()) {
            handler.sendMessage(handler.obtainMessage(SocketHttpRequester.SUCCESS, ""));
            return;
        }
        final FormFile[] files = new FormFile[mImageKeyList.size()];
        FormFile file;
        for (int i = 0; i < mImageKeyList.size(); i++) {
            file = new FormFile(mImageKeyList.get(i), ImageUtil.getPathForUpload(mImageKeyList.get(i)), mImageKeyList.get(i), "image/*");
            files[i] = file;
        }
        final String path = "http://101.200.174.65/babyLink/mobile.php/Mobile/Index/uploadFile";
        StickerExecutor.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketHttpRequester.post(path, null, files, handler);
                } catch (Exception e) {
                    handler.sendEmptyMessage(SocketHttpRequester.EXCEPTION);
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAskDialog(int position) {
        new AlertDialog.Builder(this).setTitle("删除选中图片？")//设置对话框标题
//                .setMessage("删除选中图片？")//设置显示的内容
                .setPositiveButton("确定", (dialog, which) -> {//确定按钮的响应事件
                    mImageKeyList.remove(position);
                    mChooseImageAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> {//确定按钮的响应事件
                    dialog.dismiss();
                }).show();//在按键响应事件中显示此对话框
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

    ;

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
        intent.putExtra("isfang", isFang);
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
                String resultPath = ImageUtil.saveBitmap(resultBmp, String.valueOf(System.currentTimeMillis()+".jpg"));
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
                intent.putExtra("isfang", isFang);
                startActivityForResult(intent, ImageUtil.PICK_FROM_GALLERY_CROP);
                break;
            case ImageUtil.PICK_FROM_GALLERY_CROP:
                mImageKeyList.add(data.getStringExtra("key"));
                mChooseImageAdapter.notifyDataSetChanged();
                break;
        }
    }

    public abstract GridView getGridView();

    public abstract View getParentView();
}
