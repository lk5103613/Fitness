package com.like.fitness;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConsumerCodeActivity extends BaseActivity {
	
	private EditText mTxtCode;
	private Dialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumer_code);
		
		mTxtCode = (EditText) findViewById(R.id.txt_code);
	}
	
	public void numberClick(View v) {
		switch (v.getId()) {
		case R.id.btn_num_0:
			updateCodeNum("0");
			break;
		case R.id.btn_num_1:
			updateCodeNum("1");
			break;
		case R.id.btn_num_2:
			updateCodeNum("2");
			break;
		case R.id.btn_num_3:
			updateCodeNum("3");
			break;
		case R.id.btn_num_4:
			updateCodeNum("4");
			break;
		case R.id.btn_num_5:
			updateCodeNum("5");
			break;
		case R.id.btn_num_6:
			updateCodeNum("6");
			break;
		case R.id.btn_num_7:
			updateCodeNum("7");
			break;
		case R.id.btn_num_8:
			updateCodeNum("8");
			break;
		case R.id.btn_num_9:
			updateCodeNum("9");
			break;
		case R.id.btn_delete:
			updateCodeNum("del");
			break;

		default:
			break;
		}
	}
	
	private void updateCodeNum(String content) {
		String code = mTxtCode.getText().toString();
		if(content.equals("del")) {
			if(code.length() != 0)
				code = code.substring(0, code.length() - 1);
		} else {
			code = code + content;
		}
		mTxtCode.setText(code);
	}
	
	private void showDialog(boolean show) {
		if(show) {
			if(mDialog == null) {
				View dialogView = LayoutInflater.from(mContext).inflate(R.layout.consumer_code_dialog, null);
				mDialog = new Dialog(mContext, R.style.Theme_dialog);
				mDialog.setContentView(dialogView);
				Button btnOK = (Button) dialogView.findViewById(R.id.ok);
				btnOK.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mDialog.dismiss();
						Intent intent = new Intent(mContext, QRCodeActivity.class);
						startActivity(intent);
					}
				});
			}
			mDialog.show();
		} else {
			if(mDialog != null && mDialog.isShowing()) 
				mDialog.dismiss();
		}
	}
	
	public void getCode(View v) {
		showDialog(true);
	}
	
	public void back(View v) {
		this.finish();
	}

}
