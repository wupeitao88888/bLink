package com.shiliuke.view.stickerview;

import android.graphics.Color;
import com.shiliuke.BabyLink.R;

/**
 * Created by wangzhi on 15/10/30.
 * 图片贴纸常量
 */
public class StickerImageContans {
    public static final int DEFAULTSECONDTIME = 500;//与下一张贴纸透明度开始变化的时间间隔
    public static final int TOMAXTIME = 2000;//从0到1的时间
    public static final int TOMINTIME = 2000;//从1到0的时间
    public static final float DEFAULTX = 0.5f;//添加贴纸默认X
    public static final float DEFAULTY = 0.5f;//添加贴纸默认Y


    //-------------------------下方已弃用------新增常量请在上方添加------------------
    public static final int REQUESTADDMODEL = 0;//增加贴纸的requestcode


    public static final int DEFAULTHANDLER = 0;//handler接收刷新view的标识
    public static final int DEFAULTHANDLERSTOP = -1;//handler接收停止变化标识

    public static final int DEFAULTTEXTSIZE = 30;//贴纸默认字体大小
    public static final int DEFAULTTEXTCOLOR = Color.WHITE;//贴纸默认字体颜色
    public static final int DEFAULTBGHEIGHT = 36;//贴纸背景高度
    public static final int DEFAULTBGX = 25;//贴纸背景高度
    public static final int DEFAULTBGY = 25;//贴纸背景高度
    public static final int DEFAULTBGLEFT = 20;//贴纸背景左间距
    public static final int DEFAULTCIRCLECOLOR = Color.BLACK;//贴纸背景颜色

    public static final int MAXALPHA = 255;//贴纸最大透明度
    public static final int MINALPHA = 0;//贴纸最小透明度
    public static final int COMPILEALPHA = 5;//贴纸透明度每次增加值

    public static final int DEFAULTCOMPILETIME = 40;//贴纸透明度变化时间

    public static final int DEFAULTBITMAP = R.mipmap.bg_login_guide;//默认图片

    public static final int DEFAULTMAXALTIME = 2000;//贴纸最大透明度时停留的时间
    public static final int DEFAULTMINALTIME = 1500;//贴纸最小透明度时停留的时间
}
