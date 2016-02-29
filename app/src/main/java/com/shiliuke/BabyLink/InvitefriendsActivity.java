package com.shiliuke.BabyLink;

import android.os.Bundle;
import com.shiliuke.adapter.InvitefriendsAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.InViteFriendsModel;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 邀请好友页面
 */
public class InvitefriendsActivity extends ActivitySupport implements VolleyListerner {

    private PullToRefreshLayout invitefriends_pulltorefreshlayout;
    private NOViewPagerPullableListView list_invitefriends;//页面list
    private ArrayList<InViteFriendsModel.Result> infoList;
    private InvitefriendsAdapter mAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitefriends);
        setCtenterTitle("小区好友");
        list_invitefriends = (NOViewPagerPullableListView) findViewById(R.id.list_invitefriends);
        invitefriends_pulltorefreshlayout = (PullToRefreshLayout) findViewById(R.id.invitefriends_pulltorefreshlayout);
        infoList = new ArrayList<>();
        mAdapter = new InvitefriendsAdapter(infoList, this,getIntent().getStringExtra("goods_id"));
        list_invitefriends.setAdapter(mAdapter);
        invitefriends_pulltorefreshlayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
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
        invitefriends_pulltorefreshlayout.autoRefresh();
    }

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("goods_id", getIntent().getStringExtra("goods_id"));
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("page", page + "");
        BasicRequest.getInstance().requestPost(this, 0, params, AppConfig.FIND_INVITE_LIST, InViteFriendsModel.class);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        if (page == 1) {
            infoList.clear();
            invitefriends_pulltorefreshlayout.refreshFinish(invitefriends_pulltorefreshlayout.SUCCEED);
        } else {
            invitefriends_pulltorefreshlayout.loadmoreFinish(invitefriends_pulltorefreshlayout.SUCCEED);
        }
        infoList.addAll(((InViteFriendsModel) obj).getDatas());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseError(String error, int taskid) {

    }
}
