package com.shiliuke.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.alibaba.fastjson.JSON;
import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.UnconsumeCodeActivity;
import com.shiliuke.adapter.UnconsumeAdapter;
import com.shiliuke.bean.Unconsume;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.LCSharedPreferencesHelper;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 未消费
 */
public class FragmentUnconsume extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private PullableListView unconsume_listView;
    private UnconsumeAdapter unconsumeAdapter;
    private Activity mActivity;
    private PullToRefreshLayout unconsume_PullToRefreshLayout;
    private LCSharedPreferencesHelper sharedPreferencesHelper;
    private List<Unconsume.Data> mList = new ArrayList<>();
    private int page;
    private boolean isrefresh = true;

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
        sharedPreferencesHelper = new LCSharedPreferencesHelper(getActivity(), AppConfig.SHARED_PATH);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_unconsume, null);
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
        unconsume_listView = (PullableListView) rootView.findViewById(R.id.unconsume_listView);
        unconsume_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.unconsume_PullToRefreshLayout);


        unconsume_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                isrefresh = true;
                getUnconsumeData();
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
                isrefresh = false;
                getUnconsumeData();
            }
        });
        unconsumeAdapter = new UnconsumeAdapter(mActivity, mList);
        unconsume_listView.setAdapter(unconsumeAdapter);
        getUnconsumeData();
        unconsume_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, UnconsumeCodeActivity.class);
                intent.putExtra("unconsume", mList.get(position));
                startActivity(intent);
            }
        });
    }

    private void getUnconsumeData() {
        if (isrefresh) {
            page = 1;
        } else {
            page++;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("member_id", sharedPreferencesHelper.getValue("member_id"));
        params.put("order_status", "2,4");
        params.put("page", page + "");
        BasicRequest.getInstance().requestPost(this, TaskID.ACTION_UNGET_DUIHUANMA, params, AppConfig.DUIHUANMA_LIST);
    }


    @Override
    public void onResponse(String str, int taskid, Object obj) {
        if (taskid == TaskID.ACTION_UNGET_DUIHUANMA) {
            Unconsume unconsume = JSON.parseObject(str, Unconsume.class);
            if (isrefresh) {
                mList.clear();
                unconsume_PullToRefreshLayout.refreshFinish(unconsume_PullToRefreshLayout.SUCCEED);
            } else {
                unconsume_PullToRefreshLayout.loadmoreFinish(unconsume_PullToRefreshLayout.SUCCEED);
            }
            mList.addAll(unconsume.getDatas());
            unconsumeAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResponseError(String error, int taskid) {

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
