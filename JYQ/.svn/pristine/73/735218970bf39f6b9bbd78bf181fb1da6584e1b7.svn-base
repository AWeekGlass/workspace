package com.hengyu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum ExceptionEnum {
	CLIENT_FORBIDDEN_ERROR_CODE(40001, "非法请求"), 
	FILE_NOT_SUPPORT_ERROR_CODE(40002, "不支持上传文件类型"),
	FILE_TOO_LARGE_ERROR_CODE(40003, "文件太大"), 
	FILE_UPLOAD_ERROR_CODE(40004, "上传文件失败"),
	NOT_LOGIN_ERROR_CODE(40005, "未登录"), 
	EXPIRED_JWT_ERROR_CODE(40006, "登录超时"), 
	ARGUMENT_NOT_VALID(40007, "参数不合法"),
	MISSING_REQUEST_PARAMETER(40008, "请求参数不合法"), 
	METHOD_NOT_SUPPORTED(40009, "请求方法不支持"), 
	OTHER_ERROR_CODE(500, "您的操作有误，请联系管理员");

	@Getter
	@Setter
	private Integer code;

	@Getter
	@Setter
	private String description;
}