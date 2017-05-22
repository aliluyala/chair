package com.chair.manager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chair.manager.bean.ResponseResult;

/**
 * 响应异常统一处理
 */
@ControllerAdvice(annotations = Controller.class)
public class ResponseExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseResult exceptionHandler(Exception e) {

		String errorCode = "0";
		String errorMessage = "响应成功";

		if (e instanceof ChairException) {
			errorCode = ((ChairException) e).getErrorCode();
			errorMessage = ((ChairException) e).getErrorMsg();
		}

		logger.info("请求异常: " + e);
		e.printStackTrace();

		return new ResponseResult(errorCode, errorMessage);
	}
}