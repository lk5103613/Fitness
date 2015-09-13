package com.like.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lie.fitness.fragment.CanceledOrderFragment;
import com.lie.fitness.fragment.IncomeFragment;
import com.lie.fitness.fragment.NonPayOrderFragment;
import com.lie.fitness.fragment.PaidOrderFragment;
import com.lie.fitness.fragment.ProfitLossFragment;

public class MyWalletPagerAdapter extends FragmentStatePagerAdapter{

	
	private IncomeFragment  mIncomeFragment= new IncomeFragment();
	private ProfitLossFragment mProfitLossFragment = new ProfitLossFragment();
	
	private Fragment[] mFragments = new Fragment[] { mIncomeFragment,
			mProfitLossFragment };
	private String[] mTitles = new String[] { "收入情况", "收支明细" };
	

	public MyWalletPagerAdapter(FragmentManager fm) {
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
		return 2;
	}

}
