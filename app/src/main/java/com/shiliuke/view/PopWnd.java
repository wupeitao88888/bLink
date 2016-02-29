package com.shiliuke.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.shiliuke.BabyLink.R;

public class PopWnd {
    protected OnClickListener mPlayMenuItemClick;// 监听事件
    protected PopupWindow popupWindow;
    protected Context context;
    protected View mPopBg;// 背景
    protected View popView;// 主View

    public View getPopView() {
        return popView;
    }

    /**
     * @param ctx        上下文
     * @param resourceId 布局文件
     * @param idList     viewId
     * @param itemClick  监听器
     */
    public PopWnd(Context ctx, int resourceId, int[] idList,
                  OnClickListener itemClick) {
        this(ctx, resourceId, idList, itemClick, null, true);
    }

    /**
     * @param ctx        上下文
     * @param resourceId 布局文件
     * @param idList     viewId
     * @param itemClick  监听器
     */
    public PopWnd(Context ctx, int resourceId, int[] idList,
                  OnClickListener itemClick, View popBg) {
        this(ctx, resourceId, idList, itemClick, popBg, true);
    }

    /**
     * @param ctx           上下文
     * @param resourceId    布局文件
     * @param idList        viewId
     * @param itemClick     监听器
     * @param popBg         背景
     * @param isWrapContent 是否是包裹内容
     */
    @SuppressWarnings("deprecation")
    public PopWnd(Context ctx, int resourceId, int[] idList,
                  final OnClickListener itemClick, View popBg, boolean isWrapContent) {
        context = ctx;
        mPopBg = popBg;
        popView = LayoutInflater.from(ctx).inflate(resourceId, null);
        if (idList != null) {
            for (int i = 0; i < idList.length; i++) {
                popView.findViewById(idList[i]).setOnClickListener(
                        v -> {
                            dissmiss();
                            itemClick.onClick(v);
                        });
            }
        }
        if (isWrapContent) {
            popupWindow = new PopupWindow(popView,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        } else {
            popupWindow = new PopupWindow(popView,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                if (mPopBg != null) {
                    mPopBg.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * @param ctx           上下文
     * @param resourceId    布局文件
     * @param idList        viewId
     * @param itemClick     监听器
     * @param popBgResId    背景
     * @param isWrapContent 是否是包裹内容
     */
    @SuppressWarnings("deprecation")
    public PopWnd(Context ctx, int resourceId, int[] idList,
                  final OnClickListener itemClick, int popBgResId, boolean isWrapContent) {
        context = ctx;
        popView = LayoutInflater.from(ctx).inflate(resourceId, null);
        mPopBg = popView.findViewById(popBgResId);
        mPopBg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmiss();
            }
        });
        for (int i = 0; i < idList.length; i++) {
            popView.findViewById(idList[i]).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dissmiss();
                            itemClick.onClick(v);
                        }
                    });
        }
        if (isWrapContent) {
            popupWindow = new PopupWindow(popView,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        } else {
            popupWindow = new PopupWindow(popView,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                if (mPopBg != null) {
                    mPopBg.setVisibility(View.GONE);
                }
            }
        });

    }

    public TextView getTextView(int resId) {
        return (TextView) popView.findViewById(resId);
    }

    public void dissmiss() {
        popupWindow.dismiss();
    }

    public void showPopWindow(View v) {
        if (mPopBg != null) {
            mPopBg.setVisibility(View.VISIBLE);
        }
        popupWindow.showAsDropDown(v);
    }

    public void showPopDown(View v) {
        if (mPopBg != null) {
            mPopBg.setVisibility(View.VISIBLE);
        }
        popupWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
