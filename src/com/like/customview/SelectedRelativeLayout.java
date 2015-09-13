package com.like.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SelectedRelativeLayout extends RelativeLayout{
	
	public SelectedRelativeLayout(Context context) {
		super(context);
	}
	public SelectedRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelectedRelativeLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	public void setSelected(boolean selected) {
		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).setSelected(selected);
		}
		super.setSelected(selected);
	}

}
