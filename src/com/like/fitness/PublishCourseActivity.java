package com.like.fitness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.like.customview.DateSelectPopWindow;
import com.like.customview.SelectWeekView;
import com.like.customview.SelectedRelativeLayout;
import com.like.customview.TimePopWindow;
import com.like.customview.TimePopWindow.IOnItemSelect;
import com.like.entity.CommonResult;
import com.like.entity.RegResult;
import com.like.network.DataFetcher;
import com.like.network.GsonUtil;
import com.like.utils.DateUtil;
import com.like.utils.DisplayUtil;

public class PublishCourseActivity extends BaseActivity implements OnClickListener,
		IOnItemSelect {

	// 按周计算
	private SelectedRelativeLayout mWeekTitleContainer;
	private TextView mWeekTitle;
	private TextView mWeekBeginHour;
	private TextView mWeekBeginMin;
	private TextView mWeekEndHour;
	private TextView mWeekEndMin;

	// 按年计算
	private SelectedRelativeLayout mYearTitleContainer;
	private TextView mYearTitle;
	private TextView mBeginYear;
	private TextView mBeginMonth;
	private TextView mBeginDay;
	private TextView mEndYear;
	private TextView mEndMonth;
	private TextView mEndDay;

	// 手动选择日期
	private SelectedRelativeLayout mManTitleContainer;
	private TextView mManTitle;
	private TextView mManBeginHour;
	private TextView mManBeginMin;
	private TextView mManEndHour;
	private TextView mManEndMin;

	private LinearLayout mPeopleCountContainer;
	private TextView mPeopleCount;

	private SelectWeekView mSelectWeekView;

	private ArrayList<Integer> hours = new ArrayList<Integer>();
	private ArrayList<Integer> mins = new ArrayList<Integer>();
	private ArrayList<Integer> years = new ArrayList<Integer>();
	private ArrayList<Integer> months = new ArrayList<Integer>();
	private ArrayList<Integer> days = new ArrayList<Integer>();
	private ArrayList<Integer> count = new ArrayList<Integer>();
	private ArrayList<Integer> gridDates = new ArrayList<Integer>();

	private TimePopWindow mWeekBeginHourPop, mWeekBeginMinPop, mWeekEndHourPop,
			mWeekEndMinPop;

	private TimePopWindow mBeginYearPop, mBeginMonthPop, mBeginDayPop,
			mEndYearPop, mEndMonthPop, mEndDayPop;

	private TimePopWindow mManBeginHourPop, mManBeginMinPop, mManEndHourPop,
			mManEndMinPop;

	private TimePopWindow mPeopleCountPop;

	private DateSelectPopWindow mManPop;


	private TextView mCount;
	private TextView mCauseName;
	private EditText mDescription;
	private TextView mPrice;
	private EditText mAddress;
	private RadioButton mTuiKuanSupport;
	private RadioButton mNoMinRadio;
	
	private String coachId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_publish_course);

		mCauseName = (TextView) findViewById(R.id.name);
		mDescription = (EditText) findViewById(R.id.description);
		mPrice = (TextView) findViewById(R.id.price);
		mAddress = (EditText) findViewById(R.id.location);
		mTuiKuanSupport = (RadioButton) findViewById(R.id.tuikuan_support);
		mNoMinRadio = (RadioButton) findViewById(R.id.no_min_radio);

		mWeekBeginHour = (TextView) findViewById(R.id.week_begin_hour);
		mWeekBeginMin = (TextView) findViewById(R.id.week_begin_minute);
		mWeekEndHour = (TextView) findViewById(R.id.week_end_hour);
		mWeekEndMin = (TextView) findViewById(R.id.week_end_minute);

		mBeginYear = (TextView) findViewById(R.id.year_begin_year);
		mBeginMonth = (TextView) findViewById(R.id.year_begin_month);
		mBeginDay = (TextView) findViewById(R.id.year_begin_day);

		mEndYear = (TextView) findViewById(R.id.year_end_year);
		mEndMonth = (TextView) findViewById(R.id.year_end_month);
		mEndDay = (TextView) findViewById(R.id.year_end_day);

		mManBeginHour = (TextView) findViewById(R.id.man_begin_hour);
		mManBeginMin = (TextView) findViewById(R.id.man_begin_minute);
		mManEndHour = (TextView) findViewById(R.id.man_end_hour);
		mManEndMin = (TextView) findViewById(R.id.man_end_minute);

		mPeopleCountContainer = (LinearLayout) findViewById(R.id.people_count_container);
		mPeopleCount = (TextView) findViewById(R.id.people_count);

		mManTitleContainer = (SelectedRelativeLayout) findViewById(R.id.man_title_container);
		mWeekTitleContainer = (SelectedRelativeLayout) findViewById(R.id.week_title_container);
		mYearTitleContainer = (SelectedRelativeLayout) findViewById(R.id.year_title_container);

		mWeekTitle = (TextView) findViewById(R.id.week_title);
		mYearTitle = (TextView) findViewById(R.id.year_title);
		mManTitle = (TextView) findViewById(R.id.man_title);

		mSelectWeekView = (SelectWeekView) findViewById(R.id.week_select);

		mCount = (TextView) findViewById(R.id.count);

		mWeekBeginHour.setOnClickListener(this);
		mWeekBeginMin.setOnClickListener(this);
		mWeekEndHour.setOnClickListener(this);
		mWeekEndMin.setOnClickListener(this);

		mBeginYear.setOnClickListener(this);
		mBeginMonth.setOnClickListener(this);
		mBeginDay.setOnClickListener(this);
		mEndDay.setOnClickListener(this);
		mEndMonth.setOnClickListener(this);
		mEndYear.setOnClickListener(this);

		mManBeginHour.setOnClickListener(this);
		mManBeginMin.setOnClickListener(this);
		mManEndHour.setOnClickListener(this);
		mManEndMin.setOnClickListener(this);

		mPeopleCountContainer.setOnClickListener(this);
		mManTitleContainer.setOnClickListener(this);
		mWeekTitleContainer.setOnClickListener(this);
		mYearTitleContainer.setOnClickListener(this);

		initData();
		coachId = mLoginUser.coachid;

	}

	private void initData() {
		for (int i = 0; i < 24; i++) {
			hours.add(i);
		}

		for (int i = 0; i < 61; i += 5) {
			mins.add(i);
		}

		for (int i = 0; i < 15; i++) {
			years.add(DateUtil.getYear() + i);
		}

		for (int i = 1; i <= 12; i++) {
			months.add(i);
		}
		for (int i = 0; i < 30; i++) {
			days.add(i);
		}
		for (int i = 0; i < 30; i += 5) {
			count.add(i);
		}

		for (int i = 1; i <= DateUtil.getMonthLastDay(DateUtil.getYear(),
				DateUtil.getMonth()); i++) {
			gridDates.add(i);
		}
		gridDates.add(0, 0);
		gridDates.add(0, 0);
		gridDates.add(0, 0);
		gridDates.add(0, 0);
	}

	public void back(View v) {
		finish();
	}

	private List<Integer> getDays(int daysCnt) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 1; i <= daysCnt; i++) {
			result.add(i);
		}
		return result;
	}

	@Override
	public void onClick(View v) {
		int year = 2015;
		int month = 1;
		switch (v.getId()) {
		case R.id.week_begin_hour:
			showPop(mWeekBeginHour, mWeekBeginHourPop, hours, "时");
			break;
		case R.id.week_begin_minute:
			showPop(mWeekBeginMin, mWeekBeginMinPop, mins, "分");
			break;
		case R.id.week_end_hour:
			showPop(mWeekEndHour, mWeekEndHourPop, hours, "时");
			break;
		case R.id.week_end_minute:
			showPop(mWeekEndMin, mWeekEndMinPop, mins, "分");
			break;
		case R.id.year_begin_year:
			showPop(mBeginYear, mBeginYearPop, years, "年");
			break;
		case R.id.year_begin_month:
			showPop(mBeginMonth, mBeginMonthPop, months, "月");
			break;
		case R.id.year_begin_day:
			year = Integer.valueOf(mBeginYear.getText().toString()
					.replace("年", ""));
			month = Integer.valueOf(mBeginMonth.getText().toString()
					.replace("月", ""));
			showPop(mBeginDay, mBeginDayPop,
					getDays(DateUtil.getMonthLastDay(year, month)), "日");
			break;
		case R.id.year_end_year:
			showPop(mEndYear, mEndYearPop, years, "年");
			break;
		case R.id.year_end_month:
			showPop(mEndMonth, mEndMonthPop, months, "月");
			break;
		case R.id.year_end_day:
			year = Integer.valueOf(mEndYear.getText().toString()
					.replace("年", ""));
			month = Integer.valueOf(mEndMonth.getText().toString()
					.replace("月", ""));
			showPop(mEndDay, mEndDayPop,
					getDays(DateUtil.getMonthLastDay(year, month)), "日");
			break;
		case R.id.man_begin_hour:
			showPop(mManBeginHour, mManBeginHourPop, hours, "时");
			break;
		case R.id.man_begin_minute:
			showPop(mManBeginMin, mManBeginMinPop, mins, "分");
			break;
		case R.id.man_end_hour:
			showPop(mManEndHour, mManEndHourPop, hours, "时");
			break;
		case R.id.man_end_minute:
			showPop(mManEndMin, mManEndMinPop, mins, "分");
			break;
		case R.id.people_count_container:
			if (mPeopleCountPop == null) {
				mPeopleCountPop = new TimePopWindow(mContext, mPeopleCount,
						count, "人");
			}
			mPeopleCountPop.setWidth(mPeopleCountContainer.getWidth());
			mPeopleCountPop.showAsDropDown(mPeopleCountContainer);
			break;
		case R.id.man_title_container:
			if (mManPop == null) {
				mManPop = new DateSelectPopWindow(mContext, gridDates);
			}
			mManPop.showAsDropDown(mManTitleContainer,
					(DisplayUtil.getInstance(mContext).getWidth() - mManPop
							.getWidth()) / 2, -25);
			mManTitleContainer.setSelected(true);
			mWeekTitleContainer.setSelected(false);
			mYearTitleContainer.setSelected(false);

			break;
		case R.id.week_title_container:
			mWeekTitleContainer.setSelected(true);
			mYearTitleContainer.setSelected(false);
			mManTitleContainer.setSelected(false);
			break;
		case R.id.year_title_container:
			mYearTitleContainer.setSelected(true);
			mWeekTitleContainer.setSelected(false);
			mManTitleContainer.setSelected(false);
		default:
			break;
		}

	}

	private void showPop(TextView targetView, PopupWindow popupWindow,
			List<Integer> datas, String unit) {
		if (popupWindow == null) {
			popupWindow = new TimePopWindow(mContext, targetView, datas, unit);
		}
		((TimePopWindow) popupWindow).updateDatas(datas);
		popupWindow.setWidth(targetView.getWidth());
		popupWindow.showAsDropDown(targetView);
	}

	@Override
	public void onItemSelect(int viewId, int position) {
		switch (viewId) {
		case R.id.year_begin_year:
			break;
		default:
			break;
		}
	}

	public void publish(View v) {
		String timeFlag = "0";
		String courseName = mCauseName.getText().toString();
		String fromTime = "";
		String toTime = "";
		String price = mPrice.getText().toString();
		String address = mAddress.getText().toString();
		String tuiKuan = mTuiKuanSupport.isChecked() ? "1" : "0";
		String min = mPeopleCountPop == null ? "5" : mPeopleCountPop
				.getCurrentValue() + "";
		String flag = mCount.getText().toString().contains("单") ? "1" : "2";
		String baodi = mNoMinRadio.isChecked() ? "0" : "1";
		String description = mDescription.getText().toString();
		String fromDay = "";
		String toDay = "";
		String selectDays = "";
		String weekDays = "";

		if (mYearTitleContainer.isSelected()) {
			timeFlag = "1";
			String yearBeginYear = mBeginYear.getText().toString()
					.replace("年", "");
			String yearBeginMonth = mBeginMonth.getText().toString()
					.replace("月", "");
			String yearBeginDay = mBeginDay.getText().toString()
					.replace("日", "");
			String yearEndYear = mEndYear.getText().toString().replace("年", "");
			String yearEndMonth = mEndMonth.getText().toString()
					.replace("月", "");
			String yearEndDay = mEndDay.getText().toString().replace("日", "");

			Date dateBegin = DateUtil.toDate(yearBeginDay, yearBeginMonth,
					yearBeginDay, "00", "00");
			Date dateEnd = DateUtil.toDate(yearEndDay, yearEndMonth,
					yearBeginDay, "00", "00");
			fromDay = DateUtil.formatDate(dateBegin);
			toDay = DateUtil.formatDate(dateEnd);

		} else if (mWeekTitleContainer.isSelected()) {
			timeFlag = "0";

			List<String> selectedValue = mSelectWeekView.getCheckedValue();
			String weekBeginHour = mWeekBeginHour.getText().toString()
					.replace("时", "");
			String weekBeginMin = mWeekBeginMin.getText().toString()
					.replace("分", "");
			String weekEndHour = mWeekEndHour.getText().toString()
					.replace("时", "");
			String weekEndMin = mWeekEndMin.getText().toString()
					.replace("分", "");
			fromTime = DateUtil.formatTime(weekBeginHour, weekBeginMin);
			toTime = DateUtil.formatTime(weekEndHour, weekEndMin);

			if (selectedValue.size() == 0) {
				Toast.makeText(mContext, "请选择周", Toast.LENGTH_SHORT).show();
				return;
			}
			weekDays = list2Str(selectedValue, ',');
		} else if (mManTitleContainer.isSelected()) {
			String manBeginHour = mManBeginHour.getText().toString()
					.replace("时", "");
			String manBeginMin = mManBeginMin.getText().toString()
					.replace("分", "");
			String manEndHour = mManEndHour.getText().toString()
					.replace("时", "");
			String manEndMin = mManEndMin.getText().toString().replace("分", "");

			fromTime = DateUtil.formatTime(manBeginHour, manBeginMin);
			toTime = DateUtil.formatTime(manEndHour, manEndMin);

			List<String> dates = mManPop.getDates();
			selectDays = list2Str(dates, ',');
			System.out.println("result " + selectDays);
		}

		mDataFetcher.fetchPublishCourse(timeFlag, courseName, fromTime, toTime,
				price, address, tuiKuan, min, flag, baodi, description,
				selectDays, weekDays, fromDay, toDay, coachId,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						System.out.println(response);
						CommonResult result = GsonUtil.gson.fromJson(response.toString(), CommonResult.class);
						if(result.code == 1) {
							PublishCourseActivity.this.finish();
							Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mContext, "发布失败", Toast.LENGTH_SHORT).show();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (error.getMessage() != null) {
							System.out.println();
						}
					}
				});
	}

	private String list2Str(List<String> list, char separator) {
		System.out.println("list " + list.size());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(separator);
		}
		String result = sb.toString().substring(0, sb.toString().length() - 1);

		return result;
	}

}
