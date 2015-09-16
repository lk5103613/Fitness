package com.like.customview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

import com.like.fitness.R;

public class SelectWeekView extends LinearLayout{
	
	private List<CheckBox> mCheckBoxs = new ArrayList<>();
	private List<String> mCheckedValues = new ArrayList<>();
	
	private Context mContext;
	public SelectWeekView(Context context) {
		super(context);
		init(context);
	}
	public SelectWeekView(Context context, AttributeSet attrs ){
		super(context, attrs);
		init(context);
	}

	public SelectWeekView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	private void init(Context context){
		this.mContext = context;
		LayoutInflater.from(mContext).inflate(R.layout.select_week_view, this,true);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		LinearLayout container = (LinearLayout) getChildAt(0);
		int count = container.getChildCount();
		for(int i = 0;i < count; i++){
			CheckBox checkBox = (CheckBox)container.getChildAt(i);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked) {
						mCheckedValues.add(buttonView.getText().toString());
					} else {
						mCheckedValues.remove(buttonView.getText().toString());
					}
				}
			});
			mCheckBoxs.add(checkBox);
		}
	}
	
	public List<String> getCheckedValue() {
		return mCheckedValues;
	}
	
}
