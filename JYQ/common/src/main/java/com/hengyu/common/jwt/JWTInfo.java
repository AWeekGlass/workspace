package com.hengyu.common.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTInfo implements IJWTInfo {
	private Integer id;
	private Integer companyId;

	private String userName;

	private String companyName;

	@Override
	public Integer getUserId() {
		return id;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public Integer getCompanyId() {
		return companyId;
	}

	@Override
	public String getCompanyName() {
		return companyName;
	}
}
