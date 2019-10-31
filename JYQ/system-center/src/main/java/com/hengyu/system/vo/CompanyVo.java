package com.hengyu.system.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyVo {
	private Integer id;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 公司名称
	 */
	private String name;
	
	/**
	 * 管理员
	 */
	private String userName;

	/**
	 * 职能介绍
	 */
	private String introduction;

	/**
	 * 联系电话
	 */
	private String telephone;

	/**
	 * 签租日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date signDate;

	/**
	 * 到期日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expireDate;

	/**
	 * 机构地址
	 */
	private String organizationAddress;

	/**
	 * 租金
	 */
	private Double rent;

	/**
	 * 租金类型
	 */
	private Integer rentType;

	/**
	 * 付费方式ID
	 */
	private Integer payTypeId;

	/**
	 * 备注
	 */
	private String remark;
}
