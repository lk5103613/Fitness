package com.like.fitness;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.ImageLoader;
import com.like.entity.RegResult;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.like.network.MyNetworkUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends Activity {
	
	protected final static String LOGIN_PROP_NAME = "login_properties";
	protected final static String LOGIN_USER = "login_user";
	
	protected Context mContext = null;
	
	protected SharedPreferences mLoginSharePref;
	protected DataFetcher mDataFetcher;
	protected ErrorListener mErrorListener;
	protected RegResult mLoginUser;
	protected ImageLoader mImgLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mLoginSharePref = getSharedPreferences(LOGIN_PROP_NAME, Context.MODE_PRIVATE);
		mDataFetcher = DataFetcher.getInstance(mContext);
		mErrorListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
			}
		};
		mImgLoader = MyNetworkUtil.getInstance(mContext).getImageLoader();
		mLoginUser = getLoginUser();
	}
	
	public void back(View v) {
		this.finish();
	}
	
	protected RegResult getLoginUser() {
		String json = mLoginSharePref.getString(LOGIN_USER, "");
		if(TextUtils.isEmpty(json))
			return null;
		return GsonUtil.gson.fromJson(json, RegResult.class);
	}

}
