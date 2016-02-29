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
import com.shiliuke.adapter.FriendsAdapter;
import com.shiliuke.adapter.NeighbourAdapter;
import com.shiliuke.bean.Friends;
import com.shiliuke.bean.MeStarChange;
import com.shiliuke.bean.Neighbour;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 我的好友
 */
public class FragmentFriends extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private NOViewPagerPullableListView friends_listView;
    private FriendsAdapter changeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout friends_PullToRefreshLayout;
    private int page = 1;
    private boolean isRefresh = true;
    private List<Friends.Data> datas;
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
            rootView = inflater.inflate(R.layout.fragment_friends, null);
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
        friends_listView = (NOViewPagerPullableListView) rootView.findViewById(R.id.friends_listView);
        friends_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.friends_PullToRefreshLayout);
        friends_listView.setDivider(null);

        friends_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
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
        friends_PullToRefreshLayout.autoRefresh();
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
        BasicRequest.getInstance().requestPost(FragmentFriends.this, TaskID.FRIENDS, params, AppConfig.FRIEND, Friends.class);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.FRIENDS:
                DialogUtil.stopDialogLoading(mActivity);
                friends_PullToRefreshLayout.refreshFinish(friends_PullToRefreshLayout.SUCCEED);
                friends_PullToRefreshLayout.loadmoreFinish(friends_PullToRefreshLayout.SUCCEED);
                Friends friends = (Friends) obj;
                if (isRefresh) {
                    datas = friends.getDatas();
                    changeAdapter = new FriendsAdapter(mActivity, friends.getDatas());
                    friends_listView.setAdapter(changeAdapter);
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
            case TaskID.FRIENDS:
                DialogUtil.stopDialogLoading(mActivity);
                friends_PullToRefreshLayout.refreshFinish(friends_PullToRefreshLayout.SUCCEED);
                friends_PullToRefreshLayout.loadmoreFinish(friends_PullToRefreshLayout.SUCCEED);
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
