package com.like.fitness;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.like.network.APIS;
import com.like.network.DataFetcher;
import com.like.utils.GlobalData;
import com.like.utils.UploadUtil;

public class CertificationActivity extends BaseActivity {

	private static final int REQUEST_TAKE_PHOTO = 1;
	private static final int REQUEST_FROM_FILE = 2;

	private ImageView mIdImg;
	private String mImgPath;
	private EditText mTxtIDCard;
	private Dialog mDialog;
	
	private DataFetcher mDataFetcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_certification);

		mIdImg = (ImageView) findViewById(R.id.id_img);
		mDataFetcher = DataFetcher.getInstance(mContext);
		mTxtIDCard = (EditText) findViewById(R.id.txt_idcard);
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

	public void upload(View v) {
		showDialog();
	}
	
	public void auth(View v) {
		String idcard = mTxtIDCard.getText().toString();
		if(TextUtils.isEmpty(mImgPath)) {
			Toast.makeText(mContext, "请上传图片", Toast.LENGTH_LONG).show();
			return;
		}
		if(TextUtils.isEmpty(idcard)) {
			Toast.makeText(mContext, "请输入身份证号码", Toast.LENGTH_LONG).show();
			return;
		}
		if(!Pattern .matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$", idcard)) {
			Toast.makeText(mContext, "请输入正确的身份证", Toast.LENGTH_LONG).show();
			return;
		}
		mDataFetcher.fetchAuthData(""+GlobalData.coachId, idcard, mImgPath, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Toast.makeText(mContext, "提交成功，请等待审核", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(mContext, LoginActivity.class);
				startActivity(intent);
				CertificationActivity.this.finish();
			}
		}, null);
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
					System.out.println(file.length());
					if(file.length() > 5242880) {
						Toast.makeText(mContext, "图片不能大于5M,请压缩后上传", Toast.LENGTH_LONG);
						return;
					}
					Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
					mIdImg.setImageBitmap(resizeBmp);
					
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
					mIdImg.setImageBitmap(resizeBmp);
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
