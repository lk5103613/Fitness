package com.lie.fitness.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.ClassListAdapter;
import com.like.adapter.MyStudentPagerAdapter.FragmentListener;
import com.like.entity.ClassEntity;
import com.like.entity.ClassInfo;
import com.like.fitness.R;
import com.like.network.GsonUtil;

public class ClassStudentFragment extends BaseFragment {
	
	private ListView mClassListView;
	private ClassListAdapter mAdapter;
	private List<ClassEntity> classEntities = new ArrayList<>();
	private FragmentListener mListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.class_student_fragment, container, false);
		mClassListView = (ListView) view.findViewById(R.id.class_list);
		
		mClassListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				ClassInfo classInfo = mAdapter.getItem(position);
				if(mListener != null) {
					mListener.classListClick(classInfo);
				}
			}
		});
		
		updateList();
		return view;
	}
	
	public void setListener(FragmentListener listener) {
		this.mListener = listener;
	}
	
	private void updateList() {
		mDataFetcher.fetchClasses(mLoginUser.coachid, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Type type = new TypeToken<List<ClassInfo>>(){}.getType();
				List<ClassInfo> classes = GsonUtil.gson.fromJson(response.toString(), type);
				mAdapter = new ClassListAdapter(mContext, classes);
				mClassListView.setAdapter(mAdapter);
			}
		}, mErrorListener);
	}
	
	private void initData(){
		for (int i = 0; i < 20; i++) {
			ClassEntity classEntity = new ClassEntity();
			classEntities.add(classEntity);
		}
	}

}
