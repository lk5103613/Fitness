package com.like.fitness;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.like.customview.RoundImageView;
import com.like.entity.CoachInfo;
import com.like.entity.CommonResult;
import com.like.network.APIS;
import com.like.network.GsonUtil;
import com.like.utils.BitmapUtil;
import com.like.utils.UploadUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PerEditActivity extends BaseActivity {
	
	private final static int REQUEST_TAKE_PHOTO = 0;
	private final static int REQUEST_FROM_FILE = 1;
	
	private RoundImageView mHeaderIcon;
	private String mAvatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_per_edit);
		mHeaderIcon = (RoundImageView) findViewById(R.id.header);
	}
	
	public void takePhoto(View v) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		startActivityForResult(intent, REQUEST_TAKE_PHOTO);
	}
	
	public void fromFile(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_FROM_FILE);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			Uri uri = BitmapUtil.getUriByData(data, getContentResolver());
			File file = BitmapUtil.getFileByUri(getContentResolver(), uri);
			Bitmap bmp = BitmapUtil.getResizeBitmap(file);
			System.out.println(file.getAbsolutePath());
			mHeaderIcon.setImageBitmap(bmp);
			new AsyncTask<File, Void, String>() {
				@Override
				protected String doInBackground(File... params) {
					File uploadFile = params[0];
					final String serverImgName = UploadUtil.getImgName();
					try {
						UploadUtil.post(uploadFile, APIS.UPLOAD,
								serverImgName);
						mAvatar = "upload/" + serverImgName + UploadUtil.getExtensionName(uploadFile.getAbsolutePath());
					} catch(Exception e) {
						e.printStackTrace();
					}
					return null;
				}
				
				protected void onPostExecute(String result) {
					mLoginUser.avatar = mAvatar;
					mDataFetcher.fetchUpdateCoachInfo(mLoginUser.coachid, mLoginUser.avatar, mLoginUser.skill, 
							mLoginUser.coach_description, mLoginUser.work_experience, mLoginUser.truename, 
							mLoginUser.height, mLoginUser.weight, ""+mLoginUser.gender, new Listener<JSONObject>(){
								@Override
								public void onResponse(JSONObject response) {
									CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
									if(result.code == 1) {
										Toast.makeText(mContext, "修改头像成功", Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(mContext, "修改头像失败", Toast.LENGTH_SHORT).show();
									}
								}}, mErrorListener);
				};
			}.execute(file);
		}
	}
	
}
