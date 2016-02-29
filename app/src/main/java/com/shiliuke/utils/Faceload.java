/*******************************************************************************
 * Copyright (c) 2014 by ehoo Corporation all right reserved.
 * 2014-7-2 
 * 
 *******************************************************************************/
package com.shiliuke.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;




public class Faceload {
	private static Faceload faceload = null;

	public static Faceload getInstance() {
		if (faceload == null) {
			faceload = new Faceload();
		}
		return faceload;
	}

	Bitmap bitmap = null;
	public Map<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

	public Bitmap LoadFace(Resources res, int id) {
		bitmap = BitmapFactory.decodeResource(res, id);
		if (imageCache.containsKey(id + "")) {
			SoftReference<Bitmap> softReference = imageCache.get(id + "");
			if (softReference != null && softReference.get() != null) {
				return softReference.get();
			} else {
				bitmap = Bitmap.createScaledBitmap(bitmap, 125, 125, true);
				imageCache.put(id + "", new SoftReference<Bitmap>(bitmap));
				return bitmap;
			}
		} else {
			bitmap = Bitmap.createScaledBitmap(bitmap, 125, 125, true);
			imageCache.put(id + "", new SoftReference<Bitmap>(bitmap));
			return bitmap;
		}
	}

	public void re() {
		if (bitmap != null) {
			bitmap.recycle();
			System.gc();
		}
	}
}
