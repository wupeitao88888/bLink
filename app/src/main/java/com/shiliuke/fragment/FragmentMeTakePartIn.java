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

import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.MeInitateAdapter;
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
 * 我我参与的
 */
public class FragmentMeTakePartIn extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private PullableListView me_take_part_in_listView;
    private Activity mActivity;
    private PullToRefreshLayout me_take_part_in_PullToRefreshLayout;
    private MeInitateAdapter meInitateAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_me_takepartin, null);
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
        me_take_part_in_listView = (PullableListView) rootView.findViewById(R.id.me_take_part_in_listView);
        me_take_part_in_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.me_take_part_in_PullToRefreshLayout);
        me_take_part_in_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                setResponse();

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
                ToastUtil.showShort(mActivity, "没有更多数据");
                me_take_part_in_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);


            }
        });

//        List<MeInitateActivity> mList = new ArrayList<>();
//        for(int i=0;i<9;i++){
//            MeInitateActivity ma=new MeInitateActivity("","传媒大学看妹子","2015/11/11 11:11","1","10");
//            mList.add(ma);
//        }
//
//        meInitateAdapter = new MeInitateAdapter(mActivity, mList);
//        me_take_part_in_listView.setAdapter(meInitateAdapter);
        me_take_part_in_PullToRefreshLayout.autoRefresh();
    }

    public void setResponse() {
//        DialogUtil.startDialogLoading(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());

        BasicRequest.getInstance().requestPost(FragmentMeTakePartIn.this, TaskID.ME_ACTIVTY_JION, params, AppConfig.ME_ACTIVTY_JION, MeInitateActivity.class);
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
    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.ME_ACTIVTY_JION:
                me_take_part_in_PullToRefreshLayout.refreshFinish(me_take_part_in_PullToRefreshLayout.SUCCEED);

                MeInitateActivity meInitateActivity = (MeInitateActivity) obj;
                List<MeInitateActivity.Data> datas = meInitateActivity.getDatas();
                meInitateAdapter = new MeInitateAdapter(mActivity, datas,false);
                me_take_part_in_listView.setAdapter(meInitateAdapter);
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.ME_ACTIVTY_JION:
                me_take_part_in_PullToRefreshLayout.refreshFinish(me_take_part_in_PullToRefreshLayout.SUCCEED);
                DialogUtil.stopDialogLoading(mActivity);
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
