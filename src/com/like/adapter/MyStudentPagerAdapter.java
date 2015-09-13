package com.like.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lie.fitness.fragment.AllStudentListFragment;
import com.lie.fitness.fragment.ClassStudentFragment;
import com.like.entity.ClassInfo;

public class MyStudentPagerAdapter extends FragmentStatePagerAdapter{
	
	private AllStudentListFragment  mAllStudentListFragment= new AllStudentListFragment();
	private ClassStudentFragment mClassStudentFragment = new ClassStudentFragment();
	
	private Fragment[] mFragments = new Fragment[] { mAllStudentListFragment,
			mClassStudentFragment};
	private String[] mTitles = new String[] { "全部学员", "班级学员" };
	

	public MyStudentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		return mFragments[index];
	}
	
	public ClassStudentFragment getStudentFragment() {
		return mClassStudentFragment;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles[position];
	}

	@Override
	public int getCount() {
		return 2;
	}
	
	public interface FragmentListener {
		void classListClick(ClassInfo classInfo);
	}

}
