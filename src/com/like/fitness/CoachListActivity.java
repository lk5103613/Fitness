package com.like.fitness;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.like.adapter.InfoAdapter;
import com.like.customview.pulltorefresh.PullToRefreshBase;
import com.like.customview.pulltorefresh.PullToRefreshBase.Mode;
import com.like.customview.pulltorefresh.PullToRefreshListView;
import com.like.entity.CoachList;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.like.network.MyNetworkUtil;
import com.like.utils.GlobalData;

public class CoachListActivity extends BaseActivity {
	
	private PullToRefreshListView mList;
	private DataFetcher mDataFetcher;
	private InfoAdapter mAdapter;
	private int mCurrentPage = 0;
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coach_list);
		
		mDataFetcher = mDataFetcher.getInstance(mContext);
		mHandler = new Handler();
		
		mList = (PullToRefreshListView) findViewById(R.id.list);
		mList.setMode(Mode.BOTH);
		mList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>(){

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurrentPage = 0;
				updateList();
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mList.onRefreshComplete();
					}
				}, 500);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurrentPage ++;
				updateList();
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mList.onRefreshComplete();
					}
				}, 500);
			}
		});
		updateList();

	}
	
	private void updateList() {
		if(GlobalData.coachId == null)
			GlobalData.coachId = "1";
		mDataFetcher.fetchCoachOrder(mCurrentPage+"", GlobalData.coachId+"", new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				System.out.println(response);
				CoachList list = GsonUtil.gson.fromJson(response.toString(), CoachList.class);
				if(list == null ||list.list == null ||list.list.size() == 0) 
					return;
				if(mAdapter == null) {
					mAdapter = new InfoAdapter(mContext, list.list, MyNetworkUtil.getInstance(mContext).getImageLoader());
					mList.setAdapter(mAdapter);
				} else {
					mAdapter.setList(list.list);
					mAdapter.notifyDataSetChanged();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (error.getMessage()!= null) {
				}
			}
		});
	}

}
