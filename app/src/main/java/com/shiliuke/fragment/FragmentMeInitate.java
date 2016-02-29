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
import com.shiliuke.adapter.MeInitateAdapter;
import com.shiliuke.bean.Change;
import com.shiliuke.bean.MeInitateActivity;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 我组织的活动
 */
public class FragmentMeInitate extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private PullableListView initate_listView;
    private MeInitateAdapter meInitateAdapter;
    private Activity mActivity;
    private PullToRefreshLayout initate_PullToRefreshLayout;
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
            rootView = inflater.inflate(R.layout.fragment_meinitate, null);
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
        initate_listView = (PullableListView) rootView.findViewById(R.id.initate_listView);
        initate_listView.setDivider(null);
        initate_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.initate_PullToRefreshLayout);


        initate_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                setResponse();


            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
               ToastUtil.showShort(mActivity,"没有更多数据");
                    initate_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);


                }
            }

            );
        initate_PullToRefreshLayout.autoRefresh();

        }


    public void setResponse() {
//        DialogUtil.startDialogLoading(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
//        params.put("member_id", "1");
        BasicRequest.getInstance().requestPost(FragmentMeInitate.this, TaskID.ME_ACTIVTY, params, AppConfig.ME_ACTIVTY, MeInitateActivity.class);
    }


    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.ME_ACTIVTY:
                initate_PullToRefreshLayout.refreshFinish(initate_PullToRefreshLayout.SUCCEED);
//                DialogUtil.stopDialogLoading(mActivity);
                MeInitateActivity meInitateActivity = (MeInitateActivity) obj;
                List<MeInitateActivity.Data> datas = meInitateActivity.getDatas();
                meInitateAdapter = new MeInitateAdapter(mActivity, datas,true);
                initate_listView.setAdapter(meInitateAdapter);
                break;
        }

    }

    @Override
    public void onResponseError(String error, int taskid) {

        switch (taskid) {
            case TaskID.ME_ACTIVTY:
                initate_PullToRefreshLayout.refreshFinish(initate_PullToRefreshLayout.SUCCEED);
//                DialogUtil.stopDialogLoading(mActivity);
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
