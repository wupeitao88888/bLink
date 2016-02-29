/*******************************************************************************
 * Copyright (c) 2015 by ehoo Corporation all right reserved.
 * 2015-4-21 
 * 
 *******************************************************************************/
package com.shiliuke.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.shiliuke.BabyLink.R;



public class LCDialog extends Dialog {
	Context context;
	private View view;
	public boolean isback = false;

	public LCDialog(Context context, View view) {
		super(context); // TODO Auto-generated constructor stub
		this.context = context;
		setOwnerActivity((Activity)context);
	}

	public LCDialog(Context context, int theme, View view) {
		super(context, theme);
		this.context = context;
		this.view = view;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) { // TODO Auto-generated

		super.onCreate(savedInstanceState);
		this.setContentView(view);
		Window window = getWindow();
		window.setWindowAnimations(R.style.mystyle);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (isback) {

			if (keyCode == KeyEvent.KEYCODE_BACK) {
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);

	}

	public void setIsback(boolean isback) {
		this.isback = isback;
	}
}
