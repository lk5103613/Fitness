package com.like.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.fitness.R;

public class MainListAdapter extends BaseAdapter {
	
	private int[] mDrawables = new int[]{R.drawable.main_001, R.drawable.main_002, R.drawable.main_003,
			R.drawable.main_004, R.drawable.main_005, R.drawable.main_006, R.drawable.main_007,
			R.drawable.main_008};
	private String[] mTitles = new String[]{"个人介绍", "发布课程", "课程管理", "我的视频", 
			"我的钱包", "我的学员", "设置", "客服"};
	private LayoutInflater mInflater;
	
	public MainListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mDrawables.length;
	}

	@Override
	public Object getItem(int position) {
		return mDrawables[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.main_list_item, parent, false);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.sIcon.setImageResource(mDrawables[position]);
		vh.sTitle.setText(mTitles[position]);
		return convertView;
	}
	
	public class ViewHolder {
		
		public ImageView sIcon;
		public TextView sTitle;
		
		public ViewHolder(View convertView) {
			sIcon = (ImageView) convertView.findViewById(R.id.icon);
			sTitle = (TextView) convertView.findViewById(R.id.title);
		}
		
	}

}
