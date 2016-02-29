package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import com.shiliuke.BabyLink.information.InformationUtils;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.fragment.*;
import com.shiliuke.global.MApplication;
import com.shiliuke.utils.ShareUtils;
import com.shiliuke.view.LCDialog;
import com.shiliuke.view.TabFragmentHost;
import com.squareup.otto.Subscribe;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.update.UmengUpdateAgent;


public class MainTab extends ActivitySupport {

    private long exitTime = 0;
    public TabFragmentHost mTabHost;
    // 标签
    private String[] TabTag = {"tab1", "tab2", "tab3", "tab4", "tab5"};
    // 自定义tab布局显示文本和顶部的图片
    private Integer[] ImgTab = {R.layout.tab_main_home,
            R.layout.tab_main_beanshow, R.layout.tab_main_send, R.layout.tab_main_find, R.layout.tab_main_my};

    // tab 选中的activity
    private Class[] ClassTab = {FragmentHome.class, FragmentBeanShow.class, FragmentSend.class,
            FragmentFind.class, FragmentMy.class};

    // tab选中背景 drawable 样式图片 背景都是同一个,背景颜色都是 白色。。。
    private Integer[] StyleTab = {R.color.black, R.color.black, R.color.black,
            R.color.black, R.color.black};
    private TextView tv_main_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintabs);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        MApplication.getInstance().getBus().register(this);
        setRemoveTitle();
        setupView();
        initValue();
        setLinstener();
        fillData();
    }

    private void setupView() {

        // 实例化framentTabHost
        mTabHost = (TabFragmentHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(),
                android.R.id.tabcontent);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (tv_main_information == null) {
                    tv_main_information = (TextView) findViewById(R.id.tv_main_information);
                    InformationUtils.setInformationView(InformationUtils.InformationKey.All_TAB, tv_main_information);
                }
                InformationUtils.requestAllNum();
                if (s.equals("tab3")) {
                    mTabHost.setCurrentTab(0);
                    showDialog();
                }
//                else if("tab5".equalsIgnoreCase(s)){
//                    InformationUtils.setInformationNum(InformationUtils.InformationKey.All,0);
//                    InformationUtils.updataAllView();
//                }
            }
        });
    }

    private void initValue() {

        // 初始化tab选项卡
        InitTabView();
    }

    private void setLinstener() {
        // imv_back.setOnClickListener(this);

    }

    private void fillData() {
        // TODO Auto-generated method stub

    }


    // 初始化 tab 自定义的选项卡 ///////////////
    private void InitTabView() {

        // 可以传递参数 b;传递公共的userid,version,sid
        Bundle b = new Bundle();
        // 循环加入自定义的tab
        for (int i = 0; i < TabTag.length; i++) {
            // 封装的自定义的tab的样式
            View indicator = getIndicatorView(i);
            mTabHost.addTab(
                    mTabHost.newTabSpec(TabTag[i]).setIndicator(indicator),
                    ClassTab[i], b);// 传递公共参数

        }
        mTabHost.getTabWidget().setDividerDrawable(R.color.white);
    }

    // 设置tab自定义样式:注意 每一个tab xml子布局的linearlayout 的id必须一样
    private View getIndicatorView(int i) {
        // 找到tabhost的子tab的布局视图
        View v = getLayoutInflater().inflate(ImgTab[i], null);
        LinearLayout tv_lay = (LinearLayout) v.findViewById(R.id.layout_back);
        tv_lay.setBackgroundResource(StyleTab[i]);

        return v;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return false;
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(getResources().getString(R.string.exit));
            exitTime = System.currentTimeMillis();
        } else {
            isExit();
        }
    }

    public LCDialog showDialog() {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_addmenu,
                null);
        final LCDialog dialog = new LCDialog(MainTab.this, R.style.MyDialog_menu, dialogView);
        ImageView publish_activity = (ImageView) dialogView.findViewById(R.id.publish_activity);
        ImageView publish_topic = (ImageView) dialogView.findViewById(R.id.publish_topic);
        ImageView publish_show = (ImageView) dialogView.findViewById(R.id.publish_show);
        ImageView publish_swap = (ImageView) dialogView.findViewById(R.id.publish_swap);
        dialog.show();

        publish_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent(MainTab.this, IssueActivity.class);
                dialog.cancel();
            }
        });
        publish_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent(MainTab.this, SendTopicActivity.class);
                dialog.cancel();
            }
        });
        publish_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent(MainTab.this, SendShowActivity.class);
                dialog.cancel();
            }
        });
        publish_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent(MainTab.this, SendChangeActivity.class);
                dialog.cancel();
            }
        });
        return dialog;
    }

    Handler hd = new Handler();

    @Subscribe
    public void handleMessage(Intent intent) {
        MApplication.setSend_Result(intent.getAction());
        hd.post(() -> {
            switch (intent.getAction()) {
                case BaseSendActivity.SENDSHOWSUCCESS://发布秀成功
                    mTabHost.setCurrentTab(1);
                    break;
                case BaseSendActivity.SENDCHANGESUCCESS://发布置换成功
                case BaseSendActivity.SENDISSUESUCCESS://发布活动成功
                case BaseSendActivity.SENDTOPICSUCCESS://发布话题成功
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (tv_main_information == null) {
            tv_main_information = (TextView) findViewById(R.id.tv_main_information);
            InformationUtils.setInformationView(InformationUtils.InformationKey.All_TAB, tv_main_information);
        }
        InformationUtils.requestAllNum();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MApplication.getInstance().getBus().unregister(this);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        UMSsoHandler ssoHandler = UMServiceFactory
                .getUMSocialService("com.umeng.share").getConfig().getSsoHandler(arg0) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(arg0, arg1, arg2);
        }
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == FragmentMy.LOG_OUT && arg1 == RESULT_OK) {
            finish();
        }
    }

}
