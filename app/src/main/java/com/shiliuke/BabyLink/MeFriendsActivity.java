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
import com.shiliuke.fragment.FragmentFriends;
import com.shiliuke.fragment.FragmentMeStartChange;
import com.shiliuke.fragment.FragmentMeTakeChange;
import com.shiliuke.fragment.FragmentNeighbour;
import com.shiliuke.utils.FragmentEvent;
import com.shiliuke.utils.MSG;
import com.shiliuke.view.IndexViewPager;

/**
 * 我的好友
 * Created by wupeitao on 15/11/8.
 */
public class MeFriendsActivity extends ActivitySupport implements View.OnClickListener, ViewPager.OnPageChangeListener, FragmentEvent.OnEventListener {
    private IndexViewPager mefrined_viewpager;
    private Button me_neighbour,//我组织的
            me_friends;//我参与的

    private FragmentNeighbour fe = new FragmentNeighbour();
    private FragmentFriends ft = new FragmentFriends();

    private Fragment[] fragments = {fe, ft};
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
        setContentView(R.layout.layout_mefriends);
        initView();
    }

    private void initView() {
        setCtenterTitle("我的好友");
        mefrined_viewpager = (IndexViewPager) findViewById(R.id.mefrined_viewpager);
        me_neighbour = (Button) findViewById(R.id.me_neighbour);
        me_friends = (Button) findViewById(R.id.me_friends);
        me_neighbour.setOnClickListener(this);
        me_friends.setOnClickListener(this);
        adapter = new FragmentAdapter(this.getSupportFragmentManager(), fragments, fragmentsUpdateFlag);
        mefrined_viewpager.setAdapter(adapter);
        mefrined_viewpager.setOnPageChangeListener(this);
        mefrined_viewpager.setCurrentItem(0);
        me_neighbour.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_neighbour:
                mefrined_viewpager.setCurrentItem(0);
                me_neighbour.setSelected(true);
                me_friends.setSelected(false);
                break;
            case R.id.me_friends:
                mefrined_viewpager.setCurrentItem(1);
                me_neighbour.setSelected(false);
                me_friends.setSelected(true);
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
