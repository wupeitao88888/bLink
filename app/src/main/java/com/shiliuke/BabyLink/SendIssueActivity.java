package com.shiliuke.BabyLink;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import com.shiliuke.BabyLink.chooseimage.upload.SocketHttpRequester;
import com.shiliuke.bean.IssueInfo;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.AppUtil;
import com.shiliuke.utils.DialogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 发布活动——发布
 * Created by wupeitao on 15/11/3.
 */
public class SendIssueActivity extends BaseSendActivity {

    private IssueInfo issueInfo;
    private GridView noScrollgridview;
    private View parentView;
    private EditText activity_introduce;
    private EditText hold_out_article;
    private CheckBox hold_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater().inflate(R.layout.layout_sendissue, null);
        setContentView(R.layout.layout_sendissue);
        setCtenterTitle("发布活动");
        Init();
        intBase(false, 1, hd);
    }

    public void Init() {
        issueInfo = (IssueInfo) getIntent().getSerializableExtra("model");
        if (issueInfo == null) {
            finish();
            return;
        }
        activity_introduce = (EditText) findViewById(R.id.activity_introduce);
        hold_out_article = (EditText) findViewById(R.id.hold_out_article);
        hold_out = (CheckBox) findViewById(R.id.hold_out);

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        activity_introduce.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                //这句话说的意思告诉父View我自己的事件我自己处理
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogUtil.stopDialogLoading(SendIssueActivity.this);
            switch (msg.what) {
                case SocketHttpRequester.SUCCESS:
                    String info = activity_introduce.getText().toString();
                    Map<String, String> params = new HashMap<>();
                    params.put("info", info);
                    params.put("title", issueInfo.getSubject_activity_content());
                    params.put("activity_address", issueInfo.getActivity_address_content());
                    params.put("link_name", issueInfo.getContact_man_content());
                    params.put("end_time", issueInfo.getActivity_time_content());
                    params.put("link_mobile", issueInfo.getContact_phone_content());
                    params.put("jihe_time", issueInfo.getAssemble_time());
                    params.put("jihe_address", issueInfo.getAssemble_address_content());
                    params.put("utils", issueInfo.getMeans_of_transportation_content());
                    params.put("max_man", issueInfo.getMax_person_content());
                    params.put("price", issueInfo.getCost_issue_content());
                    params.put("pay_way", issueInfo.getMethod_of_payment_content());
                    params.put("is_help", hold_out.isChecked() ? "1" : "0");
                    params.put("help", hold_out_article.getText().toString());
                    params.put("images", msg.obj.toString());
                    params.put("member_id", AppConfig.loginInfo.getMember_id());
                    BasicRequest.getInstance().requestPost(sendshowListener, 1, params, AppConfig.SENDACTIVITY);
                    break;
                case SocketHttpRequester.ERROR:
                    showToast(msg.obj.toString());
                    break;
                case SocketHttpRequester.EXCEPTION:
                    showToast("发布活动失败");
                    break;
            }
        }
    };
    VolleyListerner sendshowListener = new VolleyListerner() {
        @Override
        public void onResponse(String str, int taskid, Object obj) {
            Intent intent = new Intent(BaseSendActivity.SENDISSUESUCCESS);
            MApplication.getInstance().getBus().post(intent);
            showToast("发布活动成功");
            setResult(RESULT_OK);
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
        if (TextUtils.isEmpty(activity_introduce.getText())) {
            showToast("请输入活动介绍");
            return;
        }
        if (hold_out.isChecked() && TextUtils.isEmpty(hold_out_article.getText())) {
            showToast("请输入所需要的物品");
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
            AppUtil.closeSoftInput(SendIssueActivity.this);
            finish();
        }
        return true;
    }
}
