package com.shiliuke.BabyLink.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.shiliuke.global.AppConfig;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

        msgApi.registerApp(AppConfig.WX_APP_ID);
    }
}
