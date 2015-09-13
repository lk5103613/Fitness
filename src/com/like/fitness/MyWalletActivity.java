package com.like.fitness;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.like.adapter.MyWalletPagerAdapter;
import com.like.customview.PagerSlidingTabStrip;

public class MyWalletActivity extends FragmentActivity {
	
	private PagerSlidingTabStrip mPagerTab;
	private ViewPager mWalletPager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_wallet);
		
		mPagerTab = (PagerSlidingTabStrip) findViewById(R.id.wallet_tap);
		mWalletPager = (ViewPager) findViewById(R.id.wallet_pager);
		initTab();
		
	}
	
	private void initTab() {
		mWalletPager.setAdapter(new MyWalletPagerAdapter(
				getSupportFragmentManager()));
		mPagerTab.setShouldExpand(true);
		mPagerTab.setFillViewport(true);
		mPagerTab.setIndicatorColorResource(R.color.primary_red_color);
		mPagerTab.setTextColorStateResource(R.color.tab_text_color);
		mPagerTab.setTextSize(26);
		mPagerTab.setViewPager(mWalletPager);
		mPagerTab.setIndicatorPadding(20);
		mPagerTab.setDividerPadding(0);
		mPagerTab.setDividerColorResource(R.color.primary_background_color);
	}

	public void back(View v) {
		this.finish();
	}
	
}
