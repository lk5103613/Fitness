package com.like.adapter;

import java.util.List;

import com.like.fitness.R;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DateGridAdapter extends SimpleAdapter<Integer>{
	private List<Integer> mDatas;

	public DateGridAdapter(Context context, List<Integer> datas) {
		super(context, datas);
		this.mDatas = datas;
	}

	@Override
	public int getItemResourceId() {
		return R.layout.date_grid_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		TextView item = holder.findView(R.id.grid_date);
		if (mDatas.get(position) == 0) {
			item.setText("");
		} else {
			item.setText(mDatas.get(position)+"");
		}
		
	}

}
