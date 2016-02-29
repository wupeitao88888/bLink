package com.shiliuke.fragment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.AboutActivity;
import com.shiliuke.BabyLink.HelpActivity;
import com.shiliuke.BabyLink.MeConvertCodeActivity;
import com.shiliuke.BabyLink.MeHomePage;
import com.shiliuke.BabyLink.MeOrderActivity;
import com.shiliuke.BabyLink.PayEndActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.PersonalData;
import com.shiliuke.BabyLink.information.InformationUtils;
import com.shiliuke.bean.ActivityInfo;
import com.shiliuke.bean.OrderNum;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.GlideCircleTransform;
import com.shiliuke.utils.LCSharedPreferencesHelper;
import com.shiliuke.view.TitleBar;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import java.util.HashMap;
import java.util.Map;


public class FragmentMy extends Fragment implements OnClickListener {

    public static final int LOG_OUT = 100;

    private View rootView;
    private Activity mActivity = null;

    private RelativeLayout user_home_rl,//我的主页
            user_order_rl,//我的订单
            user_information_rl,//个人资料
            user_help_rl,//帮助中心
            version_update_rl,//版本升级
            exchange_code_rl,//兑换码
            pay_rl;//支付尾款
    private TitleBar title_my;
    private TextView user_name;
    private ImageView user_icon_image;
    private TextView exchange_count;
    private TextView pay_count;
    private TextView tv_user_information;
    private LCSharedPreferencesHelper sharedPreferencesHelper;
    private RelativeLayout about_rl;
    private TextView update;
    private ImageView backg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = initView();

        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private View initView() {
        mActivity = getActivity();
        sharedPreferencesHelper = new LCSharedPreferencesHelper(getActivity(), AppConfig.SHARED_PATH);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_me, null);
        user_home_rl = (RelativeLayout) view.findViewById(R.id.user_home_rl);
        backg= (ImageView) view.findViewById(R.id.backg);
        title_my = (TitleBar) view.findViewById(R.id.title_my);
        user_order_rl = (RelativeLayout) view.findViewById(R.id.user_order_rl);
        user_icon_image = (ImageView) view.findViewById(R.id.user_icon_image);
        user_information_rl = (RelativeLayout) view.findViewById(R.id.user_information_rl);
        user_help_rl = (RelativeLayout) view.findViewById(R.id.user_help_rl);
        version_update_rl = (RelativeLayout) view.findViewById(R.id.version_update_rl);
        about_rl = (RelativeLayout) view.findViewById(R.id.about_rl);
        exchange_code_rl = (RelativeLayout) view.findViewById(R.id.exchange_code_rl);
        pay_rl = (RelativeLayout) view.findViewById(R.id.pay_rl);
        user_name = (TextView) view.findViewById(R.id.user_name);
        exchange_count = (TextView) view.findViewById(R.id.exchange_count);
        pay_count = (TextView) view.findViewById(R.id.pay_count);
        tv_user_information = (TextView) view.findViewById(R.id.tv_user_information);
        update = (TextView) view.findViewById(R.id.update);
        title_my.setCenterTitle("我");
        title_my.isLeftVisibility(false);
        user_name.setText(sharedPreferencesHelper.getValue("member_name"));
        Glide.with(mActivity)
                .load(AppConfig.loginInfo.getMember_avar())
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(mActivity))
                .placeholder(R.mipmap.morentoux)
                .error(R.mipmap.morentoux)
                .crossFade()
                .into(user_icon_image);
        user_home_rl.setOnClickListener(this);
        user_order_rl.setOnClickListener(this);
        user_information_rl.setOnClickListener(this);
        user_help_rl.setOnClickListener(this);
        version_update_rl.setOnClickListener(this);
        about_rl.setOnClickListener(this);
        exchange_code_rl.setOnClickListener(this);
        pay_rl.setOnClickListener(this);
//
        Glide.with(mActivity)
                .load(R.drawable.my_home_bg).override(360,224)
                .into(backg);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(mActivity);
        try {
            user_name.setText(sharedPreferencesHelper.getValue("member_name"));
            Glide.with(mActivity)
                    .load(AppConfig.loginInfo.getMember_avar())
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).transform(new GlideCircleTransform(mActivity))
                    .placeholder(R.mipmap.morentoux)
                    .error(R.mipmap.morentoux)
                    .crossFade()
                    .into(user_icon_image);
            InformationUtils.setInformationView(InformationUtils.InformationKey.All_USER, tv_user_information);
            InformationUtils.setInformationView(InformationUtils.InformationKey.CODE, exchange_count);
            InformationUtils.setInformationView(InformationUtils.InformationKey.WEIKUAN, pay_count);
            InformationUtils.updataAllView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_home_rl:
                // 我的主页
                startActivity(new Intent(mActivity, MeHomePage.class));
                break;
            case R.id.user_order_rl:
                //我的订单
                startActivity(new Intent(mActivity, MeOrderActivity.class));
                break;
            case R.id.user_information_rl:
                //个人资料
                getActivity().startActivityForResult(new Intent(mActivity, PersonalData.class), LOG_OUT);
                break;
            case R.id.user_help_rl:
                //帮助中心
                startActivity(new Intent(mActivity, HelpActivity.class));
                break;
            case R.id.version_update_rl:
                //版本升级
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus,
                                                 UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(getActivity(),
                                        updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                Toast.makeText(getActivity(), "暂无最新版本",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Toast.makeText(getActivity(), "没有wifi连接， 只在wifi下更新",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.Timeout: // time out
                                Toast.makeText(getActivity(), "超时", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                        }
                    }

                });
                UmengUpdateAgent.forceUpdate(mActivity);
                UmengUpdateAgent.setDownloadListener(new UmengDownloadListener() {

                    @Override
                    public void OnDownloadUpdate(int arg0) {
                        // TODO Auto-generated method stub
                        Log.e("++++++++++", "下载中" + arg0);
                        update.setText("下载中" + arg0);
                    }

                    @Override
                    public void OnDownloadStart() {
                        // TODO Auto-generated method stub
                        Log.e("++++++++++", "开始");
                    }

                    @Override
                    public void OnDownloadEnd(int arg0, String arg1) {
                        // TODO Auto-generated method stub
                        Log.e("++++++++++", "结束：" + arg1);
                        update.setText("检查更新");
                    }
                });
                break;
            case R.id.exchange_code_rl:
                //兑换码
                startActivity(new Intent(mActivity, MeConvertCodeActivity.class));
                break;
            case R.id.pay_rl:
                startActivity(new Intent(mActivity, PayEndActivity.class));
                break;
            case R.id.about_rl:
                startActivity(new Intent(mActivity, AboutActivity.class));
                break;

        }
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(mActivity);
    }
//    @Override
//    public void onResponse(String str, int taskid, Object obj) {
//        if(null != obj){
//            OrderNum orderNum = (OrderNum) obj;
//            exchange_count.setText(orderNum.getDatas().getCode_num()+"");
//            pay_count.setText(orderNum.getDatas().getWei_num()+"");
//        }
//    }
//
//    @Override
//    public void onResponseError(String error, int taskid) {
//
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(rootView);
    }
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
