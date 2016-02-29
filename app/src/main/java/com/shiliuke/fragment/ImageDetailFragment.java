package com.shiliuke.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.R;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.photoview.PhotoView;
import com.shiliuke.view.photoview.PhotoViewAttacher;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private PhotoView mImageView;
    private PhotoViewAttacher mAttacher;
    private ProgressBar progressBar;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private View v;
    @Override
    public void onResume() {
        super.onResume();
        // FT = true;
        // L.e(TAG, "onResume");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (PhotoView) v.findViewById(R.id.image);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mAttacher = new PhotoViewAttacher(mImageView);
        progressBar.setVisibility(View.VISIBLE);
        initPop();
        Glide.with(getActivity()).load(mImageUrl).placeholder(R.color.gray).error(R.color.gray).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                progressBar.setVisibility(View.GONE);
                ToastUtil.showShort(getContext(), "加载失败！");
                L.e("加载失败！");
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                progressBar.setVisibility(View.GONE);
                L.e("加载成功！");
                return false;
            }
        }).into(mImageView);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });

        mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ll_popup.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.activity_translate_in));
                pop.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                return false;
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //创建Handler
    Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            DialogUtil.stopDialogLoading(getContext());
//            showToast(msg.obj.toString());
            Bundle bd = (Bundle) msg.obj;
//            showShare(bd.getString("net"),bd.getString("localtion"));
            ToastUtil.showShort(getContext(), "图片保存到" + bd.getString("localtion"));
        }
    };

    public void initPop() {
        pop = new PopupWindow(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_popupwindows_button, null);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        Button save_bt = (Button) view.findViewById(R.id.save_bt);
        Button cancel_bt = (Button) view.findViewById(R.id.cancel_bt);
        save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadImage(mImageUrl);
            }
        });
        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
    }

    public void downLoadImage(final String urlPath) {

        if (TextUtils.isEmpty(urlPath)) {
            ToastUtil.showShort(getContext(), "url异常");
            return;
        }
//        AbDialogUtil.showProgressDialog(mActivity, 0, "正在分享，稍等哒！");
        final String pname = urlPath.substring(urlPath.lastIndexOf("/") + 1, urlPath.lastIndexOf("."));
        new Thread() {
            public void run() {
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(6 * 1000);  // 注意要设置超时，设置时间不要超过10秒，避免被android系统回收
                    if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");
                    InputStream inSream = conn.getInputStream();
                    String path = Environment.getExternalStorageDirectory() + "/Photo_LJ/";
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    //把图片保存到项目的根目录
                    readAsFile(inSream, new File(path + pname + ".jpg"));
                    Message msg = new Message();
                    msg.what = 0;
                    Bundle bd = new Bundle();
                    bd.putString("net", urlPath);
                    bd.putString("localtion", path + pname + ".jpg");
                    msg.obj = bd;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    ToastUtil.showToastInThread(getContext(), "分享失败");
                }
            }
        }.start();
    }

    public void readAsFile(InputStream inSream, File file) throws Exception {
        FileOutputStream outStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inSream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inSream.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(v);
    }
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
