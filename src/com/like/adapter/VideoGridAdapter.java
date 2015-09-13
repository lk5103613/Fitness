package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.like.entity.VideoEntity;
import com.like.fitness.R;

public class VideoGridAdapter extends SimpleAdapter<VideoEntity>{

	public VideoGridAdapter(Context context, List<VideoEntity> datas) {
		super(context, datas);
	}

	@Override
	public int getItemResourceId() {
		return R.layout.my_video_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		ImageView addView = holder.findView(R.id.add);
		RelativeLayout videoLayout = holder.findView(R.id.video);
		
		if (position == datas.size()-1) {
			addView.setVisibility(View.VISIBLE);
			videoLayout.setVisibility(View.GONE);
		} else {
			addView.setVisibility(View.GONE);
			videoLayout.setVisibility(View.VISIBLE);
		}
	}

}
