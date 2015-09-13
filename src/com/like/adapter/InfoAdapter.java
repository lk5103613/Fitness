package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.like.entity.CoachInfo;
import com.like.fitness.R;
import com.like.network.APIS;

public class InfoAdapter extends SimpleAdapter<CoachInfo>{

	private List<CoachInfo> mCoaches;
	private ImageLoader mImageLoader;
	
	public InfoAdapter(Context context, List<CoachInfo> datas, ImageLoader imageLoader) {
		super(context, datas);
		this.mCoaches = datas;
		this.mImageLoader = imageLoader;
	}

	@Override
	public int getItemResourceId() {
		return R.layout.coach_info_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		CoachInfo coach = mCoaches.get(position);
		ImageView image = holder.findView(R.id.header);
		mImageLoader.get(APIS.BASE_URL + coach.avatar, ImageLoader.getImageListener(image,
				R.drawable.start, R.drawable.start));
		
		((TextView)holder.findView(R.id.course_name)).setText(coach.course_name);
		((TextView)holder.findView(R.id.order_no)).setText(coach.order_no);
		((TextView)holder.findView(R.id.price)).setText(coach.money);
		((TextView)holder.findView(R.id.time)).setText(coach.addTime);
		
	}

	public void setList(List<CoachInfo> list) {
		this.mCoaches = list;
	}

}
