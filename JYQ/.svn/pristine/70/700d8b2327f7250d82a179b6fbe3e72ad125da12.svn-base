package com.hengyu.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MyInfoVo {
	
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String name;
	
	/**
	 * 联系电话
	 */
	private String telephone;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 身份证号
	 */
	private String idCard;
	
	/**
	 * 实名认证 0 未认证 1 认证
	 */
	private Integer authentication;
	
	/**
	 * 公司id
	 */
	private Integer companyId;
	
	/**
	 * 公司名称
	 */
	private String companyName;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 微信号
	 */
	private String wxNo;
	
	/**
	 * 员工的头像地址
	 */
	private String headPortrait;
	
}
