package com.hengyu.common.exception;

import org.springframework.util.StringUtils;

import com.hengyu.common.enums.ExceptionEnum;

public class ClientForbiddenException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6540842411094277988L;

	public ClientForbiddenException(String message) {
		super(ExceptionEnum.CLIENT_FORBIDDEN_ERROR_CODE.getCode(),
				StringUtils.isEmpty(message) ? ExceptionEnum.CLIENT_FORBIDDEN_ERROR_CODE.getDescription() : message);
	}
}
