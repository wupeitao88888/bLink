package com.shiliuke.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.UnconsumeCodeActivity;
import com.shiliuke.adapter.NotYetDepositAdapter;
import com.shiliuke.adapter.UnconsumeAdapter;
import com.shiliuke.bean.PayEnd;
import com.shiliuke.bean.Unconsume;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;


/**
 * 未付订金
 */
public class FragmentNotYetDeposit extends Fragment {
    private View rootView;//缓存Fragment view
    private NOViewPagerPullableListView not_yet_deposit_listView;
    private NotYetDepositAdapter unconsumeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout not_yet_deposit_PullToRefreshLayout;
    @Override
    public void onResume() {
        super.onResume();
        // FT = true;
        // L.e(TAG, "onResume");
        MobclickAgent.onResume(mActivity);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(mActivity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_not_yet_deposit, null);
            initView(rootView);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView(View rootView) {
        mActivity = getActivity();
        not_yet_deposit_listView = (NOViewPagerPullableListView) rootView.findViewById(R.id.not_yet_deposit_listView);
        not_yet_deposit_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.not_yet_deposit_PullToRefreshLayout);


        not_yet_deposit_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        not_yet_deposit_PullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        not_yet_deposit_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        List<PayEnd> mList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
//            PayEnd ma = new PayEnd("去国土局", "", "","1000","");
//            mList.add(ma);
        }

        unconsumeAdapter = new NotYetDepositAdapter(mActivity, mList);
        not_yet_deposit_listView.setAdapter(unconsumeAdapter);

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
