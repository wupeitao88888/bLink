package com.shiliuke.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.ChangeAdapter;
import com.shiliuke.adapter.FriendsAdapter;
import com.shiliuke.adapter.NeighbourAdapter;
import com.shiliuke.bean.Change;
import com.shiliuke.bean.Friends;
import com.shiliuke.bean.Neighbour;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 我的邻居
 */
public class FragmentNeighbour extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private NOViewPagerPullableListView neighbour_listView;
    private NeighbourAdapter changeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout neighbour_PullToRefreshLayout;
    private List<Neighbour.Datas> datas;
    private boolean isRefresh = true;
    private int page = 1;
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
            rootView = inflater.inflate(R.layout.fragment_neighbour, null);
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
        neighbour_listView = (NOViewPagerPullableListView) rootView.findViewById(R.id.neighbour_listView);
        neighbour_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.neighbour_PullToRefreshLayout);
        neighbour_listView.setDivider(null);

        neighbour_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                isRefresh = true;
                loading();

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉
                isRefresh = false;
                loading();

            }
        });

        neighbour_PullToRefreshLayout.autoRefresh();
    }

    public void loading() {
        if (isRefresh) {
            page = 1;
        } else {
            page++;
        }

        DialogUtil.startDialogLoading(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
//        params.put("member_id", "1");
        params.put("page", page + "");
        BasicRequest.getInstance().requestPost(FragmentNeighbour.this, TaskID.NEIGHBOR, params, AppConfig.NEIGHBOR, Neighbour.class);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.NEIGHBOR:
                DialogUtil.stopDialogLoading(mActivity);
                neighbour_PullToRefreshLayout.refreshFinish(neighbour_PullToRefreshLayout.SUCCEED);
                neighbour_PullToRefreshLayout.loadmoreFinish(neighbour_PullToRefreshLayout.SUCCEED);
                Neighbour friends = (Neighbour) obj;
                if (isRefresh) {
                    datas = friends.getDatas();
                    changeAdapter = new NeighbourAdapter(mActivity, datas);
                    neighbour_listView.setAdapter(changeAdapter);
                } else {
                    datas.addAll(friends.getDatas());
                    changeAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.NEIGHBOR:
                DialogUtil.stopDialogLoading(mActivity);
                neighbour_PullToRefreshLayout.refreshFinish(neighbour_PullToRefreshLayout.SUCCEED);
                neighbour_PullToRefreshLayout.loadmoreFinish(neighbour_PullToRefreshLayout.SUCCEED);
                ToastUtil.showShort(mActivity, error);
                break;
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
