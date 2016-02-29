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
 * 发布话题
 * Created by wupeitao on 15/11/3.
 */
public class SendTopicActivity extends BaseSendActivity {
    private GridView noScrollgridview;
    private View parentView;
    private EditText activity_introduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sendtopic);
        parentView = getLayoutInflater().inflate(R.layout.layout_sendtopic, null);
        setContentView(R.layout.layout_sendtopic);
        setCtenterTitle("发布话题");
        Init();
        intBase(true, 4, hd);
    }

    public void Init() {
        activity_introduce = (EditText) findViewById(R.id.activity_introduce);
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogUtil.stopDialogLoading(SendTopicActivity.this);
            switch (msg.what) {
                case SocketHttpRequester.SUCCESS:
                    String info = activity_introduce.getText().toString();
                    Map<String, String> params = new HashMap<>();
                    params.put("info", info);
                    params.put("images", msg.obj.toString());
                    params.put("member_id", AppConfig.loginInfo.getMember_id());
                    BasicRequest.getInstance().requestPost(sendshowListener, 1, params, AppConfig.SENDTOPIC);
                    break;
                case SocketHttpRequester.ERROR:
                    showToast(msg.obj.toString());
                    break;
                case SocketHttpRequester.EXCEPTION:
                    showToast("发布话题失败");
                    break;
            }
        }
    };
    VolleyListerner sendshowListener = new VolleyListerner() {
        @Override
        public void onResponse(String str, int taskid, Object obj) {
            Intent intent = new Intent(BaseSendActivity.SENDTOPICSUCCESS);
            MApplication.getInstance().getBus().post(intent);
            showToast("发布话题成功");
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
        if (TextUtils.isEmpty(activity_introduce.getText())) {
            showToast("请输入文字");
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
            AppUtil.closeSoftInput(SendTopicActivity.this);
            finish();
        }
        return true;
    }
}
