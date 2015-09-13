package com.like.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

public class DataFetcher {

    private static DataFetcher mInstance;
    private Context mContext;
    private RequestQueue mQueue;

    private DataFetcher(Context context) {
        this.mContext = context;
        mQueue = MyNetworkUtil.getInstance(mContext).getRequestQueue();
    }

    public static DataFetcher getInstance(Context context) {
        if(mInstance == null)
            mInstance = new DataFetcher(context);
        return mInstance;
    }
    
    public void fetchRegData(final String mp, final String avatar, final String pwd, final String nickName, 
    		Listener<JSONObject> listener, ErrorListener errorListener) {
    	String name ="";
    	try {
			name =  URLEncoder.encode(nickName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	String url = UrlParamGenerator.getPath(APIS.REG, mp, pwd, name, avatar);
    	JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, 
    			null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchLoginData(String mp, String pwd, 
    		Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.LOGIN, mp, pwd);
    	JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchAuthData(String coachId, String idcard, String idcarpath, 
    		Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.UPDATE_ID_AUTH, coachId, idcard, idcarpath);
    	JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchCat(Listener<JSONArray> listener, ErrorListener errorListener) {
    	JsonArrayRequest request = new JsonArrayRequest(Method.GET, APIS.GET_CAT, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchUpdateCoachInfo(String coachid, String avatar, String skill, String qm, String workEx, String truename, String height, String weight, String gender,
    	 Listener<JSONObject> listener, ErrorListener errorListener) {
    	try {
			truename =  URLEncoder.encode(truename, "utf-8");
			qm =  URLEncoder.encode(qm, "utf-8");
			if(workEx == null)
				workEx = "";
			else 
				workEx = URLEncoder.encode(workEx, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	String url = UrlParamGenerator.getPath(APIS.UPDATE_COACH_INFO, coachid, avatar, skill, qm, truename, height, weight, gender, workEx);
    	JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchPublishCourse(String timeFlag, String courseName,
    		String fromTime, String toTime, String price, String address, String tuiKuan, 
    		String min, String flag, String baodi, String description, String selectDays, 
    		String weekDays,String fromDay, String toDay,String coachId,
    		Listener<JSONObject> listener, ErrorListener errorListener) {
    	try {
			courseName = URLEncoder.encode(courseName, "utf-8");
			address = URLEncoder.encode(address, "utf-8");
	    	description = URLEncoder.encode(description, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	String url = UrlParamGenerator.getPath(APIS.SAVE_COURSE, timeFlag, courseName, 
    			fromTime, toTime, price, address, tuiKuan, min,flag,baodi,description, 
    			selectDays, weekDays,fromDay, toDay, coachId);
    	JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }

    
    public void fetchCoachOrder(String page, String coachId, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_COACH_ORDER, page, coachId);
    	JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchAllStudents(String coachId, Listener<JSONArray> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_ALL_STUDENT, coachId);
    	JsonArrayRequest request = new JsonArrayRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchClasses(String coachId, Listener<JSONArray> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_CLASSES, coachId);
    	JsonArrayRequest request = new JsonArrayRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchCoachInfo(String coachId, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_COACH_INFO, coachId);
    	JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchShouru(String coachId, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_SHOURU, coachId);
    	JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchCoachIncomeDetail(String coachId, Listener<JSONArray> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_COACH_INCOME, coachId);
    	JsonArrayRequest request = new JsonArrayRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchUpdatePwd(String coachId, String oldPwd, String newPwd, Listener<JSONObject> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.UPDATE_PWD, coachId, oldPwd, newPwd);
    	JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchAllVideo(String coachId, Listener<JSONArray> listener, ErrorListener errorListener) {
    	String url = UrlParamGenerator.getPath(APIS.GET_VIDEO_LIST, coachId);
    	JsonArrayRequest request = new JsonArrayRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }
    
    public void fetchAddStudent(String classId, String className, String coachId, String studentName, String mp, String memo, 
    		Listener<JSONObject> listener, ErrorListener errorListener) {
    	try {
    		className = URLEncoder.encode(className, "utf-8");
			studentName = URLEncoder.encode(studentName, "utf-8");
			memo = URLEncoder.encode(memo, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	String url = UrlParamGenerator.getPath(APIS.ADD_STUDENT, classId, coachId, studentName, mp, memo, className);
    	JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, listener, errorListener);
    	mQueue.add(request);
    }

}
