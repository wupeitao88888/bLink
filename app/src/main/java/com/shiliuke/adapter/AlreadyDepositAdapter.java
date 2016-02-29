package com.shiliuke.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.PayMentActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.PayEnd;
import com.shiliuke.fragment.FragmentAlreadyDeposit;
import com.shiliuke.global.AppConfig;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*****
 * 已付定金
 */
public class AlreadyDepositAdapter extends BaseAdapter {

    private List<PayEnd.PayEndResult> mList;
    private FragmentAlreadyDeposit mContext;


    public AlreadyDepositAdapter(final FragmentAlreadyDeposit _context, List<PayEnd.PayEndResult> mList) {
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PayEnd.PayEndResult getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext.getActivity()).inflate(
                    R.layout.layout_already_deposit_item, parent, false);
        }

        final PayEnd.PayEndResult payEnd = mList.get(position);
        ImageView pay_url = ViewHolder.get(convertView, R.id.pay_url);
        TextView pay_title = ViewHolder.get(convertView, R.id.pay_title);
        TextView not_yet = ViewHolder.get(convertView, R.id.not_yet);//实付
        Button pay_end = ViewHolder.get(convertView, R.id.pay_end);//支付尾款
        Button pay_cancel = ViewHolder.get(convertView, R.id.pay_cancel);//退款
        if (payEnd.getGoods().isGoodEnd()) {
            pay_cancel.setVisibility(View.GONE);
            pay_end.setVisibility(View.VISIBLE);
        } else {
            pay_cancel.setVisibility(View.VISIBLE);
            pay_end.setVisibility(View.INVISIBLE);
        }

        setImage(pay_url, payEnd.getGoods().getImage_url(), mContext.getActivity());
        ViewUtil.setText(mContext.getActivity(), pay_title, payEnd.getGoods().getGoods_name());
        ViewUtil.setText(mContext.getActivity(), not_yet, "¥" + payEnd.getGoods().getGoods_dingjin());

        pay_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getActivity(), PayMentActivity.class);
                intent.putExtra(PayMentActivity.PARAMS_TITLE, "支付尾款");
                intent.putExtra(PayMentActivity.PARAMS_PRICE, payEnd.getGoods().getGoods_weikuan());
                intent.putExtra(PayMentActivity.PARAMS_PAYTYPE, "2");
                intent.putExtra(PayMentActivity.PARAMS_GOODS_ID, payEnd.getGoods().getGoods_id());
                intent.putExtra(PayMentActivity.PARAMS_ORDER_ID, payEnd.getOrder_id());
                mContext.startActivityForResult(intent, mContext.PAYWEIKUAN);
            }
        });
        pay_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.startDialogLoading(mContext.getActivity());
                Map<String, String> params = new HashMap<>();
                params.put("member_id", AppConfig.loginInfo.getMember_id());
                params.put("order_id", payEnd.getOrder_id());
                params.put("type", "1");
                BasicRequest.getInstance().requestPost(new VolleyListerner() {
                    @Override
                    public void onResponse(String str, int taskid, Object obj) {
                        DialogUtil.stopDialogLoading(mContext.getActivity());
                        ToastUtil.show(mContext.getActivity(), "退款申请成功", 0);
                        mList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onResponseError(String error, int taskid) {
                        DialogUtil.stopDialogLoading(mContext.getActivity());
                        ToastUtil.show(mContext.getActivity(), error, 0);
                    }
                }, 0, params, AppConfig.ORDER_REFUND);
            }
        });

        return convertView;
    }

    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                .placeholder(R.drawable.gray)
//                .error(R.drawable.gray)
                .crossFade()
                .into(iview);

    }

}
