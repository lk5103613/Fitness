package com.like.adapter;

import java.util.Date;
import java.util.List;

import org.apache.http.client.cache.HeaderConstants;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.like.entity.CoachIncomeDetail;
import com.like.fitness.R;
import com.like.utils.DateUtil;

public class ProfitLostAdapter extends SimpleAdapter<CoachIncomeDetail> {
	private int[] mBgs = new int[] { R.drawable.name_bg_1,
			R.drawable.name_bg_2, R.drawable.name_bg_3, R.drawable.name_bg_4,
			R.drawable.name_bg_5, R.drawable.name_bg_6 };

	public ProfitLostAdapter(Context context, List<CoachIncomeDetail> datas) {
		super(context, datas);
	}

	@Override
	public int getItemResourceId() {
		return R.layout.profit_lost_list_item;
	}

	@Override
	public void bindData(int position, View convertView, ViewHolder holder) {
		CoachIncomeDetail detail = datas.get(position);
		TextView nameIcon = holder.findView(R.id.name_icon);
		TextView date = holder.findView(R.id.date);
		TextView dayOfWeek = holder.findView(R.id.day_of_week);
		TextView studentName = holder.findView(R.id.student_name);
		TextView reason = holder.findView(R.id.reason);
		TextView price = holder.findView(R.id.price);
		
		ViewGroup headContainer = holder.findView(R.id.time_container);
		nameIcon.setText(detail.studentName.substring(0, 1));
		nameIcon.setBackgroundResource(mBgs[position % 4]);
		studentName.setText(detail.studentName);
		reason.setText(detail.reason);
		price.setText("￥" + detail.money);
		
		int firstPosition = getFirstPosition(position);
		if(firstPosition == position) {
			headContainer.setVisibility(View.VISIBLE);
			date.setText(DateUtil.toString(DateUtil.toDate(detail.addTime, "yyyy-MM-dd"), "yyyy.MM.dd"));
			dayOfWeek.setText(DateUtil.getDayOfWeekCN(DateUtil.toDate(detail.addTime, "yyyy-MM-dd")));
		} else {
			headContainer.setVisibility(View.GONE);
		}
	}
	
	private int getFirstPosition(int position) {
		CoachIncomeDetail detail = datas.get(position);
		Date date = DateUtil.toDate(detail.addTime, "yyyy-MM-dd hh:mm:ss");
		for(int i=0; i<datas.size(); i++) {
			if(DateUtil.isSameDay(detail.addTime, datas.get(i).addTime)) {
				return i;
			}
		}
		return -1;
	}

}
