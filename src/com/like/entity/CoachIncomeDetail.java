package com.like.entity;

import com.google.gson.annotations.SerializedName;

public class CoachIncomeDetail {

	public String id;
	@SerializedName("student_name")
	public String studentName;
	public String reason;
	public String money;
	@SerializedName("add_time")
	public String addTime;
	public String coachid;

}
