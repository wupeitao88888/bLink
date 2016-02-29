package com.shiliuke.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.PayEnd;
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
 * 已付全款
 */
public class AlreadyAllAdapter extends BaseAdapter {

    private List<PayEnd.PayEndResult> mList;
    private Context mContext;


    public AlreadyAllAdapter(final Context _context, List<PayEnd.PayEndResult> mList) {

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
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_already_all_item, parent, false);
        }

        final PayEnd.PayEndResult payEnd = mList.get(position);
        ImageView pay_url = ViewHolder.get(convertView, R.id.pay_url);
        TextView pay_title = ViewHolder.get(convertView, R.id.pay_title);
        TextView tv_item_already_all_pay = ViewHolder.get(convertView, R.id.tv_item_already_all_pay);//实付
        final TextView pay_status = ViewHolder.get(convertView, R.id.pay_status);//支付状态
        TextView tv_item_already_all_pay_des = ViewHolder.get(convertView, R.id.tv_item_already_all_pay_des);//支付详情
        Button pay_cancel = ViewHolder.get(convertView, R.id.pay_cancel);//退款
        //////////
        tv_item_already_all_pay.setText("¥" + payEnd.getGoods().getGoods_price());
        tv_item_already_all_pay_des.setText("订金：¥" + payEnd.getGoods().getGoods_dingjin() + " 尾款：¥" + payEnd.getGoods().getGoods_weikuan() + " 共计：¥" + payEnd.getGoods().getGoods_price());
        setImage(pay_url, payEnd.getGoods().getImage_url(), mContext);
        switch (payEnd.getOrder_status()) {
            case "7":
                pay_status.setText("已消费");
                break;
            case "6":
                pay_status.setText("退款申请中");
                break;
            default:
                pay_status.setText("未消费");
                break;
        }
        ViewUtil.setText(mContext, pay_title, payEnd.getGoods().getGoods_name());
        if (TextUtils.isEmpty(payEnd.getGoods().getGoods_price()) || Double.parseDouble(payEnd.getGoods().getGoods_price()) == 0) {
            pay_cancel.setText("取消预约");
        }else{
            pay_cancel.setText("退尾款");
        }
        pay_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("7".equalsIgnoreCase(payEnd.getOrder_status())) {
                    ToastUtil.show(mContext, "已消费的不能退款", Toast.LENGTH_SHORT);
                    return;
                }
                if ("6".equalsIgnoreCase(payEnd.getOrder_status())) {
                    ToastUtil.show(mContext, "已退款，请耐心等待2~3个工作日", Toast.LENGTH_SHORT);
                    return;
                }
                DialogUtil.startDialogLoading(mContext);
                Map<String, String> params = new HashMap<>();
                params.put("member_id", AppConfig.loginInfo.getMember_id());
                params.put("order_id", payEnd.getOrder_id());
                //免费的调取消订单接口
                if (TextUtils.isEmpty(payEnd.getGoods().getGoods_price()) || Double.parseDouble(payEnd.getGoods().getGoods_price()) == 0) {
                    BasicRequest.getInstance().requestPost(new VolleyListerner() {
                        @Override
                        public void onResponse(String str, int taskid, Object obj) {
                            DialogUtil.stopDialogLoading(mContext);
                            ToastUtil.show(mContext, "取消订单成功", 0);
                            mList.remove(position);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onResponseError(String error, int taskid) {
                            DialogUtil.stopDialogLoading(mContext);
                            ToastUtil.show(mContext, error, 0);
                        }
                    }, 0, params, AppConfig.ORDER_CANCEL);
                    return;
                }
                //不是免费的调退款接口
                params.put("type", "2");
                BasicRequest.getInstance().requestPost(new VolleyListerner() {
                    @Override
                    public void onResponse(String str, int taskid, Object obj) {
                        DialogUtil.stopDialogLoading(mContext);
                        ToastUtil.show(mContext, "退款申请成功", 0);
                        mList.remove(position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onResponseError(String error, int taskid) {
                        DialogUtil.stopDialogLoading(mContext);
                        ToastUtil.show(mContext, error, 0);
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
