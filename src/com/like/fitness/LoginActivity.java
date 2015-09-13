package com.like.fitness; 
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.like.entity.RegResult;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.like.utils.GlobalData;

public class LoginActivity extends BaseActivity implements OnTouchListener{
	
	private EditText mTxtMp;
	private EditText mTxtPwd;
	private LinearLayout mLoginContainer;
	
	private DataFetcher mDataFetcher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mTxtMp = (EditText) findViewById(R.id.txt_phone);
		mTxtPwd = (EditText) findViewById(R.id.txt_pwd);
		mLoginContainer = (LinearLayout) findViewById(R.id.login_container);
		mLoginContainer.setOnTouchListener(this);
		
		mDataFetcher = DataFetcher.getInstance(mContext);
	}

	public void registered(View v) {
		Intent intent = new Intent(mContext, RegActivity.class);
		startActivity(intent);
	}
	
	public void login(View v) {
		String mp = mTxtMp.getText().toString();
		String pwd = mTxtPwd.getText().toString();
		if(TextUtils.isEmpty(mp) || TextUtils.isEmpty(pwd)) {
			Toast.makeText(mContext, "请输入完整信息", Toast.LENGTH_LONG).show();
			return;
		}
		mDataFetcher.fetchLoginData(mp, pwd, new Listener<JSONObject>() {
			@Override
			public void onResponse(final JSONObject response) {
				String responseStr = "";
				try {
					responseStr = new String(response.toString().getBytes(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				RegResult result = GsonUtil.gson.fromJson(responseStr, RegResult.class);
				if(result.code != 1) {
					Toast.makeText(mContext, "手机号或者密码错误", Toast.LENGTH_LONG).show();
					return;
				} else if(result.status != 1) {
					Toast.makeText(mContext, "您的账号尚未通过审核", Toast.LENGTH_LONG).show();
					return;
				} else {
					Toast.makeText(mContext, "登录成功", Toast.LENGTH_LONG).show();
					GlobalData.COACH = result;
					new Thread(new Runnable() {
						@Override
						public void run() {
							mLoginSharePref.edit().putString(LOGIN_USER, 
									response.toString()).commit();
						}
					}).start();
					Intent intent = new Intent(mContext, MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "登录失败", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.login_container:
			v.setFocusable(true);
			v.setFocusableInTouchMode(true);
			v.requestFocus();
			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mTxtMp.getWindowToken(), 0);
			break;
		}
		return false;
	}
	
	
}
