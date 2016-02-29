package com.shiliuke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shiliuke.BabyLink.FindDesActivity;
import com.shiliuke.BabyLink.InvitListActivity;
import com.shiliuke.BabyLink.MainTab;
import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.FindAdapter;
import com.shiliuke.bean.FindCategoryModel;
import com.shiliuke.bean.FindModel;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.view.PopWnd;
import com.shiliuke.view.PullToRefresh.NOViewPagerPullableListView;
import com.shiliuke.view.PullToRefresh.PullToRefreshLayout;
import com.shiliuke.view.TitleBar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentFind extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, VolleyListerner {

    private View layout_fragment_find_category;//发现页面分类选项
    private View layout_fragment_find_sort;//发现页面排序方式选项
    private TextView tv_find_invite_sum;//分类旁边被邀请数字
    private View popBg;
    private PopWnd categoryPop;//分类pop
    private PopWnd sortPop;//排序pop
    private NOViewPagerPullableListView list_fragment_find;//页面list
    private PullToRefreshLayout find_PullToRefreshLayout;
    private FindAdapter mAdapter;
    private ArrayList<FindModel.FindModelResult> mData;
    private String yaoqing_num = "";
    private int page = 1;
    private String order_type = "1";
    private String store_class = "";
    private FindCategoryModel categoryModel;
    private View view;

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
        view = inflater.inflate(R.layout.fragment_find, null);

        ((TitleBar) view.findViewById(R.id.meCommunity_title)).setCenterTitle("发现");
        ((TitleBar) view.findViewById(R.id.meCommunity_title)).isLeftVisibility(false);
        list_fragment_find = (NOViewPagerPullableListView) view.findViewById(R.id.list_fragment_find);
        tv_find_invite_sum = (TextView) view.findViewById(R.id.tv_find_invite_sum);
        find_PullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.find_PullToRefreshLayout);
        layout_fragment_find_category = view.findViewById(R.id.layout_fragment_find_category);
        layout_fragment_find_sort = view.findViewById(R.id.layout_fragment_find_sort);
        popBg = view.findViewById(R.id.pop_bg);
        layout_fragment_find_category.setOnClickListener(this);
        layout_fragment_find_sort.setOnClickListener(this);
        mData = new ArrayList<>();
        mAdapter = new FindAdapter(list_fragment_find, this, mData);
        list_fragment_find.setAdapter(mAdapter);
        list_fragment_find.setOnItemClickListener(this);
        find_PullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
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
        DialogUtil.startDialogLoading(getContext());
        BasicRequest.getInstance().requestPost(this, TaskID.ACTION_FIND_CATEGORY, new HashMap<>(), AppConfig.FIND_CATEGORY, FindCategoryModel.class);
        return view;
    }

    private void requestData(boolean isInit) {
        if (isInit) {
            DialogUtil.startDialogLoading(getContext());
        }
        try {
            Map<String, String> params = new HashMap<>();
            params.put("latitude", MApplication.locationBD.getLatitude() + "");
            params.put("longitude", MApplication.locationBD.getLongitude() + "");
            params.put("page", page + "");
            params.put("order_type", order_type);
            params.put("member_id", AppConfig.loginInfo.getMember_id());
            if (!TextUtils.isEmpty(store_class)) {
                params.put("store_class", store_class);
            }
            BasicRequest.getInstance().requestPost(this, page > 1 ? TaskID.FIND_LIST_LOADMORE : TaskID.FIND_LIST, params, AppConfig.FINDLIST, FindModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        DialogUtil.stopDialogLoading(getContext());
        try {
            switch (taskid) {
                case TaskID.FIND_LIST:
                    mData.clear();
                    mData.addAll(((FindModel) obj).getDatas().getList());
                    yaoqing_num = ((FindModel) obj).getDatas().getYaoqing_num();
                    if (!TextUtils.isEmpty(yaoqing_num)) {
                        tv_find_invite_sum.setText(yaoqing_num);
                        tv_find_invite_sum.setVisibility(View.VISIBLE);
                    } else {
                        tv_find_invite_sum.setVisibility(View.GONE);
                    }
                    mAdapter.notifyDataSetChanged();
                    find_PullToRefreshLayout.refreshFinish(find_PullToRefreshLayout.SUCCEED);
                    break;
                case TaskID.FIND_LIST_LOADMORE:
                    mData.addAll(((FindModel) obj).getDatas().getList());
                    mAdapter.notifyDataSetChanged();
                    find_PullToRefreshLayout.loadmoreFinish(find_PullToRefreshLayout.SUCCEED);
                    break;
                case TaskID.ACTION_FIND_CATEGORY:
                    categoryModel = (FindCategoryModel) obj;
                    requestData(true);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseError(String error, int taskid) {
        try {
            switch (taskid) {
                case TaskID.FIND_LIST:
                    ((MainTab) getActivity()).showToast(error);
                    DialogUtil.stopDialogLoading(getContext());
                    find_PullToRefreshLayout.refreshFinish(find_PullToRefreshLayout.FAIL);
                    break;
                case TaskID.FIND_LIST_LOADMORE:
                    ((MainTab) getActivity()).showToast(error);
                    DialogUtil.stopDialogLoading(getContext());
                    find_PullToRefreshLayout.loadmoreFinish(find_PullToRefreshLayout.FAIL);
                    break;
                case TaskID.ACTION_FIND_CATEGORY:
                    requestData(true);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_fragment_find_category://分类
                if (categoryPop == null) {
                    int[] cateIds = new int[]{R.id.tv_pop_find_category_invite};
                    categoryPop = new PopWnd(getActivity(), R.layout.layout_pop_find_category, cateIds, this, popBg, false);
                    initCategotyView(categoryPop);
                }
                if (!TextUtils.isEmpty(yaoqing_num)) {
                    categoryPop.getTextView(R.id.tv_pop_find_category_invite_sum).setVisibility(View.VISIBLE);
                    categoryPop.getTextView(R.id.tv_pop_find_category_invite_sum).setText(yaoqing_num);
                } else {
                    categoryPop.getTextView(R.id.tv_pop_find_category_invite_sum).setVisibility(View.GONE);
                }
                categoryPop.showPopWindow(v);
                break;
            case R.id.layout_fragment_find_sort://排序方式
                if (sortPop == null) {
                    int[] cateIds = new int[]{R.id.tv_pop_find_sort_person, R.id.tv_pop_find_sort_price, R.id.tv_pop_find_sort_range};
                    sortPop = new PopWnd(getActivity(), R.layout.layout_pop_find_sort, cateIds, this, popBg, false);
                }
                sortPop.showPopWindow(v);
                break;
            case R.id.tv_pop_find_category_invite://被邀请
                yaoqing_num = "";
                tv_find_invite_sum.setVisibility(View.GONE);
                startActivity(new Intent(getActivity(), InvitListActivity.class));
                break;
            case R.id.tv_pop_find_sort_person://排序-参与人数
                page = 1;
                order_type = "3";
                requestData(true);
                break;
            case R.id.tv_pop_find_sort_range://排序-距离最近
                page = 1;
                order_type = "1";
                requestData(true);
                break;
            case R.id.tv_pop_find_sort_price://排序-价格最低
                page = 1;
                order_type = "2";
                requestData(true);
                break;
        }
    }

    private void initCategotyView(PopWnd categoryPop) {
        if (categoryModel == null || categoryModel.getDatas() == null || categoryModel.getDatas().isEmpty()) {
            return;
        }
        ListView listView = (ListView) categoryPop.getPopView().findViewById(R.id.listview_find_category);
        listView.setAdapter(new FindCategoryAdapter());
        listView.setOnItemClickListener((parent, view, position, id) -> {
            categoryPop.dissmiss();
            page = 1;
            store_class = categoryModel.getDatas().get(position).getClass_name();
            requestData(true);
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), FindDesActivity.class);
        intent.putExtra("goods_id", mData.get(position).getGoods_id());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    class FindCategoryAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return categoryModel.getDatas().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_find_category_item, null);
            }
            TextView tv = ViewHolder.get(convertView, R.id.tv_find_category_item);
            tv.setText(categoryModel.getDatas().get(position).getClass_name());
            return convertView;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(view);
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
