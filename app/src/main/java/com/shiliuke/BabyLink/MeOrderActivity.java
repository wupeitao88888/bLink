package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import com.shiliuke.adapter.FragmentAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.fragment.FragmentAlreadyAll;
import com.shiliuke.fragment.FragmentAlreadyDeposit;
import com.shiliuke.fragment.FragmentAlreadyRefund;
import com.shiliuke.fragment.FragmentNotYetDeposit;
import com.shiliuke.utils.FragmentEvent;
import com.shiliuke.utils.MSG;
import com.shiliuke.view.IndexViewPager;

/**
 * 我的订单
 * Created by wupeitao on 15/11/9.
 */
public class MeOrderActivity extends ActivitySupport implements View.OnClickListener, ViewPager.OnPageChangeListener, FragmentEvent.OnEventListener {


    private IndexViewPager order_viewpager;
    private Button not_yet_deposit,//未付订金
            already_all,//已付全部
            already_refund,//退款
            already_deposit;//已付订金

    private FragmentNotYetDeposit fe = new FragmentNotYetDeposit();//未付订金
    private FragmentAlreadyDeposit ft = new FragmentAlreadyDeposit();//已付订金
    private FragmentAlreadyAll fm = new FragmentAlreadyAll();//已付全部
    private FragmentAlreadyRefund fc = FragmentAlreadyRefund.getInstance(this);//退款
    private Fragment[] fragments = {fe, ft, fm, fc};
    private boolean[] fragmentsUpdateFlag = {false, false, false, false};
    private FragmentAdapter adapter;


    Handler mainHandler = new Handler() {

        /*
         * （非 Javadoc）
         *
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            // TODO 自动生成的方法存根
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG.INTO_05:
                    fragments[4] = ft;
                    fragmentsUpdateFlag[4] = true;
                    adapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_order);
        initView();
        setCtenterTitle("已付订金");
    }

    private void initView() {
        order_viewpager = (IndexViewPager) findViewById(R.id.order_viewpager);
        not_yet_deposit = (Button) findViewById(R.id.not_yet_deposit);
        already_deposit = (Button) findViewById(R.id.already_deposit);
        already_all = (Button) findViewById(R.id.already_all);
        already_refund = (Button) findViewById(R.id.already_refund);

        not_yet_deposit.setOnClickListener(this);
        already_deposit.setOnClickListener(this);
        already_all.setOnClickListener(this);
        already_refund.setOnClickListener(this);

        adapter = new FragmentAdapter(this.getSupportFragmentManager(), fragments, fragmentsUpdateFlag);
        order_viewpager.setAdapter(adapter);
        order_viewpager.setOnPageChangeListener(this);
        order_viewpager.setCurrentItem(1);
        already_deposit.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.not_yet_deposit:
                setCtenterTitle("未付定金");
                order_viewpager.setCurrentItem(0);
                not_yet_deposit.setSelected(true);
                already_deposit.setSelected(false);
                already_all.setSelected(false);
                already_refund.setSelected(false);
                break;
            case R.id.already_deposit:
                ft.requestData(true);
                setCtenterTitle("已付订金");
                order_viewpager.setCurrentItem(1);
                not_yet_deposit.setSelected(false);
                already_deposit.setSelected(true);
                already_all.setSelected(false);
                already_refund.setSelected(false);
                break;
            case R.id.already_all:
                setCtenterTitle("已付全部");
                fm.requestData(true);
                order_viewpager.setCurrentItem(2);
                not_yet_deposit.setSelected(false);
                already_deposit.setSelected(false);
                already_all.setSelected(true);
                already_refund.setSelected(false);
                break;
            case R.id.already_refund:
                fc.requestData(true);
                setCtenterTitle("退款");
                order_viewpager.setCurrentItem(3);
                not_yet_deposit.setSelected(false);
                already_deposit.setSelected(false);
                already_all.setSelected(false);
                already_refund.setSelected(true);
                break;

        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onEvent(int what, Bundle data, Object object) {
        mainHandler.sendEmptyMessage(what);
    }
}
