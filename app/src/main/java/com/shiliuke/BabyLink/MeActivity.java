package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shiliuke.base.ActivitySupport;
import com.shiliuke.fragment.FragmentExercise;
import com.shiliuke.fragment.FragmentMeInitate;
import com.shiliuke.fragment.FragmentMeTakePartIn;
import com.shiliuke.fragment.FragmentTopic;
import com.shiliuke.utils.FragmentEvent;
import com.shiliuke.utils.MSG;
import com.shiliuke.view.IndexViewPager;

/**
 * 我的活动
 * Created by wupeitao on 15/11/5.
 */
public class MeActivity extends ActivitySupport implements View.OnClickListener, ViewPager.OnPageChangeListener,FragmentEvent.OnEventListener {
    private IndexViewPager id_viewpager;
    private Button me_initiate,//我组织的
            me_take_part_in;//我参与的

    private FragmentMeInitate fe = new FragmentMeInitate();
    private FragmentMeTakePartIn ft = new FragmentMeTakePartIn();

    private Fragment[] fragments = {fe, ft};
    private boolean[] fragmentsUpdateFlag = {false, false};
    private MyAdapter adapter;







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
        setContentView(R.layout.layout_meactivity);
        initView();
    }

    private void initView() {
        setCtenterTitle("我的活动");
        id_viewpager = (IndexViewPager) findViewById(R.id.me_viewpager);
        me_initiate = (Button) findViewById(R.id.me_initiate);
        me_take_part_in = (Button) findViewById(R.id.me_take_part_in);
        me_initiate.setOnClickListener(this);
        me_take_part_in.setOnClickListener(this);
        adapter = new MyAdapter(this.getSupportFragmentManager());
        id_viewpager.setAdapter(adapter);
        id_viewpager.setOnPageChangeListener(this);
        id_viewpager.setCurrentItem(0);
        me_initiate.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_initiate:
                id_viewpager.setCurrentItem(0);
                me_initiate.setSelected(true);
                me_take_part_in.setSelected(false);
                break;
            case R.id.me_take_part_in:
                id_viewpager.setCurrentItem(1);
                me_initiate.setSelected(false);
                me_take_part_in.setSelected(true);
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
    class MyAdapter extends FragmentPagerAdapter {
        FragmentManager fm;

        MyAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments[position % fragments.length];
            return fragments[position % fragments.length];
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //重载该方法，防止其它视图被销毁，防止加载视图卡顿
            // super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);

            String fragmentTag = fragment.getTag();

            if (fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]) {


                FragmentTransaction ft = fm.beginTransaction();

                ft.remove(fragment);

                fragment = fragments[position % fragments.length];

                ft.add(container.getId(), fragment, fragmentTag);
                ft.attach(fragment);
                ft.commit();


                fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
            }

            return fragment;
        }
    }
}
