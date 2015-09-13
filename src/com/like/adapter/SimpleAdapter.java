package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SimpleAdapter<T> extends BaseAdapter{
	protected List<T> datas;
	protected Context context;
	private LayoutInflater inflater;
	
	public SimpleAdapter(Context context, List<T> datas) {
		super();
		this.datas = datas;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	public abstract int getItemResourceId();

	public abstract void bindData(int position, View convertView, ViewHolder holder);
	
	@Override
	public T getItem(int position) {
		return datas.get(position);
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(null == convertView) {
			convertView = inflater.inflate(getItemResourceId(), parent,false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		bindData(position, convertView, holder);
		return convertView;
	}
	
	public class ViewHolder{
		SparseArray<View> views = new SparseArray<View>();
		private View convertView;
		
		public ViewHolder(View convertView) {
			this.convertView = convertView;
		}
		
		public <T extends View> T findView(int resId) {
			View v = views.get(resId);
			if (null == v) {
				v = convertView.findViewById(resId);
				views.put(resId, v);
			}
			return (T) v;
		}
	}
}
