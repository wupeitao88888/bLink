package com.shiliuke.adapter;

import android.app.Activity;
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
import com.shiliuke.BabyLink.PayEndActivity;
import com.shiliuke.BabyLink.PayMentActivity;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.Unconsume;
import com.shiliuke.utils.ToastUtil;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.List;

/*****
 * 我参与的与我发起的共用一个adapter
 */
public class PayEndAdapter extends BaseAdapter {

    private List<Unconsume.Data> mList;
    private Activity mContext;


    public PayEndAdapter(Activity _context, List<Unconsume.Data> mList) {

        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Unconsume.Data getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_payend_item, parent, false);
        }
        Unconsume.Data payEnd = mList.get(position);
        ImageView pay_url = ViewHolder.get(convertView, R.id.pay_url);
        TextView pay_title = ViewHolder.get(convertView, R.id.pay_title);
        TextView pay_deposit = ViewHolder.get(convertView, R.id.pay_deposit);
        TextView already = ViewHolder.get(convertView, R.id.already);
        TextView not_yet = ViewHolder.get(convertView, R.id.not_yet);
        Button pay_end = ViewHolder.get(convertView, R.id.pay_end);


        setImage(pay_url, payEnd.getGoods().getImage_url(), mContext);
        ViewUtil.setText(mContext, pay_title, payEnd.getGoods().getGoods_name());
        ViewUtil.setText(mContext, pay_deposit, "定价：¥" + payEnd.getGoods().getGoods_price());
        ViewUtil.setText(mContext, already, "已付：¥" + payEnd.getGoods().getGoods_dingjin());
        double num;
        try {
            num = Double.parseDouble(payEnd.getGoods().getGoods_price()) - Double.parseDouble(payEnd.getGoods().getGoods_dingjin());

        } catch (Exception e) {
            num = 0;
        }
        ViewUtil.setText(mContext, not_yet, "未付：¥" + num);

        pay_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("0".equalsIgnoreCase(payEnd.getGoods().getEnd_status())) {
                    ToastUtil.show(mContext, "团购还没有结束", ToastUtil.SHOW_TOAST);
                    return;
                }
                Intent intent = new Intent(mContext, PayMentActivity.class);
                intent.putExtra(PayMentActivity.PARAMS_TITLE, "支付尾款");
                intent.putExtra(PayMentActivity.PARAMS_PRICE, payEnd.getGoods().getGoods_weikuan());
                intent.putExtra(PayMentActivity.PARAMS_PAYTYPE, "2");
                intent.putExtra(PayMentActivity.PARAMS_GOODS_ID, payEnd.getGoods().getGoods_id());
                intent.putExtra(PayMentActivity.PARAMS_ORDER_ID, payEnd.getOrder_id());
                mContext.startActivityForResult(intent, PayEndActivity.PAYWEIKUAN);
            }
        });

        return convertView;
    }

    private void setImage(ImageView iview, String url, Context context) {
        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.gray)
                .error(R.drawable.gray)
                .crossFade()
                .into(iview);

    }

}
