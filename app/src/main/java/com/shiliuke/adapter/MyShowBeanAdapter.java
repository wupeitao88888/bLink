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
import com.shiliuke.BabyLink.R;
import com.shiliuke.BabyLink.information.InformationRequestListener;
import com.shiliuke.BabyLink.information.InformationUtils;
import com.shiliuke.bean.BeanShowModelResult;
import com.shiliuke.global.AppConfig;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.ViewHolder;
import com.shiliuke.utils.ViewUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*****
 * 我的秀逗
 */
public class MyShowBeanAdapter extends BaseAdapter {

    private List<BeanShowModelResult> mList;
    private Activity mContext;


    public MyShowBeanAdapter(final Activity _context, List<BeanShowModelResult> mList) {
        this.mContext = _context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public BeanShowModelResult getItem(int position) {
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
                    R.layout.layout_myshowbean_item, parent, false);
        }
        final BeanShowModelResult memyshowbeanActivity = mList.get(position);
        ImageView myshowbean_pic = ViewHolder.get(convertView, R.id.myshowbean_pic);
        TextView myshowbean_name = ViewHolder.get(convertView, R.id.myshowbean_name);
        TextView tv_information_person = ViewHolder.get(convertView, R.id.tv_information_person);
//        TextView myshowbean_time = ViewHolder.get(convertView, R.id.myshowbean_time);
        Button myshowbean_status = ViewHolder.get(convertView, R.id.myshowbean_status);

        InformationUtils.setInformationText(memyshowbeanActivity.getXiu_num(), tv_information_person);
        setImage(myshowbean_pic, memyshowbeanActivity.getImage_url(), mContext);
        ViewUtil.setText(mContext, myshowbean_name, memyshowbeanActivity.getInfo());
//        ViewUtil.setText(mContext, myshowbean_time, memyshowbeanActivity.getchange_time());
//        myshowbean_status.setSelected(true);//true是灰色

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memyshowbeanActivity.setXiu_num("0");
                notifyDataSetChanged();
                updateInformationNum(memyshowbeanActivity.getXiu_id());
                Intent intent = new Intent();
                intent.setAction("com.shiliuke.StickerModifyActivity");
                intent.putExtra("model", memyshowbeanActivity);
                mContext.startActivityForResult(intent, 0);
            }
        });


        return convertView;
    }

    private void updateInformationNum(String xiu_id) {
        Map<String, String> params = new HashMap<>();
        params.put("member_id", AppConfig.loginInfo.getMember_id());
        params.put("type", "1");
        params.put("item_id", xiu_id);
        BasicRequest.getInstance().requestPost(new InformationRequestListener(), 0, params, AppConfig.UPDATE_INFORMATION, null);
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
