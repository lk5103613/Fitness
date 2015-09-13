package com.like.fitness;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.like.entity.ClassInfo;
import com.like.entity.CommonResult;
import com.like.network.GsonUtil;

public class AddStudentActivity extends BaseActivity {
	
	private final static int REQUEST_SELECT_CLASS = 1;
	private ClassInfo mClass;
	
	private EditText mTxtClassName;
	private EditText mTxtPhoneNum;
	private EditText mTxtName;
	private EditText mTxtComment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_student);
		
		mTxtName = (EditText) findViewById(R.id.name);
		mTxtPhoneNum = (EditText) findViewById(R.id.phone_num);
		mTxtClassName = (EditText) findViewById(R.id.class_name);
		mTxtComment = (EditText) findViewById(R.id.comment);
	}
	
	public void back(View v) {
		this.finish();
	}
	
	public void choiceClass(View v) {
		Intent intent = new Intent(mContext, StudentsListActivity.class);
		intent.putExtra("select", true);
		startActivityForResult(intent, REQUEST_SELECT_CLASS);
	}
	
	public void save(View v) {
		String className = mTxtClassName.getText().toString();
		String name = mTxtName.getText().toString();
		String comment = mTxtComment.getText().toString();
		String phoneNum = mTxtPhoneNum.getText().toString();
		if(TextUtils.isEmpty(className) || TextUtils.isEmpty(name) || TextUtils.isEmpty(comment) || TextUtils.isEmpty(phoneNum)) {
			Toast.makeText(mContext, "请填写完整信息", Toast.LENGTH_SHORT).show();
			return;
		}
		mDataFetcher.fetchAddStudent(mClass.classId, className, mLoginUser.coachid, name, phoneNum, comment, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
				if(result.code == 1) {
					Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
				}
			}
		}, mErrorListener);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK) {
			if(requestCode == REQUEST_SELECT_CLASS) {
				String json = data.getStringExtra("data");
				mClass = GsonUtil.gson.fromJson(json, ClassInfo.class);
				mTxtClassName.setText(mClass.classname);
			}
		}
	}

}
