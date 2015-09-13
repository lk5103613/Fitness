package com.like.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.like.adapter.MainListAdapter;

public class MainActivity extends BaseActivity {
	
	private GridView mMainList;
	private MainListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMainList = (GridView) findViewById(R.id.main_list);
		mAdapter = new MainListAdapter(mContext);
		mMainList.setAdapter(mAdapter);
		mMainList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent;
				int drawable = (int) mAdapter.getItem(position);
				switch (drawable) {
				case R.drawable.main_001:
					intent = new Intent(mContext, PerEditInfoActivity.class);
					break;
				case R.drawable.main_002:
					intent = new Intent(mContext, IssueActivity.class);
					break;
				case R.drawable.main_003:
					return;
				case R.drawable.main_004:
					intent = new Intent(mContext, MyVideoActivity.class);
					break;
				case R.drawable.main_005:
					intent = new Intent(mContext, MyWalletActivity.class);
					break;
				case R.drawable.main_006:
					intent = new Intent(mContext, StudentsListActivity.class);
					break;
				case R.drawable.main_007:
					intent = new Intent(mContext, AppSettingActivity.class);
					break;
				case R.drawable.main_008:
					intent = new Intent(mContext, ContactServiceActivity.class);
					break;
				default:
					return;
				}
				startActivity(intent);
			}
		});
	}
	
	
	public void scan(View v) {
		Intent intent = new Intent(mContext, QRCodeActivity.class);
		startActivity(intent);
	}
	
	public void verify(View v){
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
	}
	
}
