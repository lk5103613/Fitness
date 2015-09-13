package com.like.fitness;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.like.adapter.CouponsGridAdapter;
import com.like.adapter.CouponsPersonAdapter;
import com.like.entity.CouponsUser;
import com.like.utils.DisplayUtil;

public class MyCouponsActivity extends Activity implements OnClickListener{
	
	private ListView mListView;
	private GridView mGridView;
	
	private CouponsPersonAdapter mListAdapter;
	private CouponsGridAdapter mGridAdapter;
	
	private List<CouponsUser> mCouponsUsers = new ArrayList<>();
	private List<Integer> mCoupons = new ArrayList<>();
	
	private int mScreenWidth;
	private int mScreenHeight;
	
	private PopupWindow mSharePopupWindow;
	private ImageView mArrow;
	private Activity mActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_coupons);
		mActivity = this;
		
		mListView = (ListView) findViewById(R.id.person_list);
		mGridView = (GridView) findViewById(R.id.coupons_grid);
		mArrow = (ImageView) findViewById(R.id.arrow);
		
		initData();
		mListAdapter = new CouponsPersonAdapter(this, mCouponsUsers);
		mListView.setAdapter(mListAdapter);
		
		mGridAdapter = new CouponsGridAdapter(this, mCoupons);
		mGridView.setAdapter(mGridAdapter);
		
		// 获取屏幕和PopupWindow的width和height  
        mScreenWidth = DisplayUtil.getInstance(this).getWidth();  
        mScreenHeight = DisplayUtil.getInstance(this).getHeight(); 
        
        mArrow.setOnClickListener(this);
		
	}

	private void initData() {
		for (int i = 0; i < 15; i++) {
			CouponsUser user = new CouponsUser();
			mCouponsUsers.add(user);
		}
		
		mCoupons.add(10);
		mCoupons.add(10);
		mCoupons.add(20);
		mCoupons.add(30);
		mCoupons.add(50);
		mCoupons.add(100);
		mCoupons.add(100);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.arrow:
			int[] location = new int[2];
			mArrow.getLocationOnScreen(location);
			
			int popwidth = mScreenWidth/6 * 2;
			if (mSharePopupWindow == null) {
				LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = layoutInflater.inflate(R.layout.share_pop, null);
				
				mSharePopupWindow = new PopupWindow(view, popwidth, 300, true);
				mSharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
							@Override
							public void onDismiss() {
								DisplayUtil.getInstance(mActivity).backgroundAlpha(mActivity,1);
							}
						});
			}
			mSharePopupWindow.setOutsideTouchable(true);
			mSharePopupWindow.setBackgroundDrawable(new BitmapDrawable());
			DisplayUtil.getInstance(mActivity).backgroundAlpha(mActivity,1);
			mSharePopupWindow.showAtLocation(mArrow, Gravity.NO_GRAVITY, 
					location[0]-popwidth + mArrow.getWidth()/2 + DisplayUtil.getInstance(this).dip2px(12), 
					location[1]+ mArrow.getHeight()-10);
			break;

		default:
			break;
		}
		
	}

	
}
