package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.shiliuke.BabyLink.information.InformationRequestListener;
import com.shiliuke.adapter.MyShowBeanAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.BeanShowModel;
import com.shiliuke.bean.BeanShowModelResult;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 我的秀逗
 * Created by wupeitao on 15/11/8.
 */
public class MeShowBeanActivity extends ActivitySupport implements VolleyListerner {

    private NOViewPagerPullableListView listview_beanshow;
    private MyShowBeanAdapter meStarChangeAdapter;
    private PullToRefreshLayout beanshow_PullToRefreshLayout;
    private ArrayList<BeanShowModelResult> data;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_showbean);
        initView();
        setCtenterTitle("我的秀逗");
    }

    private void initView() {
        listview_beanshow = (NOViewPagerPullableListView) findViewById(R.id.showbean_listView);
        beanshow_PullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.showbean_PullToRefreshLayout);
        beanshow_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page = 1;
                requestData();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                page++;
                requestData();
            }
        });
        beanshow_PullToRefreshLayout.autoRefresh();
        data = new ArrayList<>();
        meStarChangeAdapter = new MyShowBeanAdapter(this, data);
        listview_beanshow.setAdapter(meStarChangeAdapter);
    }

    /**
     * 刷新列表
     */

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("page", page + "");
        BasicRequest.getInstance().requestPost(this, 0, params, AppConfig.MY_BEANSHOW, BeanShowModel.class);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        if (page == 1) {
            data.clear();
        }
        data.addAll(((BeanShowModel) obj).getDatas());
        meStarChangeAdapter.notifyDataSetChanged();
        beanshow_PullToRefreshLayout.refreshFinish(beanshow_PullToRefreshLayout.SUCCEED);
        beanshow_PullToRefreshLayout.loadmoreFinish(beanshow_PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onResponseError(String error, int taskid) {
        showToast(error);
        beanshow_PullToRefreshLayout.loadmoreFinish(beanshow_PullToRefreshLayout.FAIL);
        beanshow_PullToRefreshLayout.refreshFinish(beanshow_PullToRefreshLayout.FAIL);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg1 == RESULT_OK) {
            beanshow_PullToRefreshLayout.autoRefresh();
        }
    }
}
