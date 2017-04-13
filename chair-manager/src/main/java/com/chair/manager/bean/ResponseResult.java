package com.chair.manager.bean;

import java.util.List;
/**
 * 接口请求响应结果定义
 * @author good-zhiwei
 *
 */
public class ResponseResult {

	private static final String code="0";
	private static final String msg="响应成功";
	/**
	 * errorCode默认0
	 */
	private String  errorCode=code;
	/**
	 * errorMsg默认响应成功
	 */
	private String errorMsg=msg;
	/**
	 * 返回的数据
	 */
	private Object data;

	public ResponseResult(String errorCode, String errorMsg, Object data) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.data = data;
	}

	public ResponseResult(String errorCode, Object data) {
		super();
		this.errorCode = errorCode;
		this.data = data;
	}
	public ResponseResult(Object data) {
		super();
		this.data = data;
	}
	public ResponseResult() {
		super();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
