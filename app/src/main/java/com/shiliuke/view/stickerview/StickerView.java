package com.shiliuke.view.stickerview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.BeanShowModelResult;
import com.shiliuke.global.AppConfig;
import com.shiliuke.utils.GlideCircleTransform;

/**
 * Created by wangzhi on 15/11/19.
 */
public class StickerView extends RelativeLayout {
    private Context mContext;
    private BeanShowModelResult beanShowModel;
    private StickerImageModel compileModel;//正在编辑的贴纸
    private View complieView;
    private boolean isCurrentClick = false;
    private float tmpX = 0, tmpy = 0;

    public StickerView(Context context) {
        super(context);
        mContext = context;
        setWillNotDraw(false);
    }

    public StickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setWillNotDraw(false);
    }

    public StickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setWillNotDraw(false);
    }

    public void setModel(BeanShowModelResult model) {
        beanShowModel = model;
//        L.d("setModel");
        initView();
    }

    private void initView() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).clearAnimation();
        }
        removeAllViews();
        for (int i = 0; i < beanShowModel.getCommend_list().size(); i++) {
            StickerImageModel model = beanShowModel.getCommend_list().get(i);
            View v = LayoutInflater.from(mContext).inflate(R.layout.layout_sticker_item, null);
            v.setX(toModelWidth(model.getX()));
            v.setY(toModelHeight(model.getY()));

            TextView tv = (TextView) v.findViewById(R.id.tv_sticker_item);
            int a = (int) (getWidth() - toModelWidth(model.getX()) - 50);
            tv.setMaxWidth(a);
            tv.setText(model.getInfo());
            ImageView iv = (ImageView) v.findViewById(R.id.image_sticker_item);
            Glide.with(mContext).load(model.getMember_avar()).transform(new GlideCircleTransform(mContext)).into(iv);
            addView(v);
        }
        if (beanShowModel.getCommend_list().isEmpty()) {
            return;
        }
        startAnim();
    }

    /**
     * 关闭在执行的“动画”
     */
    public void stopAnim() {
//        beanShowModel.setCanAnim(false);
//        beanShowModel.setIsAniming(false);
        if (beanShowModel == null || beanShowModel.getCommend_list() == null || beanShowModel.getCommend_list().isEmpty()) {
            return;
        }
        for (int i = 0; i < beanShowModel.getCommend_list().size(); i++) {
            getChildAt(i).clearAnimation();
        }
    }

    Handler hd = new Handler();

    /**
     * 开始”动画"
     */
    public void startAnim() {
        if (beanShowModel == null || beanShowModel.getCommend_list().isEmpty()) {
            return;
        }
//        if(beanShowModel.isAniming()){
//            return;
//        }
//        beanShowModel.setCanAnim(true);
//        beanShowModel.setIsAniming(true);
        StickerExecutor.getSingleExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < getChildCount(); i++) {
//                    if (!beanShowModel.isCanAnim()) {
//                        break;
//                    }
                    final View child = getChildAt(i);
                    if (child.getTag() != null && "compileModel".equalsIgnoreCase(child.getTag().toString())) {
                        continue;
                    }
                    final AlphaAnimation aa = new AlphaAnimation(0f, 1.0f);
                    final AlphaAnimation bb = new AlphaAnimation(1.0f, 0f);
                    aa.setDuration(StickerImageContans.TOMAXTIME);
                    bb.setDuration(StickerImageContans.TOMINTIME);
                    hd.post(new Runnable() {
                        @Override
                        public void run() {
                            child.startAnimation(bb);
                        }
                    });
                    aa.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
//                            if (!beanShowModel.isCanAnim()) {
//                                return;
//                            }
                            hd.post(new Runnable() {
                                @Override
                                public void run() {
                                    child.startAnimation(bb);
                                }
                            });
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    bb.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
//                            if (!beanShowModel.isCanAnim()) {
//                                return;
//                            }
                            hd.post(new Runnable() {
                                @Override
                                public void run() {
                                    child.startAnimation(aa);
                                }
                            });
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    try {
                        Thread.sleep(StickerImageContans.DEFAULTSECONDTIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 更新正在创建贴纸的文字
     */
    public void updateModelText(String text) {
        if (compileModel == null) {
            compileModel = new StickerImageModel(text);
            compileModel.setMember_avar(AppConfig.loginInfo.getMember_avar());
            compileModel.setMember_id(AppConfig.loginInfo.getMember_id());

        } else {
            compileModel.setInfo(text);
        }
        if (complieView == null) {
            complieView = LayoutInflater.from(mContext).inflate(R.layout.layout_sticker_item, null);
            ImageView iv = (ImageView) complieView.findViewById(R.id.image_sticker_item);
            Glide.with(mContext).load(compileModel.getMember_avar()).transform(new GlideCircleTransform(mContext)).into(iv);
            complieView.setTag("compileModel");
            complieView.setX(toModelWidth(StickerImageContans.DEFAULTX));
            complieView.setY(toModelHeight(StickerImageContans.DEFAULTY));
        }
        TextView tv = (TextView) complieView.findViewById(R.id.tv_sticker_item);
        int a = (int) (getWidth() - toModelWidth(compileModel.getX()) - 50);
        tv.setMaxWidth(a);
        tv.setText(text);
        if (findViewWithTag("compileModel") == null) {
            addView(complieView);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (compileModel == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > complieView.getX() && event.getX() < complieView.getX() + complieView.getWidth() && event.getY() > complieView.getY() && event.getY() < complieView.getY() + complieView.getHeight()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    isCurrentClick = true;
                    tmpX = event.getX() - complieView.getX();
                    tmpy = event.getY() - complieView.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isCurrentClick) {
                    break;
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                float x = event.getX() - tmpX;
                float y = event.getY() - tmpy;
                float leftX = AppConfig.loginInfo.getMember_avar().contains(".jpg") ? x :
                        x + 50;
                if (leftX > getLeft() && (x + complieView.getWidth()) < getRight() && y > 0 && (y + complieView.getHeight()) < getBottom()) {
                    complieView.setX(x);
                    complieView.setY(y);
                    compileModel.setXy(toWindowWidth(complieView.getX()), toWindowHeight(complieView.getY()));
                    TextView tv = (TextView) complieView.findViewById(R.id.tv_sticker_item);
                    int a = (int) (getWidth() - toModelWidth(compileModel.getX()) - 50);
                    tv.setMaxWidth(a);
                    tv.setText(compileModel.getInfo());
                }
                break;
            case MotionEvent.ACTION_UP:
                isCurrentClick = false;
                break;
        }
        return true;
    }

    public StickerImageModel getCompileModel() {
        return compileModel;
    }

    private float toWindowWidth(float x) {
        return x / getWidth();
    }

    private float toWindowHeight(float y) {
        return y / getHeight();
    }

    private float toModelWidth(float x) {
        return x * getWidth();
    }

    private float toModelHeight(float y) {
        return y * getHeight();
    }

}
