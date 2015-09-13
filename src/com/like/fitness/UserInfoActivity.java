package com.like.fitness;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.like.customview.RoundImageView;
import com.like.entity.Category;
import com.like.entity.RegResult;
import com.like.network.APIS;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.like.utils.GlobalData;
import com.like.utils.UploadUtil;

public class UserInfoActivity extends BaseActivity {
	
	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_FROM_FILE = 2;
	
	private EditText mLblName;
	private RadioButton mRadio1;
	private RadioButton mRadio2;
	private EditText mLblQM;
	private EditText mLblHeight;
	private EditText mLblWeight;
	private List<String> mSkills;
	private RoundImageView mImg;
	
	private DataFetcher mDataFetcher;
	private Bitmap mBmp;
	
	private ViewGroup mSkillsContainer;
	private ViewGroup mSkillsContainer2;
	private ViewGroup mSkillsContainer3;
	private String mImgPath;
	private Dialog mDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		
		Intent intent = getIntent();
		if(intent != null) {
			mBmp = intent.getParcelableExtra("icon");
		}
		mImg = (RoundImageView) findViewById(R.id.user_icon);
		if(mBmp != null) {
			mImg.setImageBitmap(mBmp);
		}
		mLblName = (EditText) findViewById(R.id.name);
		mRadio1 = (RadioButton) findViewById(R.id.radioButton1);
		mRadio2= (RadioButton) findViewById(R.id.radioButton2);
		mLblQM = (EditText) findViewById(R.id.qm);
		mLblHeight = (EditText) findViewById(R.id.height);
		mLblWeight = (EditText) findViewById(R.id.weight);
		mSkillsContainer = (ViewGroup) findViewById(R.id.itemlayout1);
		mSkillsContainer2 = (ViewGroup) findViewById(R.id.itemlayout2);
		mSkillsContainer3 = (ViewGroup) findViewById(R.id.itemlayout3);
		mSkills = new ArrayList<>();
		
		mDataFetcher = DataFetcher.getInstance(mContext);
		mDataFetcher.fetchCat(new Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Type type = new TypeToken<List<Category>>(){}.getType();
				List<Category> categories = GsonUtil.gson.fromJson(response.toString(), type);
				int i=0; 
				for(final Category cat : categories) {
					final TextView textView = new TextView(mContext);
					textView.setBackgroundResource(R.drawable.check_bg);
					textView.setGravity(Gravity.CENTER);
					textView.setText(cat.catname);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 
							LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
					params.setMargins(10, 10, 10, 10);
					textView.setLayoutParams(params);
					textView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if(!v.isSelected()) {
								v.setSelected(true);
								textView.setTextColor(0xffffffff);
								mSkills.add(cat.catid + "");
							} else {
								v.setSelected(false);
								textView.setTextColor(0xff959595);
								mSkills.remove(cat.catid +"");
							}
						}
					});
					if(i < 4) 
						mSkillsContainer.addView(textView);
					else if(i < 8)
						mSkillsContainer2.addView(textView);
					else
						mSkillsContainer3.addView(textView);
					i++;
				}
			}
		}, null);
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

	
	public void next(View v) {
		String qm = mLblQM.getText().toString();
		String height = mLblHeight.getText().toString();
		String weight = mLblWeight.getText().toString();
		String nickname = mLblName.getText().toString();
		if(TextUtils.isEmpty(qm) || TextUtils.isEmpty(height) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(nickname)) {
			Toast.makeText(mContext, "请完成填写", Toast.LENGTH_LONG).show();
			return;
		}
		if(TextUtils.isEmpty(mImgPath) && mImg == null) {
			Toast.makeText(mContext, "请上传头像", Toast.LENGTH_LONG).show();
			return;
		}
		if(TextUtils.isEmpty(mImgPath)) 
			mImgPath = "";
		String gender = "1";
		if(mRadio1.isChecked()) {
			gender = "1";
		} else {
			gender = "2";
		}
		String skillsStr = "";
		for(int i=0; i<mSkills.size(); i++) {
			if(i == mSkills.size() - 1)
				skillsStr += mSkills.get(i);
			else
				skillsStr += mSkills.get(i) + ",";
		}
		if(GlobalData.coachId == null)
			GlobalData.coachId = "1";
		mDataFetcher.fetchUpdateCoachInfo(GlobalData.coachId, mImgPath, skillsStr, qm, "", nickname,
				height, weight, gender, new Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						System.out.println(response);
						RegResult result = GsonUtil.gson.fromJson(response.toString(), RegResult.class);
						if(result.code != 1) {
							Toast.makeText(mContext, "修改失败，请重试", Toast.LENGTH_LONG);
							return;
						} else {
							Intent intent = new Intent(mContext, CertificationActivity.class);
							startActivity(intent);
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
//						Intent intent = new Intent(mContext, CertificationActivity.class);
//						startActivity(intent);
						System.out.println(error.getMessage());
					}
				});
	}
	
	public void selectPhoto(View v) {
		showDialog();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
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
					if(file.length() > 5242880) {
						Toast.makeText(mContext, "图片不能大于5M,请压缩后上传", Toast.LENGTH_LONG).show();
						return;
					}
					Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
					mImg.setImageBitmap(resizeBmp);
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
								mImgPath = UploadUtil.post(uploadFile, APIS.UPLOAD,
										serverImgName);
								mImgPath = "upload/" + serverImgName;
							} catch (Exception e) {
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
		case REQUEST_TAKE_PHOTO:
			if (data != null) {
				Uri uri;
//				Uri uri = data.getData();
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
					mImg.setImageBitmap(resizeBmp);
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
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void back(View v) {
		this.finish();
	}

}
