package com.like.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lie.fitness.fragment.CanceledOrderFragment;
import com.lie.fitness.fragment.NonPayOrderFragment;
import com.lie.fitness.fragment.PaidOrderFragment;

public class MyOrderPagerAdapter extends FragmentStatePagerAdapter{

	
	private CanceledOrderFragment mCanceledFragment = new CanceledOrderFragment();
	private NonPayOrderFragment mNonPayOrderFragment = new NonPayOrderFragment();
	private PaidOrderFragment mPaidOrderFragment = new PaidOrderFragment();
	
	private Fragment[] mFragments = new Fragment[] { mCanceledFragment,
			mNonPayOrderFragment, mPaidOrderFragment };
	private String[] mTitles = new String[] { "已取消订单", "已付款", "未付款" };
	

	public MyOrderPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	

	@Override
	public Fragment getItem(int index) {
		return mFragments[index];
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles[position];
	}

	@Override
	public int getCount() {
		return 3;
	}

}
