package com.shiliuke.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiliuke.BabyLink.R;
import com.shiliuke.bean.MessagePlusEndity;


public class MessagePlusAdapter extends BaseAdapter {

	Context mContext;
	List<MessagePlusEndity> mData = new ArrayList<MessagePlusEndity>();
	LayoutInflater inflater;

	// 构�?函数
	public MessagePlusAdapter(Context context) {
		mContext = context;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// 构�?函数
	public MessagePlusAdapter(Context context, List<MessagePlusEndity> mdata) {
		mContext = context;
		mData = mdata;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// 添加数据
	public void addItem(MessagePlusEndity item) {
		mData.add(item);
	}

	// 移除数据
	public void removeItem(int position) {
		mData.remove(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewFolder folder;
		final MessagePlusEndity item = mData.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.message_function_layout,
					null);
			folder = new ViewFolder();
			folder.imagebtn = (ImageView) convertView
					.findViewById(R.id.messageFunctionBtn);
			folder.txtView = (TextView) convertView
					.findViewById(R.id.messageFunctionName);
			convertView.setTag(folder);
		} else
			folder = (ViewFolder) convertView.getTag();
		folder.imagebtn.setBackgroundResource(item.icon);
		folder.txtView.setText(item.name);
		folder.imagebtn.setTag((Integer) position);

		// folder.imagebtn.setOnTouchListener(new OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// // TODO Auto-generated method stub
		// if (event.getAction() == MotionEvent.ACTION_DOWN) {
		// folder.imagebtn.setImageResource(R.drawable.chat_tool_mask);
		// return false;
		// } else {
		// folder.imagebtn.setImageBitmap(null);
		// return true;
		// }
		// }
		// });

		folder.imagebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 图片相册
//				if (item.getPosition() == 0) {
//					Intent inte = new Intent(mContext, Showphoto.class);
//					mContext.startActivity(inte);
//				} else if (item.getPosition() == 1) {
//					Intent intew = new Intent(mContext, Showcamera.class);
//					mContext.startActivity(intew);
//					Log.e("...............................", "////////////////");
//				}
//				if (item.getPosition() == 2) {
//					Intent intew = new Intent(mContext, MyDialog.class);
//					mContext.startActivity(intew);
//					Log.e("...............................", "////////////////");
//				}

				// if (item.getPosition() == 0) {
				//
				// NetworkInfo info = Chat.connectMgr.getActiveNetworkInfo();
				//
				// if (info != null) {
				// if (info.getSubtype() != TelephonyManager.NETWORK_TYPE_CDMA)
				// {
				// if (info.getSubtype() != TelephonyManager.NETWORK_TYPE_GPRS)
				// {
				// if (info.getSubtype() != TelephonyManager.NETWORK_TYPE_EDGE)
				// {
				// if (info.getSubtype() != TelephonyManager.NETWORK_TYPE_UMTS)
				// {
				// if (info.getSubtype() != TelephonyManager.NETWORK_TYPE_HSDPA)
				// {
				//
				// Intent intent = new Intent(
				// mContext, Showphoto.class);
				// mContext.startActivity(intent);
				// } else {
				// Toast.makeText(mContext,
				// "你当前网络是2g", 1).show();
				// }
				// } else {
				// Toast.makeText(mContext, "你当前网络是2g", 1)
				// .show();
				// }
				// } else {
				// Toast.makeText(mContext, "你当前网络是2g", 1)
				// .show();
				// }
				// } else {
				// Toast.makeText(mContext, "你当前网络是2g", 1).show();
				// }
				// } else {
				// Toast.makeText(mContext, "你当前网络是2g", 1).show();
				// }
				// } else {
				// Toast.makeText(mContext, "亲！请检查网络连接", 1).show();
				// }
				//
				// }
			}
		});
		return convertView;
	}

	private static class ViewFolder {
		ImageView imagebtn;
		TextView txtView;
	}

}
