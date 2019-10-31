package com.hengyu.common.msg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> extends BaseResponse {
	private T data;

	private boolean rel;

	public RestResponse<T> status(int status) {
		this.setStatus(status);
		return this;
	}

	public RestResponse<T> rel(boolean rel) {
		this.setRel(rel);
		return this;
	}

	public RestResponse<T> message(String message) {
		this.setMessage(message);
		return this;
	}

	public RestResponse<T> data(T data) {
		this.setData(data);
		return this;
	}
}
