package com.shiliuke.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.PayEnd;
import com.shiliuke.utils.ViewHolder;

import java.util.List;

/*****
 * 已退款
 */
public class AlreadyRefundAdapter extends BaseAdapter {

    private List<PayEnd.PayEndResult> mList;
    private Context mContext;


    public AlreadyRefundAdapter(final Context _context, List<PayEnd.PayEndResult> mList) {

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
                    R.layout.layout_already_refund_item, parent, false);
        }

        PayEnd.PayEndResult payEnd = mList.get(position);
        ImageView pay_url = ViewHolder.get(convertView, R.id.pay_url);
        TextView pay_title = ViewHolder.get(convertView, R.id.pay_title);//团购名
        TextView pay_deposit = ViewHolder.get(convertView, R.id.pay_deposit);//定价
        TextView already = ViewHolder.get(convertView, R.id.already);//定金：+尾款：
        TextView not_yet_left = ViewHolder.get(convertView, R.id.not_yet_left);//已退类型
        TextView not_yet = ViewHolder.get(convertView, R.id.not_yet);//已退金额
        Button pay_end = ViewHolder.get(convertView, R.id.pay_end);//退款
        /////////////
        setImage(pay_url, payEnd.getGoods().getImage_url(), mContext);
        pay_title.setText(payEnd.getGoods().getGoods_name());

        pay_deposit.setText("定价：¥" + payEnd.getGoods().getGoods_baseprice() + "~" + payEnd.getGoods().getGoods_oprice());
        if (payEnd.isRefundDingjin()) {
            already.setText("订金：¥" + payEnd.getGoods().getGoods_dingjin());
            not_yet_left.setText("已退订金：");
            not_yet.setText("¥" + payEnd.getGoods().getGoods_dingjin());
        } else {
            already.setText("订金：¥" + payEnd.getGoods().getGoods_dingjin() + "  尾款：¥" + payEnd.getGoods().getGoods_weikuan());
            not_yet_left.setText("已退尾款：");
            not_yet.setText("¥" + payEnd.getGoods().getGoods_weikuan());
        }
        if ("6".equalsIgnoreCase(payEnd.getOrder_status())) {//退款中
            pay_end.setText("退款中");
        } else if (TextUtils.isEmpty(payEnd.getGoods().getGoods_price()) || Double.parseDouble(payEnd.getGoods().getGoods_price()) == 0) {
            pay_end.setText("已取消");
        } else {
            pay_end.setText("已退款");
        }
        pay_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
