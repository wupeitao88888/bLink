package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.FindInfoModel;
import com.shiliuke.bean.FindModel;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvitListActivity extends ActivitySupport implements VolleyListerner {

    private NOViewPagerPullableListView listview;//页面list
    private PullToRefreshLayout pullToRefreshLayout;
    private ArrayList<FindModel.FindModelResult> mData;
    private InvitListAdapter mAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invit_list);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pullToRefreshLayout);
        listview = (NOViewPagerPullableListView) findViewById(R.id.listview);
        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                page = 1;
                requestData(false);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                page++;
                requestData(false);
            }
        });
        setCtenterTitle("被邀请列表");
        mData = new ArrayList<>();
        mAdapter = new InvitListAdapter();
        listview.setAdapter(mAdapter);
        requestData(true);
    }

    private void requestData(boolean isInit) {
        if (isInit) {
            DialogUtil.startDialogLoading(this);
        }
        Map<String, String> params = new HashMap<>();
        params.put("latitude", MApplication.locationBD.getLatitude()+"");
        params.put("longitude", MApplication.locationBD.getLongitude()+"");
//        params.put("order_type", "");
        params.put("page", page + "");
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("store_class", "被邀请");
        BasicRequest.getInstance().requestPost(this, page > 1 ? TaskID.FIND_LIST_LOADMORE : TaskID.FIND_LIST, params, AppConfig.FINDLIST, FindModel.class);
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        DialogUtil.stopDialogLoading(getContext());
        switch (taskid) {
            case TaskID.FIND_LIST:
                mData.clear();
                mData.addAll(((FindModel) obj).getDatas().getList());
                mAdapter.notifyDataSetChanged();
                pullToRefreshLayout.refreshFinish(pullToRefreshLayout.SUCCEED);
                break;
            case TaskID.FIND_LIST_LOADMORE:
                mData.addAll(((FindModel) obj).getDatas().getList());
                mAdapter.notifyDataSetChanged();
                pullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.SUCCEED);
                break;
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        DialogUtil.stopDialogLoading(getContext());
        showToast(error);
        switch (taskid) {
            case TaskID.FIND_LIST:
                pullToRefreshLayout.refreshFinish(pullToRefreshLayout.FAIL);
                break;
            case TaskID.FIND_LIST_LOADMORE:
                pullToRefreshLayout.loadmoreFinish(pullToRefreshLayout.FAIL);
                break;
        }
    }

    class InvitListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(InvitListActivity.this).inflate(R.layout.item_invit_list, null);
            }
            final FindModel.FindModelResult result = mData.get(position);
            TextView tv_item_invitlist_name_user = ViewHolder.get(convertView, R.id.tv_item_invitlist_name_user);
            TextView tv_item_invitlist_name_goods = ViewHolder.get(convertView, R.id.tv_item_invitlist_name_goods);
            Button btn_invitlist_ok = ViewHolder.get(convertView, R.id.btn_invitlist_ok);
            ImageView image_item_invitlist = ViewHolder.get(convertView, R.id.image_item_invitlist);
            Glide.with(InvitListActivity.this).load(result.getMember_avar()).into(image_item_invitlist);
            tv_item_invitlist_name_user.setText(result.getMember_name());
            tv_item_invitlist_name_goods.setText(result.getStore_name());
            btn_invitlist_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InvitListActivity.this, FindDesActivity.class);
                    intent.putExtra("goods_id", result.getGoods_id());
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
