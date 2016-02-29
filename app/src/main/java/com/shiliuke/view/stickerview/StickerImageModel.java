package com.shiliuke.view.stickerview;

import java.io.Serializable;

/**
 * 图片贴纸Model
 * Created by wangzhi on 15/10/30.
 */
public class StickerImageModel implements Serializable {
    private String commend_id;//
    private String xiu_id;//
    private String member_id;//
    private String member_name;//
    private String member_avar;//
    private String info;//贴纸文字
    //    private int alpha;//透明度（弃用）
//    private int f;（弃用）
    private String position_x;
    private String position_y;
    private String add_time;

    public StickerImageModel(String text) {
        this.info = text;
        this.position_x = StickerImageContans.DEFAULTX + "";
        this.position_y = StickerImageContans.DEFAULTY + "";
//        this.alpha = StickerImageContans.MAXALPHA;（弃用）
    }

    public void setXy(float x, float y) {
        this.position_x = x + "";
        this.position_y = y + "";
    }

    public float getY() {
        return Float.parseFloat(position_y);
    }

    public float getX() {
        return Float.parseFloat(position_x);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMember_avar() {
        return member_avar;
    }

    public void setMember_avar(String member_avar) {
        this.member_avar = member_avar;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }
    //    private void updateAlpha() {（弃用）
//        alpha = StickerImageContans.COMPILEALPHA * f + alpha;
//        if (alpha >= StickerImageContans.MAXALPHA) {
//            alpha = StickerImageContans.MAXALPHA;
//        }
//        if (alpha <= StickerImageContans.MINALPHA) {
//            alpha = StickerImageContans.MINALPHA;
//        }
//    }
//    public int getAlpha() {（弃用）

//        return alpha;
//    }
//    public void setAlpha(int alpha) {（弃用）

//        this.alpha = alpha;
//    }

    /**
     * 开始改变透明度
     * （弃用）
     *
     * @param handler
     */
//    public void startLooper(final BeanShowModelResult model, final Handler handler) {
//        StickerExecutor.getSingleExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                while (model.isCanAnim()) {
//                    if (alpha == StickerImageContans.MAXALPHA) {
//                        try {
//                            Thread.sleep(StickerImageContans.DEFAULTMAXALTIME);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        f = -1;
//                        updateAlpha();
//                        handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLER);
//                        continue;
//                    }
//                    if (alpha == StickerImageContans.MINALPHA) {
//                        try {
//                            Thread.sleep(StickerImageContans.DEFAULTMINALTIME);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        f = 1;
//                        updateAlpha();
//                        handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLER);
//                        continue;
//                    }
//                    try {
//                        Thread.sleep(StickerImageContans.DEFAULTCOMPILETIME);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    updateAlpha();
//                    handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLER);
////                    L.d("isCanAnim" + model.isCanAnim());
//                }
//                handler.sendEmptyMessage(StickerImageContans.DEFAULTHANDLERSTOP);
//            }
//        });
//    }

}
