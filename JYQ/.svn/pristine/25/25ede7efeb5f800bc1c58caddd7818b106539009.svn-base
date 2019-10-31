package com.hengyu.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkExperienceVo {
	private Integer id;
	/**
	 * 员工姓名
	 */
	private String userName;

	/**
	 * 公司名称
	 */
	private String name;

	/**
	 * 公司性质ID
	 */
	private Integer natureId;

	/**
	 * 公司性质
	 */
	private String natureName;

	/**
	 * 入职日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date entryDate;

	/**
	 * 离职日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date dimissionDate;

	/**
	 * 职位
	 */
	private String position;

	/**
	 * 情况说明
	 */
	private String illustration;

	/**
	 * 操作人员
	 */
	private String createName;
}
