package com.shiliuke.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import com.shiliuke.BabyLink.BaseSendActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.global.MApplication;
import com.shiliuke.utils.FragmentEvent;
import com.shiliuke.utils.MSG;
import com.shiliuke.view.IndexViewPager;


public class FragmentHome extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener, FragmentEvent.OnEventListener {
    private View rootView;
    private Activity mActivity = null;
    private RelativeLayout meCommunity_PullScrollView;
    private FragmentTabHost mTabHost = null;
    private IndexViewPager id_viewpager;
    public FragmentExercise fe = new FragmentExercise();
    public FragmentTopic ft = new FragmentTopic();
    public FragmentChange fc = new FragmentChange();
    private Fragment[] fragments = {fe, ft, fc};
    private boolean[] fragmentsUpdateFlag = {false, false, false};
    private FragmentPagerAdapter adapter;
    public RelativeLayout tab_exrcise;
    public RelativeLayout tab_topic;
    public RelativeLayout tab_change;
    private RelativeLayout top_View;
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
                    fragments[3] = fc;
                    fragmentsUpdateFlag[3] = true;
                    adapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    };

    public void setItem(int item) {
        switch (item) {
            case 0:
                id_viewpager.setCurrentItem(0);
                fe.autoRefresh();
                tab_exrcise.setSelected(true);
                tab_topic.setSelected(false);
                tab_change.setSelected(false);
                break;
            case 1:
                id_viewpager.setCurrentItem(1);
                ft.autoRefresh();
                tab_exrcise.setSelected(false);
                tab_topic.setSelected(true);
                tab_change.setSelected(false);
                break;
            case 2:
                id_viewpager.setCurrentItem(2);
                fc.autoRefresh();
                tab_exrcise.setSelected(false);
                tab_topic.setSelected(false);
                tab_change.setSelected(true);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            initView(rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        mActivity = this.getActivity();
        top_View = (RelativeLayout) rootView.findViewById(R.id.top_View);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            top_View.setPadding(0, (int) ViewUtil.dip2px(mActivity,25.0f),0,0);
//        } else {
        top_View.setPadding(0, 0, 0, 0);
//        }
        meCommunity_PullScrollView = (RelativeLayout) rootView.findViewById(R.id.meCommunity_PullScrollView);
//        dragLayout = (DragTopLayout) rootView.findViewById(R.id.drag_layout);
        tab_exrcise = (RelativeLayout) rootView.findViewById(R.id.tab_exrcise);
        tab_topic = (RelativeLayout) rootView.findViewById(R.id.tab_topic);
        tab_change = (RelativeLayout) rootView.findViewById(R.id.tab_change);
        tab_exrcise.setOnClickListener(this);
        tab_topic.setOnClickListener(this);
        tab_change.setOnClickListener(this);
        id_viewpager = (IndexViewPager) rootView.findViewById(R.id.id_viewpager);
        adapter = new MyFragmentPagerAdapter(getFragmentManager());
        id_viewpager.setAdapter(adapter);
        id_viewpager.setOnPageChangeListener(this);
        id_viewpager.setCurrentItem(0);
        tab_exrcise.setSelected(true);
        tab_topic.setSelected(false);
        tab_change.setSelected(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_exrcise:
                id_viewpager.setCurrentItem(0);
                tab_exrcise.setSelected(true);
                tab_topic.setSelected(false);
                tab_change.setSelected(false);
                break;
            case R.id.tab_topic:
                id_viewpager.setCurrentItem(1);
                tab_exrcise.setSelected(false);
                tab_topic.setSelected(true);
                tab_change.setSelected(false);
                break;
            case R.id.tab_change:
                id_viewpager.setCurrentItem(2);
                tab_exrcise.setSelected(false);
                tab_topic.setSelected(false);
                tab_change.setSelected(true);
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


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        FragmentManager fm;

        MyFragmentPagerAdapter(FragmentManager fm) {
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

    @Override
    public void onResume() {
        super.onResume();
        if (MApplication.hasNew(BaseSendActivity.SENDCHANGESUCCESS)) {//发布置换成功
            tab_change.performClick();
            fc.updateData();
        } else if (MApplication.hasNew(BaseSendActivity.SENDISSUESUCCESS)) {//发布活动成功
            tab_exrcise.performClick();
            fe.updateData();
        } else if (MApplication.hasNew(BaseSendActivity.SENDTOPICSUCCESS)) {//发布话题成功
            tab_topic.performClick();
            ft.updateData();
        }
    }

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
