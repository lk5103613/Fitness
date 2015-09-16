package com.lie.fitness.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.ProfitLostAdapter;
import com.like.entity.CoachIncomeDetail;
import com.like.entity.ProfitLost;
import com.like.fitness.R;
import com.like.network.GsonUtil;

public class ProfitLossFragment extends BaseFragment{
	
	private ListView mListView;
	private ProfitLostAdapter mAdapter;
	private List<ProfitLost> mProfitLosts = new ArrayList<ProfitLost>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.profit_lost_fragment, container, false);
		mListView = (ListView) view.findViewById(R.id.profit_lost_list);
		
		updateList();
//		mAdapter = new ProfitLostAdapter(getActivity(), mProfitLosts);
//		mListView.setAdapter(mAdapter);
		return view;
	}
	
	
	private void updateList() {
		mDataFetcher.fetchCoachIncomeDetail(mLoginUser.coachid, new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Type type = new TypeToken<List<CoachIncomeDetail>>(){}.getType();
				List<CoachIncomeDetail> details = GsonUtil.gson.fromJson(response.toString(), type);
				if(mAdapter == null) {
					mAdapter = new ProfitLostAdapter(mContext, details);
					mListView.setAdapter(mAdapter);
				}
			}
		}, mErrorListener);
	}
}
