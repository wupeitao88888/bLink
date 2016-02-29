package com.shiliuke.BabyLink.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.shiliuke.BabyLink.R;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.utils.L;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, AppConfig.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    /**
     * int ERR_OK = 0;
     * int ERR_COMM = -1;
     * int ERR_USER_CANCEL = -2;
     * int ERR_SENT_FAILED = -3;
     * int ERR_AUTH_DENIED = -4;
     * int ERR_UNSUPPORT = -5;
     */
    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            MMAlert.showToast(this, resp.errCode + "===" + resp.errStr);
//            MMAlert.showToast(this, resp.openId);
            L.d("wxcode="+resp.errCode);
            switch (resp.errCode) {
                case 0:
                    Intent intent = new Intent(AppConfig.ACTION_WXPAY_SUCCESS);
                    MApplication.getInstance().getBus().post(intent);
                    break;
                case -2:
                    MApplication.getInstance().getBus().post(new Intent(AppConfig.ACTION_WXPAY_CANCLE));
                    break;
                case -5:
                    MApplication.getInstance().getBus().post(new Intent(AppConfig.ACTION_WXPAY_UNSUPPORT));
//                    MMAlert.showToast(this, "您的微信版本不支持微信支付");
                    break;
                default:
                    Intent i = new Intent(AppConfig.ACTION_WXPAY_FAILD);
                    MApplication.getInstance().getBus().post(i);
                    break;
            }
            finish();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("微信支付");
//            builder.setMessage("msg: " + String.valueOf(resp.errCode));
//            builder.show();
        }
    }
}