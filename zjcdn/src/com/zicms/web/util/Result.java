package com.zicms.web.util;

import java.io.Serializable;


/**
 * Ajax请求Json响应结果模型.
 * 
 * @zxztech
 * @date 2012-10-16 上午9:57:59
 */
@SuppressWarnings("serial")
public class Result  implements Serializable {

	/**
	 * 成功
	 */
	public static final int SUCCESS = 1;
	/**
	 * 警告
	 */
	public static final int WARN = 2;
	/**
	 * 失败
	 */
	public static final int ERROR = 0;
	
	/**
	 * 成功消息
	 */
	public static final String SUCCESS_MSG = "操作成功！";
	/**
	 * 失败消息
	 */
	public static final String ERROR_MSG = "操作失败:发生未知异常！";

	/**
	 * 结果状态码(可自定义结果状态码) 1:操作成功 0:操作失败
	 */
	private int code;
	/**
	 * 响应结果描述
	 */
	private String msg;
	/**
	 * 验异步验证失败是显示信息
	 */
	private String info;
	/**
	 * 验异步验证时相应结果（y:表示成功，n:表示失败）
	 */
	private String status;
	/**
	 * 其它数据信息（比如跳转地址）
	 */
	private Object obj;

	public Result() {
		super();
	}

	/**
	 * 
	 * @param code
	 *            结果状态码(可自定义结果状态码或者使用内部静态变量 1:操作成功 0:操作失败 2:警告)
	 * @param msg
	 *            响应结果描述
	 * @param obj
	 *            其它数据信息（比如跳转地址）
	 */
	public Result(int code, String msg, Object obj) {
		super();
		this.code = code;
		this.msg = msg;
		this.obj = obj;
	}
	
	public Result(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	
	/**
	 * 
	 * @param info
	 *           
	 * @param status
	 *            
	 *
	 */
	public Result( String info,String status) {
		super();
		this.info = info;
		this.status = status;
	}

	/**
	 * 默认操作成功结果.
	 */
	public static Result successResult() {
		return new Result(SUCCESS, SUCCESS_MSG, null);
	}

	/**
	 * 默认操作失败结果.
	 */
	public static Result errorResult() {
		return new Result(ERROR, ERROR_MSG, null);
	}

	/**
	 * 结果状态码(可自定义结果状态码) 1:操作成功 0:操作失败
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 设置结果状态码(约定 1:操作成功 0:操作失败 2:警告)
	 * 
	 * @param code
	 *            结果状态码
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 响应结果描述
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 设置响应结果描述
	 * 
	 * @param msg
	 *            响应结果描述
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 其它数据信息（比如跳转地址）
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * 设置其它数据信息（比如跳转地址）
	 * 
	 * @param obj
	 *            其它数据信息（比如跳转地址）
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
	

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}