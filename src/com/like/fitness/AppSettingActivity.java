package com.like.fitness;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.like.entity.CommonResult;
import com.like.network.GsonUtil;
import com.like.utils.DisplayUtil;

public class AppSettingActivity extends BaseActivity implements OnClickListener {
	private LinearLayout mChangePwd;
	private ImageView mDownArrow;
	private int mScreenWidth;
	private int mScreenHeight;

	private PopupWindow mChangePwdPop;
	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_setting);
		mActivity = this;

		// 获取屏幕和PopupWindow的width和height
		mScreenWidth = DisplayUtil.getInstance(this).getWidth();
		mScreenHeight = DisplayUtil.getInstance(this).getHeight();

		mChangePwd = (LinearLayout) findViewById(R.id.changePwd);
		mDownArrow = (ImageView) findViewById(R.id.down_arrow);

		mChangePwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.changePwd:
			showChangePwd(mDownArrow);
			break;
		default:
			break;
		}

	}

	private void showChangePwd(View parent) {
		int[] location = new int[2];
		parent.getLocationOnScreen(location);

		int popwidth = mScreenWidth / 6 * 4;
		if (mChangePwdPop == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.change_pwd_pop, null);
			final TextView txtOldPwd = (TextView) view.findViewById(R.id.old_pwd);
			final TextView txtNewPwd = (TextView) view.findViewById(R.id.new_pwd);
			final TextView txtRepeatPwd = (TextView) view.findViewById(R.id.repeat_pwd);
			view.findViewById(R.id.btn_change_pwd).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String oldPwd = txtOldPwd.getText().toString();
					String newPwd = txtNewPwd.getText().toString();
					String repeatPwd = txtRepeatPwd.getText().toString();
					if(TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(repeatPwd)) {
						Toast.makeText(mContext, "请输入完整信息", Toast.LENGTH_SHORT).show();
						return;
					}
					if(!newPwd.equals(repeatPwd)) {
						Toast.makeText(mContext, "两次密码不一致", Toast.LENGTH_SHORT).show();
						return;
					}
					mDataFetcher.fetchUpdatePwd(mLoginUser.coachid, oldPwd, newPwd, new Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
							if(result.code == 1) {
								Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
								txtOldPwd.setText("");
								txtNewPwd.setText("");
								txtRepeatPwd.setText("");
								mChangePwdPop.dismiss();
							}
						}
					}, mErrorListener);
				}
			});
			mChangePwdPop = new PopupWindow(view, popwidth, 650, true);
			mChangePwdPop
					.setOnDismissListener(new PopupWindow.OnDismissListener() {
						@Override
						public void onDismiss() {
							DisplayUtil.getInstance(mActivity).backgroundAlpha(
									mActivity, 1);
						}
					});
		}
		mChangePwdPop.setOutsideTouchable(true);
		mChangePwdPop.setBackgroundDrawable(new BitmapDrawable());
		DisplayUtil.getInstance(mActivity).backgroundAlpha(mActivity, 1);
		mChangePwdPop.showAtLocation(parent, Gravity.NO_GRAVITY, location[0]
				- popwidth + parent.getWidth()
				+ DisplayUtil.getInstance(this).dip2px(12), location[1]
				+ parent.getHeight() - 10);
	}

	public void back(View v) {
		this.finish();
	}

}
