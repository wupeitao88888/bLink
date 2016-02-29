package com.shiliuke.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.shiliuke.BabyLink.MainTab;
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.ChangeAdapter;
import com.shiliuke.bean.Change;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 置换
 */
public class FragmentChange extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private PullableListView change_listView;
    private ChangeAdapter changeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout change_PullToRefreshLayout;
    private ArrayList<Change.Data> data;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_change, null);
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
    public void autoRefresh(){
        change_PullToRefreshLayout.autoRefresh();
    }
    private void initView(View rootView) {
        mActivity = getActivity();
        change_listView = (PullableListView) rootView.findViewById(R.id.change_listView);
        change_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.change_PullToRefreshLayout);
        change_listView.setDivider(null);

        change_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                page = 1;
                requestData(true);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                page++;
                requestData(false);
            }
        });
        data = new ArrayList<>();
        changeAdapter = new ChangeAdapter(mActivity, data);
        change_listView.setAdapter(changeAdapter);

        requestData(true);

    }

    private void requestData(boolean isRefresh) {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("page", page + "");
        BasicRequest.getInstance().requestPost(this,
                isRefresh ? TaskID.ACTION_ZHIHUAN_LIST : TaskID.ACTION_ZHIHUAN_LIST_LOADMORE, params, AppConfig.HOME_ZHIHUAN_LIST, Change.class);
    }


    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.ACTION_ZHIHUAN_LIST:
                data.clear();
                data.addAll(((Change) obj).getDatas());
                changeAdapter.notifyDataSetChanged();
                change_PullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case TaskID.ACTION_ZHIHUAN_LIST_LOADMORE:
                data.addAll(((Change) obj).getDatas());
                changeAdapter.notifyDataSetChanged();
                change_PullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        ((MainTab) getActivity()).showToast(error);
        switch (taskid) {
            case TaskID.ACTION_ZHIHUAN_LIST:
                change_PullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case TaskID.ACTION_ZHIHUAN_LIST_LOADMORE:
                change_PullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }
    }

    public void updateData() {
        if (change_PullToRefreshLayout != null) {
            change_PullToRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(rootView);
    }

    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
