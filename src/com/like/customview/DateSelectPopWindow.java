package com.like.customview;

import java.util.ArrayList;
import java.util.List;

import com.like.adapter.DateGridAdapter;
import com.like.fitness.R;
import com.like.utils.DisplayUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;

public class DateSelectPopWindow extends PopupWindow implements OnItemClickListener{
	
	private Context mContext;
	private List<Integer> mDates;
	private GridView mDateGrid;
	
	private List<String> mSelectedDates = new ArrayList<>();
	
	public DateSelectPopWindow(Context context, List<Integer> dates){
		super();
		this.mContext = context;
		this.mDates = dates;
		
		init();
	}
	
	private void init(){
		View layout = LayoutInflater.from(mContext).inflate(R.layout.date_layout, null);
		mDateGrid = (GridView) layout.findViewById(R.id.date_grid);
		mDateGrid.setOnItemClickListener(this);
		setContentView(layout);
		setOutsideTouchable(true);
		setFocusable(true);
		setBackgroundDrawable(new BitmapDrawable()); 
		int popWidth = DisplayUtil.getInstance(mContext).getWidth()/3 *2;
		setWidth(popWidth);
		setHeight(400);
		
		DateGridAdapter gridAdapter = new DateGridAdapter(mContext, mDates);
		mDateGrid.setAdapter(gridAdapter);
	}

	@SuppressLint("NewApi")
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		view.getTag();
		System.out.println("date " + mDates.get(position));
		
		if (mSelectedDates.contains(mDates.get(position)+"")) {
			view.setBackgroundColor(Color.WHITE);
			mSelectedDates.remove(mDates.get(position)+"");
		} else {
			view.setBackground(mContext.getResources().getDrawable(R.drawable.stroke_grey_bg));
			mSelectedDates.add(mDates.get(position)+"");
		}
		
	}
	
	public List<String> getDates(){
		return mSelectedDates;
	} 
}
