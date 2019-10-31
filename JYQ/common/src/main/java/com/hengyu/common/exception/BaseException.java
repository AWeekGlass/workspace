package com.hengyu.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2753040657257074087L;

	private Integer status = 200;

	public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(int status, String message) {
		super(message);
		this.status = status;
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
}