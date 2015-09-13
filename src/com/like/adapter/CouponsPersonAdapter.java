package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.like.entity.CouponsUser;
import com.like.fitness.R;

public class CouponsPersonAdapter extends SimpleAdapter<CouponsUser>{

	public CouponsPersonAdapter(Context context, List<CouponsUser> datas) {
		super(context, datas);
	}

	@Override
	public int getItemResourceId() {
		return R.layout.coupons_list_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		
		
	}

}
