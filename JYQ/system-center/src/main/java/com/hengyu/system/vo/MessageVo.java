package com.hengyu.system.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MessageVo {

	private Integer id;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	/**
	 * 消息状态 0未读 1已读
	 */
	private Integer status;

	/**
	 * 是否通过审核 0 未审核 1 通过 2 未通过
	 */
	private Integer state;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 种类名称
	 */
	private String typeName;

	/**
	 * 消息种类 1更换手机号 2加入企业
	 */
	private Integer type;

	/**
	 * 案例名称
	 */
	private String title;
}
