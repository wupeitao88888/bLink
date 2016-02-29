package com.shiliuke.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shiliuke.BabyLink.R;

/**
 * @author yangyu 功能描述：标题按钮上的弹窗（继承自PopupWindow）
 */
public class EditTextPop extends PopupWindow {



	private Context mContext;



	// 实例化一个矩形
	private Rect mRect = new Rect();

	// 坐标的位置（x、y）
	private final int[] mLocation = new int[2];

	// 屏幕的宽度和高度
	private int mScreenWidth, mScreenHeight;



	public EditTextPop(Context context) {
		this.mContext = context;

		// 设置可以获得焦点
		setFocusable(true);
		// 设置弹窗内可点击
		setTouchable(true);
		// 设置弹窗外可点击
		setOutsideTouchable(true);
		// 设置弹窗的宽度和高度
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new BitmapDrawable());
		// 设置弹窗的布局界面
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.comment_popu, null);
		setContentView(view);

	}

	/**
	 * 显示弹窗列表界面
	 */
	public void show(final View c) {
		// 获得点击屏幕的位置坐标
		c.getLocationOnScreen(mLocation);
		// 设置矩形的大小
		mRect.set(mLocation[0], mLocation[1], mLocation[0] + c.getWidth(),
				mLocation[1] + c.getHeight());

		showAsDropDown(c);
//		showAtLocation(c, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	}




}
