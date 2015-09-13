package com.like.network;

public class APIS {

	public final static String BASE_URL = "http://120.26.67.29";

	public final static String REG = BASE_URL
			+ "/index.php/CoachMgr/reg?mp=%1&pwd=%2&nickname=%3&avatar=%4";
	public final static String LOGIN = BASE_URL
			+ "/index.php/CoachMgr/loginFn?mp=%1&pwd=%2";
	public final static String UPLOAD = BASE_URL + "/yw_uploadify.php";
	public final static String UPDATE_ID_AUTH = BASE_URL
			+ "/index.php/coachMgr/updateIdCardAuth?coachid=%1&idcard=%2&idcarpath=%3";
	public final static String GET_CAT = BASE_URL
			+ "/index.php/home/categoryFn";
	public final static String UPDATE_COACH_INFO = BASE_URL
			+ "/index.php/coachMgr/updateCoachInfo?coachid=%1&avatar=%2&skill=%3&qm=%4&truename=%5&height=%6&weight=%7&gender=%8&work_experience=%9";
	public final static String GET_COACH_ORDER = BASE_URL
			+ "/index.php/CoachMgr/findOrderPageList?currentPage=%1&coachid=%2";
	public final static String GET_ALL_STUDENT = BASE_URL
			+ "/index.php/CoachMgr/findMyStudentList?coachid=%1";
	public final static String SAVE_COURSE = BASE_URL
			+ "/index.php/AppCourse/saveCourseFn?timeFlag=%1&course_name=%2&from_time=%3&to_time=%4&price=%5&detail_address=%6&tuikuan=%7&min_students=%8&flag=%9&baodi=%10&course_description=%11&hand_select_days=%12&week_days=%13&fromDay=%14&toDay=%15&coach_id=%16";
	public final static String GET_CLASSES = BASE_URL
			+ "/index.php/AppClass/findMyClassFn?coach_id=%1";
	public final static String GET_COACH_INFO = BASE_URL
			+ "/index.php/coach/detail?coachid=%1";
	public final static String GET_SHOURU = BASE_URL
			+ "/index.php/CoachMgr/shouru4WeekFn?coachId=%1";
	public final static String GET_COACH_INCOME = BASE_URL
			+ "/index.php/CoachMgr/findMyIncomeList?coachid=%1";
	public final static String UPDATE_PWD = BASE_URL
			+ "/index.php/CoachMgr/updatePwdFn?coachid=%1&oldPwd=%2&newPwd=%3";
	public final static String GET_VIDEO_LIST = BASE_URL
			+ "/index.php/CoachMgr/findMyVideoList?coachid=%1";
	public final static String ADD_STUDENT = BASE_URL
			+ "/index.php/CoachMgr/addStudent2ClassFn?class_id=%1&coach_id=%2&student_name=%3&mp=%4&memo=%5&class_name=%6";
}