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

import com.shiliuke.BabyLink.ChangeInfoActivity;
import com.shiliuke.BabyLink.MeChange;
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.JionChangeAdapter;
import com.shiliuke.adapter.MeStarChangeAdapter;
import com.shiliuke.bean.MeStarChange;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.L;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;


/**
 * 我参与的置换
 */
public class FragmentMeTakeChange extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private PullableListView metakechange_listView;
    private JionChangeAdapter meStarChangeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout metakechange_PullToRefreshLayout;
    private List<MeStarChange.Datas> datas;
    public static final int  ACTION_REFRESH=1100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_metakechange, null);
            initView(rootView);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
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

    public void isAutoRefresh() {
        metakechange_PullToRefreshLayout.autoRefresh();
    }

    private void initView(View rootView) {
        mActivity = getActivity();
        metakechange_listView = (PullableListView) rootView.findViewById(R.id.metakechange_listView);
        metakechange_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.metakechange_PullToRefreshLayout);


        metakechange_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        loading();

                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作

                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        metakechange_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        metakechange_PullToRefreshLayout.autoRefresh();
    }

    public void loading() {
        DialogUtil.startDialogLoading(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("role", "2");
        BasicRequest.getInstance().requestPost(FragmentMeTakeChange.this, TaskID.MECHANGE, params, AppConfig.MECHANGE, MeStarChange.class);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {

        switch (taskid) {
            case TaskID.MECHANGE:
                DialogUtil.stopDialogLoading(getContext());
                L.e(str);
                metakechange_PullToRefreshLayout.refreshFinish(metakechange_PullToRefreshLayout.SUCCEED);
                MeStarChange meStarChange = (MeStarChange) obj;
                datas = meStarChange.getDatas();
                meStarChangeAdapter = new JionChangeAdapter(mActivity, datas);
                metakechange_listView.setAdapter(meStarChangeAdapter);
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.MECHANGE:
                DialogUtil.stopDialogLoading(getContext());
                metakechange_PullToRefreshLayout.refreshFinish(metakechange_PullToRefreshLayout.SUCCEED);
                break;
        }
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
