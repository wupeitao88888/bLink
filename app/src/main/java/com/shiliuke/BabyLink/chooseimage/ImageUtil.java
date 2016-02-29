package com.shiliuke.BabyLink.chooseimage;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.*;
import java.util.Locale;

public class ImageUtil {

    //读取图片的旋转角度
    public static int getExifOrientation(String filepath) {
        try {
            ExifInterface exif = new ExifInterface(filepath);
            if (exif != null) {
                return exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ExifInterface.ORIENTATION_UNDEFINED;
    }


    public static Bitmap getBitmap(String path, Activity context) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            ContentResolver mContentResolver = context.getContentResolver();
            in = mContentResolver.openInputStream(uri);

            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            int IMAGE_MAX_SIZE = 1024;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            in = mContentResolver.openInputStream(uri);
            Bitmap b = BitmapFactory.decodeStream(in, null, o2);
            in.close();

            return b;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * 写bitmap到磁盘
     *
     * @param bmp
     * @param filename
     * @return
     */
    public static String saveBitmap(Bitmap bmp, String filename) {
        File dest = new File(FileManager.getTempFilePath(), filename);
        try {
            FileOutputStream out = new FileOutputStream(dest);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            return null;
        } finally {
//            if (bmp != null && !bmp.isRecycled())
//                bmp.recycle();
        }
        return dest.getAbsolutePath();
    }

    public static Bitmap rotateImage(Bitmap src, float degree) {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        //Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public static File getPathForCameraCrop(String imgKey) {
        return new File(FileManager.getTempFilePath(), String.format(
                Locale.getDefault(), "%s", imgKey));
    }

    public static File getPathForUpload(String imgKey) {
        return new File(FileManager.getTempFilePath(), String.format(
                Locale.getDefault(), "%s", imgKey));
    }


    public static final int PICK_FROM_CAMERA = 10;
    public static final int PICK_FROM_GALLERY = 11;
    public static final int PICK_FROM_GALLERY_CROP = 12;


}
