package com.shiliuke.view.stickerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.shiliuke.bean.BeanShowModelResult;

/**
 * 图片贴纸View
 * Created by wangzhi on 15/10/30.
 */
public class StickerImageView extends View {

    //    private ArrayList<StickerImageModel> mdata;
    private StickerImageModel compileModel;//正在编辑的贴纸
    private BeanShowModelResult beanShowModel;
    private Paint mTextPaint;
    private Paint mBgPaint;

    private float x1, x2, y1, y2, tmpx;
    private boolean isCurrentClick = false;


    public StickerImageView(Context context) {
        this(context, null);
    }

    public StickerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFilterBitmap(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth(1.0f);
        mTextPaint.setTextSize(StickerImageContans.DEFAULTTEXTSIZE);
        mTextPaint.setColor(StickerImageContans.DEFAULTTEXTCOLOR);
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setFilterBitmap(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setStrokeWidth(1.0f);
        mBgPaint.setTextSize(StickerImageContans.DEFAULTTEXTSIZE);
        mBgPaint.setColor(StickerImageContans.DEFAULTCIRCLECOLOR);
    }

    /**
     * 设置背景图片以及弹幕列表
     */
    public void setBeanShowModel(BeanShowModelResult model) {
        this.beanShowModel = model;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (beanShowModel == null) {
//            return;
//        }
//        for (int i = 0; i < beanShowModel.getCommend_list().size(); i++) {
//            StickerImageModel model = beanShowModel.getCommend_list().get(i);
//            mTextPaint.setAlpha(model.getAlpha());
//            mBgPaint.setAlpha(model.getAlpha());
//            RectF oval3 = new RectF(model.getX() - StickerImageContans.DEFAULTBGLEFT, model.getY() - StickerImageContans.DEFAULTBGHEIGHT, model.getX() + getTextWidth(mTextPaint, model.getInfo()) + StickerImageContans.DEFAULTBGLEFT, model.getY() + StickerImageContans.DEFAULTBGHEIGHT / 2);// 设置个新的长方形
//            canvas.drawRoundRect(oval3, StickerImageContans.DEFAULTBGX, StickerImageContans.DEFAULTBGY, mBgPaint);
//            canvas.drawText(model.getInfo(), model.getX(), model.getY(), mTextPaint);
//        }
//        if (compileModel != null) {
//            mTextPaint.setAlpha(compileModel.getAlpha());
//            mBgPaint.setAlpha(compileModel.getAlpha());
//            RectF oval3 = new RectF(compileModel.getX() - StickerImageContans.DEFAULTBGLEFT, compileModel.getY() - StickerImageContans.DEFAULTBGHEIGHT, compileModel.getX() + getTextWidth(mTextPaint, compileModel.getInfo()) + StickerImageContans.DEFAULTBGLEFT, compileModel.getY() + StickerImageContans.DEFAULTBGHEIGHT / 2);// 设置个新的长方形
//            canvas.drawRoundRect(oval3, StickerImageContans.DEFAULTBGX, StickerImageContans.DEFAULTBGY, mBgPaint);
//            canvas.drawText(compileModel.getInfo(), compileModel.getX(), compileModel.getY(), mTextPaint);
//        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (compileModel == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > x1 && event.getX() < x2 && event.getY() > y1 && event.getY() < y2) {
                    tmpx = event.getX() - x1;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    invalidate();
                    isCurrentClick = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isCurrentClick) {
                    break;
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                compileModel.setXy(event.getX() - tmpx, event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                setCurrentXy();
                isCurrentClick = false;
                break;
        }
        return true;
    }

    /**
     * 更新正在创建贴纸的文字
     */
    public void updateModelText(String text) {
        if (compileModel == null) {
            compileModel = new StickerImageModel(text);
        } else {
            compileModel.setInfo(text);
        }
        setCurrentXy();
        invalidate();
    }

    /**
     * 更新新建贴纸的点击区域
     */
    private void setCurrentXy() {
        x1 = compileModel.getX() - StickerImageContans.DEFAULTBGLEFT;
        y1 = compileModel.getY() - StickerImageContans.DEFAULTBGHEIGHT;
        x2 = compileModel.getX() + getTextWidth(mTextPaint, compileModel.getInfo()) + StickerImageContans.DEFAULTBGLEFT;
        y2 = compileModel.getY() + StickerImageContans.DEFAULTBGHEIGHT / 2;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case StickerImageContans.DEFAULTHANDLER:
                    invalidate();
                    break;
                case StickerImageContans.DEFAULTHANDLERSTOP:
//                    beanShowModel.setIsAniming(false);
                    break;
            }
            super.handleMessage(msg);
        }
    };
//    public static boolean canAnim;

    /**
     * 关闭在执行的“动画”
     */
    public void stopAnim() {
//        beanShowModel.setCanAnim(false);
//        beanShowModel.setIsAniming(false);
    }

    /**
     * 开始”动画"
     */
    public void startAnim() {
//        if (beanShowModel == null || beanShowModel.getCommend_list().isEmpty() || beanShowModel.isAniming()) {
//            return;
//        }
//        beanShowModel.setCanAnim(true);
//        beanShowModel.setIsAniming(true);
//        StickerExecutor.getSingleExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < beanShowModel.getCommend_list().size(); i++) {
//                    StickerImageModel model = beanShowModel.getCommend_list().get(i);
////                    model.startLooper(beanShowModel, handler);
//                    if (!beanShowModel.isCanAnim()) {
//                        break;
//                    }
//                    try {
//                        Thread.sleep(StickerImageContans.DEFAULTSECONDTIME);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    /**
     * 得到文字的长度
     *
     * @param paint
     * @param str
     * @return
     */
    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
        return iRet;
    }

    public StickerImageModel getCompileModel() {
        return compileModel;
    }
}
