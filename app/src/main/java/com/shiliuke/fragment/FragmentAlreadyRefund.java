package com.shiliuke.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.shiliuke.BabyLink.MeOrderDesActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.AlreadyRefundAdapter;
import com.shiliuke.bean.PayEnd;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 已退款
 */
public class FragmentAlreadyRefund extends Fragment implements VolleyListerner {
    private View rootView;//缓存Fragment view
    private NOViewPagerPullableListView deposit_refund_listView;
    private AlreadyRefundAdapter alreadyRefundAdapter;
    private static Context mContext;
    private PullToRefreshLayout deposit_refund_PullToRefreshLayout;
    private List<PayEnd.PayEndResult> mList;
    private int page = 1;
    @Override
    public void onResume() {
        super.onResume();
        // FT = true;
        // L.e(TAG, "onResume");
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_alreadyrefund, null);
            initView(rootView);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public static FragmentAlreadyRefund getInstance(Context context) {
        mContext = context;
        return new FragmentAlreadyRefund();
    }


    private void initView(View rootView) {
        deposit_refund_listView = (NOViewPagerPullableListView) rootView.findViewById(R.id.deposit_refund_listView);
        deposit_refund_PullToRefreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.deposit_refund_PullToRefreshLayout);


        deposit_refund_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                page = 1;
                requestData(false);

            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                // 上拉刷新操作
                page++;
                requestData(false);
            }
        });
        mList = new ArrayList<>();
        alreadyRefundAdapter = new AlreadyRefundAdapter(mContext, mList);
        deposit_refund_listView.setAdapter(alreadyRefundAdapter);
        deposit_refund_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MeOrderDesActivity.class);
                intent.putExtra("model", mList.get(position));
                intent.putExtra("type", MeOrderDesActivity.ORDERTYPE.REFUND);
                startActivity(intent);
            }
        });
    }

    public void requestData(boolean isInit) {
        if (mList != null && !mList.isEmpty() && isInit) {
            return;
        }
        if (isInit) {
            DialogUtil.startDialogLoading(mContext);
        }
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("order_status", "3,6");
        params.put("page", page + "");
        BasicRequest.getInstance().requestPost(this, 0, params, AppConfig.ORDER_LIST, PayEnd.class);
    }


    @Override
    public void onResponse(String str, int taskid, Object obj) {
        DialogUtil.stopDialogLoading(mContext);
        if (page == 1) {
            mList.clear();
        }
        mList.addAll(((PayEnd) obj).getDatas());
        alreadyRefundAdapter.notifyDataSetChanged();
        deposit_refund_PullToRefreshLayout.refreshFinish(deposit_refund_PullToRefreshLayout.SUCCEED);
        deposit_refund_PullToRefreshLayout.loadmoreFinish(deposit_refund_PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onResponseError(String error, int taskid) {
        DialogUtil.stopDialogLoading(mContext);
        ToastUtil.show(getContext(), error, Toast.LENGTH_SHORT);
        deposit_refund_PullToRefreshLayout.refreshFinish(deposit_refund_PullToRefreshLayout.FAIL);
        deposit_refund_PullToRefreshLayout.loadmoreFinish(deposit_refund_PullToRefreshLayout.FAIL);
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
