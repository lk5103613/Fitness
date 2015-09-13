package com.like.fitness;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.like.customview.RoundImageView;
import com.like.entity.CoachInfo;
import com.like.entity.CommonResult;
import com.like.network.APIS;
import com.like.network.GsonUtil;

public class PerEditInfoActivity extends BaseActivity {

	private EditText mTxtDes;
	private EditText mTxtWorkExp;
	private RoundImageView mHeaderIcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_per_edit_info);

		mTxtDes = (EditText) findViewById(R.id.coach_des);
		mTxtWorkExp = (EditText) findViewById(R.id.work_exp);
		mHeaderIcon = (RoundImageView) findViewById(R.id.header);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initUser();
	}

	public void editIcon(View v) {
		Intent intent = new Intent(mContext, PerEditActivity.class);
		startActivity(intent);
	}

	public void back(View v) {
		this.finish();
	}
	
	private void initUser() {
		mDataFetcher.fetchCoachInfo(mLoginUser.coachid, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				mDataFetcher.fetchCoachInfo(mLoginUser.coachid, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CoachInfo coach = GsonUtil.gson.fromJson(response.toString(), CoachInfo.class);
						mTxtDes.setText(coach.coachDescription);
						mTxtWorkExp.setText(coach.workExp);
						String picPath = APIS.BASE_URL + "/" + coach.avatar;
						mImgLoader.get(picPath, ImageLoader.getImageListener(mHeaderIcon, R.drawable.tuxiang, R.drawable.tuxiang));
					}
				}, mErrorListener);
			}
		}, mErrorListener);
	}

	public void save(View v) {
		String des = mTxtDes.getText().toString();
		String workExp = mTxtWorkExp.getText().toString();
		if (!TextUtils.isEmpty(des)) {
			mLoginUser.coach_description = des;
		}
		if (!TextUtils.isEmpty(workExp)) {
			mLoginUser.work_experience = workExp;
		}
		mDataFetcher.fetchUpdateCoachInfo(mLoginUser.coachid,
				mLoginUser.avatar, mLoginUser.skill,
				mLoginUser.coach_description, mLoginUser.work_experience,
				mLoginUser.truename, mLoginUser.height, mLoginUser.weight, ""
						+ mLoginUser.gender, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						CommonResult result = GsonUtil.gson.fromJson(
								response.toString(), CommonResult.class);
						if (result.code == 1) {
							Toast.makeText(mContext, "更新成功",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mContext, "更新失败",
									Toast.LENGTH_SHORT).show();
						}
					}
				}, mErrorListener);
	}

}
