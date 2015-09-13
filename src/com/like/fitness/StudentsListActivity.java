package com.like.fitness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.like.adapter.MyStudentPagerAdapter;
import com.like.adapter.MyStudentPagerAdapter.FragmentListener;
import com.like.customview.PagerSlidingTabStrip;
import com.like.entity.ClassInfo;
import com.like.network.GsonUtil;

public class StudentsListActivity extends FragmentActivity implements FragmentListener {
	private PagerSlidingTabStrip mPagerTab;
	private ViewPager mStudentPager;
	private Context mContext;
	private boolean mIsForSelect = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_students_list);
		
		Intent intent = getIntent();
		if(intent != null) {
			mIsForSelect = intent.getBooleanExtra("select", false);
		}
		
		mPagerTab = (PagerSlidingTabStrip) findViewById(R.id.student_tap);
		mStudentPager = (ViewPager) findViewById(R.id.student_pager);
		mContext = this;
		initTab();
	}

	private void initTab() {
		MyStudentPagerAdapter adapter = new MyStudentPagerAdapter(
				getSupportFragmentManager());
		adapter.getStudentFragment().setListener(this);
		mStudentPager.setAdapter(adapter);
		mPagerTab.setShouldExpand(true);
		mPagerTab.setFillViewport(true);
		mPagerTab.setIndicatorColorResource(R.color.primary_red_color);
		mPagerTab.setTextColorStateResource(R.color.tab_text_color);
		mPagerTab.setTextSize(26);
		mPagerTab.setViewPager(mStudentPager);
		mPagerTab.setIndicatorPadding(20);
		mPagerTab.setDividerPadding(0);
		mPagerTab.setDividerColorResource(R.color.primary_background_color);
		
		if(mIsForSelect)
			mStudentPager.setCurrentItem(1);
		else
			mStudentPager.setCurrentItem(0);
	}
	
	public void addStudent(View v) {
		Intent intent = new Intent(mContext, AddStudentActivity.class);
		startActivity(intent);
	}
	
	public void back(View v) {
		this.finish();
	}

	@Override
	public void classListClick(ClassInfo classInfo) {
		if(!mIsForSelect) 
			return;
		Intent result = new Intent();
		String json = GsonUtil.gson.toJson(classInfo);
		result.putExtra("data", json);
		setResult(RESULT_OK, result);
		finish();
	}
	
}
