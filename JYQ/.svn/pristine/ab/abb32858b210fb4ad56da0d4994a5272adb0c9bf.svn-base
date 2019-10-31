package com.hengyu.common.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import com.hengyu.common.enums.ExceptionEnum;
import com.hengyu.common.exception.ClientForbiddenException;
import com.hengyu.common.exception.UploadNotSupportTypeException;
import com.hengyu.common.msg.BaseResponse;
import com.hengyu.common.msg.RestResponse;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public BaseResponse missingServletRequestParameterException(MissingServletRequestParameterException exception) {
		log.error("操作异常:{}", exception);
		return new RestResponse<String>().rel(false).status(ExceptionEnum.MISSING_REQUEST_PARAMETER.getCode())
				.message(ExceptionEnum.MISSING_REQUEST_PARAMETER.getDescription());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public BaseResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
		log.error("操作异常:{}", exception);
		return new RestResponse<String>().rel(false).status(ExceptionEnum.METHOD_NOT_SUPPORTED.getCode())
				.message(ExceptionEnum.METHOD_NOT_SUPPORTED.getDescription());
	}

	@ExceptionHandler(BindException.class)
	public BaseResponse bindException(BindException e) {
		BindingResult bindingResult = e.getBindingResult();
		List<FieldError> errors = bindingResult.getFieldErrors();
		String errorMsg = errors.stream()
				.map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
				.collect(Collectors.joining(","));
		log.error("参数异常:{}", errorMsg);
		return new RestResponse<String>().rel(false).status(ExceptionEnum.ARGUMENT_NOT_VALID.getCode())
				.message(errorMsg);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public BaseResponse expiredJwtException(ExpiredJwtException exception) {
		log.error("操作异常:{}", exception);
		return new RestResponse<String>().rel(false).status(ExceptionEnum.EXPIRED_JWT_ERROR_CODE.getCode())
				.message(ExceptionEnum.EXPIRED_JWT_ERROR_CODE.getDescription());
	}

	@ExceptionHandler(ClientForbiddenException.class)
	public BaseResponse clientForbiddenException(ClientForbiddenException exception) {
		log.error("操作异常:{}", exception);
		return new RestResponse<String>().rel(false).status(ExceptionEnum.CLIENT_FORBIDDEN_ERROR_CODE.getCode())
				.message(ExceptionEnum.CLIENT_FORBIDDEN_ERROR_CODE.getDescription());
	}

	@ExceptionHandler(UploadNotSupportTypeException.class)
	public BaseResponse uploadNotSupportTypeException(UploadNotSupportTypeException exception) {
		log.error("操作异常:{}", exception);
		return new RestResponse<String>().rel(false).status(ExceptionEnum.FILE_NOT_SUPPORT_ERROR_CODE.getCode())
				.message(ExceptionEnum.FILE_NOT_SUPPORT_ERROR_CODE.getDescription());
	}

	@ExceptionHandler(MultipartException.class)
	public BaseResponse fileTooLargeException(MultipartException exception) {
		return new RestResponse<String>().rel(false).status(ExceptionEnum.FILE_TOO_LARGE_ERROR_CODE.getCode())
				.message(ExceptionEnum.FILE_TOO_LARGE_ERROR_CODE.getDescription());
	}

	@ExceptionHandler(Exception.class)
	public BaseResponse otherException(Exception exception) {
		log.error("其他异常:{}", exception);
		return new RestResponse<String>().rel(false).status(ExceptionEnum.OTHER_ERROR_CODE.getCode())
				.message(ExceptionEnum.OTHER_ERROR_CODE.getDescription());
	}
}
