package com.like.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CoachInfo {

	public int order_id;
	public String order_no;
	public String username;
	public int status;
	public int uid;
	public int course_duration;
	public int category_id;
	public String course_name;
	public int course_id;
	public String money;
	public int coachid;
	public String avatar;
	@SerializedName("work_experience")
	public String workExp;
	public String truename;
	public String weight;
	public String mp;
	public String pwd;
	public String gender;
	public String nickname;
	public String height;
	public List<String> tag;
	public String idcard;
	public String score;
	@SerializedName("click_cnt")
	public String clickCnt;
	public String skill;
	public String lng;
	public String lat;
	@SerializedName("add_time")
	public String addTime;
	@SerializedName("coach_description")
	public String coachDescription;
	@SerializedName("self_evaluate")
	public String selfEvaluate;
	public String address;
	public String qm;
	public String card;
	public String path;
	@SerializedName("courList")
	public List<Cour> courlist;
	@SerializedName("commentList")
	public List<Comment> commentlist;
	@SerializedName("skillList")
	public List<Skill> skilllist;

	public class Cour {
		@SerializedName("course_id")
		private String courseId;
		@SerializedName("course_name")
		private String courseName;
		private String price;
		@SerializedName("coach_id")
		private String coachId;
		@SerializedName("add_time")
		private String addTime;
		private String cnt;
		@SerializedName("from_time")
		private String fromTime;
		@SerializedName("to_time")
		private String toTime;
		private String flag;
		@SerializedName("course_description")
		private String courseDescription;
		@SerializedName("category_id")
		private String categoryId;
		private String area;
		@SerializedName("course_duration")
		private String courseDuration;
		@SerializedName("fromDay")
		private String fromday;
		@SerializedName("toDay")
		private String today;
		@SerializedName("detail_address")
		private String detailAddress;
		private String tuikuan;
		@SerializedName("min_students")
		private String minStudents;
		@SerializedName("timeFlag")
		private String timeflag;
		@SerializedName("week_days")
		private String weekDays;
		@SerializedName("hand_select_days")
		private String handSelectDays;
	}

	public class Comment {
		public String avatar;
		public String nickname;
		public String content;
		public String img0;
		public String img1;
		public String img2;
		public String img3;
		public String img4;
	}

	public class Skill {
		public String catname;
	}

}
