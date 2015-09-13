package com.like.adapter;

import java.util.List;

import com.like.fitness.R;

import android.content.Context;
import android.view.View;

public class CouponsGridAdapter extends SimpleAdapter<Integer>{

	public CouponsGridAdapter(Context context, List<Integer> datas) {
		super(context, datas);
	}

	@Override
	public int getItemResourceId() {
		return R.layout.coupons_grid_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		
		
	}

}
