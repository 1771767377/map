package com.zicms.web.util;

public class SysConfigKey {
	
	/**
	 * file save root path,value store in the sys_config table
	 */
	public static String FILE_SAVE_ROOT_PATH = "FILE_SAVE_ROOT_PATH";
	public static String ATTACH_SIZE_LIMIT = "ATTACH_SIZE_LIMIT";
	
	//登陆配置
	public final static String LOGIN_ERROR_COUNT = "LOGIN_ERROR_COUNT";
	public final static String LOGIN_UNLOCK_TIME = "LOGIN_UNLOCK_TIME";		
	public final static String PWD_FIRST_LOGIN_MOD = "PWD_FIRST_LOGIN_MOD";		
	public final static String PWD_NEXT_MOD_TIME = "PWD_NEXT_MOD_TIME";			
	public final static String LOGIN_CAPTCHA_ON	 = "LOGIN_CAPTCHA_ON";				
	public final static String PWD_STRONG_VERIFCATION = "PWD_STRONG_VERIFCATION";
	
	// 班级名称生成规则
	public static final String CLASS_NAME_RULE = "CLASS_NAME_RULE";
	// 班级名称开始序号
	public static final String CLASS_NAME_RULE_START_NO = "CLASS_NAME_RULE_START_NO";
	// 班级名称序号生成方式
	public static final String CLASS_NAME_RULE_WAY = "CLASS_NAME_RULE_WAY";
	// 班级名称生成规则是否可用
	public static final String CLASS_NAME_RULE_STATUS = "CLASS_NAME_RULE_STATUS";
	// 写评语状态
	public static final String STUDENT_WIRTE_STATUS = "STUDENT_WIRTE_STATUS";
	// 写评语时能返回到前几年
	public static final String STUDENT_WIRTE_SEMESTER = "STUDENT_WIRTE_SEMESTER";
	// 课程安排可以返回到前几学期
	public static final String COURSE_SET_SEMESTER = "COURSE.SET.SEMESTER";
	// 成绩录入安排可以返回到前几学期
	public static final String SCORE_SET_SEMESTER = "SCORE_SET_SEMESTER";

	
}
