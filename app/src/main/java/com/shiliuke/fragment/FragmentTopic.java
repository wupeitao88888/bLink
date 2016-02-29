package com.shiliuke.fragment;

import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.TopicAdapter;
import com.shiliuke.bean.Comment;
import com.shiliuke.bean.Topic;
import com.shiliuke.bean.UserImgs;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.FaceConversionUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.PullToRefresh.PullableListView;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * 话体
 */
public class FragmentTopic extends Fragment implements VolleyListerner {
    private View rootView;
    private LayoutInflater inflater;
    private NOViewPagerPullableListView toaic_listView;
    private Activity mActivity = null;
    private PullToRefreshLayout toaic_PullToRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            this.inflater = inflater;
            rootView = initView();
//			i;
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public void autoRefresh() {
        toaic_PullToRefreshLayout.autoRefresh();
    }

    private View initView() {
        mActivity = this.getActivity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                FaceConversionUtil
                        .getInstace().getFileText(
                        mActivity);
            }
        }).start();
        View view = inflater.inflate(R.layout.fragment_topic, null);
        toaic_listView = (NOViewPagerPullableListView) view.findViewById(R.id.toaic_listView);
        toaic_PullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.toaic_PullToRefreshLayout);

        toaic_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                initDate();

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        toaic_PullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 2000);

            }
        });
        toaic_PullToRefreshLayout.autoRefresh();
        return view;
    }

    private void initDate() {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        BasicRequest.getInstance().requestPOST(FragmentTopic.this, TaskID.ACTION_TALK, params, AppConfig.TALK, Topic.class);
    }

    public boolean isOdd(int i) {
        return i % 2 != 0;//如果一个数是偶数，就算是负数整除2余数也为0
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        toaic_PullToRefreshLayout.refreshFinish(toaic_PullToRefreshLayout.SUCCEED);
        switch (taskid) {
            case TaskID.ACTION_TALK:
                Topic topic = (Topic) obj;
                TopicAdapter mWeChatAdapter = new TopicAdapter(mActivity);
                mWeChatAdapter.setData(topic.getDates());
                toaic_listView.setAdapter(mWeChatAdapter);
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        try {
            toaic_PullToRefreshLayout.refreshFinish(toaic_PullToRefreshLayout.FAIL);
        } catch (Exception e) {

        }
        switch (taskid) {
            case TaskID.ACTION_TALK:
                ToastUtil.showShort(mActivity, error);
                break;
        }
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

    public void updateData() {
        if (toaic_PullToRefreshLayout != null) {
            toaic_PullToRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
