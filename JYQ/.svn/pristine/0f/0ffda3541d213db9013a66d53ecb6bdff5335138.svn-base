package com.hengyu.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HonorVo {
	private Integer id;

	/**
	 * 荣誉称号
	 */
	private String name;

	/**
	 * 授予时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date grantTime;

	/**
	 * 授予单位
	 */
	private String grantCompany;

	/**
	 * 说明
	 */
	private String remark;
}
