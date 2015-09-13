package com.like.adapter;

import java.util.List;

import com.like.fitness.R;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DropdownListAdapter extends SimpleAdapter<Integer>{
	private List<Integer> mDatas;
	private String mUnit;

	public DropdownListAdapter(Context context, List<Integer> datas, String unit) {
		super(context, datas);
		this.mDatas = datas;
		this.mUnit = unit;
	}
	
	public void updateList(List<Integer> datas) {
		this.mDatas = datas;
		notifyDataSetChanged();
	}

	@Override
	public int getItemResourceId() {
		return R.layout.hour_spinner_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		TextView data = holder.findView(R.id.data);
		TextView unit = holder.findView(R.id.unit);
		String date=mDatas.get(position) >= 10? mDatas.get(position)+"" : "0"+mDatas.get(position);
		
		data.setText(date);
		unit.setText(mUnit);
		
	}

}
