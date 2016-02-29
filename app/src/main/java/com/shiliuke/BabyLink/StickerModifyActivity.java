package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.BeanShowModelResult;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.AppUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ShareUtils;
import com.shiliuke.view.PopWnd;
import com.shiliuke.view.stickerview.StickerImageModel;
import com.shiliuke.view.stickerview.StickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 弹幕详情（添加评论）传了position
 * 我的秀逗详情 不传position
 * <p>
 * Created by wangzhi on 15/11/4.
 */
public class StickerModifyActivity extends ActivitySupport implements View.OnClickListener, VolleyListerner {
    private EditText edit_modify_sticker;//弹幕输入框
    private Button btn_modify_sticker;//发布按钮
    private ImageView image_beanshow_item_head;//头像
    private TextView tv_beanshow_item_name;//用户名
    private TextView tv_beanshow_item_time;//发布时间
    private Button btn_beanshow_item_addfocus;//加关注按钮
    private StickerView mSticker_View;//主View
    private ImageView mSticker_image_View;//主View
    private TextView tv_beanshow_item_dou;//多少逗
    private TextView tv_beanshow_item_msg;//msg
    private LinearLayout layout_beanshow_item_zan;//赞列表
    private Button btn_beanshow_item_dou;//逗一逗
    private Button btn_beanshow_item_zan;//赞
    private ImageButton btn_beanshow_item_share;//分享
    private View layout_modify_sticker_edit;//评论View
    private String xiu_id;

    private int result = RESULT_CANCELED;
    private Intent intent;

    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sticker);
        initView();
        initData();
    }

    private void initView() {
        edit_modify_sticker = (EditText) findViewById(R.id.edit_modify_sticker);
        edit_modify_sticker.addTextChangedListener(mEditWatcher);

        layout_modify_sticker_edit = findViewById(R.id.layout_modify_sticker_edit);
        btn_modify_sticker = (Button) findViewById(R.id.btn_modify_sticker);
        btn_beanshow_item_addfocus = (Button) findViewById(R.id.btn_beanshow_item_addfocus);
        btn_beanshow_item_dou = (Button) findViewById(R.id.btn_beanshow_item_dou);
        btn_beanshow_item_zan = (Button) findViewById(R.id.btn_beanshow_item_zan);
        btn_beanshow_item_share = (ImageButton) findViewById(R.id.btn_beanshow_item_share);
        btn_modify_sticker.setOnClickListener(this);

        image_beanshow_item_head = (ImageView) findViewById(R.id.image_beanshow_item_head);
        mSticker_image_View = (ImageView) findViewById(R.id.sticker_image_beanshow_item);
        tv_beanshow_item_name = (TextView) findViewById(R.id.tv_beanshow_item_name);
        tv_beanshow_item_time = (TextView) findViewById(R.id.tv_beanshow_item_time);
        tv_beanshow_item_dou = (TextView) findViewById(R.id.tv_beanshow_item_dou);
        tv_beanshow_item_msg = (TextView) findViewById(R.id.tv_beanshow_item_msg);
        layout_beanshow_item_zan = (LinearLayout) findViewById(R.id.layout_beanshow_item_zan);
        mSticker_View = (StickerView) findViewById(R.id.sticker_beanshow_item);

        btn_beanshow_item_addfocus.setVisibility(View.GONE);
        if (TextUtils.isEmpty(getIntent().getStringExtra("position"))) {
            setCtenterTitle("我的秀逗");
            layout_modify_sticker_edit.setVisibility(View.GONE);
            btn_beanshow_item_dou.setOnClickListener(douClick);
            btn_beanshow_item_share.setOnClickListener(shareClick);
            return;
        }
        btn_beanshow_item_share.setVisibility(View.GONE);
        btn_beanshow_item_dou.setVisibility(View.GONE);
        btn_beanshow_item_zan.setVisibility(View.GONE);
        setCtenterTitle("弹幕详情");
        tv_beanshow_item_dou.setVisibility(View.GONE);
        tv_beanshow_item_msg.setVisibility(View.GONE);
        layout_beanshow_item_zan.setVisibility(View.GONE);
        findViewById(R.id.tv_beanshow_item_dou_text).setVisibility(View.GONE);

    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        old = (BeanShowModelResult) intent.getSerializableExtra("model");
        if (old == null) {
            finish();
            return;
        }
        xiu_id = old.getXiu_id();
        final BeanShowModelResult model = new BeanShowModelResult();
        model.setInfo(old.getInfo());
        model.setCommend_list(old.getCommend_list());
        model.setZan_list(old.getZan_list());
        model.setXiu_id(old.getXiu_id());
        Glide.with(context).load(old.getMember_avar()).into(image_beanshow_item_head);
        L.d("old.getImage_url()" + old.getImage_url());
        Glide.with(context).load(old.getImage_url()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                mSticker_View.setModel(model);
                mSticker_View.startAnim();
                return false;
            }
        }).into(mSticker_image_View);
        tv_beanshow_item_name.setText(old.getMember_name());
        tv_beanshow_item_time.setText(old.getTime());
        tv_beanshow_item_dou.setText(old.getCommend_list().size() + "");
        tv_beanshow_item_msg.setText(old.getInfo());
        initZanList(model.getZan_list(), layout_beanshow_item_zan);

//        btn_beanshow_item_share.setTag("http://www.baidu.com");
        if (old.isZan()) {
            btn_beanshow_item_zan.setBackgroundResource(R.mipmap.icon_xd_zan_yes);
        } else {
            btn_beanshow_item_zan.setBackgroundResource(R.mipmap.icon_xd_zan_no);
        }
        btn_beanshow_item_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isZan()) {
                    showToast("已经赞过了");
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put("member_id", AppConfig.loginInfo.getMember_id());
                    params.put("xiu_id", model.getXiu_id());
                    BasicRequest.getInstance().requestPost(new VolleyListerner() {
                        @Override
                        public void onResponse(String str, int taskid, Object obj) {
                            StickerImageModel zan = new StickerImageModel("");
                            zan.setMember_avar(AppConfig.loginInfo.getMember_avar());
                            model.setIsZan(true);
                            model.getZan_list().add(zan);
                            result = RESULT_OK;
                            btn_beanshow_item_zan.setBackgroundResource(R.mipmap.icon_xd_zan_yes);
                            showToast("赞成功");
                            ImageView view = new ImageView(StickerModifyActivity.this);
                            StickerModifyActivity.this.params.setMargins(10, 0, 0, 0);
                            view.setLayoutParams(StickerModifyActivity.this.params);
                            Glide.with(context).load(AppConfig.loginInfo.getMember_avar()).into(view);
                            layout_beanshow_item_zan.addView(view);
                        }

                        @Override
                        public void onResponseError(String error, int taskid) {
                            showToast(error);
                        }
                    }, TaskID.ACTION_XIUDOU_ZAN, params, AppConfig.XIUDOU_ZAN);
                }
            }
        });
    }

    /**
     * button“逗一逗”
     */
    private View.OnClickListener douClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layout_modify_sticker_edit.setVisibility(View.VISIBLE);
        }
    };
    /**
     * button“分享”
     */
    private BeanShowModelResult old;
    private View.OnClickListener shareClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_beanshow_item_share:
//                    msg = v.getTag().toString();
                    int[] ids = {R.id.tv_share_wxpyq, R.id.tv_share_wx, R.id.tv_share_xlwb, R.id.tv_share_qq, R.id.tv_share_dx, R.id.tv_share_yj};
                    PopWnd popWnd = new PopWnd(getContext(), R.layout.layout_share_pop, ids, shareClick, R.id.pop_bg
                            , false);
                    popWnd.showPopDown(v);
                    break;
                case R.id.tv_share_wx:
                    ShareUtils.getInstance(context).shareWx("秀逗分享", old.getInfo(), old.getImage_url(), old.getFenxiang_url());
                    break;
                case R.id.tv_share_wxpyq:
                    ShareUtils.getInstance(context).shareWxPyq("秀逗分享：" + old.getInfo(), old.getImage_url(), old.getFenxiang_url());
                    break;
                case R.id.tv_share_qq:
                    ShareUtils.getInstance(StickerModifyActivity.this).shareQq("秀逗分享", old.getInfo(), old.getImage_url(), old.getFenxiang_url());
                    break;
                case R.id.tv_share_xlwb:
                    ShareUtils.getInstance(StickerModifyActivity.this).shareXlwb("秀逗分享：" + old.getInfo(), old.getImage_url(), old.getFenxiang_url());
                    break;
            }
        }
    };

    private String getZanText(ArrayList<StickerImageModel> zanList, String addZan) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < zanList.size(); i++) {
            if (i != 0) {
                buffer.append(",");
            }
            buffer.append(zanList.get(i).getMember_name());
        }
        if (!TextUtils.isEmpty(addZan)) {
            if (!zanList.isEmpty()) {
                buffer.append(",");
            }
            buffer.append(addZan);
        }
        return buffer.toString();
    }

    TextWatcher mEditWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mSticker_View.updateModelText(s.toString());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_modify_sticker:
                String info = edit_modify_sticker.getText().toString();
                if (TextUtils.isEmpty(info)) {
                    showToast("请输入内容");
                    return;
                }
                AppUtil.closeSoftInput(this);
                if (mSticker_View.getCompileModel() != null) {
                    Map<String, String> params = new HashMap<>();
                    params.put("info", info);
                    params.put("member_id", AppConfig.loginInfo.getMember_id());
                    params.put("xiu_id", xiu_id);
                    params.put("position_x", mSticker_View.getCompileModel().getX() + "");
                    params.put("position_y", mSticker_View.getCompileModel().getY() + "");
                    BasicRequest.getInstance().requestPost(this, TaskID.ACTION_XIUDOU_ADD_COMMEND, params, AppConfig.XIUDOU_ADDCOMMEND);
                } else {
                    showToast("评论失败");
                    setResult(RESULT_CANCELED);
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mSticker_View.stopAnim();
        super.onDestroy();
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        intent = new Intent();
        intent.putExtra("model", mSticker_View.getCompileModel());
        intent.putExtra("position", getIntent().getStringExtra("position"));
        showToast("完成");
        result = RESULT_OK;
        finish();
    }

    @Override
    public void onResponseError(String error, int taskid) {
        showToast(error);
    }

    @Override
    public void finish() {
        setResult(result, intent);
        super.finish();
    }

    private void initZanList(ArrayList<StickerImageModel> zanList, LinearLayout rootView) {
        rootView.removeAllViews();
        params.setMargins(10, 0, 0, 0);
        for (int i = 0; i < zanList.size(); i++) {
            ImageView view = new ImageView(this);
            view.setLayoutParams(params);
            Glide.with(context).load(zanList.get(i).getMember_avar()).into(view);
            rootView.addView(view);
        }
    }
}
