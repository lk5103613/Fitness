package com.like.entity;

import com.google.gson.annotations.SerializedName;

public class ClassInfo {

	@SerializedName("class_id")
	public String classId;
	public String classname;
	@SerializedName("coach_id")
	public String coachId;
}
