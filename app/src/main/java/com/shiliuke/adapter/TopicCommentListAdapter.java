package com.shiliuke.adapter;

import android.content.Context;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.Comment;
import com.shiliuke.utils.FaceConversionUtil;
import com.shiliuke.utils.ViewHolder;

import java.util.List;

public class TopicCommentListAdapter extends BaseAdapter {
    private List<Comment> mUI;
    private Context context;
    private LayoutInflater mLayoutInflater;
    private String id;
    public TopicCommentListAdapter(List<Comment> ui, Context context,String id) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mUI = ui;
        this.context = context;
        this.id=id;
    }

    @Override
    public int getCount() {
        return mUI == null ? 0 : mUI.size();
    }

    @Override
    public String getItem(int position) {
        return mUI.get(position).getMember_name();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = mUI.get(position);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_comment_item,
                    parent, false);
        }
        TextView commentContent = ViewHolder.get(convertView, R.id.commentContent);

        if ("0".equals(comment.getTo_id()) || TextUtils.isEmpty(comment.getTo_id()) || id.equals(comment.getTo_id()) || comment.getFrom_id().equalsIgnoreCase(comment.getTo_id())) {
            if (!TextUtils.isEmpty(comment.getInfo())) {
                //内容转译成表情
                SpannableString expressionString = FaceConversionUtil.getInstace().getExpressionString(context, comment.getInfo());
                commentContent.setText(Html.fromHtml(comment.getFrom_name()+"<font color = #383838 >"+":"+expressionString+"</font>"));
            } else {
                commentContent.setText(Html.fromHtml(comment.getFrom_name()+"<font color = #383838 >"+":"+"</font>"));
            }
        } else {
            if (!TextUtils.isEmpty(comment.getInfo())) {
                //内容转译成表情
                SpannableString expressionString = FaceConversionUtil.getInstace().getExpressionString(context, comment.getInfo());
                commentContent.setText(Html.fromHtml(comment.getFrom_name()+"<font color = #383838 >回复</font>"+comment.getTo_name()+"<font color = #383838 >"+":"+expressionString+"</font>"));
            } else {
                commentContent.setText(Html.fromHtml(comment.getFrom_name()+"<font color = #383838 >回复</font>"+comment.getTo_name()+"<font color = #383838 >"+":"+"</font>"));
            }
        }

        return convertView;
    }


}
