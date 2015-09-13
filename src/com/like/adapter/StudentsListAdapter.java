package com.like.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sortlistview.SortModel;
import com.like.entity.StudentInfo;
import com.like.fitness.R;

public class StudentsListAdapter extends BaseAdapter {
	
	private List<StudentInfo> mStudents;
	private LayoutInflater mInflater;
	private int[] mBgs = new int[]{
			R.drawable.name_bg_1,
			R.drawable.name_bg_2,
			R.drawable.name_bg_3,
			R.drawable.name_bg_4 };
	
	public StudentsListAdapter(Context context, List<StudentInfo> students) {
		this.mStudents = students;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mStudents.size();
	}

	@Override
	public Object getItem(int position) {
		return mStudents.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		StudentInfo student = mStudents.get(position);
		final SortModel content = student.sortModel;
		ViewHolder viewHolder;
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.students_item, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		int section = getSectionForPosition(position);
		if(position == getPositionForSection(section)){
			viewHolder.sTxtLetter.setVisibility(View.VISIBLE);
			viewHolder.sTxtLetter.setText(content.getSortLetters());
		}else{
			viewHolder.sTxtLetter.setVisibility(View.GONE);
		}
		viewHolder.sTxtCourseCount.setText(student.courseCnt+"节课程");
		viewHolder.sTxtName.setText(student.sortModel.getName());
		viewHolder.sTxtPhoneNum.setText(student.mp);
		viewHolder.sTxtSortName.setBackgroundResource(mBgs[position % 4]);
		viewHolder.sTxtSortName.setText(student.sortModel.getName().substring(0, 1));
		
		return convertView;
	}
	
	public void updateList(List<StudentInfo> students) {
		this.mStudents = students;
		notifyDataSetChanged();
	}
	
	public int getSectionForPosition(int position) {
		return mStudents.get(position).sortModel.getSortLetters().charAt(0);
	}
	
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}
	
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = mStudents.get(i).sortModel.getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	public class ViewHolder {
		public TextView sTxtSortName;
		public TextView sTxtName;
		public TextView sTxtPhoneNum;
		public TextView sTxtCourseCount;
		public TextView sTxtLetter;
		
		public ViewHolder(View convertView) {
			sTxtSortName = (TextView) convertView.findViewById(R.id.name_icon);
			sTxtName = (TextView) convertView.findViewById(R.id.student_name);
			sTxtPhoneNum = (TextView) convertView.findViewById(R.id.phone_num);
			sTxtCourseCount = (TextView) convertView.findViewById(R.id.course_remain);
			sTxtLetter = (TextView) convertView.findViewById(R.id.catalog);
		}
		
	}

}
