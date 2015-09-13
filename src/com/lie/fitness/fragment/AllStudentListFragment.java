package com.lie.fitness.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.example.sortlistview.CharacterParser;
import com.example.sortlistview.PinyinComparator;
import com.example.sortlistview.SideBar;
import com.example.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.example.sortlistview.SortAdapter;
import com.example.sortlistview.SortModel;
import com.example.sortlistview.StudentComparator;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.StudentsListAdapter;
import com.like.entity.RegResult;
import com.like.entity.StudentInfo;
import com.like.fitness.R;
import com.like.network.GsonUtil;

public class AllStudentListFragment extends BaseFragment{
	
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private StudentsListAdapter mAdapter;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<StudentInfo> mStudents;
	private List<SortModel> sourceDateList;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	private StudentComparator studentComparator;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.all_student_fragment, container, false);
		initViews(view);
		return view;
	}
	
	private void initViews(View view) {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		studentComparator = new StudentComparator();
		
		sideBar = (SideBar) view.findViewById(R.id.sidrbar);
		dialog = (TextView) view.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = mAdapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
			}
		});
		
		sortListView = (ListView) view.findViewById(R.id.country_lvcountry);
		updateList();
//		mStudents = initStudents();
//		
//		// 根据a-z进行排序源数据
//		Collections.sort(mStudents, studentComparator);
//		mAdapter = new StudentsListAdapter(mContext, mStudents);
//		sortListView.setAdapter(mAdapter);
		
	}
	
	private void updateList() {
		mDataFetcher.fetchAllStudents(mLoginUser.coachid, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Type type = new TypeToken<List<StudentInfo>>(){}.getType();
				mStudents = GsonUtil.gson.fromJson(response.toString(), type);
				initStudents();
				if(mAdapter == null) {
					 mAdapter = new StudentsListAdapter(mContext, mStudents);
					 sortListView.setAdapter(mAdapter);
				} else {
					mAdapter.updateList(mStudents);
				}
			}
		}, mErrorListener);
	}
	
	private void initStudents() {
		for(int i=0; i<mStudents.size(); i++) {
			StudentInfo student = mStudents.get(i);
			SortModel model = filledData(student.truename);
			student.sortModel = model;
		}
		Collections.sort(mStudents, studentComparator);
	}


	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
	}
	
	private SortModel filledData(String data) {
		SortModel model = new SortModel();
		model.setName(data);
		String pinyin = characterParser.getSelling(data);
		String sortString = pinyin.substring(0, 1).toUpperCase();
		if(sortString.matches("[A-Z]")){
			model.setSortLetters(sortString.toUpperCase());
		}else{
			model.setSortLetters("#");
		}
		return model;
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = sourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : sourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
	
	

}
