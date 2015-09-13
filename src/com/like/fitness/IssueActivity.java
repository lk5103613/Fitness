package com.like.fitness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IssueActivity extends BaseActivity implements OnClickListener{
	private Button mBtnSingle;
	private Button mBtnMulity;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_issue);
		
		this.mContext = this;
		
		mBtnSingle = (Button) findViewById(R.id.btn_single);
		mBtnMulity = (Button) findViewById(R.id.btn_mulity);
		
		mBtnMulity.setOnClickListener(this);
		mBtnSingle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(mContext, PublishCourseActivity.class);
		switch (v.getId()) {
		case R.id.btn_single:
			intent.putExtra("count", "单次");
			break;
		case R.id.btn_mulity:
			intent.putExtra("count", "多次");
			break;
		default:
			break;
		}
		startActivity(intent);
	}

	public void back(View v) {
		this.finish();
	}
	
}
