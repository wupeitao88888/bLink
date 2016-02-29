package com.shiliuke.BabyLink;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.shiliuke.BabyLink.information.InformationUtils;
import com.shiliuke.adapter.ChangeInfoGridPicAdapter;
import com.shiliuke.adapter.SpinnerAdapter;
import com.shiliuke.adapter.TakePartInChangeAdapter;
import com.shiliuke.adapter.TopicPicGridAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.Change;
import com.shiliuke.bean.ChangeInfo;
import com.shiliuke.bean.EnableChange;
import com.shiliuke.bean.Result;
import com.shiliuke.fragment.FragmentMeTakeChange;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewUtil;
import com.shiliuke.view.LCDialog;
import com.shiliuke.view.NoScrollGridView;
import com.shiliuke.view.NoScrollListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by wpt on 2015/10/29.
 */
public class ChangeInfoActivity extends ActivitySupport implements View.OnClickListener, VolleyListerner {
    private Button changeinfo_submit;
    private ImageView changepic;//置换发起人头像
    private TextView changeName;/*置换发起人姓名*/
    private TextView changAddress;//地点
    private NoScrollGridView changeUrl;
    private TextView mineGoods;
    private TextView exchangeGoods;
    private TextView googs_info;// 商品详情
    private TextView changeUser_info;// 交换人联系方式
    private LinearLayout user_info;

    private Bundle model;
    private RelativeLayout pay_cancel;//关闭提示新华联
    private RelativeLayout toast;//信息提示
    private Button submit;
    private Button cancel;

    private RelativeLayout choose;
    private Spinner spinnerBase1;
    private TextView close;
    private TextView finish_text;

    private EnableChange enableChange;
    private EnableChange.Datas eDatas;
    private ChangeInfo.Datas datas;
    private TextView googs_info_text;
    private NoScrollListView change_goode_info;
    private LCDialog lcDialog;
    private TextView positive;
    private TextView cancle;

    @Override
    protected void onRestart() {
        super.onRestart();
        DialogUtil.startDialogLoading(context);
        enableChange();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_changeinfo);
        setCtenterTitle("置换详情");
        Intent intent = getIntent();
        model = (Bundle) intent.getBundleExtra("model");
        changeinfo_submit = (Button) findViewById(R.id.changeinfo_submit);
        changepic = (ImageView) findViewById(R.id.changepic);
        changeUrl = (NoScrollGridView) findViewById(R.id.changeUrl);
        changeName = (TextView) findViewById(R.id.changeName);
        changAddress = (TextView) findViewById(R.id.changAddress);
        mineGoods = (TextView) findViewById(R.id.mineGoods);
        exchangeGoods = (TextView) findViewById(R.id.exchangeGoods);
        googs_info = (TextView) findViewById(R.id.googs_info);
        changeUser_info = (TextView) findViewById(R.id.changeUser_info);
        changeUser_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in2 = new Intent();
                in2.setAction(Intent.ACTION_CALL);//指定意图动作
                in2.setData(Uri.parse("tel:"+changeUser_info.getText()));//指定电话号码
                startActivity(in2);
            }
        });
        user_info = (LinearLayout) findViewById(R.id.user_info);
        change_goode_info = (NoScrollListView) findViewById(R.id.change_goode_info);

        pay_cancel = (RelativeLayout) findViewById(R.id.pay_cancel);
        toast = (RelativeLayout) findViewById(R.id.toast);
        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);

        choose = (RelativeLayout) findViewById(R.id.choose);
        spinnerBase1 = (Spinner) findViewById(R.id.spinnerBase1);
        close = (TextView) findViewById(R.id.close);
        finish_text = (TextView) findViewById(R.id.finish);
        googs_info_text = (TextView) findViewById(R.id.googs_info_text);
        requestData();
        user_info.setVisibility(View.GONE);
        changeinfo_submit.setOnClickListener(this);
        pay_cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        finish_text.setOnClickListener(this);
        close.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeinfo_submit:
                if (datas == null) {
                    return;
                }
                if (TextUtils.isEmpty(datas.getStatus())) {
                    return;
                }
                if ("0".equals(datas.getStatus())) {
                    if (AppConfig.loginInfo.getMember_id().equals(datas.getMember_id())) {

                    } else {
                        if (enableChange.getDatas().size() > 0) {
                            choose.setVisibility(View.VISIBLE);
                        } else {
                            toast.setVisibility(View.VISIBLE);
                        }
                    }
                } else if ("1".equals(datas.getStatus())) {
                    if (AppConfig.loginInfo.getMember_id().equals(datas.getMember_id())) {
                        View views = LayoutInflater.from(context).inflate(R.layout.exit_dialog_view,null);
                        lcDialog = new LCDialog(context,R.style.DialogTheme,views);
                        positive = (TextView) views.findViewById(R.id.positive);
                        cancle = (TextView) views.findViewById(R.id.cancle);
                        positive.setOnClickListener(this);
                        cancle.setOnClickListener(this);
                        lcDialog.show();
                    } else {

                    }
                }
                break;
            case R.id.positive:
                lcDialog.dismiss();
                confirmChange();
                break;
            case R.id.cancle:
                lcDialog.dismiss();
                Intent in2 = new Intent();
                in2.setAction(Intent.ACTION_CALL);//指定意图动作
                in2.setData(Uri.parse("tel:"+changeUser_info.getText()));//指定电话号码
                startActivity(in2);
                break;
            case R.id.pay_cancel:
                //关闭
                toast.setVisibility(View.GONE);
                break;
            case R.id.submit:
                //跳转到发布置换
                Intent intetn = new Intent(context, SendChangeActivity.class);
                startActivity(intetn);
                //关闭
                toast.setVisibility(View.GONE);
                break;
            case R.id.cancel:
                toast.setVisibility(View.GONE);
                break;
            case R.id.close:
                //关闭选择
                choose.setVisibility(View.GONE);
                break;
            case R.id.finish:
                //完成选择
                choose.setVisibility(View.GONE);
                if (eDatas != null) {
                    changeGoods(eDatas.getExchange_id());
                } else {
                    confirmChange();
                }
                break;
        }
    }

    public void changeGoods(String my_exchange_id) {
        DialogUtil.startDialogLoading(context);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("exchange_id", model.getString("exchange_id"));//置换id
        params.put("my_exchange_id", my_exchange_id);
        BasicRequest.getInstance().requestPost(this,
                TaskID.ACTION_ZHIHUAN_EXCHANGE, params, AppConfig.HOME_ZHIHUAN_LIST_EXCHANGE);
    }


    private void requestData() {
        DialogUtil.startDialogLoading(context);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("exchange_id", model.getString("exchange_id"));//置换id
        BasicRequest.getInstance().requestPost(this,
                TaskID.ACTION_ZHIHUAN_LIST_INFO, params, AppConfig.HOME_ZHIHUAN_LIST_INFO, ChangeInfo.class);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.ACTION_ZHIHUAN_LIST_INFO:
                InformationUtils.requestAllNum();
                ChangeInfo changeInfo = (ChangeInfo) obj;
                datas = changeInfo.getDatas();
                ViewUtil.setHeadImage(changepic, model.getString("changepic"), context);//头像
                ViewUtil.setText(context, changeName, model.getString("changeName"));//名字
                ViewUtil.setText(context, changAddress, model.getString("changAddress"));//地址
//                ViewUtil.setImage(changeUrl, model.getImage_url(), context);
                ViewUtil.setText(context, mineGoods, datas.getFrom_gname());//我的物品
                ViewUtil.setText(context, exchangeGoods, datas.getTo_gname());//要置换的物品
                ViewUtil.setText(context, googs_info_text, datas.getInfo());//商品详情

                if (datas.getImages() != null && datas.getImages().size() > 0) {
                    changeUrl.setVisibility(View.VISIBLE);
                    List<String> images = datas.getImages();
                    changeUrl.setAdapter(new ChangeInfoGridPicAdapter(images, context));
                }
                changeUrl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
                        ArrayList<String> news_img = (ArrayList<String>) datas.getImages();
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, news_img);
                        context.startActivity(intent);
                    }
                });


                if ("0".equals(datas.getStatus())) {
                    user_info.setVisibility(View.GONE);
                    if (AppConfig.loginInfo.getMember_id().equals(datas.getMember_id())) {
                        changeinfo_submit.setText("没有人参与置换");
                        changeinfo_submit.setBackgroundResource(R.drawable.button_gray_normal);
                        changeinfo_submit.setEnabled(false);
                    } else {
                        changeinfo_submit.setText("我要置换");
                        changeinfo_submit.setBackgroundResource(R.drawable.button_normal_long);
                        changeinfo_submit.setEnabled(true);
                    }
                } else if ("1".equals(datas.getStatus())) {
                    if (AppConfig.loginInfo.getMember_id().equals(datas.getMember_id())) {
                        user_info.setVisibility(View.VISIBLE);
                        changeinfo_submit.setText("确认置换");
                        changeinfo_submit.setEnabled(true);
                        changeUser_info.setText(datas.getToExchange().getLink_mobile());
                        ChangeInfo.Datas.ToExchange toExchange = datas.getToExchange();
                        List<ChangeInfo.Datas.ToExchange> toExchangeList = new ArrayList<>();
                        toExchangeList.add(toExchange);
                        change_goode_info.setAdapter(new TakePartInChangeAdapter(context, toExchangeList));
                        change_goode_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(context, ChangeInfoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("exchange_id", toExchangeList.get(position).getExchange_id());
                                bundle.putString("changepic", toExchangeList.get(position).getLink_avar());
                                bundle.putString("changeName", toExchangeList.get(position).getLink_name());
                                bundle.putString("changAddress", "");
                                intent.putExtra("model", bundle);
                                context.startActivity(intent);
                            }
                        });
//                        changeinfo_submit.setBackground(getContext().getDrawable(R.drawable.button_normal_long)); s啥网址
                    } else {
                        user_info.setVisibility(View.VISIBLE);
                        changeinfo_submit.setText("置换中");
                        changeUser_info.setText(datas.getLink_mobile());
                        changeinfo_submit.setEnabled(false);
                        changeinfo_submit.setBackgroundResource(R.drawable.button_gray_normal);
                    }
                } else if ("2".equals(datas.getStatus())) {
                    ChangeInfo.Datas.ToExchange toExchange = datas.getToExchange();
                    List<ChangeInfo.Datas.ToExchange> toExchangeList = new ArrayList<>();
                    toExchangeList.add(toExchange);
                    change_goode_info.setAdapter(new TakePartInChangeAdapter(context, toExchangeList));
                    change_goode_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(context, ChangeInfoActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("exchange_id", toExchangeList.get(position).getExchange_id());
                            bundle.putString("changepic", toExchangeList.get(position).getLink_avar());
                            bundle.putString("changeName", toExchangeList.get(position).getLink_name());
                            bundle.putString("changAddress", "");
                            intent.putExtra("model", bundle);
                            context.startActivity(intent);
                        }
                    });
//                    user_info.setVisibility(View.GONE);
                    changeinfo_submit.setText("置换完成");
                    changeinfo_submit.setEnabled(false);
                    changeinfo_submit.setBackgroundResource(R.drawable.button_gray_normal);


                    if (AppConfig.loginInfo.getMember_id().equals(datas.getMember_id())) {
                        user_info.setVisibility(View.VISIBLE);
                        changeUser_info.setText(datas.getToExchange().getLink_mobile());
//                        changeinfo_submit.setBackground(getContext().getDrawable(R.drawable.button_normal_long));
                    } else {
                        user_info.setVisibility(View.VISIBLE);
                        changeUser_info.setText(datas.getLink_mobile());
                    }
                }
                enableChange();
                break;
            case TaskID.ACTION_ZHIHUAN_LIST_ISSUEEXCHANGE:
                DialogUtil.stopDialogLoading(context);
                enableChange = (EnableChange) obj;
                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context, enableChange.getDatas());
                spinnerBase1.setAdapter(spinnerAdapter);
                spinnerBase1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        eDatas = enableChange.getDatas().get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case TaskID.ACTION_ZHIHUAN_EXCHANGE:
                DialogUtil.stopDialogLoading(context);
                choose.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    String code = jsonObject.optString("code", 0 + "");
                    String datas = jsonObject.optString("datas", 0 + "");
                    ToastUtil.showShort(context, datas);
                    requestData();
                } catch (JSONException e) {
                    ToastUtil.showShort(context, "发生点小状况！");
                }

                break;
            case TaskID.FINISH_EXCHANGE:
                DialogUtil.stopDialogLoading(context);
                Result result = (Result) obj;
                if ("0".equals(result.getCode())) {
                    changeinfo_submit.setText("置换完成");
                    changeinfo_submit.setEnabled(false);
                    changeinfo_submit.setBackgroundResource(R.drawable.button_gray_normal);
                    EventBus.getDefault().post(FragmentMeTakeChange.ACTION_REFRESH);
                }
                ToastUtil.showShort(context, result.getDatas());

                break;
        }
    }

    //我可以参与的置换列表
    private void enableChange() {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        BasicRequest.getInstance().requestPost(this,
                TaskID.ACTION_ZHIHUAN_LIST_ISSUEEXCHANGE, params, AppConfig.HOME_ZHIHUAN_LIST_ISSUEEXCHANGE, EnableChange.class);
    }

    //确认置换
    private void confirmChange() {
        DialogUtil.startDialogLoading(context);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("to_exchange_id", datas.getToExchange().getExchange_id());
        params.put("me_exchange_id", datas.getExchange_id());
        BasicRequest.getInstance().requestPost(this,
                TaskID.FINISH_EXCHANGE, params, AppConfig.FINISH_EXCHANGE, Result.class);
    }

    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.ACTION_ZHIHUAN_LIST_INFO:
                ToastUtil.showShort(context, error);
                break;
            case TaskID.ACTION_ZHIHUAN_LIST_ISSUEEXCHANGE:
                DialogUtil.stopDialogLoading(context);
                ToastUtil.showShort(context, error);
                break;
            case TaskID.ACTION_ZHIHUAN_EXCHANGE:
                DialogUtil.stopDialogLoading(context);
                ToastUtil.showShort(context, error);
                break;
            case TaskID.FINISH_EXCHANGE:
                DialogUtil.stopDialogLoading(context);
                ToastUtil.showShort(context, error);
                break;
        }
    }



}
