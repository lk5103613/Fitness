package com.like.fitness;

import com.like.adapter.MyOrderPagerAdapter;
import com.like.customview.PagerSlidingTabStrip;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MyOrderActivity extends FragmentActivity {
	private PagerSlidingTabStrip mPagerTab;
	private ViewPager mOrderPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);

		mPagerTab = (PagerSlidingTabStrip) findViewById(R.id.order_tap);
		mOrderPager = (ViewPager) findViewById(R.id.order_pager);

		initTab();
	}

	private void initTab() {
		mOrderPager.setAdapter(new MyOrderPagerAdapter(
				getSupportFragmentManager()));
		mPagerTab.setShouldExpand(true);
		mPagerTab.setFillViewport(true);
		mPagerTab.setIndicatorColorResource(R.color.primary_red_color);
		mPagerTab.setTextColorStateResource(R.color.tab_text_color);
		mPagerTab.setTextSize(26);
		mPagerTab.setViewPager(mOrderPager);
		mPagerTab.setIndicatorPadding(20);
		mPagerTab.setDividerColorResource(R.color.primary_background_color);
	}

}
