package com.like.fitness;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.like.entity.RegResult;
import com.like.network.APIS;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.like.utils.GlobalData;
import com.like.utils.UploadUtil;

public class RegActivity extends BaseActivity implements OnTouchListener{
	
	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_FROM_FILE = 2;
	
	private EditText mTxtNickName;
	private EditText mTxtMP;
	private EditText mTxtValidate;
	private EditText mTxtPwd;
	private LinearLayout mRegContainer;
	private ImageView mHeaderIcon;
	private String mImgPath;
	private Bitmap mBmp;
	
	private DataFetcher mDataFetcher;
	private Dialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg);
		
		mTxtNickName = (EditText) findViewById(R.id.txt_nick_name);
		mTxtMP = (EditText) findViewById(R.id.txt_phone);
		mTxtValidate = (EditText) findViewById(R.id.validate_code);
		mTxtPwd = (EditText) findViewById(R.id.txt_pwd);
		mRegContainer = (LinearLayout) findViewById(R.id.reg_container);
		mHeaderIcon = (ImageView) findViewById(R.id.header_icon);
		
		mRegContainer.setOnTouchListener(this);
		
		mDataFetcher = DataFetcher.getInstance(mContext);
	}
	
	public void back(View v) {
		this.finish();
	}
	
	public void reg(View v) {
		final String nickName = mTxtNickName.getText().toString();
		String mp = mTxtMP.getText().toString();
		String validateCode = mTxtValidate.getText().toString();
		String pwd = mTxtPwd.getText().toString();
		if(TextUtils.isEmpty(nickName) || TextUtils.isEmpty(mp) || 
				TextUtils.isEmpty(validateCode) || TextUtils.isEmpty(pwd)) {
			Toast.makeText(mContext, "请输入完整信息", Toast.LENGTH_LONG).show();
			return;
		}
		if (mp.length() < 11) {
			Toast.makeText(mContext, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
			mTxtMP.requestFocus();
			return;
		}
		if (pwd.length() < 6) {
			Toast.makeText(mContext, "密码至少六位", Toast.LENGTH_LONG).show();
			mTxtPwd.requestFocus();
			return;
		}
		if(TextUtils.isEmpty(mImgPath)) {
			Toast.makeText(mContext, "请上传头像", Toast.LENGTH_LONG).show();
			return;
		}
		
		mDataFetcher.fetchRegData(mp, mImgPath, pwd, nickName, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				RegResult result = GsonUtil.gson.fromJson(response.toString(), RegResult.class);
				if(result.code == 2) {
					Toast.makeText(mContext, "您输入的手机号已经存在", Toast.LENGTH_SHORT).show();
					return;
				} else if(result.code != 1) {
					Toast.makeText(mContext, "注册失败", Toast.LENGTH_SHORT).show();
					return;
				} else {
					Toast.makeText(mContext, "注册成功", Toast.LENGTH_LONG).show();
					GlobalData.coachId = result.coachid;
					GlobalData.nickname = nickName;
					System.out.println("注册成功  。。。。  ");
					Intent intent = new Intent(mContext, UserInfoActivity.class);
					intent.putExtra("icon", mBmp);
					startActivity(intent);
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (error.getMessage()!= null) {
					System.out.println(error.getMessage());
				}
				Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.setFocusable(true);
		v.setFocusableInTouchMode(true);
		v.requestFocus();
		// 隐藏键盘
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mTxtNickName.getWindowToken(), 0);
		return false;
	}
	
	public void takePhoto(View v) {
		showDialog();
	}
	
	private void showDialog() {
		if(mDialog == null) {
			mDialog = new Dialog(mContext, R.style.Theme_dialog);
			View view = LayoutInflater.from(mContext).inflate(R.layout.choice_photo_dialog, null);
			Button btnCamera = (Button)view.findViewById(R.id.take_from_camert);
			btnCamera.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					startActivityForResult(intent, REQUEST_TAKE_PHOTO);
				}
			});
			Button btnGalley = (Button) view.findViewById(R.id.take_from_file);
			btnGalley.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mDialog.dismiss();
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setType("image/*");
					startActivityForResult(intent, REQUEST_FROM_FILE);
				}
			});
			mDialog.setContentView(view);
		}
		mDialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_TAKE_PHOTO:
			if (data != null) {
				Uri uri;
				Bitmap bitmap = null;
				try {
					Bundle extras = data.getExtras();
				    bitmap = (Bitmap) extras.get("data");
					if (data.getData() != null) { 
			            uri = data.getData();
			        } 
			        else { 
			            uri  = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));      
			        } 
					String[] filePathColumn = { MediaStore.Images.Media.DATA };
					final Cursor cursor = getContentResolver().query(uri,
							filePathColumn, null, null, null);
					cursor.moveToFirst();
					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String picturePath = cursor.getString(columnIndex);
					final File file = new File(picturePath);
					BitmapFactory.Options opts = new BitmapFactory.Options();
					if (file.length() < 20480) { // 0-20k
						opts.inSampleSize = 1;
					} else if (file.length() < 51200) { // 20-50k
						opts.inSampleSize = 2;
					} else if (file.length() < 307200) { // 50-300k
						opts.inSampleSize = 4;
					} else if (file.length() < 819200) { // 300-800k
						opts.inSampleSize = 6;
					} else if (file.length() < 1048576) { // 800-1024k
						opts.inSampleSize = 8;
					} else {
						opts.inSampleSize = 10;
					}
					if(file.length() > 5242880) {
						Toast.makeText(mContext, "图片不能大于5M,请压缩后上传", Toast.LENGTH_LONG);
						return;
					}
					Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
					mBmp = resizeBmp;
					mHeaderIcon.setImageBitmap(resizeBmp);
					new AsyncTask<File, Void, String>() {
						@Override
						protected String doInBackground(File... params) {
							File uploadFile = params[0];
							DateFormat f2 = new SimpleDateFormat("yyyyMMddHHmmss");
							String day = f2.format(new Date());
							int max = 10000;
							int min = 99999;
							Random random = new Random();
							int s = random.nextInt(max) % (max - min + 1) + min;
							final String serverImgName = day + s;
							try {
								UploadUtil.post(uploadFile, APIS.UPLOAD,
										serverImgName);
								mImgPath = "upload/" + serverImgName;
							} catch(Exception e) {
								e.printStackTrace();
							}
							cursor.close();
							return null;
						}
					}.execute(file);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case REQUEST_FROM_FILE:
			if (data != null) {
				Uri uri = data.getData();
				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(
							this.getContentResolver(), uri);
					
					String[] filePathColumn = { MediaStore.Images.Media.DATA };

					final Cursor cursor = getContentResolver().query(uri,
							filePathColumn, null, null, null);
					cursor.moveToFirst();
					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String picturePath = cursor.getString(columnIndex);
					final File file = new File(picturePath);
					BitmapFactory.Options opts = new BitmapFactory.Options();
					if (file.length() < 20480) { // 0-20k
						opts.inSampleSize = 1;
					} else if (file.length() < 51200) { // 20-50k
						opts.inSampleSize = 2;
					} else if (file.length() < 307200) { // 50-300k
						opts.inSampleSize = 4;
					} else if (file.length() < 819200) { // 300-800k
						opts.inSampleSize = 6;
					} else if (file.length() < 1048576) { // 800-1024k
						opts.inSampleSize = 8;
					} else {
						opts.inSampleSize = 10;
					}
					System.out.println(file.length());
					if(file.length() > 5242880) {
						Toast.makeText(mContext, "图片不能大于5M,请压缩后上传", Toast.LENGTH_LONG);
						return;
					}
					Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
					mBmp = resizeBmp;
					mHeaderIcon.setImageBitmap(resizeBmp);
					new AsyncTask<File, Void, String>() {
						@Override
						protected String doInBackground(File... params) {
							File uploadFile = params[0];
							DateFormat f2 = new SimpleDateFormat("yyyyMMddHHmmss");
							String day = f2.format(new Date());
							int max = 10000;
							int min = 99999;
							Random random = new Random();
							int s = random.nextInt(max) % (max - min + 1) + min;
							final String serverImgName = day + s;
							try {
								UploadUtil.post(uploadFile, APIS.UPLOAD,
										serverImgName);
								mImgPath = "upload/" + serverImgName;
							} catch(Exception e) {
								e.printStackTrace();
							}
							cursor.close();
							return null;
						}
					}.execute(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
