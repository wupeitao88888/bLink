package com.shiliuke.BabyLink.wxapi;

import android.os.Bundle;
import com.shiliuke.global.AppConfig;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {
    private IWXAPI wxApi;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxApi = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID, true);
        wxApi.handleIntent(getIntent(), this);
    }
}