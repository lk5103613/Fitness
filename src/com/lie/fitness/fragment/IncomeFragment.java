package com.lie.fitness.fragment;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.dacer.androidcharts.LineView;
import com.like.entity.InCome;
import com.like.fitness.R;
import com.like.network.GsonUtil;

public class IncomeFragment extends BaseFragment{
	 int randomint = 7;
	 ArrayList<String> bottomStr = new ArrayList<String>();
	 private String coachId = "1";
	 private LineView mLineView;
	 private TextView mTotal;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.income_fragment, container, false);
		mLineView = (LineView)view.findViewById(R.id.line_view);
		
		mTotal = (TextView) view.findViewById(R.id.total);
		
		
		//must*
       
        initData();
        mLineView.setBottomTextList(bottomStr);
        mLineView.setDrawDotLine(false);
        mLineView.setShowPopup(LineView.SHOW_POPUPS_All);
        mLineView.setUnit("￥");
        
//        randomSet(lineView);
        fetchData();
		return view;
	}
	
	 private void initData() {
		bottomStr.add("周一");
		bottomStr.add("周二");
		bottomStr.add("周三");
		bottomStr.add("周四");
		bottomStr.add("周五");
		bottomStr.add("周六");
		bottomStr.add("周日");
	}
	 
	 private void fetchData(){
		 mDataFetcher.fetchShouru(coachId, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				InCome result = GsonUtil.gson.fromJson(response.toString(), InCome.class);
				setData(result);
			}
		}, mErrorListener);
	 }

	private void setData(InCome income){
	        ArrayList<Integer> dataList = new ArrayList<Integer>();
	        dataList.add(Integer.parseInt(income.one));
	        dataList.add(Integer.parseInt(income.two));
	        dataList.add(Integer.parseInt(income.three));
	        dataList.add(Integer.parseInt(income.four));
	        dataList.add(Integer.parseInt(income.five));
	        dataList.add(Integer.parseInt(income.six));
	        dataList.add(Integer.parseInt(income.seven));
	        
	        int total = Integer.valueOf(income.one) + Integer.valueOf(income.two) + Integer.valueOf(income.three) + Integer.valueOf(income.four)
	        		+ Integer.valueOf(income.five) + Integer.valueOf(income.six) + Integer.valueOf(income.seven);
	        mTotal.setText("合计：" + total + "元");

	        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<ArrayList<Integer>>();
	        dataLists.add(dataList);
	        
	        mLineView.setDataList(dataLists);
	    }
}
