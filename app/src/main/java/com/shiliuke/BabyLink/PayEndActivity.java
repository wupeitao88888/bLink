package com.shiliuke.BabyLink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.shiliuke.adapter.PayEndAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.Unconsume;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <!-- 支付尾款 -->
 * Created by wupeitao on 15/11/7.
 */
public class PayEndActivity extends ActivitySupport implements VolleyListerner {
    public static final int PAYWEIKUAN = 1;

    private PullableListView payend_listView;
    private PayEndAdapter payEndAdapter;
    private PullToRefreshLayout payend_PullToRefreshLayout;
    private List<Unconsume.Data> list = new ArrayList<>();
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payend);
        initView();
    }

    private void initView() {
        setCtenterTitle("支付尾款");
        payend_listView = (PullableListView) findViewById(R.id.payend_listView);
        payend_PullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.payend_PullToRefreshLayout);


        payend_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                page = 1;
                requestData();

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉
                page++;
                requestData();

            }
        });
        payEndAdapter = new PayEndAdapter(this, list);
        payend_listView.setAdapter(payEndAdapter);
        payend_PullToRefreshLayout.autoRefresh();
    }

    private void requestData() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("member_id", sharedPreferencesHelper.getValue("member_id"));
        params.put("order_status", "1");
        params.put("page", page + "");
        BasicRequest.getInstance().requestPost(this, TaskID.ACTION_GET_RETAINAGE, params, AppConfig.DUIHUANMA_LIST);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        if (taskid == TaskID.ACTION_GET_RETAINAGE) {
            Unconsume unconsume = JSON.parseObject(str, Unconsume.class);
            if (page == 1) {
                list.clear();
            }
            list.addAll(unconsume.getDatas());
            payEndAdapter.notifyDataSetChanged();
            payend_PullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            payend_PullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        payend_PullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
        payend_PullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYWEIKUAN && resultCode == Activity.RESULT_OK) {
            payend_PullToRefreshLayout.autoRefresh();
        }
    }
}
