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
import com.shiliuke.fragment.FragmentConsume;
import com.shiliuke.fragment.FragmentMeStartChange;
import com.shiliuke.fragment.FragmentMeTakeChange;
import com.shiliuke.fragment.FragmentUnconsume;
import com.shiliuke.utils.FragmentEvent;
import com.shiliuke.utils.MSG;
import com.shiliuke.view.IndexViewPager;

/**
 * 我的置换
 * Created by wupeitao on 15/11/8.
 */
public class MeChangeActivity extends ActivitySupport implements View.OnClickListener, ViewPager.OnPageChangeListener, FragmentEvent.OnEventListener {
    private IndexViewPager mechange_viewpager;
    private Button me_start_change,//我组织的
            me_take_change;//我参与的

    private FragmentMeStartChange fe = new FragmentMeStartChange();
    private FragmentMeTakeChange ft = new FragmentMeTakeChange();

    private Fragment[] fragments = {ft, fe};
    private boolean[] fragmentsUpdateFlag = {false, false};
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
                    fragments[2] = ft;
                    fragmentsUpdateFlag[2] = true;
                    adapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mechange);
        setCtenterTitle("我的置换");
        initView();
    }

    private void initView() {
        mechange_viewpager = (IndexViewPager) findViewById(R.id.mechange_viewpager);
        me_start_change = (Button) findViewById(R.id.me_start_change);
        me_take_change = (Button) findViewById(R.id.me_take_change);
        me_start_change.setOnClickListener(this);
        me_take_change.setOnClickListener(this);
        adapter = new FragmentAdapter(this.getSupportFragmentManager(), fragments, fragmentsUpdateFlag);
        mechange_viewpager.setAdapter(adapter);
        mechange_viewpager.setOnPageChangeListener(this);
        mechange_viewpager.setCurrentItem(0);
        me_take_change.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_start_change:
                mechange_viewpager.setCurrentItem(1);
                me_start_change.setSelected(true);
                me_take_change.setSelected(false);
                break;
            case R.id.me_take_change:
                mechange_viewpager.setCurrentItem(0);
                me_start_change.setSelected(false);
                me_take_change.setSelected(true);
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
