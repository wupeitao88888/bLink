package com.shiliuke.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.shiliuke.BabyLink.MeChange;
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.MeStarChangeAdapter;
import com.shiliuke.bean.MeStarChange;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;


/**
 * 我发起的置换
 */
public class FragmentMeStartChange extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private PullableListView mestarchange_listView;
    private MeStarChangeAdapter meStarChangeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout mestarchange_PullToRefreshLayout;
    private List<MeStarChange.Datas> datas;

    @Override
    public void onResume() {
        super.onResume();
        // FT = true;
        // L.e(TAG, "onResume");
        MobclickAgent.onResume(mActivity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // register
        EventBus.getDefault().register(this);
    }

    public void onEvent(Object event) {
        int obj = (int) event;
        if (obj == FragmentMeTakeChange.ACTION_REFRESH) {
            isAutoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
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

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mestarchange, null);
            initView(rootView);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public void isAutoRefresh() {
        mestarchange_PullToRefreshLayout.autoRefresh();
    }

    private void initView(View rootView) {
        mActivity = getActivity();
        mestarchange_listView = (PullableListView) rootView.findViewById(R.id.mestarchange_listView);
        mestarchange_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.mestarchange_PullToRefreshLayout);
        mestarchange_listView.setDivider(null);

        mestarchange_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作


                loading();

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mestarchange_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });


        mestarchange_PullToRefreshLayout.autoRefresh();
    }

    public void loading() {
        DialogUtil.startDialogLoading(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("role", "1");
        BasicRequest.getInstance().requestPost(FragmentMeStartChange.this, TaskID.MECHANGE_TAKE, params, AppConfig.MECHANGE, MeStarChange.class);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.MECHANGE_TAKE:
                DialogUtil.stopDialogLoading(getContext());
                mestarchange_PullToRefreshLayout.refreshFinish(mestarchange_PullToRefreshLayout.SUCCEED);
                MeStarChange meStarChange = (MeStarChange) obj;
                datas = meStarChange.getDatas();
                meStarChangeAdapter = new MeStarChangeAdapter(mActivity, datas);
                mestarchange_listView.setAdapter(meStarChangeAdapter);
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.MECHANGE_TAKE:
                DialogUtil.stopDialogLoading(getContext());
                mestarchange_PullToRefreshLayout.refreshFinish(mestarchange_PullToRefreshLayout.SUCCEED);
                break;
        }
    }



}
