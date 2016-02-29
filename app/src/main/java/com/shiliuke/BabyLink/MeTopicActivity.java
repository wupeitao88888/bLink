package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.shiliuke.BabyLink.information.InformationRequestListener;
import com.shiliuke.adapter.MeInitateAdapter;
import com.shiliuke.adapter.MeTopicAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.MeInitateActivity;
import com.shiliuke.bean.Topic;
import com.shiliuke.bean.TopicInfo;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的话题
 * Created by wupeitao on 15/11/5.
 */
public class MeTopicActivity extends ActivitySupport implements VolleyListerner {


    private PullableListView meTopic_listView;
    private PullToRefreshLayout meTopic_PullToRefreshLayout;
    private MeTopicAdapter meTopicAdapter;
    private List<TopicInfo> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_metopic);
        initView();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        meTopic_PullToRefreshLayout.autoRefresh();
    }

    private void initView() {
        setCtenterTitle("我的话题");
        meTopic_listView = (PullableListView) findViewById(R.id.meTopic_listView);
        meTopic_listView.setDivider(null);
        meTopic_PullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.meTopic_PullToRefreshLayout);


        meTopic_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                setResponse();

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                showToast("没有更多的数据了");
                meTopic_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);


            }
        });
//        List<MeInitateActivity> mList = new ArrayList<>();
//        for(int i=0;i<9;i++){
//            MeInitateActivity ma=new MeInitateActivity("","去国土局","2015/11/11 11:11","1","10");
//            mList.add(ma);
//        }
//
        meTopic_PullToRefreshLayout.autoRefresh();
        meTopic_listView.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, String> params = new HashMap<>();
            params.put("member_id", AppConfig.loginInfo.getMember_id());
            params.put("type", "0");
            params.put("item_id", dates.get(position).getTalk_id());
            BasicRequest.getInstance().requestPost(new InformationRequestListener(), 0, params, AppConfig.UPDATE_INFORMATION, null);
            dates.get(position).setTalk_num("0");
            meTopicAdapter.notifyDataSetChanged();
            Intent intent=new Intent(context,MeTopicInfoActivity.class);
            intent.putExtra("Topic", dates.get(position));
            startActivity(intent);
        });
    }

    public void setResponse() {
        DialogUtil.startDialogLoading(context);
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("me", "1");
//        params.put("member_id", "1");
        BasicRequest.getInstance().requestPost(MeTopicActivity.this, TaskID.ACTION_METALK, params, AppConfig.ME_TALK, Topic.class);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        switch (taskid) {
            case TaskID.ACTION_METALK:
                meTopic_PullToRefreshLayout.refreshFinish(meTopic_PullToRefreshLayout.SUCCEED);
                DialogUtil.stopDialogLoading(context);
                Topic topic = (Topic) obj;
                dates = topic.getDates();
                meTopicAdapter = new MeTopicAdapter(context, dates);
                meTopic_listView.setAdapter(meTopicAdapter);
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        switch (taskid) {
            case TaskID.ACTION_METALK:
                meTopic_PullToRefreshLayout.refreshFinish(meTopic_PullToRefreshLayout.SUCCEED);
                DialogUtil.stopDialogLoading(context);
                ToastUtil.showShort(context, error);
                break;
        }
    }
}

