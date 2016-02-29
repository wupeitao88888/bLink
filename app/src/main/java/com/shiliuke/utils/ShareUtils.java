package com.shiliuke.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.shiliuke.global.AppConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.utils.OauthHelper;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ShareUtils implements SnsPostListener {
    final UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");
    private static ShareUtils shareUtils;
    private Context context;

    public static ShareUtils getInstance(Context context) {
        com.umeng.socialize.utils.Log.LOG = true;
        if (shareUtils == null) {
            shareUtils = new ShareUtils();
            shareUtils.context = context;
        }
        return shareUtils;
    }

    private ShareUtils() {
    }

    /**
     * 分享到微信
     */
    public void shareWx(String title, String content, String imageurl, String targeturl) {
        String appID = AppConfig.WX_APP_ID;
        String appSecret = AppConfig.WX_APP_SECRET;
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, appID, appSecret);
        wxHandler.addToSocialSDK();
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        // /////////////////////////////////////////////////
        // 设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setTitle(title);
        // 设置分享文字
        weixinContent.setShareContent(content);
        // 设置分享内容跳转URL
        weixinContent.setTargetUrl(targeturl);
        //设置分享图片
        weixinContent.setShareImage(new UMImage(context, imageurl));
        mController.setShareMedia(weixinContent);
        mController.postShare(context, SHARE_MEDIA.WEIXIN, this);
    }

    /**
     * 分享到微信朋友圈
     */
    public void shareWxPyq(String title, String imageurl, String targeturl) {
        String appID = AppConfig.WX_APP_ID;
        String appSecret = AppConfig.WX_APP_SECRET;
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, appID, appSecret);
        wxHandler.addToSocialSDK();
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        // /////////////////////////////////////////////////
        // 设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setTitle(title);
        circleMedia.setShareContent(title);
        circleMedia.setShareImage(new UMImage(context, imageurl));
        circleMedia.setTargetUrl(targeturl);
        mController.setShareMedia(circleMedia);
        mController.postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE, this);
    }

    /**
     * 分享到qq
     */
    public void shareQq(String title, String content, String url, String targeturl) {
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context,
                "1104869195", "ZKscrjTqoGkHSEOE");
        qqSsoHandler.addToSocialSDK();
        // /////////////////////////////////////////////////
        QQShareContent qqShareContent = new QQShareContent();
        // 设置分享文字
        qqShareContent.setShareContent(content);
        // 设置分享title
        qqShareContent.setTitle(title);
        // 设置分享图片
        qqShareContent
                .setShareImage(new UMImage(context, url));
        // 设置点击分享内容的跳转链接
        qqShareContent.setTargetUrl(targeturl);
        mController.setShareMedia(qqShareContent);
        mController.postShare(context, SHARE_MEDIA.QQ, this);
    }

    /**
     * 分享到新浪微博
     */
    public void shareXlwb(String content, String url, String targeturl) {
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.setShareContent(content + "," + targeturl);
        mController.setShareImage(new UMImage(context, url));
        mController.postShare(context, SHARE_MEDIA.SINA, this);
    }

    /**
     * 分享到短信
     */
    public void shareDuanXin(String content) {
        Intent targeted = new Intent(Intent.ACTION_SEND);
        targeted.setType("text/plain");
        // targeted.putExtra(Intent.EXTRA_SUBJECT, "EXTRA_SUBJECT");
        targeted.putExtra(Intent.EXTRA_TEXT, content);
        targeted.setPackage("com.android.contacts");
        targeted.setClassName("com.android.contacts",
                "com.android.mms.ui.ComposeMessageActivity");
        ((Activity) context).startActivity(targeted);
    }

    /**
     * 分享到邮件
     */
    public void shareEmail(String title, String content) {
        Intent targeted = new Intent(Intent.ACTION_SEND);
        targeted.setType("text/plain");
        targeted.putExtra(Intent.EXTRA_SUBJECT, title);
        targeted.putExtra(Intent.EXTRA_TEXT, content);
        targeted.setPackage("com.android.email");
        targeted.setClassName("com.android.email",
                "com.android.email.activity.MessageCompose");
        ((Activity) context).startActivity(targeted);
    }

    @Override
    public void onComplete(SHARE_MEDIA platform, int eCode,
                           SocializeEntity entity) {
        if (eCode == 200) {
            Toast.makeText(context, "分享成功.", Toast.LENGTH_SHORT).show();
        } else {
            String eMsg = "";
            if (eCode == -101) {
                eMsg = "没有授权";
            }
//			Toast.makeText(context, "分享失败[" + eCode + "] " + eMsg,
//					Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        // Toast.makeText(context, "开始分享.", Toast.LENGTH_SHORT).show();
    }
}
