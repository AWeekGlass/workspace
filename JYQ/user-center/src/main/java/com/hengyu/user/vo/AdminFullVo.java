package com.hengyu.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminFullVo {
	/**
	 * 公司名称
	 */
	private String companyName;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date createTime;

	/**
	 * 姓名
	 */
	private String userName;

	/**
	 * 性别（1:男；2:女）
	 */
	private String sex;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 出生日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date birthday;

	/**
	 * 婚姻状况（1:未婚；2:已婚）
	 */
	private String maritalStatus;

	/**
	 * 民族
	 */
	private String nationName;

	/**
	 * 政治面貌（1:党员；2:团员；3:群众）
	 */
	private String politicalName;

	/**
	 * 户籍详细地址
	 */
	private String birthplaceAddress;

	/**
	 * 毕业院校
	 */
	private String university;

	/**
	 * 学历
	 */
	private String educationName;

	/**
	 * 专业
	 */
	private String major;

	/**
	 * 联系电话
	 */
	private String telephone;

	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * QQ
	 */
	private String qq;

	/**
	 * 紧急联系人
	 */
	private String contactPhone;

	/**
	 * 工资银行卡号
	 */
	private String cardNo;

	/**
	 * 现居地址
	 */
	private String residenceAddress;

	/**
	 * 门店
	 */
	private String storeName;

	/**
	 * 角色
	 */
	private String roleName;
	
	/**
	 * 状态（1:试用；2:在职；3:离职；4:禁用）
	 */
	private String statusName;

	/**
	 * 入职日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date entryDate;

	/**
	 * 转正日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date regularDate;
	
	/**
	 * 离职日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date quitDate;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateTime;
}
