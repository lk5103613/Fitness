package com.lie.fitness.fragment;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.like.entity.RegResult;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BaseFragment extends Fragment {
	
	protected final static String LOGIN_PROP_NAME = "login_properties";
	protected final static String LOGIN_USER = "login_user";
	
	protected Context mContext = null;
	
	protected SharedPreferences mLoginSharePref;
	protected DataFetcher mDataFetcher;
	protected ErrorListener mErrorListener;
	protected RegResult mLoginUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		mContext = getActivity();
		
		mLoginSharePref = mContext.getSharedPreferences(LOGIN_PROP_NAME, Context.MODE_PRIVATE);
		mDataFetcher = DataFetcher.getInstance(mContext);
		mErrorListener = new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
			}
		};
		mLoginUser = getLoginUser();
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	protected RegResult getLoginUser() {
		String json = mLoginSharePref.getString(LOGIN_USER, "");
		if(TextUtils.isEmpty(json))
			return null;
		return GsonUtil.gson.fromJson(json, RegResult.class);
	}

}
