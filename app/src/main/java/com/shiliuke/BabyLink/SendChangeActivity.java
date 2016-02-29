package com.shiliuke.BabyLink;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import com.shiliuke.BabyLink.chooseimage.upload.SocketHttpRequester;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.AppUtil;
import com.shiliuke.utils.DialogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 发布置换
 * Created by wupeitao on 15/11/3.
 */
public class SendChangeActivity extends BaseSendActivity {

    private GridView noScrollgridview;
    private View parentView;
    private EditText change_article_name;//物品名称
    private EditText change_want_article_name;//想要置换的物品名称
    private EditText change_article;//置换物品描述

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater().inflate(R.layout.layout_sendchange, null);
        setContentView(R.layout.layout_sendchange);
        Init();
        setCtenterTitle("发布置换");
        intBase(true, 3, hd);
    }

    public void Init() {
        change_article_name = (EditText) findViewById(R.id.change_article_name);
        change_want_article_name = (EditText) findViewById(R.id.change_want_article_name);
        change_article = (EditText) findViewById(R.id.change_article);
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogUtil.stopDialogLoading(SendChangeActivity.this);
            switch (msg.what) {
                case SocketHttpRequester.SUCCESS:
                    String from_gname = change_article_name.getText().toString();
                    String to_gname = change_want_article_name.getText().toString();
                    String info = change_article.getText().toString();
                    Map<String, String> params = new HashMap<>();
                    params.put("from_gname", from_gname);
                    params.put("to_gname", to_gname);
                    params.put("info", info);
                    params.put("images", msg.obj.toString());
                    params.put("member_id", AppConfig.loginInfo.getMember_id());
                    BasicRequest.getInstance().requestPost(sendshowListener, 1, params, AppConfig.SENDTEXCHANGE);
                    break;
                case SocketHttpRequester.ERROR:
                    showToast(msg.obj.toString());
                    break;
                case SocketHttpRequester.EXCEPTION:
                    showToast("发布置换失败");
                    break;
            }
        }
    };
    VolleyListerner sendshowListener = new VolleyListerner() {
        @Override
        public void onResponse(String str, int taskid, Object obj) {
            Intent intent = new Intent(BaseSendActivity.SENDCHANGESUCCESS);
            MApplication.getInstance().getBus().post(intent);
            showToast("发布置换成功");
            finish();
        }

        @Override
        public void onResponseError(String error, int taskid) {
            showToast(error);
        }
    };

    /**
     * 确定按钮监听
     *
     * @param v
     */
    public void okClick(View v) {
        if (mImageKeyList.isEmpty()) {
            showToast("请选择图片");
            return;
        }
        if (TextUtils.isEmpty(change_article_name.getText())) {
            showToast("请输入物品名称");
            return;
        }
        if (TextUtils.isEmpty(change_want_article_name.getText())) {
            showToast("请输入想要置换的物品名称");
            return;
        }
        if (TextUtils.isEmpty(change_article.getText())) {
            showToast("请输入置换物品描述");
            return;
        }
        uploadFiles();
    }

    @Override
    public GridView getGridView() {
        return noScrollgridview;
    }

    @Override
    public View getParentView() {
        return parentView;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppUtil.closeSoftInput(SendChangeActivity.this);
            finish();
        }
        return true;
    }

}
