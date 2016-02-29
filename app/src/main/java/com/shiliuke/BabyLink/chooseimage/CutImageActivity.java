package com.shiliuke.BabyLink.chooseimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.shiliuke.BabyLink.R;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.utils.L;
import com.shiliuke.view.photoview.PhotoView;

public class CutImageActivity extends ActivitySupport {

    private RelativeLayout layout_cut_image_fang;
    private RelativeLayout layout_cut_image_chang;
    private PhotoView cut_image_fang;
    private PhotoView cut_image_chang;
    private String mImageUrl;
    private boolean isFang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_image);
        initData();
        initView();
    }

    private void initData() {
        isFang = getIntent().getBooleanExtra("isfang", true);
        mImageUrl = getIntent().getStringExtra("url");
        setCtenterTitle("裁剪");
        setRightTitle("完成");
        setRightTitleListener(v -> {
            String key = String.valueOf(System.currentTimeMillis() + ".jpg");
            Bitmap bit;
            if (isFang) {
                bit = cut_image_fang.getVisibleRectangleBitmap();
            } else {
                bit = cut_image_chang.getVisibleRectangleBitmap();
            }
            ImageUtil.saveBitmap(bit, key);
            Intent intent = new Intent();
            intent.putExtra("key", key);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void initView() {
        layout_cut_image_fang = (RelativeLayout) findViewById(R.id.layout_cut_image_fang);
        layout_cut_image_chang = (RelativeLayout) findViewById(R.id.layout_cut_image_chang);

        cut_image_fang = (PhotoView) findViewById(R.id.cut_image_fang);
        cut_image_chang = (PhotoView) findViewById(R.id.cut_image_chang);

        if (isFang) {
            layout_cut_image_fang.setVisibility(ImageView.VISIBLE);
            cut_image_fang.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(this).load(mImageUrl).into(cut_image_fang);
        } else {
            layout_cut_image_chang.setVisibility(ImageView.VISIBLE);
            cut_image_chang.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Glide.with(this).load(mImageUrl).into(cut_image_chang);
        }
    }

}
