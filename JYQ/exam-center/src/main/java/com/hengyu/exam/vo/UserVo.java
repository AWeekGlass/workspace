package com.hengyu.exam.vo;

import lombok.Data;

@Data
public class UserVo {

	/**
	 * 人员id
	 */
	private Integer id;
	
	/**
	 * 人员姓名
	 */
	private String userName;
	
	/**
	 * 0-未批阅；1-已批阅
	 */
	private Integer isCheck;
	
	/**
	 * 微信openId
	 */
	private String wxOpenId;
	
	/**
	 * 微信openId唯一
	 */
	private String wxNo;
}
