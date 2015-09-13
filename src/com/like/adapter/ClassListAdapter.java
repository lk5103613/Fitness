package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.like.entity.ClassInfo;
import com.like.fitness.R;

public class ClassListAdapter extends SimpleAdapter<ClassInfo>{
	
	public ClassListAdapter(Context context, List<ClassInfo> datas) {
		super(context, datas);
	}

	@Override
	public int getItemResourceId() {
		return R.layout.class_list_item;
	}

	@Override
	public void bindData(int position, View convertView,ViewHolder holder) {
		TextView className = holder.findView(R.id.class_name);
		className.setText(datas.get(position).classname);
	}
	
}
