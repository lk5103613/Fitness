package com.example.sortlistview;

import java.util.Comparator;

import com.like.entity.StudentInfo;

public class StudentComparator implements Comparator<StudentInfo> {

	@Override
	public int compare(StudentInfo o1, StudentInfo o2) {
		if (o1.sortModel.getSortLetters().equals("@")
				|| o2.sortModel.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.sortModel.getSortLetters().equals("#")
				|| o2.sortModel.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.sortModel.getSortLetters().compareTo(o2.sortModel.getSortLetters());
		}
	}

}
